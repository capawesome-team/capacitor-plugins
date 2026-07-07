import Foundation
import Capacitor

@objc public class OpenUrlOptions: NSObject {
    let url: String

    init(_ call: CAPPluginCall) throws {
        guard let url = call.getString("url") else {
            throw CustomError.urlMissing
        }
        self.url = url
    }
}
