import Foundation
import Capacitor

@objc public class IsFileEncryptedOptions: NSObject {
    let path: String

    init(_ call: CAPPluginCall) throws {
        self.path = try IsFileEncryptedOptions.getPathFromCall(call)
    }

    private static func getPathFromCall(_ call: CAPPluginCall) throws -> String {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        return IntuneHelper.getFilePath(path)
    }
}
