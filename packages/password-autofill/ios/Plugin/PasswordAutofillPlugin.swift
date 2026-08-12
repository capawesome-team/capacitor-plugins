import Foundation
import Capacitor

@objc(PasswordAutofillPlugin)
public class PasswordAutofillPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "PasswordAutofillPlugin"
    public let jsName = "PasswordAutofill"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "savePassword", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "PasswordAutofillPlugin"

    private var implementation: PasswordAutofill?

    override public func load() {
        self.implementation = PasswordAutofill(plugin: self)
    }

    @objc func savePassword(_ call: CAPPluginCall) {
        do {
            let options = try SavePasswordOptions(call)
            implementation?.savePassword(options) { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            }
        } catch {
            self.rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", PasswordAutofillPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }
}
