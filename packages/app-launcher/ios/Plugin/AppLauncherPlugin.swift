import Foundation
import Capacitor

@objc(AppLauncherPlugin)
public class AppLauncherPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AppLauncherPlugin"
    public let jsName = "AppLauncher"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "canOpenUrl", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openUrl", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "AppLauncherPlugin"

    private var implementation: AppLauncher?

    override public func load() {
        self.implementation = AppLauncher(plugin: self)
    }

    @objc func canOpenUrl(_ call: CAPPluginCall) {
        do {
            let options = try CanOpenUrlOptions(call)
            implementation?.canOpenUrl(options) { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call, result)
            }
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func openUrl(_ call: CAPPluginCall) {
        do {
            let options = try OpenUrlOptions(call)
            implementation?.openUrl(options) { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call, result)
            }
        } catch {
            self.rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", AppLauncherPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
