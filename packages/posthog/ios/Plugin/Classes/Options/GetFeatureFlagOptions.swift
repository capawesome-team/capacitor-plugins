import Foundation
import Capacitor

@objc public class GetFeatureFlagOptions: NSObject {
    private var key: String

    init(call: CAPPluginCall) throws {
        self.key = try GetFeatureFlagOptions.getKeyFromCall(call)
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
