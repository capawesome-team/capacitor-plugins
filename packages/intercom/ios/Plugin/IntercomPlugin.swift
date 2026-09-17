import Foundation
import Capacitor

@objc(IntercomPlugin)
public class IntercomPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "IntercomPlugin"
    public let jsName = "Intercom"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getUnreadConversationCount", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "handlePushNotification", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "hide", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isIntercomPushNotification", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "logEvent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "loginUnidentifiedUser", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "loginUser", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "logout", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "present", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "presentContent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "presentMessageComposer", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "sendPushTokenToIntercom", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setBottomPadding", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setInAppMessagesVisible", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setLauncherVisible", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setUserHash", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setUserJwt", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "updateUser", returnType: CAPPluginReturnPromise)
    ]
    public static let eventMessengerHidden = "messengerHidden"
    public static let eventMessengerShown = "messengerShown"
    public static let eventUnreadConversationCountChange = "unreadConversationCountChange"
    public let tag = "Intercom"
    private var implementation: IntercomImpl?

    override public func load() {
        super.load()
        self.implementation = IntercomImpl(plugin: self)
    }

    @objc func getUnreadConversationCount(_ call: CAPPluginCall) {
        implementation?.getUnreadConversationCount(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func handlePushNotification(_ call: CAPPluginCall) {
        do {
            let options = try HandlePushNotificationOptions(call)
            implementation?.handlePushNotification(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func hide(_ call: CAPPluginCall) {
        implementation?.hide(completion: { error in
            self.handleCompletion(call, error)
        })
    }

    @objc func initialize(_ call: CAPPluginCall) {
        do {
            let options = try InitializeOptions(call)
            implementation?.initialize(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func isIntercomPushNotification(_ call: CAPPluginCall) {
        do {
            let options = try IsIntercomPushNotificationOptions(call)
            implementation?.isIntercomPushNotification(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func logEvent(_ call: CAPPluginCall) {
        do {
            let options = try LogEventOptions(call)
            implementation?.logEvent(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func loginUnidentifiedUser(_ call: CAPPluginCall) {
        implementation?.loginUnidentifiedUser(completion: { error in
            self.handleCompletion(call, error)
        })
    }

    @objc func loginUser(_ call: CAPPluginCall) {
        do {
            let options = try LoginUserOptions(call)
            implementation?.loginUser(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func logout(_ call: CAPPluginCall) {
        implementation?.logout(completion: { error in
            self.handleCompletion(call, error)
        })
    }

    public func notifyMessengerHiddenListeners() {
        notifyListeners(Self.eventMessengerHidden, data: [:])
    }

    public func notifyMessengerShownListeners() {
        notifyListeners(Self.eventMessengerShown, data: [:])
    }

    public func notifyUnreadConversationCountChangeListeners(_ event: UnreadConversationCountChangeEvent) {
        notifyListeners(Self.eventUnreadConversationCountChange, data: event.toJSObject() as? [String: Any])
    }

    @objc func present(_ call: CAPPluginCall) {
        let options = PresentOptions(call)
        implementation?.present(options, completion: { error in
            self.handleCompletion(call, error)
        })
    }

    @objc func presentContent(_ call: CAPPluginCall) {
        do {
            let options = try PresentContentOptions(call)
            implementation?.presentContent(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func presentMessageComposer(_ call: CAPPluginCall) {
        let options = PresentMessageComposerOptions(call)
        implementation?.presentMessageComposer(options, completion: { error in
            self.handleCompletion(call, error)
        })
    }

    @objc func sendPushTokenToIntercom(_ call: CAPPluginCall) {
        do {
            let options = try SendPushTokenToIntercomOptions(call)
            implementation?.sendPushTokenToIntercom(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setBottomPadding(_ call: CAPPluginCall) {
        do {
            let options = try SetBottomPaddingOptions(call)
            implementation?.setBottomPadding(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setInAppMessagesVisible(_ call: CAPPluginCall) {
        do {
            let options = try SetInAppMessagesVisibleOptions(call)
            implementation?.setInAppMessagesVisible(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setLauncherVisible(_ call: CAPPluginCall) {
        do {
            let options = try SetLauncherVisibleOptions(call)
            implementation?.setLauncherVisible(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setUserHash(_ call: CAPPluginCall) {
        do {
            let options = try SetUserHashOptions(call)
            implementation?.setUserHash(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setUserJwt(_ call: CAPPluginCall) {
        do {
            let options = try SetUserJwtOptions(call)
            implementation?.setUserJwt(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func updateUser(_ call: CAPPluginCall) {
        let options = UpdateUserOptions(call)
        implementation?.updateUser(options, completion: { error in
            self.handleCompletion(call, error)
        })
    }

    private func handleCompletion(_ call: CAPPluginCall, _ error: Error?) {
        if let error = error {
            rejectCall(call, error)
        } else {
            resolveCall(call)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        call.reject(error.localizedDescription, (error as? CustomError)?.code)
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
}
