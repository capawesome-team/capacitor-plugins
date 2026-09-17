import Foundation
import Capacitor

@objc public class GetPolicyResult: NSObject, Result {
    let contactSyncAllowed: Bool
    let fileEncryptionRequired: Bool
    let managedBrowserRequired: Bool
    let pinRequired: Bool
    let saveToPersonalStorageAllowed: Bool
    let screenCaptureAllowed: Bool

    init(
        contactSyncAllowed: Bool,
        fileEncryptionRequired: Bool,
        managedBrowserRequired: Bool,
        pinRequired: Bool,
        saveToPersonalStorageAllowed: Bool,
        screenCaptureAllowed: Bool
    ) {
        self.contactSyncAllowed = contactSyncAllowed
        self.fileEncryptionRequired = fileEncryptionRequired
        self.managedBrowserRequired = managedBrowserRequired
        self.pinRequired = pinRequired
        self.saveToPersonalStorageAllowed = saveToPersonalStorageAllowed
        self.screenCaptureAllowed = screenCaptureAllowed
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["contactSyncAllowed"] = contactSyncAllowed
        result["fileEncryptionRequired"] = fileEncryptionRequired
        result["managedBrowserRequired"] = managedBrowserRequired
        result["pinRequired"] = pinRequired
        result["saveToPersonalStorageAllowed"] = saveToPersonalStorageAllowed
        result["screenCaptureAllowed"] = screenCaptureAllowed
        return result as AnyObject
    }
}
