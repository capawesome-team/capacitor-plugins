import Foundation
import Capacitor

@objc(OptionPickerPlugin)
public class OptionPickerPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "OptionPickerPlugin"
    public let jsName = "OptionPicker"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "present", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "OptionPickerPlugin"

    private var implementation: OptionPicker?

    override public func load() {
        self.implementation = OptionPicker(plugin: self)
    }

    @objc func present(_ call: CAPPluginCall) {
        do {
            let options = try PresentOptions(call)
            implementation?.present(options) { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call, result)
            }
        } catch {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", OptionPickerPlugin.tag, "] ", error)
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
