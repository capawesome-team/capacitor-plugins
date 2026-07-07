import Foundation
import Capacitor

@objc(AppLanguagePlugin)
public class AppLanguagePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AppLanguagePlugin"
    public let jsName = "AppLanguage"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getLanguage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openSettings", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "resetLanguage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setLanguage", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "AppLanguagePlugin"

    private var implementation: AppLanguage?

    override public func load() {
        self.implementation = AppLanguage(plugin: self)
    }

    @objc func getLanguage(_ call: CAPPluginCall) {
        implementation?.getLanguage { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc func openSettings(_ call: CAPPluginCall) {
        implementation?.openSettings { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    @objc func resetLanguage(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func setLanguage(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", AppLanguagePlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func rejectCallAsUnimplemented(_ call: CAPPluginCall) {
        call.unimplemented("This method is not available on this platform.")
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
