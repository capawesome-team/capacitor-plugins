import Foundation
import Capacitor

@objc public class SetPersonPropertiesOptions: NSObject {
    private var properties: [String: Any]?
    private var setOnceProperties: [String: Any]?

    init(call: CAPPluginCall) {
        self.properties = PosthogHelper.createHashMapFromJSObject(call.getObject("properties"))
        self.setOnceProperties = PosthogHelper.createHashMapFromJSObject(call.getObject("setOnceProperties"))
    }

    func getProperties() -> [String: Any]? {
        return properties
    }

    func getSetOnceProperties() -> [String: Any]? {
        return setOnceProperties
    }
}
