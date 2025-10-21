import Foundation
import Capacitor

@objc(AgeSignalsPlugin)
public class AgeSignalsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AgeSignalsPlugin"
    public let jsName = "AgeSignals"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "checkAgeSignals", returnType: CAPPluginReturnPromise)
    ]

    @objc func checkAgeSignals(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }
}
