import Foundation
import Capacitor

@objc public class ExecuteBatchOptions: NSObject {
    let connectionId: String
    let statement: [String]
    let values: [[Any]]?

    init(_ call: CAPPluginCall) throws {
        guard let connectionId = call.getString("connectionId") else {
            throw LibsqlError.connectionIdMissing
        }
        guard let statementArray = call.getArray("statement") else {
            throw LibsqlError.statementMissing
        }
        
        var statements: [String] = []
        for item in statementArray.capacitor.replacingNullValues() {
            guard let statement = item as? String else {
                throw LibsqlError.invalidStatement
            }
            statements.append(statement)
        }
        
        self.connectionId = connectionId
        self.statement = statements
        
        if let valuesArray = call.getArray("values") {
            var allValues: [[Any]] = []
            for item in valuesArray.capacitor.replacingNullValues() {
                guard let subArray = item as? [Any] else {
                    throw LibsqlError.invalidValues
                }
                allValues.append(subArray)
            }
            self.values = allValues
        } else {
            self.values = nil
        }
    }
}