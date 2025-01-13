import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(ManagedConfigurationsPlugin)
public class ManagedConfigurationsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "ManagedConfigurationsPlugin"
    public let jsName = "ManagedConfigurations"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getString", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getNumber", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getBoolean", returnType: CAPPluginReturnPromise)
    ]
    public let errorKeyMissing = "traceName must be provided."
    private var implementation: ManagedConfigurations?

    override public func load() {
        implementation = ManagedConfigurations()
    }

    @objc func getString(_ call: CAPPluginCall) {
        guard let key = call.getString("key") else {
            call.reject(errorKeyMissing)
            return
        }

        var result = JSObject()
        if implementation?.keyExists(key) == true {
            result["value"] = implementation?.getString(key)
        } else {
            result["value"] = NSNull()
        }
        call.resolve(result)
    }

    @objc func getNumber(_ call: CAPPluginCall) {
        guard let key = call.getString("key") else {
            call.reject(errorKeyMissing)
            return
        }

        var result = JSObject()
        if implementation?.keyExists(key) == true {
            result["value"] = implementation?.getInt(key)
        } else {
            result["value"] = NSNull()
        }
        call.resolve(result)
    }

    @objc func getBoolean(_ call: CAPPluginCall) {
        guard let key = call.getString("key") else {
            call.reject(errorKeyMissing)
            return
        }

        var result = JSObject()
        if implementation?.keyExists(key) == true {
            result["value"] = implementation?.getBool(key)
        } else {
            result["value"] = NSNull()
        }
        call.resolve(result)
    }
}
