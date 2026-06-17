import Foundation
import Capacitor

@objc public class CaptureExceptionOptions: NSObject {
    private var message: String
    private var name: String?
    private var stack: String?
    private var properties: [String: Any]?

    init(message: String, name: String?, stack: String?, properties: JSObject?) {
        self.message = message
        self.name = name
        self.stack = stack
        self.properties = PosthogHelper.createHashMapFromJSObject(properties)
    }

    func getMessage() -> String {
        return message
    }

    func getName() -> String? {
        return name
    }

    func getStack() -> String? {
        return stack
    }

    func getProperties() -> [String: Any]? {
        return properties
    }
}
