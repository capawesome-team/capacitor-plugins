import Foundation
import Capacitor

@objc public class ConfirmOptions: NSObject {
    let cancelButtonTitle: String
    let message: String
    let okButtonTitle: String
    let title: String?

    init(_ call: CAPPluginCall) throws {
        guard let message = call.getString("message") else {
            throw CustomError.messageMissing
        }
        self.message = message
        self.cancelButtonTitle = call.getString("cancelButtonTitle", "Cancel")
        self.okButtonTitle = call.getString("okButtonTitle", "OK")
        self.title = call.getString("title")
    }
}
