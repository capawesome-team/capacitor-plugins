import Capacitor

@objc public class SetNotificationsSupportOptions: NSObject {
    let enabled: Bool

    init(_ call: CAPPluginCall) throws {
        self.enabled = try SetNotificationsSupportOptions.getEnabledFromCall(call)
    }

    private static func getEnabledFromCall(_ call: CAPPluginCall) throws -> Bool {
        guard let enabled = call.getBool("enabled") else {
            throw CustomError.enabledMissing
        }
        return enabled
    }
}
