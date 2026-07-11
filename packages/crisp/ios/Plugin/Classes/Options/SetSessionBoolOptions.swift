import Foundation
import Capacitor

@objc public class SetSessionBoolOptions: NSObject {
    let key: String
    let value: Bool

    init(_ call: CAPPluginCall) throws {
        guard let key = call.getString("key") else {
            throw CustomError.keyMissing
        }
        guard let value = call.getBool("value") else {
            throw CustomError.valueMissing
        }
        self.key = key
        self.value = value
    }
}
