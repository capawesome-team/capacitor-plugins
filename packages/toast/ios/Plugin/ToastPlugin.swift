import Foundation
import Capacitor

@objc(ToastPlugin)
public class ToastPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "ToastPlugin"
    public let jsName = "Toast"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "show", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "ToastPlugin"

    private var implementation: Toast?

    override public func load() {
        self.implementation = Toast(plugin: self)
    }

    @objc func show(_ call: CAPPluginCall) {
        do {
            let options = try ShowOptions(call)
            implementation?.show(options) { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            }
        } catch {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", ToastPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }
}
