import Foundation
import Capacitor

@objc(DeviceInfoPlugin)
public class DeviceInfoPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "DeviceInfoPlugin"
    public let jsName = "DeviceInfo"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getInfo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getUptime", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "DeviceInfoPlugin"

    private var implementation: DeviceInfo?

    override public func load() {
        self.implementation = DeviceInfo(plugin: self)
    }

    @objc func getId(_ call: CAPPluginCall) {
        implementation?.getId { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func getInfo(_ call: CAPPluginCall) {
        implementation?.getInfo { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func getUptime(_ call: CAPPluginCall) {
        implementation?.getUptime { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", DeviceInfoPlugin.tag, "] ", error)
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
