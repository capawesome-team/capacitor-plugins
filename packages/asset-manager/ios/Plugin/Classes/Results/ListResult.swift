import Foundation
import Capacitor

@objc public class ListResult: NSObject, Result {
    let files: [String]

    init(files: [String]) {
        self.files = files
    }

    public func toJSObject() -> AnyObject {
        var filesResult = JSArray()
        for file in files {
            filesResult.append(file)
        }
        
        var result = JSObject()
        result["files"] = filesResult
        return result as AnyObject
    }
}
