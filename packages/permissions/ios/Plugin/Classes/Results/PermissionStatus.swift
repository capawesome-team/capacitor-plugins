import Foundation
import Capacitor

@objc public class PermissionStatus: NSObject, Result {
    let permission: Permission
    let state: PermissionState

    init(permission: Permission, state: PermissionState) {
        self.permission = permission
        self.state = state
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["permission"] = permission.rawValue
        result["state"] = state.rawValue
        return result as AnyObject
    }
}
