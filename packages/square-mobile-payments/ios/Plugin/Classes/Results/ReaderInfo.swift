import Foundation
import Capacitor

@objc public class ReaderInfo: NSObject, Result {
    let serialNumber: String
    let model: String
    let status: String
    let firmwareVersion: String?
    let batteryLevel: Int?
    let isCharging: Bool?
    let supportedCardInputMethods: [String]
    let unavailableReasonInfo: UnavailableReasonInfo?

    init(
        serialNumber: String,
        model: String,
        status: String,
        firmwareVersion: String?,
        batteryLevel: Int?,
        isCharging: Bool?,
        supportedCardInputMethods: [String],
        unavailableReasonInfo: UnavailableReasonInfo?
    ) {
        self.serialNumber = serialNumber
        self.model = model
        self.status = status
        self.firmwareVersion = firmwareVersion
        self.batteryLevel = batteryLevel
        self.isCharging = isCharging
        self.supportedCardInputMethods = supportedCardInputMethods
        self.unavailableReasonInfo = unavailableReasonInfo
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["serialNumber"] = serialNumber
        result["model"] = model
        result["status"] = status
        result["firmwareVersion"] = firmwareVersion ?? NSNull()
        result["batteryLevel"] = batteryLevel ?? NSNull()
        result["isCharging"] = isCharging ?? NSNull()
        result["supportedCardInputMethods"] = supportedCardInputMethods
        result["unavailableReasonInfo"] = unavailableReasonInfo?.toJSObject() ?? NSNull()
        return result as AnyObject
    }
}
