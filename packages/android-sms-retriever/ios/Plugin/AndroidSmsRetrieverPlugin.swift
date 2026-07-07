import Foundation
import Capacitor

@objc(AndroidSmsRetrieverPlugin)
public class AndroidSmsRetrieverPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AndroidSmsRetrieverPlugin"
    public let jsName = "AndroidSmsRetriever"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "requestPhoneNumber", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "retrieveSms", returnType: CAPPluginReturnPromise)
    ]

    @objc func requestPhoneNumber(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func retrieveSms(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }
}
