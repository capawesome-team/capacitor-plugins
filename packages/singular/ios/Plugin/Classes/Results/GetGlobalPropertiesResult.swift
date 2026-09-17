import Foundation
import Capacitor

@objc public class GetGlobalPropertiesResult: NSObject, Result {
    private let properties: [AnyHashable: Any]?

    init(properties: [AnyHashable: Any]?) {
        self.properties = properties
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["properties"] = SingularHelper.createStringJSObjectFromHashMap(properties)
        return result as AnyObject
    }
}
