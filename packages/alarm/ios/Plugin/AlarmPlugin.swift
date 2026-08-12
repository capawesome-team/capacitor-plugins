import Foundation
import Capacitor

@objc(AlarmPlugin)
public class AlarmPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AlarmPlugin"
    public let jsName = "Alarm"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "cancelAlarm", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "createAlarm", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "createTimer", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getAlarms", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openAlarms", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "AlarmPlugin"

    private var implementation: Any?

    override public func load() {
        if #available(iOS 26.0, *) {
            self.implementation = Alarm(plugin: self)
        }
    }

    @objc func cancelAlarm(_ call: CAPPluginCall) {
        guard #available(iOS 26.0, *), let implementation = implementation as? Alarm else {
            rejectCallAsUnavailable(call)
            return
        }
        do {
            let options = try CancelAlarmOptions(call)
            implementation.cancelAlarm(options, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call)
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        guard #available(iOS 26.0, *), let implementation = implementation as? Alarm else {
            rejectCallAsUnavailable(call)
            return
        }
        implementation.checkPermissions(completion: { result in
            self.resolveCall(call, result)
        })
    }

    @objc func createAlarm(_ call: CAPPluginCall) {
        guard #available(iOS 26.0, *), let implementation = implementation as? Alarm else {
            rejectCallAsUnavailable(call)
            return
        }
        do {
            let options = try CreateAlarmOptions(call)
            implementation.createAlarm(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call, result)
            })
        } catch {
            self.rejectCall(call, error)
        }
    }

    @objc func createTimer(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc func getAlarms(_ call: CAPPluginCall) {
        guard #available(iOS 26.0, *), let implementation = implementation as? Alarm else {
            rejectCallAsUnavailable(call)
            return
        }
        implementation.getAlarms(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        })
    }

    @objc func isAvailable(_ call: CAPPluginCall) {
        if #available(iOS 26.0, *), let implementation = implementation as? Alarm {
            implementation.isAvailable(completion: { result in
                self.resolveCall(call, result)
            })
        } else {
            resolveCall(call, IsAvailableResult(available: false))
        }
    }

    @objc func openAlarms(_ call: CAPPluginCall) {
        rejectCallAsUnimplemented(call)
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        guard #available(iOS 26.0, *), let implementation = implementation as? Alarm else {
            rejectCallAsUnavailable(call)
            return
        }
        implementation.requestPermissions(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        })
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", AlarmPlugin.tag, "] ", error)
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
