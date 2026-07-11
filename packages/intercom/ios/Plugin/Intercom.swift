import Foundation
import Capacitor
import Intercom

@objc public class IntercomImpl: NSObject {
    private var initialized = false
    private let plugin: IntercomPlugin

    init(plugin: IntercomPlugin) {
        self.plugin = plugin
        super.init()
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleUnreadConversationCountDidChange),
            name: NSNotification.Name.IntercomUnreadConversationCountDidChange,
            object: nil
        )
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleWindowDidShow),
            name: NSNotification.Name.IntercomWindowDidShow,
            object: nil
        )
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleWindowDidHide),
            name: NSNotification.Name.IntercomWindowDidHide,
            object: nil
        )
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    @objc public func getUnreadConversationCount(completion: @escaping (_ result: GetUnreadConversationCountResult?, _ error: Error?) -> Void) {
        guard initialized else {
            completion(nil, CustomError.notInitialized)
            return
        }
        let count = Int(Intercom.unreadConversationCount())
        completion(GetUnreadConversationCountResult(count: count), nil)
    }

    @objc public func handlePushNotification(_ options: HandlePushNotificationOptions, completion: @escaping (_ error: Error?) -> Void) {
        Intercom.handlePushNotification(options.data)
        completion(nil)
    }

    @objc public func hide(completion: @escaping (_ error: Error?) -> Void) {
        guard initialized else {
            completion(CustomError.notInitialized)
            return
        }
        DispatchQueue.main.async {
            Intercom.hide()
            completion(nil)
        }
    }

    @objc public func initialize(_ options: InitializeOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard let iosApiKey = options.iosApiKey else {
            completion(CustomError.iosApiKeyMissing)
            return
        }
        Intercom.setApiKey(iosApiKey, forAppId: options.appId)
        initialized = true
        completion(nil)
    }

    @objc public func isIntercomPushNotification(_ options: IsIntercomPushNotificationOptions, completion: @escaping (_ result: IsIntercomPushNotificationResult?, _ error: Error?) -> Void) {
        let isIntercom = Intercom.isIntercomPushNotification(options.data)
        completion(IsIntercomPushNotificationResult(intercom: isIntercom), nil)
    }

    @objc public func logEvent(_ options: LogEventOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard initialized else {
            completion(CustomError.notInitialized)
            return
        }
        if let data = options.data {
            Intercom.logEvent(withName: options.name, metaData: data)
        } else {
            Intercom.logEvent(withName: options.name)
        }
        completion(nil)
    }

    @objc public func loginUnidentifiedUser(completion: @escaping (_ error: Error?) -> Void) {
        guard initialized else {
            completion(CustomError.notInitialized)
            return
        }
        Intercom.loginUnidentifiedUser { result in
            switch result {
            case .success:
                completion(nil)
            case .failure(let error):
                completion(CustomError.loginFailed(message: error.localizedDescription))
            }
        }
    }

    @objc public func loginUser(_ options: LoginUserOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard initialized else {
            completion(CustomError.notInitialized)
            return
        }
        let attributes = ICMUserAttributes()
        attributes.userId = options.userId
        attributes.email = options.email
        Intercom.loginUser(with: attributes) { result in
            switch result {
            case .success:
                completion(nil)
            case .failure(let error):
                completion(CustomError.loginFailed(message: error.localizedDescription))
            }
        }
    }

    @objc public func logout(completion: @escaping (_ error: Error?) -> Void) {
        guard initialized else {
            completion(CustomError.notInitialized)
            return
        }
        Intercom.logout()
        completion(nil)
    }

    @objc public func present(_ options: PresentOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard initialized else {
            completion(CustomError.notInitialized)
            return
        }
        DispatchQueue.main.async {
            Intercom.present(self.mapSpace(options.space))
            completion(nil)
        }
    }

    @objc public func presentContent(_ options: PresentContentOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard initialized else {
            completion(CustomError.notInitialized)
            return
        }
        guard let content = mapContent(options) else {
            completion(CustomError.typeInvalid)
            return
        }
        DispatchQueue.main.async {
            Intercom.presentContent(content)
            completion(nil)
        }
    }

    @objc public func presentMessageComposer(_ options: PresentMessageComposerOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard initialized else {
            completion(CustomError.notInitialized)
            return
        }
        DispatchQueue.main.async {
            Intercom.presentMessageComposer(options.initialMessage)
            completion(nil)
        }
    }

    @objc public func sendPushTokenToIntercom(_ options: SendPushTokenToIntercomOptions, completion: @escaping (_ error: Error?) -> Void) {
        let deviceToken = IntercomHelper.hexStringToData(options.token)
        Intercom.setDeviceToken(deviceToken) { result in
            switch result {
            case .success:
                completion(nil)
            case .failure(let error):
                completion(error)
            }
        }
    }

    @objc public func setBottomPadding(_ options: SetBottomPaddingOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            Intercom.setBottomPadding(options.padding)
            completion(nil)
        }
    }

    @objc public func setInAppMessagesVisible(_ options: SetInAppMessagesVisibleOptions, completion: @escaping (_ error: Error?) -> Void) {
        Intercom.setInAppMessagesVisible(options.visible)
        completion(nil)
    }

    @objc public func setLauncherVisible(_ options: SetLauncherVisibleOptions, completion: @escaping (_ error: Error?) -> Void) {
        Intercom.setLauncherVisible(options.visible)
        completion(nil)
    }

    @objc public func setUserHash(_ options: SetUserHashOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard initialized else {
            completion(CustomError.notInitialized)
            return
        }
        Intercom.setUserHash(options.userHash)
        completion(nil)
    }

    @objc public func setUserJwt(_ options: SetUserJwtOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard initialized else {
            completion(CustomError.notInitialized)
            return
        }
        Intercom.setUserJwt(options.jwt)
        completion(nil)
    }

    @objc public func updateUser(_ options: UpdateUserOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard initialized else {
            completion(CustomError.notInitialized)
            return
        }
        let attributes: ICMUserAttributes
        do {
            attributes = try buildUserAttributes(options)
        } catch {
            completion(error)
            return
        }
        Intercom.updateUser(with: attributes) { result in
            switch result {
            case .success:
                completion(nil)
            case .failure(let error):
                completion(CustomError.updateFailed(message: error.localizedDescription))
            }
        }
    }

    private func buildCompany(_ company: JSObject) throws -> ICMCompany {
        guard let id = company["id"] as? String else {
            throw CustomError.companyIdMissing
        }
        let result = ICMCompany()
        result.companyId = id
        if let name = company["name"] as? String {
            result.name = name
        }
        if let plan = company["plan"] as? String {
            result.plan = plan
        }
        if let monthlySpend = company["monthlySpend"] as? NSNumber {
            result.monthlySpend = monthlySpend
        }
        if let createdAt = company["createdAt"] as? NSNumber {
            result.createdAt = Date(timeIntervalSince1970: createdAt.doubleValue)
        }
        if let customAttributes = company["customAttributes"] as? JSObject {
            result.customAttributes = customAttributes
        }
        return result
    }

    private func buildUserAttributes(_ options: UpdateUserOptions) throws -> ICMUserAttributes {
        let attributes = ICMUserAttributes()
        attributes.name = options.name
        attributes.email = options.email
        attributes.userId = options.userId
        attributes.phone = options.phone
        attributes.languageOverride = options.languageOverride
        attributes.signedUpAt = options.signedUpAt
        if let unsubscribedFromEmails = options.unsubscribedFromEmails {
            attributes.unsubscribedFromEmails = unsubscribedFromEmails
        }
        if let customAttributes = options.customAttributes {
            attributes.customAttributes = customAttributes
        }
        if let companies = options.companies {
            attributes.companies = try companies.map { try buildCompany($0) }
        }
        return attributes
    }

    @objc private func handleUnreadConversationCountDidChange() {
        let count = Int(Intercom.unreadConversationCount())
        plugin.notifyUnreadConversationCountChangeListeners(UnreadConversationCountChangeEvent(count: count))
    }

    @objc private func handleWindowDidHide() {
        plugin.notifyMessengerHiddenListeners()
    }

    @objc private func handleWindowDidShow() {
        plugin.notifyMessengerShownListeners()
    }

    private func mapContent(_ options: PresentContentOptions) -> Intercom.Content? {
        switch options.type {
        case "article":
            return .article(id: options.id ?? "")
        case "carousel":
            return .carousel(id: options.id ?? "")
        case "survey":
            return .survey(id: options.id ?? "")
        case "conversation":
            return .conversation(id: options.id ?? "")
        case "help-center-collections":
            return .helpCenterCollections(ids: options.ids ?? [])
        default:
            return nil
        }
    }

    private func mapSpace(_ space: String) -> Space {
        switch space {
        case "messages":
            return .messages
        case "help-center":
            return .helpCenter
        case "tickets":
            return .tickets
        default:
            return .home
        }
    }
}
