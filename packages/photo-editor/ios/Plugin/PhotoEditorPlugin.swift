import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(PhotoEditorPlugin)
public class PhotoEditorPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "PhotoEditorPlugin"
    public let jsName = "PhotoEditor"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "editPhoto", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = PhotoEditor()

    @objc func editPhoto(_ call: CAPPluginCall) {
        call.reject("Not available on iOS.")
    }
}
