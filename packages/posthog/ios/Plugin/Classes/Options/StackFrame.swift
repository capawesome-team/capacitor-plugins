import Foundation
import Capacitor

@objc public class StackFrame: NSObject {
    private var functionName: String?
    private var fileName: String?
    private var lineNumber: Int?
    private var columnNumber: Int?

    init(_ frame: JSObject) {
        self.functionName = frame["functionName"] as? String
        self.fileName = frame["fileName"] as? String
        self.lineNumber = (frame["lineNumber"] as? NSNumber)?.intValue
        self.columnNumber = (frame["columnNumber"] as? NSNumber)?.intValue
    }

    func getFunctionName() -> String? {
        return functionName
    }

    func getFileName() -> String? {
        return fileName
    }

    func getLineNumber() -> Int? {
        return lineNumber
    }

    func getColumnNumber() -> Int? {
        return columnNumber
    }
}
