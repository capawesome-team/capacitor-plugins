import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(PhotoEditorPlugin)
public class PhotoEditorPlugin: CAPPlugin {
    private let implementation = PhotoEditor()

    @objc func editPhoto(_ call: CAPPluginCall) {
        call.reject("Not available on iOS.")
    }
}
