import Foundation
import Capacitor

@objc public class IdentifyOptions: NSObject {
    private var distinctId: String
    private var userProperties: [String: Any]?

    init(distinctId: String, userProperties: JSObject?) {
        self.distinctId = distinctId
        self.userProperties = PosthogHelper.createHashMapFromJSObject(userProperties)
    }

    func getDistinctId() -> String {
        return distinctId
    }

    func getUserProperties() -> [String: Any]? {
        return userProperties
    }
}
