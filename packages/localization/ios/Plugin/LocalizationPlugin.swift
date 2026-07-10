import Foundation
import Capacitor

@objc(LocalizationPlugin)
public class LocalizationPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "LocalizationPlugin"
    public let jsName = "Localization"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getLocales", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getSettings", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "LocalizationPlugin"

    private var implementation: Localization?

    override public func load() {
        self.implementation = Localization(plugin: self)
    }

    @objc func getLocales(_ call: CAPPluginCall) {
        implementation?.getLocales { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func getSettings(_ call: CAPPluginCall) {
        implementation?.getSettings { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", LocalizationPlugin.tag, "] ", error)
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
