import Capacitor
import Foundation

@objc public class StackFrameOptions: NSObject {
    private let columnNumber: NSNumber?
    private let fileName: String?
    private let functionName: String?
    private let lineNumber: NSNumber?

    init(source: JSObject) {
        self.columnNumber = source["columnNumber"] as? NSNumber
        self.fileName = source["fileName"] as? String
        self.functionName = source["functionName"] as? String
        self.lineNumber = source["lineNumber"] as? NSNumber
    }

    func getColumnNumber() -> NSNumber? {
        return columnNumber
    }

    func getFileName() -> String? {
        return fileName
    }

    func getFunctionName() -> String? {
        return functionName
    }

    func getLineNumber() -> NSNumber? {
        return lineNumber
    }
}
