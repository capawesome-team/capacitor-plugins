import Foundation
import Capacitor

@objc public class SetNextBundleOptions: NSObject {
    private var bundleId: String

    init(_ call: CAPPluginCall) throws {
        if let bundleId = call.getString("bundleId") {
            self.bundleId = bundleId
        } else {
            throw CustomError.bundleIdMissing
        }
    }

    func getBundleId() -> String {
        return bundleId
    }
}
