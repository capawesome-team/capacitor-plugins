import Foundation
import Capacitor

@objc public class GetCookiesOptions: NSObject {
    let url: URL

    init(_ call: CAPPluginCall) throws {
        self.url = try GetCookiesOptions.getUrlFromCall(call)
    }

    private static func getUrlFromCall(_ call: CAPPluginCall) throws -> URL {
        guard let urlString = call.getString("url"), !urlString.isEmpty else {
            throw CustomError.urlMissing
        }
        guard let url = URL(string: urlString) else {
            throw CustomError.urlInvalid
        }
        return url
    }
}
