import Foundation
import Capacitor

@objc public class IsEmulatorResult: NSObject, Result {
    let emulator: Bool

    init(emulator: Bool) {
        self.emulator = emulator
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["emulator"] = emulator
        return result as AnyObject
    }
}
