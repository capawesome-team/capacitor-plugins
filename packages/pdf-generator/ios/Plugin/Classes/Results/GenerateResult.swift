import Foundation
import Capacitor

@objc public class GenerateResult: NSObject, Result {
    let url: URL

    init(url: URL) {
        self.url = url
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["path"] = url.absoluteString
        return result as AnyObject
    }
}
