import Foundation
import Capacitor

@objc public class DisconnectButtonByIdOptions: NSObject {
    let id: String

    init(_ call: CAPPluginCall) throws {
        self.id = try DisconnectButtonByIdOptions.getIdFromCall(call)
    }

    private static func getIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let id = call.getString("id") else {
            throw CustomError.idMissing
        }
        return id
    }
}
