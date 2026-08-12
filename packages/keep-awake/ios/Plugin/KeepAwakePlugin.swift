import Foundation
import Capacitor

@objc(KeepAwakePlugin)
public class KeepAwakePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "KeepAwakePlugin"
    public let jsName = "KeepAwake"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "allowSleep", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isKeptAwake", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "keepAwake", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "KeepAwakePlugin"

    private var implementation: KeepAwake?

    override public func load() {
        self.implementation = KeepAwake(plugin: self)
    }

    @objc func allowSleep(_ call: CAPPluginCall) {
        implementation?.allowSleep {
            self.resolveCall(call)
        }
    }

    @objc func isAvailable(_ call: CAPPluginCall) {
        let result = implementation?.isAvailable()
        resolveCall(call, result)
    }

    @objc func isKeptAwake(_ call: CAPPluginCall) {
        implementation?.isKeptAwake { result in
            self.resolveCall(call, result)
        }
    }

    @objc func keepAwake(_ call: CAPPluginCall) {
        implementation?.keepAwake {
            self.resolveCall(call)
        }
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
