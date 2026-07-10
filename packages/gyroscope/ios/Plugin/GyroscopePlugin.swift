import Foundation
import Capacitor

@objc(GyroscopePlugin)
public class GyroscopePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "GyroscopePlugin"
    public let jsName = "Gyroscope"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getMeasurement", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startMeasurementUpdates", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopMeasurementUpdates", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise)
    ]

    public static let measurementEvent = "measurement"

    public static let tag = "GyroscopePlugin"

    private var implementation: Gyroscope?

    override public func load() {
        self.implementation = Gyroscope(plugin: self)
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        call.resolve(["gyroscope": "granted"])
    }

    @objc func getMeasurement(_ call: CAPPluginCall) {
        if !hasUsageDescription(forKey: "NSMotionUsageDescription") {
            rejectCall(call, CustomError.privacyDescriptionsMissing)
            return
        }

        implementation?.getMeasurement { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func handleMeasurementEvent(_ event: Measurement) {
        if let data = event.toJSObject() as? JSObject {
            notifyListeners(GyroscopePlugin.measurementEvent, data: data)
        }
    }

    @objc func isAvailable(_ call: CAPPluginCall) {
        implementation?.isAvailable { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        call.resolve(["gyroscope": "granted"])
    }

    @objc func startMeasurementUpdates(_ call: CAPPluginCall) {
        if !hasUsageDescription(forKey: "NSMotionUsageDescription") {
            rejectCall(call, CustomError.privacyDescriptionsMissing)
            return
        }

        implementation?.startMeasurementUpdates { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    @objc func stopMeasurementUpdates(_ call: CAPPluginCall) {
        implementation?.stopMeasurementUpdates { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    @objc override public func removeAllListeners(_ call: CAPPluginCall) {
        super.removeAllListeners(call)
        implementation?.stopMeasurementUpdates()
    }

    private func hasUsageDescription(forKey key: String) -> Bool {
        return Bundle.main.object(forInfoDictionaryKey: key) is String
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", GyroscopePlugin.tag, "] ", error)
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
