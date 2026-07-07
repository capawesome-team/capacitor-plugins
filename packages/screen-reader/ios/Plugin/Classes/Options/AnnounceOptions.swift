import Foundation
import Capacitor

@objc public class AnnounceOptions: NSObject {
    let value: String
    let language: String?

    init(_ call: CAPPluginCall) throws {
        guard let value = call.getString("value") else {
            throw CustomError.valueMissing
        }
        self.value = value
        self.language = call.getString("language")
    }
}
