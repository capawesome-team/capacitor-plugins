import Foundation
import Capacitor

@objc(SystemWebViewPlugin)
public class SystemWebViewPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "SystemWebViewPlugin"
    public let jsName = "SystemWebView"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getInfo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isUpdateRequired", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openAppStore", returnType: CAPPluginReturnPromise)
    ]

    @objc func getInfo(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func isUpdateRequired(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func openAppStore(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }
}
