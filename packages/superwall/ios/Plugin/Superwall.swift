import Foundation
import Capacitor
import SuperwallKit

@objc public class Superwall: NSObject, SuperwallDelegate {
    private let plugin: SuperwallPlugin
    private var isConfigured = false

    init(plugin: SuperwallPlugin) {
        self.plugin = plugin
    }

    @objc public func configure(_ options: ConfigureOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        SuperwallKit.Superwall.configure(
            apiKey: options.apiKey,
            purchaseController: nil,
            options: options.options
        ) {
            self.isConfigured = true
            SuperwallKit.Superwall.shared.delegate = self
            completion(nil)
        }
    }

    @objc public func register(_ options: RegisterOptions, completion: @escaping (_ result: RegisterResult?, _ error: Error?) -> Void) throws {
        guard isConfigured else {
            completion(nil, CustomError.notConfigured)
            return
        }

        let handler = PaywallPresentationHandler()
        handler.onDismiss { _, result in
            let resultString: String
            switch result {
            case .purchased:
                resultString = "PURCHASED"
            case .restored:
                resultString = "RESTORED"
            case .declined:
                resultString = "CANCELLED"
            }
            completion(RegisterResult(result: resultString), nil)
        }
        handler.onError { error in
            completion(nil, error)
        }
        handler.onSkip { _ in
            // If skipped, treat as cancelled
            completion(RegisterResult(result: "CANCELLED"), nil)
        }

        SuperwallKit.Superwall.shared.register(
            placement: options.placement,
            params: options.params,
            handler: handler
        )
    }

    @objc public func getPresentationResult(_ options: GetPresentationResultOptions, completion: @escaping (_ result: GetPresentationResultResult?, _ error: Error?) -> Void) throws {
        guard isConfigured else {
            completion(nil, CustomError.notConfigured)
            return
        }

        SuperwallKit.Superwall.shared.getPresentationResult(
            forPlacement: options.placement,
            params: options.params
        ) { presentationResult in
            let typeString: String
            var experiment: Experiment?

            switch presentationResult {
            case .placementNotFound:
                typeString = "PLACEMENT_NOT_FOUND"
            case .noAudienceMatch:
                typeString = "NO_AUDIENCE_MATCH"
            case .paywall(let exp):
                typeString = "PAYWALL"
                experiment = exp
            case .holdout(let exp):
                typeString = "HOLDOUT"
                experiment = exp
            case .paywallNotAvailable:
                typeString = "PAYWALL_NOT_AVAILABLE"
            }

            completion(GetPresentationResultResult(type: typeString, experiment: experiment), nil)
        }
    }

    @objc public func identify(_ options: IdentifyOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        guard isConfigured else {
            completion(CustomError.notConfigured)
            return
        }

        let identityOptions = options.restorePaywallAssignments ? IdentityOptions(restorePaywallAssignments: true) : nil
        SuperwallKit.Superwall.shared.identify(userId: options.userId, options: identityOptions)
        completion(nil)
    }

    @objc public func reset(completion: @escaping (_ error: Error?) -> Void) throws {
        guard isConfigured else {
            completion(CustomError.notConfigured)
            return
        }

        SuperwallKit.Superwall.shared.reset()
        completion(nil)
    }

    @objc public func getUserId(completion: @escaping (_ result: GetUserIdResult?, _ error: Error?) -> Void) throws {
        guard isConfigured else {
            completion(nil, CustomError.notConfigured)
            return
        }

        let userId = SuperwallKit.Superwall.shared.userId
        completion(GetUserIdResult(userId: userId), nil)
    }

    @objc public func getIsLoggedIn(completion: @escaping (_ result: GetIsLoggedInResult?, _ error: Error?) -> Void) throws {
        guard isConfigured else {
            completion(nil, CustomError.notConfigured)
            return
        }

        let isLoggedIn = SuperwallKit.Superwall.shared.isLoggedIn
        completion(GetIsLoggedInResult(isLoggedIn: isLoggedIn), nil)
    }

    @objc public func setUserAttributes(_ options: SetUserAttributesOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        guard isConfigured else {
            completion(CustomError.notConfigured)
            return
        }

        SuperwallKit.Superwall.shared.setUserAttributes(options.attributes)
        completion(nil)
    }

    @objc public func handleDeepLink(_ options: HandleDeepLinkOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        guard isConfigured else {
            completion(CustomError.notConfigured)
            return
        }

        guard let url = URL(string: options.url) else {
            completion(CustomError.urlMissing)
            return
        }

        _ = SuperwallKit.Superwall.shared.handleDeepLink(url)
        completion(nil)
    }

    @objc public func getSubscriptionStatus(completion: @escaping (_ result: GetSubscriptionStatusResult?, _ error: Error?) -> Void) throws {
        guard isConfigured else {
            completion(nil, CustomError.notConfigured)
            return
        }

        let status = SuperwallKit.Superwall.shared.subscriptionStatus
        let statusString: String
        switch status {
        case .unknown:
            statusString = "UNKNOWN"
        case .active:
            statusString = "ACTIVE"
        case .inactive:
            statusString = "INACTIVE"
        }
        completion(GetSubscriptionStatusResult(status: statusString), nil)
    }

    // MARK: - SuperwallDelegate

    public func handleSuperwallEvent(withInfo eventInfo: SuperwallEventInfo) {
        let event = SuperwallEvent(eventInfo)
        plugin.notifySuperwallEventListeners(event)
    }

    public func subscriptionStatusDidChange(from oldValue: SubscriptionStatus, to newValue: SubscriptionStatus) {
        let statusString: String
        switch newValue {
        case .unknown:
            statusString = "UNKNOWN"
        case .active:
            statusString = "ACTIVE"
        case .inactive:
            statusString = "INACTIVE"
        }
        let event = SubscriptionStatusDidChangeEvent(status: statusString)
        plugin.notifySubscriptionStatusDidChangeListeners(event)
    }

    public func didPresentPaywall(withInfo paywallInfo: PaywallInfo) {
        let event = PaywallPresentedEvent(paywallInfo: paywallInfo)
        plugin.notifyPaywallPresentedListeners(event)
    }

    public func willDismissPaywall(withInfo paywallInfo: PaywallInfo) {
        let event = PaywallWillDismissEvent(paywallInfo: paywallInfo)
        plugin.notifyPaywallWillDismissListeners(event)
    }

    public func didDismissPaywall(withInfo paywallInfo: PaywallInfo) {
        let event = PaywallDismissedEvent(paywallInfo: paywallInfo)
        plugin.notifyPaywallDismissedListeners(event)
    }

    public func handleCustomPaywallAction(withName name: String) {
        let event = CustomPaywallActionEvent(name: name)
        plugin.notifyCustomPaywallActionListeners(event)
    }
}
