import Foundation
import Capacitor

@objc public class RouteChangeEvent: NSObject, Result {
    private let outputs: [AudioSessionOutput]
    private let reason: String

    init(reason: String, outputs: [AudioSessionOutput]) {
        self.reason = reason
        self.outputs = outputs
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["reason"] = reason
        result["outputs"] = outputs.map { $0.toJSObject() }
        return result as AnyObject
    }
}
