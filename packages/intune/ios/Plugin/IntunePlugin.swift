import Foundation
import Capacitor

@objc(IntunePlugin)
public class IntunePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "IntunePlugin"
    public let jsName = "Intune"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "acquireToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "acquireTokenSilent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getAppConfig", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getEnrolledAccount", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getPolicy", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getSdkVersion", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "loginAndEnrollAccount", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "registerAndEnrollAccount", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "showDiagnosticConsole", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "unenrollAccount", returnType: CAPPluginReturnPromise)
    ]
    public static let errorUnknownError = "An unknown error has occurred."
    public static let eventAppConfigChange = "appConfigChange"
    public static let eventEnrollmentChange = "enrollmentChange"
    public static let eventPolicyChange = "policyChange"
    public static let eventWipeRequested = "wipeRequested"
    public let tag = "Intune"
    private var implementation: Intune?

    override public func load() {
        super.load()
        self.implementation = Intune(plugin: self)
    }

    @objc func acquireToken(_ call: CAPPluginCall) {
        do {
            let options = try AcquireTokenOptions(call)
            implementation?.acquireToken(options, completion: { result, error in
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

    @objc func acquireTokenSilent(_ call: CAPPluginCall) {
        do {
            let options = try AcquireTokenSilentOptions(call)
            implementation?.acquireTokenSilent(options, completion: { result, error in
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

    @objc func getAppConfig(_ call: CAPPluginCall) {
        do {
            let options = try GetAppConfigOptions(call)
            implementation?.getAppConfig(options, completion: { result, error in
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

    @objc func getEnrolledAccount(_ call: CAPPluginCall) {
        implementation?.getEnrolledAccount(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getPolicy(_ call: CAPPluginCall) {
        do {
            let options = try GetPolicyOptions(call)
            implementation?.getPolicy(options, completion: { result, error in
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

    @objc func getSdkVersion(_ call: CAPPluginCall) {
        implementation?.getSdkVersion(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    public func hasWipeRequestedListeners() -> Bool {
        return hasListeners(Self.eventWipeRequested)
    }

    @objc func loginAndEnrollAccount(_ call: CAPPluginCall) {
        implementation?.loginAndEnrollAccount(completion: { error in
            self.handleCompletion(call, error)
        })
    }

    public func notifyAppConfigChangeListeners(_ event: AppConfigChangeEvent) {
        notifyListeners(Self.eventAppConfigChange, data: event.toJSObject() as? [String: Any])
    }

    public func notifyEnrollmentChangeListeners(_ event: EnrollmentChangeEvent) {
        notifyListeners(Self.eventEnrollmentChange, data: event.toJSObject() as? [String: Any], retainUntilConsumed: true)
    }

    public func notifyPolicyChangeListeners(_ event: PolicyChangeEvent) {
        notifyListeners(Self.eventPolicyChange, data: event.toJSObject() as? [String: Any])
    }

    public func notifyWipeRequestedListeners(_ event: WipeRequestedEvent) {
        notifyListeners(Self.eventWipeRequested, data: event.toJSObject() as? [String: Any], retainUntilConsumed: true)
    }

    @objc func registerAndEnrollAccount(_ call: CAPPluginCall) {
        do {
            let options = try RegisterAndEnrollAccountOptions(call)
            implementation?.registerAndEnrollAccount(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func showDiagnosticConsole(_ call: CAPPluginCall) {
        implementation?.showDiagnosticConsole(completion: { error in
            self.handleCompletion(call, error)
        })
    }

    @objc func unenrollAccount(_ call: CAPPluginCall) {
        do {
            let options = try UnenrollAccountOptions(call)
            implementation?.unenrollAccount(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    private func handleCompletion(_ call: CAPPluginCall, _ error: Error?) {
        if let error = error {
            rejectCall(call, error)
        } else {
            resolveCall(call)
        }
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
