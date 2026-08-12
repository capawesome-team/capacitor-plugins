import Foundation
import Capacitor

@objc public class DialOptions: NSObject {
    let number: String

    init(_ call: CAPPluginCall) throws {
        guard let number = call.getString("number") else {
            throw CustomError.numberMissing
        }
        let allowed = CharacterSet(charactersIn: "0123456789+*#")
        let sanitized = number.components(separatedBy: allowed.inverted).joined()
        if sanitized.isEmpty {
            throw CustomError.numberInvalid
        }
        self.number = sanitized
    }
}
