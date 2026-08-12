import Foundation
import Capacitor

@objc public class IsIntercomPushNotificationOptions: NSObject {
    let data: [String: Any]

    init(_ call: CAPPluginCall) throws {
        guard let data = call.getObject("data") else {
            throw CustomError.dataMissing
        }
        self.data = data
    }
}
