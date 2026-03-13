import Capacitor

@objc public class SetARViewTouchEnabledOptions: NSObject {
    let enabled: Bool

    init(_ call: CAPPluginCall) throws {
        self.enabled = try SetARViewTouchEnabledOptions.getEnabledFromCall(call)
    }

    private static func getEnabledFromCall(_ call: CAPPluginCall) throws -> Bool {
        guard let enabled = call.getBool("enabled") else {
            throw CustomError.enabledMissing
        }
        return enabled
    }
}
