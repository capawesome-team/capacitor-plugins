import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(TorchPlugin)
public class TorchPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "TorchPlugin"
    public let jsName = "Torch"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "enable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "disable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "toggle", returnType: CAPPluginReturnPromise)
    ]
    public static let errorNotAvailable = "Not available on this device."
    public static let tag = "Torch"

    private var implementation: Torch?

    override public func load() {
        self.implementation = Torch(plugin: self)
    }

    @objc func enable(_ call: CAPPluginCall) {
        guard let _ = implementation?.isAvailable() else {
            call.reject(TorchPlugin.errorNotAvailable)
            return
        }

        do {
            try implementation?.enable()
            call.resolve()
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func disable(_ call: CAPPluginCall) {
        guard let _ = implementation?.isAvailable() else {
            call.reject(TorchPlugin.errorNotAvailable)
            return
        }

        do {
            try implementation?.disable()
            call.resolve()
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func isAvailable(_ call: CAPPluginCall) {
        let isAvailable = implementation?.isAvailable() ?? false
        var result = JSObject()
        result["available"] = isAvailable
        call.resolve(result)
    }

    @objc func isEnabled(_ call: CAPPluginCall) {
        let isEnabled = implementation?.isEnabled() ?? false
        var result = JSObject()
        result["enabled"] = isEnabled
        call.resolve(result)
    }

    @objc func toggle(_ call: CAPPluginCall) {
        guard let _ = implementation?.isAvailable() else {
            call.reject(TorchPlugin.errorNotAvailable)
            return
        }

        do {
            try implementation?.toggle()
            call.resolve()
        } catch {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", TorchPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }
}
