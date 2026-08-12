import Foundation
import Capacitor

@objc(VolumePlugin)
public class VolumePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "VolumePlugin"
    public let jsName = "Volume"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getVolume", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isWatching", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setVolume", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startWatching", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopWatching", returnType: CAPPluginReturnPromise)
    ]

    public static let eventVolumeButtonPressed = "volumeButtonPressed"
    public static let eventVolumeChange = "volumeChange"
    public static let tag = "VolumePlugin"

    private var implementation: Volume?

    @objc func getVolume(_ call: CAPPluginCall) {
        implementation?.getVolume(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        })
    }

    @objc func isWatching(_ call: CAPPluginCall) {
        implementation?.isWatching(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        })
    }

    override public func load() {
        self.implementation = Volume(plugin: self)
    }

    @objc public func notifyVolumeButtonPressedListeners(_ event: VolumeButtonPressedEvent) {
        self.notifyListeners(Self.eventVolumeButtonPressed, data: event.toJSObject() as? [String: Any])
    }

    @objc public func notifyVolumeChangeListeners(_ event: VolumeChangeEvent) {
        self.notifyListeners(Self.eventVolumeChange, data: event.toJSObject() as? [String: Any])
    }

    @objc func setVolume(_ call: CAPPluginCall) {
        do {
            let options = try SetVolumeOptions(call)

            implementation?.setVolume(options, completion: { error in
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

    @objc func startWatching(_ call: CAPPluginCall) {
        let options = StartWatchingOptions(call)

        implementation?.startWatching(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    @objc func stopWatching(_ call: CAPPluginCall) {
        implementation?.stopWatching { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", VolumePlugin.tag, "] ", error)
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
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
