import Foundation
import Capacitor

@objc(NavigationBarPlugin)
public class NavigationBarPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "NavigationBarPlugin"
    public let jsName = "NavigationBar"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getColor", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getStyle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "hide", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setColor", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setStyle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "show", returnType: CAPPluginReturnPromise)
    ]

    @objc func getColor(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func getStyle(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func hide(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func setColor(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func setStyle(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func show(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }
}
