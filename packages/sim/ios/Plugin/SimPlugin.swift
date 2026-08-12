import Foundation
import Capacitor

@objc(SimPlugin)
public class SimPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "SimPlugin"
    public let jsName = "Sim"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getSimCards", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise)
    ]

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func getSimCards(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }
}
