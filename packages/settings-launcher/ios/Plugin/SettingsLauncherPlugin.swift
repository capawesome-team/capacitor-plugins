import Foundation
import Capacitor

@objc(SettingsLauncherPlugin)
public class SettingsLauncherPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "SettingsLauncherPlugin"
    public let jsName = "SettingsLauncher"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "openAndroidSettings", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openAppSettings", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openNotificationSettings", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "SettingsLauncherPlugin"

    private var implementation: SettingsLauncher?

    override public func load() {
        self.implementation = SettingsLauncher(plugin: self)
    }

    @objc func openAndroidSettings(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func openAppSettings(_ call: CAPPluginCall) {
        implementation?.openAppSettings { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    @objc func openNotificationSettings(_ call: CAPPluginCall) {
        guard #available(iOS 16.0, *) else {
            rejectCallAsUnavailable(call)
            return
        }
        implementation?.openNotificationSettings { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", SettingsLauncherPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }
}
