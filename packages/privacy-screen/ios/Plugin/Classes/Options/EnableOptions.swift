import Foundation
import Capacitor

@objc public class EnableOptions: NSObject {
    let preventScreenshots: Bool

    init(_ call: CAPPluginCall) {
        let iosOptions = call.getObject("ios")
        self.preventScreenshots = iosOptions?["preventScreenshots"] as? Bool ?? false
    }
}
