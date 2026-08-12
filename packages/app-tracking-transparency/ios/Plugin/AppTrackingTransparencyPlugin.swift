import Foundation
import Capacitor

@objc(AppTrackingTransparencyPlugin)
public class AppTrackingTransparencyPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AppTrackingTransparencyPlugin"
    public let jsName = "AppTrackingTransparency"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getAdvertisingIdentifier", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getStatus", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermission", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "AppTrackingTransparencyPlugin"

    private var implementation: AppTrackingTransparency?

    override public func load() {
        self.implementation = AppTrackingTransparency(plugin: self)
    }

    @objc func getAdvertisingIdentifier(_ call: CAPPluginCall) {
        implementation?.getAdvertisingIdentifier { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func getStatus(_ call: CAPPluginCall) {
        implementation?.getStatus { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func requestPermission(_ call: CAPPluginCall) {
        implementation?.requestPermission { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", AppTrackingTransparencyPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
