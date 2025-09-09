import Foundation
import Capacitor
import Libsql

@objc public class QueryResult: NSObject, Result {
    let rows: [[Any]]

    init(rows: Rows, columns: [String]) {
        self.rows = QueryResult.convertRowsToAnyArray(rows, columns)
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["rows"] = rows
        return result as AnyObject
    }

    private static func convertRowsToAnyArray(_ rows: Rows, _ columns: [String]) -> [[Any]] {
        var rowsResult: [[Any]] = []
        while let row = rows.next() {
            var rowResult: [Any] = []
            var index = Int32(0)
            for _ in columns {
                do {
                    let value = try row.get(index)
                    rowResult.append(value)
                    index += 1
                } catch {
                    break // Exit the loop when an error occurs (e.g., no more columns)
                }
            }
            rowsResult.append(rowResult)
        }
        return rowsResult
    }
}
