import Foundation
import Capacitor

@objc public class PresentMessageComposerOptions: NSObject {
    let initialMessage: String?

    init(_ call: CAPPluginCall) {
        self.initialMessage = call.getString("initialMessage")
    }
}
