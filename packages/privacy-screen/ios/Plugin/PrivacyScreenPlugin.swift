import Foundation
import Capacitor

@objc(PrivacyScreenPlugin)
public class PrivacyScreenPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "PrivacyScreenPlugin"
    public let jsName = "PrivacyScreen"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "enable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "disable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isEnabled", returnType: CAPPluginReturnPromise)
    ]

    public static let eventScreenshotTaken = "screenshotTaken"

    public static let tag = "PrivacyScreenPlugin"

    private var implementation: PrivacyScreen?

    override public func load() {
        self.implementation = PrivacyScreen(plugin: self)
    }

    @objc override public func addListener(_ call: CAPPluginCall) {
        super.addListener(call)
        implementation?.startObserving()
    }

    @objc func disable(_ call: CAPPluginCall) {
        implementation?.disable { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    @objc func enable(_ call: CAPPluginCall) {
        let options = EnableOptions(call)
        implementation?.enable(options) { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    @objc func isEnabled(_ call: CAPPluginCall) {
        implementation?.isEnabled { result in
            self.resolveCall(call, result)
        }
    }

    public func notifyScreenshotTakenListeners() {
        self.notifyListeners(Self.eventScreenshotTaken, data: nil)
    }

    @objc override public func removeAllListeners(_ call: CAPPluginCall) {
        super.removeAllListeners(call)
        implementation?.stopObserving()
    }

    @objc override public func removeListener(_ call: CAPPluginCall) {
        super.removeListener(call)
        if !hasListeners(Self.eventScreenshotTaken) {
            implementation?.stopObserving()
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", PrivacyScreenPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
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
