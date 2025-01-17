import Foundation
import Capacitor

@objc public class ScreenOptions: NSObject {
    private var screenTitle: String
    private var properties: [String: Any]?

    init(screenTitle: String, properties: JSObject?) {
        self.screenTitle = screenTitle
        self.properties = PosthogHelper.createHashMapFromJSObject(properties)
    }

    func getScreenTitle() -> String {
        return screenTitle
    }

    func getProperties() -> [String: Any]? {
        return properties
    }
}
