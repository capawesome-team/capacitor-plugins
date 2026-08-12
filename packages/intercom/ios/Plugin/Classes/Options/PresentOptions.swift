import Foundation
import Capacitor

@objc public class PresentOptions: NSObject {
    let space: String

    init(_ call: CAPPluginCall) {
        self.space = call.getString("space", "home")
    }
}
