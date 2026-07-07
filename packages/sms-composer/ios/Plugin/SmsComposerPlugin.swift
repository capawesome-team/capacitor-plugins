import Foundation
import Capacitor

@objc(SmsComposerPlugin)
public class SmsComposerPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "SmsComposerPlugin"
    public let jsName = "SmsComposer"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "canComposeSms", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "composeSms", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "SmsComposerPlugin"

    private var implementation: SmsComposer?

    override public func load() {
        self.implementation = SmsComposer(plugin: self)
    }

    @objc func canComposeSms(_ call: CAPPluginCall) {
        let canCompose = implementation?.canComposeSms() ?? false
        resolveCall(call, CanComposeSmsResult(canCompose: canCompose))
    }

    @objc func composeSms(_ call: CAPPluginCall) {
        guard let implementation = implementation, implementation.canComposeSms() else {
            rejectCallAsUnavailable(call)
            return
        }
        do {
            let options = try ComposeSmsOptions(call)
            implementation.composeSms(options) { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call, result)
            }
        } catch {
            self.rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", SmsComposerPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
