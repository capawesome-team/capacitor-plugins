import Foundation
import Capacitor

@objc public class OpenOptions: NSObject {
    let page: Int
    let password: String?
    let path: String
    let title: String?

    init(_ call: CAPPluginCall) throws {
        self.page = call.getInt("page", 1)
        self.password = call.getString("password")
        self.path = try OpenOptions.getPathFromCall(call)
        self.title = call.getString("title")
    }

    private static func getPathFromCall(_ call: CAPPluginCall) throws -> String {
        guard let path = call.getString("path") else {
            throw CustomError.pathMissing
        }
        return path
    }
}
