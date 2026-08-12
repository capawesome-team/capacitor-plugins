import Foundation
import Capacitor

@objc(HomeIndicatorPlugin)
public class HomeIndicatorPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "HomeIndicatorPlugin"
    public let jsName = "HomeIndicator"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "hide", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isHidden", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "show", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "HomeIndicatorPlugin"

    private var implementation: HomeIndicator?

    override public func load() {
        self.implementation = HomeIndicator(plugin: self)
    }

    @objc func hide(_ call: CAPPluginCall) {
        implementation?.hide { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    @objc func isHidden(_ call: CAPPluginCall) {
        implementation?.isHidden { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func show(_ call: CAPPluginCall) {
        implementation?.show { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", HomeIndicatorPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
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
