import Foundation
import Capacitor

@objc public class AudioSessionOutput: NSObject, Result {
    private let portName: String
    private let portType: String

    init(portType: String, portName: String) {
        self.portType = portType
        self.portName = portName
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["portType"] = portType
        result["portName"] = portName
        return result as AnyObject
    }
}
