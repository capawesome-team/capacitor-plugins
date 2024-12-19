import Foundation
import Capacitor

@objc public class FetchLatestBundleResult: NSObject, Result {
    let bundleId: String?
    let downloadUrl: String?

    init(bundleId: String?, downloadUrl: String?) {
        self.bundleId = bundleId
        self.downloadUrl = downloadUrl
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["bundleId"] = bundleId == nil ? NSNull() : bundleId
        if let downloadUrl = downloadUrl {
            result["downloadUrl"] = downloadUrl
        }
        return result as AnyObject
    }
}
