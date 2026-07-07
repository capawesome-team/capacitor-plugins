import Foundation
import Capacitor

@objc(ProximitySensorPlugin)
public class ProximitySensorPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "ProximitySensorPlugin"
    public let jsName = "ProximitySensor"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getMeasurement", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startMeasurementUpdates", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopMeasurementUpdates", returnType: CAPPluginReturnPromise)
    ]

    public static let measurementEvent = "measurement"

    private var implementation: ProximitySensor?

    override public func load() {
        self.implementation = ProximitySensor(plugin: self)
    }

    @objc func getMeasurement(_ call: CAPPluginCall) {
        guard implementation?.isAvailable() == true else {
            rejectCallAsUnavailable(call)
            return
        }

        implementation?.getMeasurement { measurement in
            self.resolveCall(call, measurement)
        }
    }

    @objc func isAvailable(_ call: CAPPluginCall) {
        let available = implementation?.isAvailable() ?? false
        resolveCall(call, IsAvailableResult(available: available))
    }

    @objc public func notifyMeasurementEventListeners(_ measurement: Measurement) {
        if let data = measurement.toJSObject() as? JSObject {
            notifyListeners(ProximitySensorPlugin.measurementEvent, data: data)
        }
    }

    @objc override public func removeAllListeners(_ call: CAPPluginCall) {
        super.removeAllListeners(call)
        implementation?.stopMeasurementUpdates()
    }

    @objc func startMeasurementUpdates(_ call: CAPPluginCall) {
        guard implementation?.isAvailable() == true else {
            rejectCallAsUnavailable(call)
            return
        }

        implementation?.startMeasurementUpdates {
            self.resolveCall(call)
        }
    }

    @objc func stopMeasurementUpdates(_ call: CAPPluginCall) {
        implementation?.stopMeasurementUpdates {
            self.resolveCall(call)
        }
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
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
