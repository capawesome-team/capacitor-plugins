import Foundation
import Capacitor

@objc(ShakePlugin)
public class ShakePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "ShakePlugin"
    public let jsName = "Shake"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "startWatching", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopWatching", returnType: CAPPluginReturnPromise)
    ]

    public static let shakeEvent = "shake"
    public static let tag = "ShakePlugin"

    private var implementation: Shake?

    override public func load() {
        self.implementation = Shake(plugin: self)
    }

    @objc public func notifyShakeListeners() {
        notifyListeners(Self.shakeEvent, data: nil)
    }

    @objc func startWatching(_ call: CAPPluginCall) {
        do {
            let options = try StartWatchingOptions(call)
            guard implementation?.isAvailable() == true else {
                rejectCallAsUnavailable(call)
                return
            }
            implementation?.startWatching(options) { error in
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

    @objc func stopWatching(_ call: CAPPluginCall) {
        implementation?.stopWatching { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", ShakePlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }
}
