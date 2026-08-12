import Foundation
import Capacitor

@objc(MailComposerPlugin)
public class MailComposerPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "MailComposerPlugin"
    public let jsName = "MailComposer"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "canComposeMail", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "composeMail", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "MailComposerPlugin"

    private var implementation: MailComposer?

    override public func load() {
        self.implementation = MailComposer(plugin: self)
    }

    @objc func canComposeMail(_ call: CAPPluginCall) {
        implementation?.canComposeMail { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func composeMail(_ call: CAPPluginCall) {
        let options = ComposeMailOptions(call)
        implementation?.composeMail(options) { result, error in
            if let error = error {
                if case CustomError.mailServicesUnavailable = error {
                    self.rejectCallAsUnavailable(call)
                } else {
                    self.rejectCall(call, error)
                }
                return
            }
            self.resolveCall(call, result)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", MailComposerPlugin.tag, "] ", error)
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
