import Foundation
import Capacitor

@objc(AudioSessionPlugin)
public class AudioSessionPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AudioSessionPlugin"
    public let jsName = "AudioSession"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "configure", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCurrentOutputs", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "overrideOutput", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setActive", returnType: CAPPluginReturnPromise)
    ]
    public static let eventInterruption = "interruption"
    public static let eventRouteChange = "routeChange"
    public static let tag = "AudioSessionPlugin"

    private var implementation: AudioSession?

    override public func load() {
        self.implementation = AudioSession(plugin: self)
    }

    @objc func configure(_ call: CAPPluginCall) {
        do {
            let options = try ConfigureOptions(call)
            implementation?.configure(options) { error in
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

    @objc func getCurrentOutputs(_ call: CAPPluginCall) {
        implementation?.getCurrentOutputs { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        }
    }

    @objc public func notifyInterruptionListeners(_ event: InterruptionEvent) {
        self.notifyListeners(Self.eventInterruption, data: event.toJSObject() as? [String: Any])
    }

    @objc public func notifyRouteChangeListeners(_ event: RouteChangeEvent) {
        self.notifyListeners(Self.eventRouteChange, data: event.toJSObject() as? [String: Any])
    }

    @objc func overrideOutput(_ call: CAPPluginCall) {
        let options = OverrideOutputOptions(call)
        implementation?.overrideOutput(options) { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    @objc func setActive(_ call: CAPPluginCall) {
        do {
            let options = try SetActiveOptions(call)
            implementation?.setActive(options) { error in
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
        CAPLog.print("[", AudioSessionPlugin.tag, "] ", error)
        if let customError = error as? CustomError {
            call.reject(customError.localizedDescription, customError.code)
        } else {
            call.reject(error.localizedDescription)
        }
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
