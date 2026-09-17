import Foundation
import Capacitor

@objc(AgeSignalsPlugin)
public class AgeSignalsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AgeSignalsPlugin"
    public let jsName = "AgeSignals"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getAgeRange", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getRegulatoryRequirements", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestAgeRange", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setNextAgeSignalsAccessResult", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setNextAgeSignalsException", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setNextAgeSignalsResult", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setNextRequestAgeSignalsAccessException", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setUseFakeManager", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "showSignificantUpdateAcknowledgment", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "AgeSignalsPlugin"

    var implementation: AgeSignals?

    override public func load() {
        self.implementation = AgeSignals(plugin: self)
    }

    @objc func getAgeRange(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func getRegulatoryRequirements(_ call: CAPPluginCall) {
        implementation?.getRegulatoryRequirements { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func isAvailable(_ call: CAPPluginCall) {
        implementation?.isAvailable { result in
            self.resolveCall(call, result)
        }
    }

    @objc func requestAgeRange(_ call: CAPPluginCall) {
        do {
            let options = try RequestAgeRangeOptions(call)

            implementation?.requestAgeRange(options) { result, error in
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

    @objc func setNextAgeSignalsAccessResult(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func setNextAgeSignalsException(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func setNextAgeSignalsResult(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func setNextRequestAgeSignalsAccessException(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func setUseFakeManager(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func showSignificantUpdateAcknowledgment(_ call: CAPPluginCall) {
        do {
            let options = try ShowSignificantUpdateAcknowledgmentOptions(call)

            implementation?.showSignificantUpdateAcknowledgment(options) { error in
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
        CAPLog.print("[", AgeSignalsPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
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
