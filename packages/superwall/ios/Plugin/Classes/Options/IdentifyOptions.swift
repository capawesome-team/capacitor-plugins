import Foundation
import Capacitor

@objc public class IdentifyOptions: NSObject {
    let userId: String
    let restorePaywallAssignments: Bool

    init(_ call: CAPPluginCall) throws {
        self.userId = try IdentifyOptions.getUserIdFromCall(call)
        self.restorePaywallAssignments = IdentifyOptions.getRestorePaywallAssignmentsFromCall(call)
    }

    private static func getUserIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let userId = call.getString("userId") else {
            throw CustomError.userIdMissing
        }
        return userId
    }

    private static func getRestorePaywallAssignmentsFromCall(_ call: CAPPluginCall) -> Bool {
        if let optionsObj = call.getObject("options") as? [String: Any] {
            return optionsObj["restorePaywallAssignments"] as? Bool ?? false
        }
        return false
    }
}
