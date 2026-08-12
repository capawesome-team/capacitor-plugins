import Foundation
import Capacitor

@objc public class GetInfoResult: NSObject, Result {
    let androidSdkVersion: Int?
    let deviceType: String
    let iosVersion: Int?
    let isVirtual: Bool
    let manufacturer: String
    let model: String
    let name: String?
    let operatingSystem: String
    let osVersion: String
    let platform: String
    let totalMemory: Int?
    let usedMemory: Int?
    let webViewVersion: String?

    init(
        androidSdkVersion: Int?,
        deviceType: String,
        iosVersion: Int?,
        isVirtual: Bool,
        manufacturer: String,
        model: String,
        name: String?,
        operatingSystem: String,
        osVersion: String,
        platform: String,
        totalMemory: Int?,
        usedMemory: Int?,
        webViewVersion: String?
    ) {
        self.androidSdkVersion = androidSdkVersion
        self.deviceType = deviceType
        self.iosVersion = iosVersion
        self.isVirtual = isVirtual
        self.manufacturer = manufacturer
        self.model = model
        self.name = name
        self.operatingSystem = operatingSystem
        self.osVersion = osVersion
        self.platform = platform
        self.totalMemory = totalMemory
        self.usedMemory = usedMemory
        self.webViewVersion = webViewVersion
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["androidSdkVersion"] = androidSdkVersion == nil ? NSNull() : androidSdkVersion
        result["deviceType"] = deviceType
        result["iosVersion"] = iosVersion == nil ? NSNull() : iosVersion
        result["isVirtual"] = isVirtual
        result["manufacturer"] = manufacturer
        result["model"] = model
        result["name"] = name == nil ? NSNull() : name
        result["operatingSystem"] = operatingSystem
        result["osVersion"] = osVersion
        result["platform"] = platform
        result["totalMemory"] = totalMemory == nil ? NSNull() : totalMemory
        result["usedMemory"] = usedMemory == nil ? NSNull() : usedMemory
        result["webViewVersion"] = webViewVersion == nil ? NSNull() : webViewVersion
        return result as AnyObject
    }
}
