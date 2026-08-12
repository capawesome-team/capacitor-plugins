import Foundation
import Capacitor

@objc(ExifPlugin)
public class ExifPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "ExifPlugin"
    public let jsName = "Exif"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "readExif", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeExif", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "writeExif", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "ExifPlugin"

    private var implementation: Exif?

    override public func load() {
        self.implementation = Exif(plugin: self)
    }

    @objc func readExif(_ call: CAPPluginCall) {
        do {
            let options = try ReadExifOptions(call)
            implementation?.readExif(options) { result, error in
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

    @objc func removeExif(_ call: CAPPluginCall) {
        do {
            let options = try RemoveExifOptions(call)
            implementation?.removeExif(options) { error in
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

    @objc func writeExif(_ call: CAPPluginCall) {
        do {
            let options = try WriteExifOptions(call)
            implementation?.writeExif(options) { error in
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
        CAPLog.print("[", ExifPlugin.tag, "] ", error)
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
