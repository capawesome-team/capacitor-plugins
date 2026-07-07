import Foundation
import Capacitor

@objc(DialogPlugin)
public class DialogPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "DialogPlugin"
    public let jsName = "Dialog"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "alert", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "confirm", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "prompt", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "DialogPlugin"

    private var implementation: Dialog?

    override public func load() {
        self.implementation = Dialog(plugin: self)
    }

    @objc func alert(_ call: CAPPluginCall) {
        do {
            let options = try AlertOptions(call)
            implementation?.alert(options) { error in
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

    @objc func confirm(_ call: CAPPluginCall) {
        do {
            let options = try ConfirmOptions(call)
            implementation?.confirm(options) { result, error in
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

    @objc func prompt(_ call: CAPPluginCall) {
        do {
            let options = try PromptOptions(call)
            implementation?.prompt(options) { result, error in
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
        CAPLog.print("[", DialogPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
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
