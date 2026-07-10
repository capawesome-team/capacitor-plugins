import Foundation
import Capacitor

@objc public class StartWatchingOptions: NSObject {
    let suppressVolumeChange: Bool

    init(_ call: CAPPluginCall) {
        self.suppressVolumeChange = call.getBool("suppressVolumeChange", false)
    }
}
