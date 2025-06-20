import Foundation
import Capacitor

@objc public class RollbackTransactionOptions: NSObject {
    let connectionId: String
    let transactionId: String

    init(_ call: CAPPluginCall) throws {
        guard let connectionId = call.getString("connectionId") else {
            throw LibsqlError.connectionIdMissing
        }
        guard let transactionId = call.getString("transactionId") else {
            throw LibsqlError.transactionIdMissing
        }

        self.connectionId = connectionId
        self.transactionId = transactionId
    }
}
