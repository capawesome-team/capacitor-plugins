import Foundation
import Capacitor

@objc(WalletPlugin)
public class WalletPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "WalletPlugin"
    public let jsName = "Wallet"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "addPasses", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "canAddPasses", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "saveToGoogleWallet", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "WalletPlugin"

    private var implementation: Wallet?

    override public func load() {
        self.implementation = Wallet(plugin: self)
    }

    @objc func addPasses(_ call: CAPPluginCall) {
        do {
            let options = try AddPassesOptions(call)
            implementation?.addPasses(options, completion: { error in
                if let error = error {
                    if let customError = error as? CustomError, case .unavailable = customError {
                        self.rejectCallAsUnavailable(call)
                    } else {
                        self.rejectCall(call, error)
                    }
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func canAddPasses(_ call: CAPPluginCall) {
        implementation?.canAddPasses(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        })
    }

    @objc func saveToGoogleWallet(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", WalletPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("Passes cannot be added to Apple Wallet on this device.")
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
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
