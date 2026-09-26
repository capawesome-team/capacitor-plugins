import Foundation
import Capacitor
import IntuneMAMSwift
import MSAL

// swiftlint:disable:next type_body_length
@objc public class Intune: NSObject {
    private static let pendingWipeAccountIdsKey = "capawesome_capacitor_intune_pending_wipe_account_ids"
    private static let pendingWipeNilAccountId = ""

    private var msalApplication: MSALPublicClientApplication?
    private let plugin: IntunePlugin
    private var remediateComplianceCompletions: [String: [(_ result: RemediateComplianceResult?, _ error: Error?) -> Void]] = [:]
    private let remediateComplianceCompletionsLock = NSLock()

    init(plugin: IntunePlugin) {
        self.plugin = plugin
        super.init()
        IntuneMAMComplianceManager.instance().delegate = self
        IntuneMAMEnrollmentManager.instance().delegate = self
        IntuneMAMPolicyManager.instance().delegate = self
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleAppConfigDidChange(_:)),
            name: NSNotification.Name.IntuneMAMAppConfigDidChange,
            object: nil
        )
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handlePolicyDidChange(_:)),
            name: NSNotification.Name.IntuneMAMPolicyDidChange,
            object: nil
        )
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleOpenUrl(_:)),
            name: Notification.Name.capacitorOpenURL,
            object: nil
        )
        deliverPendingWipeRequestedEvents()
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    @objc public func acquireToken(_ options: AcquireTokenOptions, completion: @escaping (_ result: AcquireTokenResult?, _ error: Error?) -> Void) {
        DispatchQueue.main.async {
            do {
                let application = try self.getMsalApplication()
                guard let viewController = self.plugin.bridge?.viewController else {
                    throw CustomError.tokenAcquisitionFailed(message: "The view controller is not available.")
                }
                let webviewParameters = MSALWebviewParameters(authPresentationViewController: viewController)
                let parameters = MSALInteractiveTokenParameters(scopes: options.scopes, webviewParameters: webviewParameters)
                if let loginHint = options.loginHint {
                    parameters.loginHint = loginHint
                }
                if options.forcePrompt {
                    parameters.promptType = .login
                }
                application.acquireToken(with: parameters) { result, error in
                    if let error = error {
                        completion(nil, self.createTokenAcquisitionError(error))
                        return
                    }
                    guard let result = result else {
                        completion(nil, CustomError.tokenAcquisitionFailed(message: IntunePlugin.errorUnknownError))
                        return
                    }
                    self.createAcquireTokenResult(result, completion: completion)
                }
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func acquireTokenSilent(_ options: AcquireTokenSilentOptions, completion: @escaping (_ result: AcquireTokenResult?, _ error: Error?) -> Void) {
        do {
            let application = try getMsalApplication()
            guard let account = findAccount(in: application, accountId: options.accountId) else {
                completion(nil, CustomError.notEnrolled)
                return
            }
            let parameters = MSALSilentTokenParameters(scopes: options.scopes, account: account)
            parameters.forceRefresh = options.forceRefresh
            application.acquireTokenSilent(with: parameters) { result, error in
                if let error = error {
                    completion(nil, self.createTokenAcquisitionError(error))
                    return
                }
                guard let result = result else {
                    completion(nil, CustomError.tokenAcquisitionFailed(message: IntunePlugin.errorUnknownError))
                    return
                }
                self.createAcquireTokenResult(result, completion: completion)
            }
        } catch {
            completion(nil, error)
        }
    }

    @objc public func decryptFile(_ options: DecryptFileOptions, completion: @escaping (_ error: Error?) -> Void) {
        do {
            let path = options.path
            let destination = options.destination ?? path
            if (destination as NSString).standardizingPath != (path as NSString).standardizingPath {
                try copyFile(atPath: path, toPath: destination)
            }
            try IntuneMAMFile.decryptFile(atPath: destination)
            completion(nil)
        } catch {
            completion(error)
        }
    }

    @objc public func getAppConfig(_ options: GetAppConfigOptions, completion: @escaping (_ result: GetAppConfigResult?, _ error: Error?) -> Void) {
        let appConfig = IntuneMAMAppConfigManager.instance().appConfig(forAccountId: options.accountId)
        var config: [String: [String]] = [:]
        for data in appConfig.fullData ?? [] {
            for (key, value) in data {
                guard let key = key as? String, key != "__IsDefault" else {
                    continue
                }
                let stringValue = value as? String ?? "\(value)"
                var values = config[key] ?? []
                if !values.contains(stringValue) {
                    values.append(stringValue)
                }
                config[key] = values
            }
        }
        completion(GetAppConfigResult(config: config), nil)
    }

    @objc public func getEnrolledAccount(completion: @escaping (_ result: GetEnrolledAccountResult?, _ error: Error?) -> Void) {
        let accountId = IntuneMAMEnrollmentManager.instance().enrolledAccountId()
        var username: String?
        if let accountId = accountId, let application = try? getMsalApplication() {
            username = findAccount(in: application, accountId: accountId)?.username
        }
        completion(GetEnrolledAccountResult(accountId: accountId, username: username), nil)
    }

    @objc public func getPolicy(_ options: GetPolicyOptions, completion: @escaping (_ result: GetPolicyResult?, _ error: Error?) -> Void) {
        let policy = IntuneMAMPolicyManager.instance().policy(forAccountId: options.accountId)
        let result = GetPolicyResult(
            contactSyncAllowed: policy?.isContactSyncAllowed ?? true,
            fileEncryptionRequired: policy?.isFileEncryptionRequired ?? false,
            managedBrowserRequired: policy?.isManagedBrowserRequired ?? false,
            pinRequired: policy?.isPINRequired ?? false,
            saveToPersonalStorageAllowed: policy?.isSaveToAllowed(for: .localDrive, withAccountId: options.accountId) ?? true,
            screenCaptureAllowed: policy?.isScreenCaptureAllowed ?? true
        )
        completion(result, nil)
    }

    @objc public func getSdkVersion(completion: @escaping (_ result: GetSdkVersionResult?, _ error: Error?) -> Void) {
        let result = GetSdkVersionResult(
            intuneSdkVersion: IntuneMAMVersionInfo.sdkVersion(),
            msalVersion: MSALPublicClientApplication.sdkVersion
        )
        completion(result, nil)
    }

    @objc public func isFileEncrypted(_ options: IsFileEncryptedOptions, completion: @escaping (_ result: IsFileEncryptedResult?, _ error: Error?) -> Void) {
        let encrypted = IntuneMAMFile.isFileEncrypted(atPath: options.path)
        completion(IsFileEncryptedResult(encrypted: encrypted), nil)
    }

    @objc public func loginAndEnrollAccount(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            IntuneMAMEnrollmentManager.instance().loginAndEnrollAccount(nil)
            completion(nil)
        }
    }

    @objc public func protectFile(_ options: ProtectFileOptions, completion: @escaping (_ error: Error?) -> Void) {
        do {
            try protectPath(options.path, accountId: options.accountId)
            completion(nil)
        } catch {
            completion(error)
        }
    }

    @objc public func registerAndEnrollAccount(_ options: RegisterAndEnrollAccountOptions, completion: @escaping (_ error: Error?) -> Void) {
        IntuneMAMEnrollmentManager.instance().registerAndEnrollAccountId(options.accountId)
        completion(nil)
    }

    @objc public func remediateCompliance(_ options: RemediateComplianceOptions, completion: @escaping (_ result: RemediateComplianceResult?, _ error: Error?) -> Void) {
        // The completion must be added before the remediation starts, because the result may be reported immediately.
        addRemediateComplianceCompletion(completion, forAccountId: options.accountId)
        DispatchQueue.main.async {
            IntuneMAMComplianceManager.instance().remediateCompliance(forAccountId: options.accountId, silent: options.silent)
        }
    }

    @objc public func showDiagnosticConsole(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            IntuneMAMDiagnosticConsole.display()
            completion(nil)
        }
    }

    @objc public func unenrollAccount(_ options: UnenrollAccountOptions, completion: @escaping (_ error: Error?) -> Void) {
        IntuneMAMEnrollmentManager.instance().deRegisterAndUnenrollAccountId(options.accountId, withWipe: options.wipe)
        completion(nil)
    }

    private func addRemediateComplianceCompletion(
        _ completion: @escaping (_ result: RemediateComplianceResult?, _ error: Error?) -> Void,
        forAccountId accountId: String
    ) {
        remediateComplianceCompletionsLock.lock()
        defer { remediateComplianceCompletionsLock.unlock() }
        remediateComplianceCompletions[accountId.lowercased(), default: []].append(completion)
    }

    private func copyFile(atPath sourcePath: String, toPath destinationPath: String) throws {
        let fileManager = FileManager.default
        if fileManager.fileExists(atPath: destinationPath) {
            try fileManager.removeItem(atPath: destinationPath)
        }
        try fileManager.copyItem(atPath: sourcePath, toPath: destinationPath)
    }

    private func createAcquireTokenResult(_ result: MSALResult, completion: @escaping (_ result: AcquireTokenResult?, _ error: Error?) -> Void) {
        guard let accountId = result.account.homeAccountId?.objectId ?? result.tenantProfile.identifier else {
            completion(nil, CustomError.tokenAcquisitionFailed(message: "The accountId could not be determined."))
            return
        }
        let acquireTokenResult = AcquireTokenResult(
            accessToken: result.accessToken,
            accountId: accountId,
            idToken: result.idToken,
            tenantId: result.tenantProfile.tenantId ?? result.account.homeAccountId?.tenantId,
            username: result.account.username
        )
        completion(acquireTokenResult, nil)
    }

    private func createProtectionPolicyRequiredError(_ error: NSError) -> CustomError? {
        guard let homeAccountId = error.userInfo[MSALHomeAccountIdKey] as? String else {
            return nil
        }
        // The home account ID has the format `<objectId>.<tenantId>`.
        let homeAccountIdParts = homeAccountId.split(separator: ".").map(String.init)
        guard let accountId = homeAccountIdParts.first else {
            return nil
        }
        return CustomError.protectionPolicyRequired(
            accountId: accountId,
            tenantId: homeAccountIdParts.count > 1 ? homeAccountIdParts[1] : nil,
            username: error.userInfo[MSALDisplayableUserIdKey] as? String
        )
    }

    private func createTokenAcquisitionError(_ error: Error) -> CustomError {
        let nsError = error as NSError
        if nsError.domain == MSALErrorDomain && nsError.code == MSALError.userCanceled.rawValue {
            return CustomError.interactionCanceled
        }
        if nsError.domain == MSALErrorDomain && nsError.code == MSALError.serverProtectionPoliciesRequired.rawValue,
           let protectionPolicyRequiredError = createProtectionPolicyRequiredError(nsError) {
            return protectionPolicyRequiredError
        }
        return CustomError.tokenAcquisitionFailed(message: error.localizedDescription)
    }

    private func deliverPendingWipeRequestedEvents() {
        let userDefaults = UserDefaults.standard
        guard let accountIds = userDefaults.stringArray(forKey: Intune.pendingWipeAccountIdsKey) else {
            return
        }
        userDefaults.removeObject(forKey: Intune.pendingWipeAccountIdsKey)
        for accountId in accountIds {
            let event = WipeRequestedEvent(accountId: accountId == Intune.pendingWipeNilAccountId ? nil : accountId)
            plugin.notifyWipeRequestedListeners(event)
        }
    }

    private func findAccount(in application: MSALPublicClientApplication, accountId: String) -> MSALAccount? {
        let accounts = (try? application.allAccounts()) ?? []
        return accounts.first { $0.homeAccountId?.objectId?.caseInsensitiveCompare(accountId) == .orderedSame }
    }

    private func getMsalApplication() throws -> MSALPublicClientApplication {
        if let application = msalApplication {
            return application
        }
        let settings = Bundle.main.object(forInfoDictionaryKey: "IntuneMAMSettings") as? [String: Any]
        guard let clientId = settings?["ADALClientId"] as? String, !clientId.isEmpty else {
            throw CustomError.clientIdMissing
        }
        var redirectUri = settings?["ADALRedirectUri"] as? String
        if redirectUri == nil, let scheme = settings?["ADALRedirectScheme"] as? String, let bundleId = Bundle.main.bundleIdentifier {
            redirectUri = "\(scheme)://\(bundleId)"
        }
        var authority: MSALAuthority?
        if let authorityUri = settings?["ADALAuthority"] as? String, let authorityUrl = URL(string: authorityUri) {
            authority = try MSALAADAuthority(url: authorityUrl)
        }
        let configuration = MSALPublicClientApplicationConfig(clientId: clientId, redirectUri: redirectUri, authority: authority)
        configuration.clientApplicationCapabilities = ["protapp"]
        let application = try MSALPublicClientApplication(configuration: configuration)
        msalApplication = application
        return application
    }

    @objc private func handleAppConfigDidChange(_ notification: Notification) {
        let accountId = notification.userInfo?[IntuneMAMAppConfigDidChangeNotificationAccountId] as? String
        plugin.notifyAppConfigChangeListeners(AppConfigChangeEvent(accountId: accountId))
    }

    private func handleEnrollmentChange(_ status: IntuneMAMEnrollmentStatus, isUnenrollment: Bool) {
        let mappedStatus = status.didSucceed ? (isUnenrollment ? "unenrolled" : "enrolled") : "failed"
        plugin.notifyEnrollmentChangeListeners(EnrollmentChangeEvent(accountId: status.accountId, status: mappedStatus))
    }

    @objc private func handleOpenUrl(_ notification: Notification) {
        guard let object = notification.object as? [String: Any], let url = object["url"] as? URL else {
            return
        }
        let options = object["options"] as? [UIApplication.OpenURLOptionsKey: Any]
        let sourceApplication = options?[.sourceApplication] as? String
        MSALPublicClientApplication.handleMSALResponse(url, sourceApplication: sourceApplication)
    }

    @objc private func handlePolicyDidChange(_ notification: Notification) {
        let accountId = notification.userInfo?[IntuneMAMPolicyDidChangeNotificationAccountId] as? String
        plugin.notifyPolicyChangeListeners(PolicyChangeEvent(accountId: accountId))
    }

    private func handleWipeRequested(accountId: String?) {
        let event = WipeRequestedEvent(accountId: accountId)
        if plugin.hasWipeRequestedListeners() {
            plugin.notifyWipeRequestedListeners(event)
            return
        }
        persistPendingWipeAccountId(accountId)
        plugin.notifyWipeRequestedListeners(event)
    }

    private func mapComplianceStatus(_ status: IntuneMAMComplianceStatus) -> String {
        switch status {
        case .compliant:
            return "compliant"
        case .interactionRequired:
            return "interactionRequired"
        case .networkFailure:
            return "networkFailure"
        case .notCompliant:
            return "notCompliant"
        case .serviceFailure:
            return "serviceFailure"
        case .userCancelled:
            return "canceled"
        @unknown default:
            return "unknown"
        }
    }

    private func persistPendingWipeAccountId(_ accountId: String?) {
        let userDefaults = UserDefaults.standard
        var accountIds = userDefaults.stringArray(forKey: Intune.pendingWipeAccountIdsKey) ?? []
        let accountIdToPersist = accountId ?? Intune.pendingWipeNilAccountId
        if !accountIds.contains(accountIdToPersist) {
            accountIds.append(accountIdToPersist)
        }
        userDefaults.set(accountIds, forKey: Intune.pendingWipeAccountIdsKey)
    }

    private func protectPath(_ path: String, accountId: String) throws {
        var isDirectory: ObjCBool = false
        FileManager.default.fileExists(atPath: path, isDirectory: &isDirectory)
        guard isDirectory.boolValue else {
            try IntuneMAMFile.protect(atPath: path, forAccountId: accountId)
            return
        }
        IntuneMAMFileProtectionManager.instance().protect(path, accountId: accountId)
        for name in try FileManager.default.contentsOfDirectory(atPath: path) {
            try protectPath((path as NSString).appendingPathComponent(name), accountId: accountId)
        }
    }

    private func resolveRemediateComplianceCompletions(
        accountId: String,
        status: IntuneMAMComplianceStatus,
        errorMessage: String,
        errorTitle: String
    ) {
        remediateComplianceCompletionsLock.lock()
        let completions = remediateComplianceCompletions.removeValue(forKey: accountId.lowercased()) ?? []
        remediateComplianceCompletionsLock.unlock()
        let result = RemediateComplianceResult(errorMessage: errorMessage, errorTitle: errorTitle, status: mapComplianceStatus(status))
        for completion in completions {
            completion(result, nil)
        }
    }
}

extension Intune: IntuneMAMComplianceDelegate {
    public func accountId(
        _ accountId: String,
        hasComplianceStatus status: IntuneMAMComplianceStatus,
        withErrorMessage errMsg: String,
        andErrorTitle errTitle: String
    ) {
        resolveRemediateComplianceCompletions(accountId: accountId, status: status, errorMessage: errMsg, errorTitle: errTitle)
    }
}

extension Intune: IntuneMAMEnrollmentDelegate {
    public func enrollmentRequest(with status: IntuneMAMEnrollmentStatus) {
        handleEnrollmentChange(status, isUnenrollment: false)
    }

    public func unenrollRequest(with status: IntuneMAMEnrollmentStatus) {
        handleEnrollmentChange(status, isUnenrollment: true)
    }
}

extension Intune: IntuneMAMPolicyDelegate {
    public func wipeData(forAccountId accountId: String) -> Bool {
        handleWipeRequested(accountId: accountId)
        return true
    }
}
