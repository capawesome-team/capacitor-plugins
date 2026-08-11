import Foundation
import Capacitor

@objc public class ClearCookiesOptions: NSObject {
    let url: URL?

    init(_ call: CAPPluginCall) throws {
        self.url = try ClearCookiesOptions.getUrlFromCall(call)
    }

    private static func getUrlFromCall(_ call: CAPPluginCall) throws -> URL? {
        guard let urlString = call.getString("url"), !urlString.isEmpty else {
            return nil
        }
        guard let url = URL(string: urlString) else {
            throw CustomError.urlInvalid
        }
        return url
    }
}
