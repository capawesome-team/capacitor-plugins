import Foundation
import Capacitor

@objc(AppIconPlugin)
public class AppIconPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AppIconPlugin"
    public let jsName = "AppIcon"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getCurrentIcon", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "resetIcon", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setIcon", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "AppIcon"

    private var implementation: AppIcon?

    override public func load() {
        self.implementation = AppIcon(plugin: self)
    }

    @objc func getCurrentIcon(_ call: CAPPluginCall) {
        let result = implementation?.getCurrentIcon()
        resolveCall(call, result)
    }

    @objc func isAvailable(_ call: CAPPluginCall) {
        let result = implementation?.isAvailable()
        resolveCall(call, result)
    }

    @objc func resetIcon(_ call: CAPPluginCall) {
        implementation?.resetIcon { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    @objc func setIcon(_ call: CAPPluginCall) {
        do {
            let options = try SetIconOptions(call)
            implementation?.setIcon(options) { error in
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
        CAPLog.print("[", AppIconPlugin.tag, "] ", error)
        if let customError = error as? CustomError {
            call.reject(error.localizedDescription, customError.code)
        } else {
            call.reject(error.localizedDescription)
        }
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
