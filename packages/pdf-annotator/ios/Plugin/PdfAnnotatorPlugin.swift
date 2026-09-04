import Foundation
import Capacitor

@objc(PdfAnnotatorPlugin)
public class PdfAnnotatorPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "PdfAnnotatorPlugin"
    public let jsName = "PdfAnnotator"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "open", returnType: CAPPluginReturnPromise)
    ]

    public static let tag = "PdfAnnotatorPlugin"

    private var implementation: PdfAnnotator?

    @objc func isAvailable(_ call: CAPPluginCall) {
        implementation?.isAvailable(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call, result)
        })
    }

    override public func load() {
        self.implementation = PdfAnnotator(plugin: self)
    }

    @objc func open(_ call: CAPPluginCall) {
        do {
            let options = try OpenOptions(call)

            implementation?.open(options, completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                self.resolveCall(call, result)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", PdfAnnotatorPlugin.tag, "] ", error)
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
