import Foundation
import Capacitor

@objc public class TrackEventOptions: NSObject {
    let attributes: [String: Any]?
    let name: String

    init(_ call: CAPPluginCall) throws {
        guard let name = call.getString("name") else {
            throw CustomError.nameMissing
        }
        self.name = name
        self.attributes = SingularHelper.createHashMapFromJSObject(call.getObject("attributes"))
    }
}
