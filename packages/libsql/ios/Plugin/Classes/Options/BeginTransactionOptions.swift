import Foundation
import Capacitor

@objc public class BeginTransactionOptions: NSObject {
    let connectionId: String

    init(_ call: CAPPluginCall) throws {
        guard let connectionId = call.getString("connectionId") else {
            throw LibsqlError.connectionIdMissing
        }

        self.connectionId = connectionId
    }
}
