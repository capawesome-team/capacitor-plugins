import Capacitor
import Foundation

@objc public class SetStyleOptions: NSObject {
    let json: String?
    let mapId: String
    let url: URL?

    init(_ call: CAPPluginCall) throws {
        let json = call.getString("json")
        let urlValue = call.getString("url")
        guard (json == nil) != (urlValue == nil) else {
            throw CustomError.styleMissing
        }
        if let urlValue = urlValue {
            guard let url = URL(string: urlValue) else {
                throw CustomError.styleMissing
            }
            self.url = url
        } else {
            self.url = nil
        }
        self.json = json
        self.mapId = try MapLibreHelper.getString(call, "mapId", .mapIdMissing)
    }
}
