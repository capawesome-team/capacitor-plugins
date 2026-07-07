import Foundation
import Capacitor

@objc public class ComposeSmsOptions: NSObject {
    let body: String?
    let recipients: [String]?

    init(_ call: CAPPluginCall) throws {
        self.recipients = call.getArray("recipients", String.self)
        self.body = call.getString("body")
    }
}
