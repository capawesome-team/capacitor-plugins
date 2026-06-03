import Foundation
import Capacitor

@objc public class GetDownloadedBundlesResult: NSObject, Result {
    let bundleIds: [String]

    init(bundleIds: [String]) {
        self.bundleIds = bundleIds
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["bundleIds"] = bundleIds
        return result as AnyObject
    }
}
