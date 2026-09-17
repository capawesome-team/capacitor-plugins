import Foundation
import Capacitor

@objc(TiktokAppEventsPlugin)
public class TiktokAppEventsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "TiktokAppEventsPlugin"
    public let jsName = "TiktokAppEvents"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "flush", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "identify", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "logout", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "trackEvent", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "TiktokAppEvents"

    private let implementation = TiktokAppEvents()

    @objc func flush(_ call: CAPPluginCall) {
        implementation.flush(completion: { error in
            self.handleCompletion(call, error)
        })
    }

    @objc func identify(_ call: CAPPluginCall) {
        do {
            let options = try IdentifyOptions(call)
            implementation.identify(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func initialize(_ call: CAPPluginCall) {
        do {
            let options = try InitializeOptions(call)
            implementation.initialize(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func logout(_ call: CAPPluginCall) {
        implementation.logout(completion: { error in
            self.handleCompletion(call, error)
        })
    }

    @objc func trackEvent(_ call: CAPPluginCall) {
        do {
            let options = try TrackEventOptions(call)
            implementation.trackEvent(options, completion: { error in
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
}
