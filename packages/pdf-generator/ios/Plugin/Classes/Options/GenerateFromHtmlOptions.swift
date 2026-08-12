import Foundation
import Capacitor

@objc public class GenerateFromHtmlOptions: GenerateOptions {
    let baseUrl: URL?
    let html: String

    override init(_ call: CAPPluginCall) throws {
        self.baseUrl = GenerateFromHtmlOptions.getBaseUrlFromCall(call)
        self.html = try GenerateFromHtmlOptions.getHtmlFromCall(call)
        try super.init(call)
    }

    private static func getBaseUrlFromCall(_ call: CAPPluginCall) -> URL? {
        guard let baseUrl = call.getString("baseUrl") else {
            return nil
        }
        return URL(string: baseUrl)
    }

    private static func getHtmlFromCall(_ call: CAPPluginCall) throws -> String {
        guard let html = call.getString("html") else {
            throw CustomError.htmlMissing
        }
        return html
    }
}
