import Foundation
import Capacitor

@objc(LightSensorPlugin)
public class LightSensorPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "LightSensorPlugin"
    public let jsName = "LightSensor"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getMeasurement", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startMeasurementUpdates", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopMeasurementUpdates", returnType: CAPPluginReturnPromise)
    ]

    @objc func getMeasurement(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func isAvailable(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func startMeasurementUpdates(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func stopMeasurementUpdates(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }
}
