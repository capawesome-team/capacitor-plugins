import Foundation
import Capacitor

@objc(FormbricksPlugin)
public class FormbricksPlugin: CAPPlugin, CAPBridgedPlugin {
    public static let tag = "Formbricks"

    public let identifier = "FormbricksPlugin"
    public let jsName = "Formbricks"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "logout", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setAttribute", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setAttributes", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setLanguage", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setUserId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setup", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "track", returnType: CAPPluginReturnPromise)
    ]

    private var implementation: Formbricks?

    override public func load() {
        self.implementation = Formbricks()
    }

    @objc func logout(_ call: CAPPluginCall) {
        implementation?.logout()
        call.resolve()
    }

    @objc func setAttribute(_ call: CAPPluginCall) {
        do {
            let options = try SetAttributeOptions(call: call)
            implementation?.setAttribute(options)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func setAttributes(_ call: CAPPluginCall) {
        do {
            let options = try SetAttributesOptions(call: call)
            implementation?.setAttributes(options)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func setLanguage(_ call: CAPPluginCall) {
        do {
            let options = try SetLanguageOptions(call: call)
            implementation?.setLanguage(options)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func setUserId(_ call: CAPPluginCall) {
        do {
            let options = try SetUserIdOptions(call: call)
            implementation?.setUserId(options)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func setup(_ call: CAPPluginCall) {
        do {
            let options = try SetupOptions(call: call)
            implementation?.setup(options)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func track(_ call: CAPPluginCall) {
        do {
            let options = try TrackOptions(call: call)
            implementation?.track(options)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", FormbricksPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }
}
