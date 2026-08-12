import Foundation
import Capacitor

@objc(AndroidIntentLauncherPlugin)
public class AndroidIntentLauncherPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AndroidIntentLauncherPlugin"
    public let jsName = "AndroidIntentLauncher"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "canResolveActivity", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startActivity", returnType: CAPPluginReturnPromise)
    ]

    @objc func canResolveActivity(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func startActivity(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }
}
