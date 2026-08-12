import Foundation
import Capacitor

@objc public class SetSessionIntOptions: NSObject {
    let key: String
    let value: Int

    init(_ call: CAPPluginCall) throws {
        guard let key = call.getString("key") else {
            throw CustomError.keyMissing
        }
        guard let value = call.getInt("value") else {
            throw CustomError.valueMissing
        }
        self.key = key
        self.value = value
    }
}
