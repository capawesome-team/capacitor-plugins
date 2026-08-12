import Foundation
import Capacitor

@objc(NetworkPlugin)
public class NetworkPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "NetworkPlugin"
    public let jsName = "Network"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getStatus", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAirplaneModeEnabled", returnType: CAPPluginReturnPromise)
    ]

    public static let eventNetworkStatusChange = "networkStatusChange"

    public static let tag = "NetworkPlugin"

    private var implementation: Network?

    override public func load() {
        self.implementation = Network(plugin: self)
    }

    @objc override public func addListener(_ call: CAPPluginCall) {
        super.addListener(call)
        implementation?.startObserving()
    }

    @objc func getStatus(_ call: CAPPluginCall) {
        implementation?.getStatus { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func isAirplaneModeEnabled(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    public func notifyNetworkStatusChangeListeners(_ event: GetStatusResult) {
        self.notifyListeners(Self.eventNetworkStatusChange, data: event.toJSObject() as? [String: Any])
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
        return hasListeners(Self.eventNetworkStatusChange)
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", NetworkPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
