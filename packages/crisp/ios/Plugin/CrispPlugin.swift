import Foundation
import Capacitor

@objc(CrispPlugin)
public class CrispPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CrispPlugin"
    public let jsName = "Crisp"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "configure", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "handlePushNotification", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isCrispPushNotification", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openChat", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openHelpdeskArticle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "pushSessionEvent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "resetSession", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "searchHelpdesk", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setCompany", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setNotificationsEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setSessionBool", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setSessionInt", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setSessionSegment", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setSessionString", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setShouldPromptForNotificationPermission", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setTokenId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setUser", returnType: CAPPluginReturnPromise)
    ]
    public static let eventChatClosed = "chatClosed"
    public static let eventChatOpened = "chatOpened"
    public static let eventMessageReceived = "messageReceived"
    public static let eventMessageSent = "messageSent"
    public static let eventSessionLoaded = "sessionLoaded"
    public let tag = "Crisp"
    private var implementation: CrispImpl?

    override public func load() {
        super.load()
        self.implementation = CrispImpl(plugin: self)
    }

    @objc func configure(_ call: CAPPluginCall) {
        do {
            let options = try ConfigureOptions(call)
            implementation?.configure(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func handlePushNotification(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func isCrispPushNotification(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    public func notifyChatClosedListeners() {
        notifyListeners(Self.eventChatClosed, data: [:])
    }

    public func notifyChatOpenedListeners() {
        notifyListeners(Self.eventChatOpened, data: [:])
    }

    public func notifyMessageReceivedListeners(_ event: MessageReceivedEvent) {
        notifyListeners(Self.eventMessageReceived, data: event.toJSObject() as? [String: Any])
    }

    public func notifyMessageSentListeners(_ event: MessageSentEvent) {
        notifyListeners(Self.eventMessageSent, data: event.toJSObject() as? [String: Any])
    }

    public func notifySessionLoadedListeners(_ event: SessionLoadedEvent) {
        notifyListeners(Self.eventSessionLoaded, data: event.toJSObject() as? [String: Any])
    }

    @objc func openChat(_ call: CAPPluginCall) {
        implementation?.openChat(completion: { error in
            self.handleCompletion(call, error)
        })
    }

    @objc func openHelpdeskArticle(_ call: CAPPluginCall) {
        do {
            let options = try OpenHelpdeskArticleOptions(call)
            implementation?.openHelpdeskArticle(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func pushSessionEvent(_ call: CAPPluginCall) {
        do {
            let options = try PushSessionEventOptions(call)
            implementation?.pushSessionEvent(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func resetSession(_ call: CAPPluginCall) {
        implementation?.resetSession(completion: { error in
            self.handleCompletion(call, error)
        })
    }

    @objc func searchHelpdesk(_ call: CAPPluginCall) {
        implementation?.searchHelpdesk(completion: { error in
            self.handleCompletion(call, error)
        })
    }

    @objc func setCompany(_ call: CAPPluginCall) {
        do {
            let options = try SetCompanyOptions(call)
            implementation?.setCompany(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setNotificationsEnabled(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func setSessionBool(_ call: CAPPluginCall) {
        do {
            let options = try SetSessionBoolOptions(call)
            implementation?.setSessionBool(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setSessionInt(_ call: CAPPluginCall) {
        do {
            let options = try SetSessionIntOptions(call)
            implementation?.setSessionInt(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setSessionSegment(_ call: CAPPluginCall) {
        do {
            let options = try SetSessionSegmentOptions(call)
            implementation?.setSessionSegment(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setSessionString(_ call: CAPPluginCall) {
        do {
            let options = try SetSessionStringOptions(call)
            implementation?.setSessionString(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setShouldPromptForNotificationPermission(_ call: CAPPluginCall) {
        do {
            let options = try SetShouldPromptForNotificationPermissionOptions(call)
            implementation?.setShouldPromptForNotificationPermission(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setTokenId(_ call: CAPPluginCall) {
        do {
            let options = try SetTokenIdOptions(call)
            implementation?.setTokenId(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setUser(_ call: CAPPluginCall) {
        let options = SetUserOptions(call)
        implementation?.setUser(options, completion: { error in
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

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on iOS.")
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }
}
