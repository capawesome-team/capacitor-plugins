import Foundation
import Capacitor

@objc(SilentModePlugin)
public class SilentModePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "SilentModePlugin"
    public let jsName = "SilentMode"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getRingerMode", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isSilent", returnType: CAPPluginReturnPromise)
    ]

    public static let eventSilentModeChange = "silentModeChange"

    public static let tag = "SilentModePlugin"

    private var implementation: SilentMode?

    override public func load() {
        self.implementation = SilentMode(plugin: self)
    }

    @objc override public func addListener(_ call: CAPPluginCall) {
        super.addListener(call)
        implementation?.startObserving()
    }

    @objc func getRingerMode(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func isSilent(_ call: CAPPluginCall) {
        implementation?.isSilent { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    public func notifySilentModeChangeListeners(_ event: SilentModeChangeEvent) {
        self.notifyListeners(Self.eventSilentModeChange, data: event.toJSObject() as? [String: Any])
    }

    @objc override public func removeAllListeners(_ call: CAPPluginCall) {
        super.removeAllListeners(call)
        implementation?.stopObserving()
    }

    @objc override public func removeListener(_ call: CAPPluginCall) {
        super.removeListener(call)
        if !hasListeners(Self.eventSilentModeChange) {
            implementation?.stopObserving()
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", SilentModePlugin.tag, "] ", error)
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
