import Foundation
import Capacitor

@objc(FacebookSignInPlugin)
public class FacebookSignInPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FacebookSignInPlugin"
    public let jsName = "FacebookSignIn"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getCurrentAccessToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signIn", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "signOut", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "FacebookSignIn"
    private var implementation: FacebookSignInImpl?

    override public func load() {
        super.load()
        self.implementation = FacebookSignInImpl(plugin: self)
    }

    @objc func getCurrentAccessToken(_ call: CAPPluginCall) {
        implementation?.getCurrentAccessToken(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func initialize(_ call: CAPPluginCall) {
        let options = InitializeOptions(call)
        implementation?.initialize(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    @objc func signIn(_ call: CAPPluginCall) {
        let options = SignInOptions(call)
        implementation?.signIn(options, completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func signOut(_ call: CAPPluginCall) {
        implementation?.signOut(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call)
            }
        })
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        call.reject(error.localizedDescription, (error as? CustomError)?.code)
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
