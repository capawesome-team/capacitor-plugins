import Foundation
import Capacitor

@objc public class SetNextBundleOptions: NSObject {
    private var bundleId: String?

    init(_ call: CAPPluginCall) {
        self.bundleId = call.getString("bundleId")
    }

    func getBundleId() -> String? {
        return bundleId
    }
}
