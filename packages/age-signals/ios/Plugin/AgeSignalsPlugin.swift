import Foundation
import Capacitor

@objc(AgeSignalsPlugin)
public class AgeSignalsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AgeSignalsPlugin"
    public let jsName = "AgeSignals"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "checkAgeSignals", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "AgeSignalsPlugin"

    var implementation: AgeSignals?

    override public func load() {
        self.implementation = AgeSignals(plugin: self)
    }

    @objc func checkAgeSignals(_ call: CAPPluginCall) {
        do {
            let options = CheckAgeSignalsOptions(call)
            implementation?.checkAgeSignals(options) { result, error in
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
        CAPLog.print("[", AgeSignalsPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
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
