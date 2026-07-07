import Foundation
import Capacitor

@objc(InAppBrowserPlugin)
public class InAppBrowserPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "InAppBrowserPlugin"
    public let jsName = "InAppBrowser"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "clearCache", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "clearSessionData", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "close", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "executeScript", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCookies", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openInExternalBrowser", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openInSystemBrowser", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openInWebView", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "postMessage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "show", returnType: CAPPluginReturnPromise)
    ]

    public static let eventBrowserClosed = "browserClosed"
    public static let eventBrowserMessageReceived = "browserMessageReceived"
    public static let eventBrowserNavigationCompleted = "browserNavigationCompleted"
    public static let eventBrowserPageLoaded = "browserPageLoaded"
    public static let eventBrowserUrlChanged = "browserUrlChanged"
    public static let tag = "InAppBrowserPlugin"

    private var implementation: InAppBrowser?

    @objc func clearCache(_ call: CAPPluginCall) {
        implementation?.clearCache(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    @objc func clearSessionData(_ call: CAPPluginCall) {
        implementation?.clearSessionData(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    @objc func close(_ call: CAPPluginCall) {
        implementation?.close(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    @objc func executeScript(_ call: CAPPluginCall) {
        do {
            let options = try ExecuteScriptOptions(call)

            implementation?.executeScript(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call, result)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getCookies(_ call: CAPPluginCall) {
        do {
            let options = try GetCookiesOptions(call)

            implementation?.getCookies(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call, result)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    override public func load() {
        self.implementation = InAppBrowser(plugin: self)
    }

    @objc public func notifyBrowserClosedListeners() {
        self.notifyListeners(Self.eventBrowserClosed, data: [:])
    }

    @objc public func notifyBrowserMessageReceivedListeners(_ event: BrowserMessageReceivedEvent) {
        self.notifyListeners(Self.eventBrowserMessageReceived, data: event.toJSObject() as? [String: Any])
    }

    @objc public func notifyBrowserNavigationCompletedListeners(_ event: BrowserNavigationCompletedEvent) {
        self.notifyListeners(Self.eventBrowserNavigationCompleted, data: event.toJSObject() as? [String: Any])
    }

    @objc public func notifyBrowserPageLoadedListeners() {
        self.notifyListeners(Self.eventBrowserPageLoaded, data: [:])
    }

    @objc public func notifyBrowserUrlChangedListeners(_ event: BrowserUrlChangedEvent) {
        self.notifyListeners(Self.eventBrowserUrlChanged, data: event.toJSObject() as? [String: Any])
    }

    @objc func openInExternalBrowser(_ call: CAPPluginCall) {
        do {
            let options = try OpenInExternalBrowserOptions(call)

            implementation?.openInExternalBrowser(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func openInSystemBrowser(_ call: CAPPluginCall) {
        do {
            let options = try OpenInSystemBrowserOptions(call)

            implementation?.openInSystemBrowser(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func openInWebView(_ call: CAPPluginCall) {
        do {
            let options = try OpenInWebViewOptions(call)

            implementation?.openInWebView(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func postMessage(_ call: CAPPluginCall) {
        do {
            let options = try PostMessageOptions(call)

            implementation?.postMessage(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func show(_ call: CAPPluginCall) {
        implementation?.show(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", InAppBrowserPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
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
