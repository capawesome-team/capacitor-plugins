import Foundation
import Capacitor

@objc public class CancelAlarmOptions: NSObject {
    let id: UUID

    init(_ call: CAPPluginCall) throws {
        self.id = try CancelAlarmOptions.getIdFromCall(call)
    }

    private static func getIdFromCall(_ call: CAPPluginCall) throws -> UUID {
        guard let id = call.getString("id") else {
            throw CustomError.idMissing
        }
        guard let uuid = UUID(uuidString: id) else {
            throw CustomError.idInvalid
        }
        return uuid
    }
}
