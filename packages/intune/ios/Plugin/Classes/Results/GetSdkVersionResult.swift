import Foundation
import Capacitor

@objc public class GetSdkVersionResult: NSObject, Result {
    let intuneSdkVersion: String
    let msalVersion: String?

    init(intuneSdkVersion: String, msalVersion: String?) {
        self.intuneSdkVersion = intuneSdkVersion
        self.msalVersion = msalVersion
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["intuneSdkVersion"] = intuneSdkVersion
        result["msalVersion"] = msalVersion == nil ? NSNull() : msalVersion
        return result as AnyObject
    }
}
