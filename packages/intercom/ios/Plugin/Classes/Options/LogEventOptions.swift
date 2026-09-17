import Foundation
import Capacitor

@objc public class LogEventOptions: NSObject {
    let data: [String: Any]?
    let name: String

    init(_ call: CAPPluginCall) throws {
        guard let name = call.getString("name") else {
            throw CustomError.nameMissing
        }
        self.name = name
        self.data = call.getObject("data")
    }
}
