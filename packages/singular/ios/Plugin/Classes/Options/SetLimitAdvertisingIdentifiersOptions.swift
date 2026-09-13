import Foundation
import Capacitor

@objc public class SetLimitAdvertisingIdentifiersOptions: NSObject {
    let limit: Bool

    init(_ call: CAPPluginCall) throws {
        guard let limit = call.getBool("limit") else {
            throw CustomError.limitMissing
        }
        self.limit = limit
    }
}
