import Foundation
import Capacitor

@objc(TextInteractionPlugin)
public class TextInteractionPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "TextInteractionPlugin"
    public let jsName = "TextInteraction"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "disable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "enable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isEnabled", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "TextInteractionPlugin"

    private var implementation: TextInteraction?

    override public func load() {
        self.implementation = TextInteraction(plugin: self)
    }

    @objc func disable(_ call: CAPPluginCall) {
        implementation?.disable { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    @objc func enable(_ call: CAPPluginCall) {
        implementation?.enable { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
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

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", TextInteractionPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
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
