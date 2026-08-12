import Foundation
import Capacitor
import UIKit

@objc public class NotificationOptions: NSObject {
    let type: UINotificationFeedbackGenerator.FeedbackType

    init(_ call: CAPPluginCall) throws {
        self.type = try NotificationOptions.getTypeFromCall(call)
    }

    private static func getTypeFromCall(_ call: CAPPluginCall) throws -> UINotificationFeedbackGenerator.FeedbackType {
        let type = call.getString("type", "SUCCESS")
        switch type {
        case "ERROR":
            return .error
        case "SUCCESS":
            return .success
        case "WARNING":
            return .warning
        default:
            throw CustomError.typeInvalid
        }
    }
}
