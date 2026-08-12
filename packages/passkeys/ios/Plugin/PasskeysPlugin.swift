import Foundation
import Capacitor
import AuthenticationServices

@objc(PasskeysPlugin)
public class PasskeysPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "PasskeysPlugin"
    public let jsName = "Passkeys"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "createPasskey", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getPasskey", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise)
    ]

    private var implementation: Passkeys?
    private let tag = "Passkeys"

    override public func load() {
        self.implementation = Passkeys()
    }

    @objc func createPasskey(_ call: CAPPluginCall) {
        do {
            let options = try CreatePasskeyOptions(call)

            implementation?.createPasskey(options, presentationContextProvider: self, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getPasskey(_ call: CAPPluginCall) {
        do {
            let options = try GetPasskeyOptions(call)

            implementation?.getPasskey(options, presentationContextProvider: self, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func isAvailable(_ call: CAPPluginCall) {
        implementation?.isAvailable(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        var code: String?
        if let customError = error as? CustomError {
            code = customError.code
        }
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

extension PasskeysPlugin: ASAuthorizationControllerPresentationContextProviding {
    public func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
        return self.bridge?.webView?.window ?? ASPresentationAnchor()
    }
}
