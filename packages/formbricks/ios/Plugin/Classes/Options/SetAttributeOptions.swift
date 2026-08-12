import Foundation
import Capacitor

@objc public class SetAttributeOptions: NSObject {
    private let key: String
    private let value: String

    init(call: CAPPluginCall) throws {
        guard let key = call.getString("key") else {
            throw CustomError.keyMissing
        }
        guard let value = call.getString("value") else {
            throw CustomError.valueMissing
        }
        self.key = key
        self.value = value
    }

    func getKey() -> String {
        return key
    }

    func getValue() -> String {
        return value
    }
}
