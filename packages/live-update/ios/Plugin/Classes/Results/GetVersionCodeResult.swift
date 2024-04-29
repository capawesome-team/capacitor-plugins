import Foundation
import Capacitor

@objc public class GetVersionCodeResult: NSObject, Result {
    let versionCode: String

    init(versionCode: String) {
        self.versionCode = versionCode
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["versionCode"] = versionCode
        return result as AnyObject
    }
}
