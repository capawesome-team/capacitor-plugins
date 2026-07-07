import Foundation
import Capacitor

@objc public class GetLanguageResult: NSObject, Result {
    let languageTag: String?

    init(languageTag: String?) {
        self.languageTag = languageTag
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["languageTag"] = languageTag == nil ? NSNull() : languageTag
        return result as AnyObject
    }
}
