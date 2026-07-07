import Foundation
import Capacitor

@objc(PhoneDialerPlugin)
public class PhoneDialerPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "PhoneDialerPlugin"
    public let jsName = "PhoneDialer"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "canDial", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "dial", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "PhoneDialerPlugin"

    private var implementation: PhoneDialer?

    override public func load() {
        self.implementation = PhoneDialer(plugin: self)
    }

    @objc func canDial(_ call: CAPPluginCall) {
        let canDial = implementation?.canDial() ?? false
        resolveCall(call, CanDialResult(canDial: canDial))
    }

    @objc func dial(_ call: CAPPluginCall) {
        guard let implementation = implementation, implementation.canDial() else {
            rejectCallAsUnavailable(call)
            return
        }
        do {
            let options = try DialOptions(call)
            implementation.dial(options) { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            }
        } catch {
            self.rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", PhoneDialerPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
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
