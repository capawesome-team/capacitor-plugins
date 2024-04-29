import Foundation
import Capacitor

@objc public class DeleteBundleOptions: NSObject {
    private var bundleId: String

    init(bundleId: String) {
        self.bundleId = bundleId
    }

    func getBundleId() -> String {
        return bundleId
    }
}
