import Foundation
import Capacitor

@objc public class AlertOptions: NSObject {
    let buttonTitle: String
    let message: String
    let title: String?

    init(_ call: CAPPluginCall) throws {
        guard let message = call.getString("message") else {
            throw CustomError.messageMissing
        }
        self.message = message
        self.buttonTitle = call.getString("buttonTitle", "OK")
        self.title = call.getString("title")
    }
}
