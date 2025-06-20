import Foundation
import Capacitor

@objc public class QueryOptions: NSObject {
    let connectionId: String
    let statement: String
    let transactionId: String?
    let values: [Any]?

    init(_ call: CAPPluginCall) throws {
        guard let connectionId = call.getString("connectionId") else {
            throw LibsqlError.connectionIdMissing
        }
        guard let statement = call.getString("statement") else {
            throw LibsqlError.statementMissing
        }

        self.connectionId = connectionId
        self.statement = statement
        self.transactionId = call.getString("transactionId")
        self.values = call.getArray("values")?.capacitor.replacingNullValues()
    }
}
