import Foundation
import Capacitor

@objc public class CaptureExceptionOptions: NSObject {
    private var exception: Any
    private var properties: [String: Any]?

    init(exception: Any, properties: JSObject?) {
        self.exception = exception
        self.properties = PosthogHelper.createHashMapFromJSObject(properties)
    }

    func getException() -> Any {
        return exception
    }

    func getProperties() -> [String: Any]? {
        return properties
    }
}
