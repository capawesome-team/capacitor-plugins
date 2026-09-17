import Foundation
import Capacitor

@objc public class SetSessionStringOptions: NSObject {
    let key: String
    let value: String

    init(_ call: CAPPluginCall) throws {
        guard let key = call.getString("key") else {
            throw CustomError.keyMissing
        }
        guard let value = call.getString("value") else {
            throw CustomError.valueMissing
        }
        self.key = key
        self.value = value
    }
}
