import Foundation
import Capacitor

@objc(AccessibilityPreferencesPlugin)
public class AccessibilityPreferencesPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AccessibilityPreferencesPlugin"
    public let jsName = "AccessibilityPreferences"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getPreferences", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "AccessibilityPreferencesPlugin"

    private var implementation: AccessibilityPreferences?

    override public func load() {
        self.implementation = AccessibilityPreferences(plugin: self)
    }

    @objc func getPreferences(_ call: CAPPluginCall) {
        implementation?.getPreferences { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", AccessibilityPreferencesPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
