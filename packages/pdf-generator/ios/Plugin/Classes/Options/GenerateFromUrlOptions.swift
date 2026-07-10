import Foundation
import Capacitor

@objc public class GenerateFromUrlOptions: GenerateOptions {
    let url: URL

    override init(_ call: CAPPluginCall) throws {
        self.url = try GenerateFromUrlOptions.getUrlFromCall(call)
        try super.init(call)
    }

    private static func getUrlFromCall(_ call: CAPPluginCall) throws -> URL {
        guard let urlString = call.getString("url") else {
            throw CustomError.urlMissing
        }
        guard let url = URL(string: urlString) else {
            throw CustomError.urlInvalid
        }
        return url
    }
}
