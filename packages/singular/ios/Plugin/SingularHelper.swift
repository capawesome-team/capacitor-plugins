import Foundation
import Capacitor

public class SingularHelper {
    public static func createHashMapFromJSObject(_ object: JSObject?) -> [String: Any]? {
        guard let object = object else {
            return nil
        }
        var map: [String: Any] = [:]
        for key in object.keys {
            if let value = object[key] {
                map[key] = value
            }
        }
        return map
    }

    public static func createStringHashMapFromJSObject(_ object: JSObject?) -> [String: String]? {
        guard let object = object else {
            return nil
        }
        var map: [String: String] = [:]
        for key in object.keys {
            if let value = object[key] as? String {
                map[key] = value
            }
        }
        return map
    }

    public static func createStringJSObjectFromHashMap(_ map: [AnyHashable: Any]?) -> JSObject {
        var object = JSObject()
        for (key, value) in map ?? [:] {
            if let key = key as? String, let value = value as? String {
                object[key] = value
            }
        }
        return object
    }
}
