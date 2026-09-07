import Foundation
import Capacitor

@objc public class TrackEventOptions: NSObject {
    let id: String?
    let name: String
    let properties: JSObject?

    init(_ call: CAPPluginCall) throws {
        guard let name = call.getString("name") else {
            throw CustomError.nameMissing
        }
        self.name = name
        self.id = call.getString("id")
        self.properties = call.getObject("properties")
    }
}
