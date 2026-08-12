import Foundation
import Capacitor

@objc public class ReadExifOptions: NSObject {
    let url: URL

    init(_ call: CAPPluginCall) throws {
        self.url = try ReadExifOptions.getUrlFromCall(call)
    }

    static func getUrlFromCall(_ call: CAPPluginCall) throws -> URL {
        guard let path = call.getString("path"), !path.isEmpty else {
            throw CustomError.pathMissing
        }
        if path.hasPrefix("file://"), let url = URL(string: path) {
            return url
        }
        return URL(fileURLWithPath: path)
    }
}
