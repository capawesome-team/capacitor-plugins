import Foundation
import Capacitor

@objc public class PermissionStatusResult: NSObject, Result {
    let bluetooth: String
    let bluetoothConnect: String
    let bluetoothScan: String
    let location: String

    init(bluetooth: String, bluetoothConnect: String, bluetoothScan: String, location: String) {
        self.bluetooth = bluetooth
        self.bluetoothConnect = bluetoothConnect
        self.bluetoothScan = bluetoothScan
        self.location = location
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["bluetooth"] = bluetooth
        result["bluetoothConnect"] = bluetoothConnect
        result["bluetoothScan"] = bluetoothScan
        result["location"] = location
        return result as AnyObject
    }
}
