import Foundation
import Capacitor

@objc(ScreenBrightnessPlugin)
public class ScreenBrightnessPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "ScreenBrightnessPlugin"
    public let jsName = "ScreenBrightness"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getBrightness", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "resetBrightness", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setBrightness", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "ScreenBrightnessPlugin"

    private var implementation: ScreenBrightness?

    override public func load() {
        self.implementation = ScreenBrightness(plugin: self)
    }

    @objc func getBrightness(_ call: CAPPluginCall) {
        implementation?.getBrightness { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func resetBrightness(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func setBrightness(_ call: CAPPluginCall) {
        do {
            let options = try SetBrightnessOptions(call)

            implementation?.setBrightness(options) { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            }
        } catch {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", ScreenBrightnessPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
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
