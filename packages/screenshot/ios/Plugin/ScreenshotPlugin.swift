import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(ScreenshotPlugin)
public class ScreenshotPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "ScreenshotPlugin"
    public let jsName = "Screenshot"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "take", returnType: CAPPluginReturnPromise)
    ]
    public let tag = "Screenshot"
    private var implementation: Screenshot?

    override public func load() {
        super.load()
        self.implementation = Screenshot(plugin: self)
    }

    @objc func take(_ call: CAPPluginCall) {
        implementation?.take(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            }
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: JSObject) {
        call.resolve(result)
    }
}
