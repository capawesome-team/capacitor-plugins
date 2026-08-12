import Foundation
import Capacitor

@objc(MapsLauncherPlugin)
public class MapsLauncherPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "MapsLauncherPlugin"
    public let jsName = "MapsLauncher"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getAvailableApps", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getDefaultApp", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "navigate", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "MapsLauncherPlugin"

    private var implementation: MapsLauncher?

    override public func load() {
        self.implementation = MapsLauncher(plugin: self)
    }

    @objc func getAvailableApps(_ call: CAPPluginCall) {
        implementation?.getAvailableApps { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func getDefaultApp(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func navigate(_ call: CAPPluginCall) {
        do {
            let options = try NavigateOptions(call)
            implementation?.navigate(options) { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            }
        } catch {
            self.rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", MapsLauncherPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
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
