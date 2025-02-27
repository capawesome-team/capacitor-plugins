import Foundation
import Capacitor

@objc public class GetFeatureFlagPayloadOptions: NSObject {
    private var key: String

    init(call: CAPPluginCall) throws {
        self.key = try GetFeatureFlagPayloadOptions.getKeyFromCall(call)
    }

    private static func getKeyFromCall(_ call: CAPPluginCall) throws -> String {
        guard let key = call.getString("key") else {
            throw CustomError.keyMissing
        }
        return key
    }

    public func getKey() -> String {
        return key
    }
}
