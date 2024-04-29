import Foundation
import Capacitor

@objc public class GetVersionNameResult: NSObject, Result {
    let versionName: String

    init(versionName: String) {
        self.versionName = versionName
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["versionName"] = versionName
        return result as AnyObject
    }
}
