import Foundation
import Capacitor

@objc(PdfGeneratorPlugin)
public class PdfGeneratorPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "PdfGeneratorPlugin"
    public let jsName = "PdfGenerator"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "generateFromHtml", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "generateFromUrl", returnType: CAPPluginReturnPromise)
    ]
    public static let tag = "PdfGeneratorPlugin"

    private var implementation: PdfGenerator?

    override public func load() {
        self.implementation = PdfGenerator(plugin: self)
    }

    @objc func generateFromHtml(_ call: CAPPluginCall) {
        do {
            let options = try GenerateFromHtmlOptions(call)
            implementation?.generateFromHtml(options) { result, error in
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

    @objc func generateFromUrl(_ call: CAPPluginCall) {
        do {
            let options = try GenerateFromUrlOptions(call)
            implementation?.generateFromUrl(options) { result, error in
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
        CAPLog.print("[", PdfGeneratorPlugin.tag, "] ", error)
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
