import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FileOpenerPlugin)
public class FileOpenerPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FileOpenerPlugin"
    public let jsName = "FileOpener"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "openFile", returnType: CAPPluginReturnPromise)
    ]

    private var implementation: FileOpener?

    override public func load() {
        self.implementation = FileOpener(plugin: self)
    }

    @objc func openFile(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            rejectCall(call, CustomError.pathMissing)
            return
        }
        let mimeType = call.getString("mimeType")
        guard let url = implementation?.getFileUrlByPath(path) else {
            rejectCall(call, CustomError.fileNotFound)
            return
        }

        implementation?.openFile(url: url, mimeType: mimeType, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                call.resolve()
            }
        })
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        let code = (error as? CustomError)?.code
        call.reject(error.localizedDescription, code)
    }
}
