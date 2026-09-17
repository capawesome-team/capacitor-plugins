import Foundation
import Capacitor

@objc public class SetShouldPromptForNotificationPermissionOptions: NSObject {
    let enabled: Bool

    init(_ call: CAPPluginCall) throws {
        guard let enabled = call.getBool("enabled") else {
            throw CustomError.valueMissing
        }
        self.enabled = enabled
    }
}
