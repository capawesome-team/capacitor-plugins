import Foundation
import Capacitor

@objc public class CopyOptions: NSObject {
    public let from: String
    public let to: String

    init(_ call: CAPPluginCall) throws {
        self.from = try CopyOptions.getFromFromCall(call)
        self.to = try CopyOptions.getToFromCall(call)
    }

    private static func getFromFromCall(_ call: CAPPluginCall) throws -> String {
        guard let from = call.getString("from") else {
            throw CustomError.fromMissing
        }
        return from
    }

    private static func getToFromCall(_ call: CAPPluginCall) throws -> String {
        guard let to = call.getString("to") else {
            throw CustomError.toMissing
        }
        return to
    }
}
