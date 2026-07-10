import Foundation
import Capacitor

@objc(PermissionsPlugin)
public class PermissionsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "PermissionsPlugin"
    public let jsName = "Permissions"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "check", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "request", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "PermissionsPlugin"

    private var implementation: Permissions?

    override public func load() {
        self.implementation = Permissions()
    }

    @objc func check(_ call: CAPPluginCall) {
        do {
            let options = try CheckOptions(call)
            implementation?.check(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call, result)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func request(_ call: CAPPluginCall) {
        do {
            let options = try RequestOptions(call)
            implementation?.request(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call, result)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", PermissionsPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
