import Foundation
import Capacitor

@objc public class OpenInWebViewOptions: NSObject {
    let allowsBackForwardNavigationGestures: Bool
    let headers: [String: String]
    let isolatedDataStore: Bool
    let mediaPlaybackRequiresUserAction: Bool
    let overscroll: Bool
    let toolbar: WebViewToolbarOptions
    let url: URL
    let userAgent: String?

    init(_ call: CAPPluginCall) throws {
        let iosOptions = call.getObject("ios")
        self.allowsBackForwardNavigationGestures = iosOptions?["allowsBackForwardNavigationGestures"] as? Bool ?? false
        self.headers = OpenInWebViewOptions.getHeadersFromCall(call)
        self.isolatedDataStore = call.getString("dataStore") == "isolated"
        self.mediaPlaybackRequiresUserAction = call.getBool("mediaPlaybackRequiresUserAction", false)
        self.overscroll = iosOptions?["overscroll"] as? Bool ?? true
        self.toolbar = try WebViewToolbarOptions(call.getObject("toolbar"))
        self.url = try OpenInWebViewOptions.getUrlFromCall(call)
        self.userAgent = call.getString("userAgent")
    }

    private static func getHeadersFromCall(_ call: CAPPluginCall) -> [String: String] {
        var headers: [String: String] = [:]
        guard let headersObject = call.getObject("headers") else {
            return headers
        }
        for (key, value) in headersObject {
            if let value = value as? String {
                headers[key] = value
            }
        }
        return headers
    }

    private static func getUrlFromCall(_ call: CAPPluginCall) throws -> URL {
        guard let urlString = call.getString("url"), !urlString.isEmpty else {
            throw CustomError.urlMissing
        }
        guard let url = URL(string: urlString), let scheme = url.scheme, ["http", "https"].contains(scheme.lowercased()) else {
            throw CustomError.urlInvalid
        }
        return url
    }
}
