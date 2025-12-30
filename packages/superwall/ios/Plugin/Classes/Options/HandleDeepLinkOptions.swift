import Foundation
import Capacitor

@objc public class HandleDeepLinkOptions: NSObject {
    let url: String

    init(_ call: CAPPluginCall) throws {
        self.url = try HandleDeepLinkOptions.getUrlFromCall(call)
    }

    private static func getUrlFromCall(_ call: CAPPluginCall) throws -> String {
        guard let url = call.getString("url") else {
            throw CustomError.urlMissing
        }
        return url
    }
}
