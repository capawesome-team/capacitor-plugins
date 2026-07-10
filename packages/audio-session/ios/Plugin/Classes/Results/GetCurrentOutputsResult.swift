import Foundation
import Capacitor

@objc public class GetCurrentOutputsResult: NSObject, Result {
    private let outputs: [AudioSessionOutput]

    init(outputs: [AudioSessionOutput]) {
        self.outputs = outputs
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["outputs"] = outputs.map { $0.toJSObject() }
        return result as AnyObject
    }
}
