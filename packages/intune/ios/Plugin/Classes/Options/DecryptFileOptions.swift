import Foundation
import Capacitor

@objc public class DecryptFileOptions: NSObject {
    let destination: String?
    let path: String

    init(_ call: CAPPluginCall) throws {
        self.destination = DecryptFileOptions.getDestinationFromCall(call)
        self.path = try DecryptFileOptions.getPathFromCall(call)
    }

    private static func getDestinationFromCall(_ call: CAPPluginCall) -> String? {
        guard let destination = call.getString("destination") else {
            return nil
        }
        return IntuneHelper.getFilePath(destination)
    }

    private static func getPathFromCall(_ call: CAPPluginCall) throws -> String {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        return IntuneHelper.getFilePath(path)
    }
}
