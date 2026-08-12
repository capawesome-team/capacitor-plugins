import Foundation
import Capacitor

@objc public class GetInfoResult: NSObject, Result {
    let format: String?
    let height: Int
    let width: Int

    init(format: String?, height: Int, width: Int) {
        self.format = format
        self.height = height
        self.width = width
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["format"] = format == nil ? NSNull() : format
        result["height"] = height
        result["width"] = width
        return result as AnyObject
    }
}
