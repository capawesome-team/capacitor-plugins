import Foundation
import Capacitor

@objc(TextZoomPlugin)
public class TextZoomPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "TextZoomPlugin"
    public let jsName = "TextZoom"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getPreferredZoom", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getZoom", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setZoom", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "TextZoomPlugin"

    private var implementation: TextZoom?

    override public func load() {
        self.implementation = TextZoom(plugin: self)
    }

    @objc func getPreferredZoom(_ call: CAPPluginCall) {
        implementation?.getPreferredZoom { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func getZoom(_ call: CAPPluginCall) {
        implementation?.getZoom { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func setZoom(_ call: CAPPluginCall) {
        do {
            let options = try SetZoomOptions(call)

            implementation?.setZoom(options) { error in
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

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", TextZoomPlugin.tag, "] ", error)
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
