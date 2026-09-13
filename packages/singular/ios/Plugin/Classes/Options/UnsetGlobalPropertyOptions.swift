import Foundation
import Capacitor

@objc public class UnsetGlobalPropertyOptions: NSObject {
    let key: String

    init(_ call: CAPPluginCall) throws {
        guard let key = call.getString("key") else {
            throw CustomError.keyMissing
        }
        self.key = key
    }
}
