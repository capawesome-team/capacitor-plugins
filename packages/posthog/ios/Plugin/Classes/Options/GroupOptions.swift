import Foundation
import Capacitor

@objc public class GroupOptions: NSObject {
    private var type: String
    private var key: String
    private var groupProperties: [String: Any]?

    init(type: String, key: String, groupProperties: [String: Any]?) {
        self.type = type
        self.key = key
        self.groupProperties = groupProperties
    }

    func getType() -> String {
        return type
    }

    func getKey() -> String {
        return key
    }

    func getGroupProperties() -> [String: Any]? {
        return groupProperties
    }
}
