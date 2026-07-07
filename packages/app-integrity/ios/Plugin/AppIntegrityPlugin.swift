import Foundation
import Capacitor

@objc(AppIntegrityPlugin)
public class AppIntegrityPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AppIntegrityPlugin"
    public let jsName = "AppIntegrity"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "attestKey", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "generateAssertion", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "generateKey", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "prepareIntegrityToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestIntegrityToken", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "AppIntegrityPlugin"

    private var implementation: AppIntegrity?

    override public func load() {
        self.implementation = AppIntegrity(plugin: self)
    }

    @objc func attestKey(_ call: CAPPluginCall) {
        guard let implementation = implementation, implementation.isSupported() else {
            rejectCallAsUnavailable(call)
            return
        }
        do {
            let options = try AttestKeyOptions(call)
            implementation.attestKey(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func generateAssertion(_ call: CAPPluginCall) {
        guard let implementation = implementation, implementation.isSupported() else {
            rejectCallAsUnavailable(call)
            return
        }
        do {
            let options = try GenerateAssertionOptions(call)
            implementation.generateAssertion(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func generateKey(_ call: CAPPluginCall) {
        guard let implementation = implementation, implementation.isSupported() else {
            rejectCallAsUnavailable(call)
            return
        }
        implementation.generateKey(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
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

    @objc func prepareIntegrityToken(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func requestIntegrityToken(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", AppIntegrityPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
