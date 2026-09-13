import Foundation
import Capacitor
import Singular

@objc public class SingularLinkResolvedEvent: NSObject, Result {
    private let params: SingularLinkParams

    init(params: SingularLinkParams) {
        self.params = params
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        if let deepLink = params.getDeepLink() {
            result["deepLink"] = deepLink
        } else {
            result["deepLink"] = NSNull()
        }
        result["isDeferred"] = params.isDeferred()
        if let passthrough = params.getPassthrough() {
            result["passthrough"] = passthrough
        } else {
            result["passthrough"] = NSNull()
        }
        result["urlParameters"] = SingularHelper.createStringJSObjectFromHashMap(params.getUrlParameters())
        return result as AnyObject
    }
}
