import Foundation
import Capacitor

@objc(InstallReferrerPlugin)
public class InstallReferrerPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "InstallReferrerPlugin"
    public let jsName = "InstallReferrer"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getAttributionToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getInstallReferrer", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "InstallReferrerPlugin"

    private var implementation: InstallReferrer?

    override public func load() {
        self.implementation = InstallReferrer(plugin: self)
    }

    @objc func getAttributionToken(_ call: CAPPluginCall) {
        implementation?.getAttributionToken { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func getInstallReferrer(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", InstallReferrerPlugin.tag, "] ", error)
        call.reject(error.localizedDescription, (error as? CustomError)?.code)
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
