import Foundation
import Capacitor

@objc(PhotoManipulatorPlugin)
public class PhotoManipulatorPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "PhotoManipulatorPlugin"
    public let jsName = "PhotoManipulator"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getInfo", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "transform", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "PhotoManipulatorPlugin"

    private var implementation: PhotoManipulator?

    override public func load() {
        self.implementation = PhotoManipulator(plugin: self)
    }

    @objc func getInfo(_ call: CAPPluginCall) {
        do {
            let options = try GetInfoOptions(call)
            implementation?.getInfo(options) { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call, result)
            }
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func transform(_ call: CAPPluginCall) {
        do {
            let options = try TransformOptions(call)
            implementation?.transform(options) { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call, result)
            }
        } catch {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", PhotoManipulatorPlugin.tag, "] ", error)
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
