import Foundation
import Capacitor

@objc(CompassPlugin)
public class CompassPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CompassPlugin"
    public let jsName = "Compass"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getHeading", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startHeadingUpdates", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopHeadingUpdates", returnType: CAPPluginReturnPromise)
    ]

    public static let eventHeadingChange = "headingChange"

    public static let tag = "CompassPlugin"

    private var implementation: Compass?

    override public func load() {
        self.implementation = Compass(plugin: self)
    }

    @objc func getHeading(_ call: CAPPluginCall) {
        guard let implementation = implementation, implementation.isAvailable() else {
            rejectCallAsUnavailable(call)
            return
        }
        implementation.getHeading { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func isAvailable(_ call: CAPPluginCall) {
        let available = implementation?.isAvailable() ?? false
        resolveCall(call, IsAvailableResult(available: available))
    }

    @objc public func notifyHeadingChangeListeners(_ event: HeadingChangeEvent) {
        self.notifyListeners(Self.eventHeadingChange, data: event.toJSObject() as? [String: Any])
    }

    @objc func startHeadingUpdates(_ call: CAPPluginCall) {
        guard let implementation = implementation, implementation.isAvailable() else {
            rejectCallAsUnavailable(call)
            return
        }
        implementation.startHeadingUpdates { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    @objc func stopHeadingUpdates(_ call: CAPPluginCall) {
        implementation?.stopHeadingUpdates { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    @objc override public func removeAllListeners(_ call: CAPPluginCall) {
        super.removeAllListeners(call)
        implementation?.stopHeadingUpdates()
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", CompassPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
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
