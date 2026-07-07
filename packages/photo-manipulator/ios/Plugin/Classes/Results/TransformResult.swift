import Foundation
import Capacitor

@objc public class TransformResult: NSObject, Result {
    let height: Int
    let url: URL
    let width: Int

    init(url: URL, height: Int, width: Int) {
        self.height = height
        self.url = url
        self.width = width
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["height"] = height
        result["path"] = url.absoluteString
        result["width"] = width
        return result as AnyObject
    }
}
