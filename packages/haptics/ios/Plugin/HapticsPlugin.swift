import Foundation
import Capacitor

@objc(HapticsPlugin)
public class HapticsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "HapticsPlugin"
    public let jsName = "Haptics"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "impact", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "notification", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "performAndroidHaptic", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "playPattern", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "selectionChanged", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "selectionEnd", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "selectionStart", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "vibrate", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "HapticsPlugin"

    private var implementation: Haptics?

    override public func load() {
        self.implementation = Haptics(plugin: self)
    }

    @objc func impact(_ call: CAPPluginCall) {
        do {
            let options = try ImpactOptions(call)
            implementation?.impact(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func isAvailable(_ call: CAPPluginCall) {
        implementation?.isAvailable(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        })
    }

    @objc func notification(_ call: CAPPluginCall) {
        do {
            let options = try NotificationOptions(call)
            implementation?.notification(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func performAndroidHaptic(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func playPattern(_ call: CAPPluginCall) {
        guard implementation?.supportsHaptics() == true else {
            rejectCallAsUnavailable(call)
            return
        }
        do {
            let options = try PlayPatternOptions(call)
            implementation?.playPattern(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func selectionChanged(_ call: CAPPluginCall) {
        implementation?.selectionChanged(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    @objc func selectionEnd(_ call: CAPPluginCall) {
        implementation?.selectionEnd(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    @objc func selectionStart(_ call: CAPPluginCall) {
        implementation?.selectionStart(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    @objc func vibrate(_ call: CAPPluginCall) {
        implementation?.vibrate(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", HapticsPlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
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
