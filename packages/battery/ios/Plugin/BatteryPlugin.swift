import Foundation
import Capacitor

@objc(BatteryPlugin)
public class BatteryPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "BatteryPlugin"
    public let jsName = "Battery"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getBatteryLevel", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getBatteryState", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isLowPowerModeEnabled", returnType: CAPPluginReturnPromise)
    ]

    public static let eventBatteryLevelChange = "batteryLevelChange"

    public static let eventBatteryStateChange = "batteryStateChange"

    public static let eventLowPowerModeChange = "lowPowerModeChange"

    public static let tag = "BatteryPlugin"

    private var implementation: Battery?

    override public func load() {
        self.implementation = Battery(plugin: self)
    }

    @objc override public func addListener(_ call: CAPPluginCall) {
        super.addListener(call)
        implementation?.startObserving()
    }

    @objc func getBatteryLevel(_ call: CAPPluginCall) {
        implementation?.getBatteryLevel { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func getBatteryState(_ call: CAPPluginCall) {
        implementation?.getBatteryState { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func isLowPowerModeEnabled(_ call: CAPPluginCall) {
        implementation?.isLowPowerModeEnabled { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    public func notifyBatteryLevelChangeListeners(_ event: BatteryLevelChangeEvent) {
        self.notifyListeners(Self.eventBatteryLevelChange, data: event.toJSObject() as? [String: Any])
    }

    public func notifyBatteryStateChangeListeners(_ event: BatteryStateChangeEvent) {
        self.notifyListeners(Self.eventBatteryStateChange, data: event.toJSObject() as? [String: Any])
    }

    public func notifyLowPowerModeChangeListeners(_ event: LowPowerModeChangeEvent) {
        self.notifyListeners(Self.eventLowPowerModeChange, data: event.toJSObject() as? [String: Any])
    }

    @objc override public func removeAllListeners(_ call: CAPPluginCall) {
        super.removeAllListeners(call)
        implementation?.stopObserving()
    }

    @objc override public func removeListener(_ call: CAPPluginCall) {
        super.removeListener(call)
        if !hasAnyListeners() {
            implementation?.stopObserving()
        }
    }

    private func hasAnyListeners() -> Bool {
        return hasListeners(Self.eventBatteryLevelChange)
            || hasListeners(Self.eventBatteryStateChange)
            || hasListeners(Self.eventLowPowerModeChange)
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", BatteryPlugin.tag, "] ", error)
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
