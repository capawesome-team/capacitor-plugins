import Foundation
import Capacitor

@objc public class GetReadersResult: NSObject, Result {
    let readers: [ReaderInfo]

    init(readers: [ReaderInfo]) {
        self.readers = readers
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["readers"] = readers.map { $0.toJSObject() }
        return result as AnyObject
    }
}
