import Foundation
import Capacitor
import IntuneMAMSwift
import MSAL

@objc public class Intune: NSObject {
    private static let pendingWipeAccountIdsKey = "capawesome_capacitor_intune_pending_wipe_account_ids"
    private static let pendingWipeNilAccountId = ""

    private var msalApplication: MSALPublicClientApplication?
    private let plugin: IntunePlugin

    init(plugin: IntunePlugin) {
        self.plugin = plugin
        super.init()
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

    @objc public func loginAndEnrollAccount(completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            IntuneMAMEnrollmentManager.instance().loginAndEnrollAccount(nil)
            completion(nil)
        }
    }

    @objc public func registerAndEnrollAccount(_ options: RegisterAndEnrollAccountOptions, completion: @escaping (_ error: Error?) -> Void) {
        IntuneMAMEnrollmentManager.instance().registerAndEnrollAccountId(options.accountId)
        completion(nil)
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

    private func createTokenAcquisitionError(_ error: Error) -> CustomError {
        let nsError = error as NSError
        if nsError.domain == MSALErrorDomain && nsError.code == MSALError.userCanceled.rawValue {
            return CustomError.interactionCanceled
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

    private func persistPendingWipeAccountId(_ accountId: String?) {
        let userDefaults = UserDefaults.standard
        var accountIds = userDefaults.stringArray(forKey: Intune.pendingWipeAccountIdsKey) ?? []
        let accountIdToPersist = accountId ?? Intune.pendingWipeNilAccountId
        if !accountIds.contains(accountIdToPersist) {
            accountIds.append(accountIdToPersist)
        }
        userDefaults.set(accountIds, forKey: Intune.pendingWipeAccountIdsKey)
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
