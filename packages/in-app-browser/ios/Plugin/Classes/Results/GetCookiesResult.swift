import Foundation
import Capacitor

@objc public class GetCookiesResult: NSObject, Result {
    let cookies: [String: String]

    init(cookies: [String: String]) {
        self.cookies = cookies
    }

    @objc public func toJSObject() -> AnyObject {
        var cookies = JSObject()
        for (key, value) in self.cookies {
            cookies[key] = value
        }
        var result = JSObject()
        result["cookies"] = cookies
        return result as AnyObject
    }
}
