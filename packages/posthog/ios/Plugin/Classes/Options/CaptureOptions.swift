import Foundation
import Capacitor

@objc public class CaptureOptions: NSObject {
    private var event: String
    private var properties: [String: Any]?

    init(event: String, properties: JSObject?) {
        self.event = event
        self.properties = PosthogHelper.createHashMapFromJSObject(properties)
    }

    func getEvent() -> String {
        return event
    }

    func getProperties() -> [String: Any]? {
        return properties
    }
}
