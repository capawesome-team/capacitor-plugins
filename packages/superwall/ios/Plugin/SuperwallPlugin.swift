import Foundation
import Capacitor

@objc(SuperwallPlugin)
public class SuperwallPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "SuperwallPlugin"
    public let jsName = "Superwall"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "configure", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "register", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getPresentationResult", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "identify", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "reset", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getUserId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getIsLoggedIn", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setUserAttributes", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "handleDeepLink", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getSubscriptionStatus", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "Superwall"
    private static let errorUnknownError = "An unknown error occurred."
    public static let eventSuperwallEvent = "superwallEvent"
    public static let eventSubscriptionStatusDidChange = "subscriptionStatusDidChange"
    public static let eventPaywallPresented = "paywallPresented"
    public static let eventPaywallWillDismiss = "paywallWillDismiss"
    public static let eventPaywallDismissed = "paywallDismissed"
    public static let eventCustomPaywallAction = "customPaywallAction"

    private var implementation: Superwall?

    override public func load() {
        implementation = Superwall(plugin: self)
    }

    @objc func configure(_ call: CAPPluginCall) {
        do {
            let options = try ConfigureOptions(call)

            try implementation?.configure(options) { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            }
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func register(_ call: CAPPluginCall) {
        do {
            let options = try RegisterOptions(call)

            try implementation?.register(options) { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            }
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getPresentationResult(_ call: CAPPluginCall) {
        do {
            let options = try GetPresentationResultOptions(call)

            try implementation?.getPresentationResult(options) { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            }
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func identify(_ call: CAPPluginCall) {
        do {
            let options = try IdentifyOptions(call)

            try implementation?.identify(options) { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            }
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func reset(_ call: CAPPluginCall) {
        do {
            try implementation?.reset { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            }
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getUserId(_ call: CAPPluginCall) {
        do {
            try implementation?.getUserId { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            }
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getIsLoggedIn(_ call: CAPPluginCall) {
        do {
            try implementation?.getIsLoggedIn { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            }
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setUserAttributes(_ call: CAPPluginCall) {
        do {
            let options = try SetUserAttributesOptions(call)

            try implementation?.setUserAttributes(options) { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            }
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func handleDeepLink(_ call: CAPPluginCall) {
        do {
            let options = try HandleDeepLinkOptions(call)

            try implementation?.handleDeepLink(options) { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            }
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getSubscriptionStatus(_ call: CAPPluginCall) {
        do {
            try implementation?.getSubscriptionStatus { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            }
        } catch {
            rejectCall(call, error)
        }
    }

    // MARK: - Helper Methods

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", SuperwallPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on this platform.")
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }

    // MARK: - Event Notification Methods

    public func notifySuperwallEventListeners(_ event: SuperwallEvent) {
        notifyListeners(SuperwallPlugin.eventSuperwallEvent, data: event.toJSObject())
    }

    public func notifySubscriptionStatusDidChangeListeners(_ event: SubscriptionStatusDidChangeEvent) {
        notifyListeners(SuperwallPlugin.eventSubscriptionStatusDidChange, data: event.toJSObject())
    }

    public func notifyPaywallPresentedListeners(_ event: PaywallPresentedEvent) {
        notifyListeners(SuperwallPlugin.eventPaywallPresented, data: event.toJSObject())
    }

    public func notifyPaywallWillDismissListeners(_ event: PaywallWillDismissEvent) {
        notifyListeners(SuperwallPlugin.eventPaywallWillDismiss, data: event.toJSObject())
    }

    public func notifyPaywallDismissedListeners(_ event: PaywallDismissedEvent) {
        notifyListeners(SuperwallPlugin.eventPaywallDismissed, data: event.toJSObject())
    }

    public func notifyCustomPaywallActionListeners(_ event: CustomPaywallActionEvent) {
        notifyListeners(SuperwallPlugin.eventCustomPaywallAction, data: event.toJSObject())
    }
}
