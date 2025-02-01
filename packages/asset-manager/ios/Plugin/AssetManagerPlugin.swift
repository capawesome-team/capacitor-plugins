import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AssetManagerPlugin)
public class AssetManagerPlugin: CAPPlugin, CAPBridgedPlugin {
    public static let tag = "AssetManager"
    public let identifier = "AssetManagerPlugin" // DO NOT CHANGE
    public let jsName = "AssetManager" // DO NOT CHANGE
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "copy", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "list", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "read", returnType: CAPPluginReturnPromise)
    ]
    private var implementation: AssetManager?

    override public func load() {
        self.implementation = AssetManager(plugin: self)
    }

    @objc func copy(_ call: CAPPluginCall) {
        do {
            let options = try CopyOptions(call)
            
            try implementation?.copy(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call)
                }
            })
        } catch {
            self.rejectCall(call, error)
        }
    }
    
    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", AssetManagerPlugin.tag, "] ", error)
        var message = error.localizedDescription
        call.reject(message)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: JSObject) {
        call.resolve(result)
    }
}
