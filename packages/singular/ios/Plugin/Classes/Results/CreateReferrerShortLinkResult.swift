import Foundation
import Capacitor

@objc public class CreateReferrerShortLinkResult: NSObject, Result {
    private let link: String

    init(link: String) {
        self.link = link
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["link"] = link
        return result as AnyObject
    }
}
