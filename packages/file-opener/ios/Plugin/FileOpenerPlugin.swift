import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FileOpenerPlugin)
public class FileOpenerPlugin: CAPPlugin {
    public let errorPathMissing = "path must be provided."
    public let errorFileNotExist = "File does not exist."
    public let errorCannotOpenFile = "File cannot be opened."

    private var implementation: FileOpener?

    override public func load() {
        self.implementation = FileOpener(plugin: self)
    }

    @objc func openFile(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }
        let mimeType = call.getString("mimeType")
        guard let url = implementation?.getFileUrlByPath(path) else {
            call.reject(errorFileNotExist)
            return
        }

        implementation?.openFile(url: url, mimeType: mimeType, completion: {completionResult in
            if completionResult == false {
                call.reject(self.errorCannotOpenFile)
            } else {
                call.resolve()
            }
        })
    }
}
