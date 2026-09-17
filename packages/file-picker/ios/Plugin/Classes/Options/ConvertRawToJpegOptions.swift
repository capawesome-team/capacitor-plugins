import Foundation
import Capacitor

@objc public class ConvertRawToJpegOptions: NSObject {
    private let url: URL

    init(_ call: CAPPluginCall) throws {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        guard let url = URL(string: path) else {
            throw CustomError.invalidPath
        }
        guard FileManager.default.fileExists(atPath: url.path) else {
            throw CustomError.fileNotFound
        }
        self.url = url
    }

    func getUrl() -> URL {
        return url
    }
}
