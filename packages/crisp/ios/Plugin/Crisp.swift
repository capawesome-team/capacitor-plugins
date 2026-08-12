import Foundation
import Capacitor
import Crisp

@objc public class CrispImpl: NSObject {
    private var callbackTokens: [CallbackToken] = []
    private var configured = false
    private let plugin: CrispPlugin

    init(plugin: CrispPlugin) {
        self.plugin = plugin
        super.init()
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleDidRegisterForRemoteNotifications(_:)),
            name: .capacitorDidRegisterForRemoteNotifications,
            object: nil
        )
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
        removeCallbacks()
    }

    @objc public func configure(_ options: ConfigureOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            if let tokenId = options.tokenId {
                CrispSDK.setTokenID(tokenID: tokenId)
            }
            CrispSDK.configure(websiteID: options.websiteId)
            self.registerCallbacks()
            self.configured = true
            completion(nil)
        }
    }

    @objc public func openChat(completion: @escaping (_ error: Error?) -> Void) {
        guard configured else {
            completion(CustomError.notConfigured)
            return
        }
        DispatchQueue.main.async {
            guard let viewController = self.plugin.bridge?.viewController else {
                completion(nil)
                return
            }
            let chatViewController = ChatViewController()
            viewController.present(chatViewController, animated: true)
            completion(nil)
        }
    }

    @objc public func openHelpdeskArticle(_ options: OpenHelpdeskArticleOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard configured else {
            completion(CustomError.notConfigured)
            return
        }
        DispatchQueue.main.async {
            CrispSDK.openHelpdeskArticle(
                locale: options.locale,
                slug: options.id,
                title: options.title,
                category: options.category
            )
            completion(nil)
        }
    }

    @objc public func pushSessionEvent(_ options: PushSessionEventOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard configured else {
            completion(CustomError.notConfigured)
            return
        }
        CrispSDK.session.pushEvents([SessionEvent(name: options.name, color: options.color)])
        completion(nil)
    }

    @objc public func resetSession(completion: @escaping (_ error: Error?) -> Void) {
        guard configured else {
            completion(CustomError.notConfigured)
            return
        }
        CrispSDK.session.reset()
        completion(nil)
    }

    @objc public func searchHelpdesk(completion: @escaping (_ error: Error?) -> Void) {
        guard configured else {
            completion(CustomError.notConfigured)
            return
        }
        DispatchQueue.main.async {
            CrispSDK.searchHelpdesk()
            completion(nil)
        }
    }

    @objc public func setCompany(_ options: SetCompanyOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard configured else {
            completion(CustomError.notConfigured)
            return
        }
        var employment: Employment?
        if options.employmentTitle != nil || options.employmentRole != nil {
            employment = Employment(title: options.employmentTitle, role: options.employmentRole)
        }
        var geolocation: Geolocation?
        if options.geolocationCity != nil || options.geolocationCountry != nil {
            geolocation = Geolocation(city: options.geolocationCity, country: options.geolocationCountry)
        }
        let url = options.url.flatMap { URL(string: $0) }
        CrispSDK.user.company = Company(
            name: options.name,
            url: url,
            companyDescription: options.companyDescription,
            employment: employment,
            geolocation: geolocation
        )
        completion(nil)
    }

    @objc public func setSessionBool(_ options: SetSessionBoolOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard configured else {
            completion(CustomError.notConfigured)
            return
        }
        CrispSDK.session.setBool(options.value, forKey: options.key)
        completion(nil)
    }

    @objc public func setSessionInt(_ options: SetSessionIntOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard configured else {
            completion(CustomError.notConfigured)
            return
        }
        CrispSDK.session.setInt(options.value, forKey: options.key)
        completion(nil)
    }

    @objc public func setSessionSegment(_ options: SetSessionSegmentOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard configured else {
            completion(CustomError.notConfigured)
            return
        }
        CrispSDK.session.setSegments([options.segment], overwrite: false)
        completion(nil)
    }

    @objc public func setSessionString(_ options: SetSessionStringOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard configured else {
            completion(CustomError.notConfigured)
            return
        }
        CrispSDK.session.setString(options.value, forKey: options.key)
        completion(nil)
    }

    @objc public func setShouldPromptForNotificationPermission(
        _ options: SetShouldPromptForNotificationPermissionOptions,
        completion: @escaping (_ error: Error?) -> Void
    ) {
        CrispSDK.setShouldPromptForNotificationPermission(options.enabled)
        completion(nil)
    }

    @objc public func setTokenId(_ options: SetTokenIdOptions, completion: @escaping (_ error: Error?) -> Void) {
        CrispSDK.setTokenID(tokenID: options.tokenId)
        completion(nil)
    }

    @objc public func setUser(_ options: SetUserOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard configured else {
            completion(CustomError.notConfigured)
            return
        }
        if let email = options.email {
            CrispSDK.user.email = email
        }
        if let emailSignature = options.emailSignature {
            CrispSDK.user.signature = emailSignature
        }
        if let nickname = options.nickname {
            CrispSDK.user.nickname = nickname
        }
        if let phone = options.phone {
            CrispSDK.user.phone = phone
        }
        if let avatarUrl = options.avatarUrl, let url = URL(string: avatarUrl) {
            CrispSDK.user.avatar = url
        }
        completion(nil)
    }

    @objc private func handleDidRegisterForRemoteNotifications(_ notification: Notification) {
        guard let deviceToken = notification.object as? Data else {
            return
        }
        CrispSDK.setDeviceToken(deviceToken)
    }

    private func registerCallbacks() {
        guard callbackTokens.isEmpty else {
            return
        }
        callbackTokens.append(CrispSDK.addCallback(.chatOpened({ [weak self] in
            self?.plugin.notifyChatOpenedListeners()
        })))
        callbackTokens.append(CrispSDK.addCallback(.chatClosed({ [weak self] in
            self?.plugin.notifyChatClosedListeners()
        })))
        callbackTokens.append(CrispSDK.addCallback(.sessionLoaded({ [weak self] sessionId in
            self?.plugin.notifySessionLoadedListeners(SessionLoadedEvent(sessionId: sessionId))
        })))
        callbackTokens.append(CrispSDK.addCallback(.messageSent({ [weak self] message in
            self?.plugin.notifyMessageSentListeners(MessageSentEvent(content: CrispHelper.getMessageContent(message)))
        })))
        callbackTokens.append(CrispSDK.addCallback(.messageReceived({ [weak self] message in
            self?.plugin.notifyMessageReceivedListeners(MessageReceivedEvent(content: CrispHelper.getMessageContent(message)))
        })))
    }

    private func removeCallbacks() {
        for token in callbackTokens {
            CrispSDK.removeCallback(token: token)
        }
        callbackTokens.removeAll()
    }
}
