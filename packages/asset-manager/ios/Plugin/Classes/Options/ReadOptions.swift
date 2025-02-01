import Foundation
import Capacitor

@objc public class ReadOptions: NSObject {
    public let encoding: String?
    public let path: String

    init(_ call: CAPPluginCall) throws {
        self.encoding = call.getString("encoding")
        self.path = try ReadOptions.getPathFromCall(call)
    }

    private static func getPathFromCall(_ call: CAPPluginCall) throws -> String {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        return path
    }
}
