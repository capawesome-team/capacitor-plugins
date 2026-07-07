import Foundation
import Capacitor

@objc(ThermalStatePlugin)
public class ThermalStatePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "ThermalStatePlugin"
    public let jsName = "ThermalState"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getThermalState", returnType: CAPPluginReturnPromise)
    ]

    public static let eventThermalStateChange = "thermalStateChange"

    public static let tag = "ThermalStatePlugin"

    private var implementation: ThermalState?

    override public func load() {
        self.implementation = ThermalState(plugin: self)
    }

    @objc override public func addListener(_ call: CAPPluginCall) {
        super.addListener(call)
        implementation?.startObserving()
    }

    @objc func getThermalState(_ call: CAPPluginCall) {
        implementation?.getThermalState { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    public func notifyThermalStateChangeListeners(_ event: ThermalStateChangeEvent) {
        self.notifyListeners(Self.eventThermalStateChange, data: event.toJSObject() as? [String: Any])
    }

    @objc override public func removeAllListeners(_ call: CAPPluginCall) {
        super.removeAllListeners(call)
        implementation?.stopObserving()
    }

    @objc override public func removeListener(_ call: CAPPluginCall) {
        super.removeListener(call)
        if !hasListeners(Self.eventThermalStateChange) {
            implementation?.stopObserving()
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", ThermalStatePlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
