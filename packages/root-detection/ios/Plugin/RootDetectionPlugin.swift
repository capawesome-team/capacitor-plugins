import Foundation
import Capacitor

@objc(RootDetectionPlugin)
public class RootDetectionPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "RootDetectionPlugin"
    public let jsName = "RootDetection"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "isDeveloperModeEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isEmulator", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isRooted", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "RootDetectionPlugin"

    private var implementation: RootDetection?

    override public func load() {
        self.implementation = RootDetection(plugin: self)
    }

    @objc func isDeveloperModeEnabled(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func isEmulator(_ call: CAPPluginCall) {
        implementation?.isEmulator { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func isRooted(_ call: CAPPluginCall) {
        implementation?.isRooted { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", RootDetectionPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
