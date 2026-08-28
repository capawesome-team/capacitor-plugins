import Foundation
import Capacitor

@objc public class InitializeOptions: NSObject {
    let background: Bool

    init(_ call: CAPPluginCall) {
        self.background = call.getBool("iosBackground") ?? false
    }
}
