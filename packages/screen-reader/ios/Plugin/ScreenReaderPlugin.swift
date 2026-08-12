import Foundation
import Capacitor

@objc(ScreenReaderPlugin)
public class ScreenReaderPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "ScreenReaderPlugin"
    public let jsName = "ScreenReader"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "announce", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isEnabled", returnType: CAPPluginReturnPromise)
    ]

    public static let eventStateChange = "stateChange"

    public static let tag = "ScreenReaderPlugin"

    private var implementation: ScreenReader?

    override public func load() {
        self.implementation = ScreenReader(plugin: self)
    }

    @objc override public func addListener(_ call: CAPPluginCall) {
        super.addListener(call)
        implementation?.startObserving()
    }

    @objc func announce(_ call: CAPPluginCall) {
        do {
            let options = try AnnounceOptions(call)
            implementation?.announce(options) { error in
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

    @objc func isEnabled(_ call: CAPPluginCall) {
        implementation?.isEnabled { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    public func notifyStateChangeListeners(_ event: StateChangeEvent) {
        self.notifyListeners(Self.eventStateChange, data: event.toJSObject() as? [String: Any])
    }

    @objc override public func removeAllListeners(_ call: CAPPluginCall) {
        super.removeAllListeners(call)
        implementation?.stopObserving()
    }

    @objc override public func removeListener(_ call: CAPPluginCall) {
        super.removeListener(call)
        if !hasListeners(Self.eventStateChange) {
            implementation?.stopObserving()
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", ScreenReaderPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
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
