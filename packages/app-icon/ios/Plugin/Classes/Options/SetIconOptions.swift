import Foundation
import Capacitor

@objc public class SetIconOptions: NSObject {
    let icon: String

    init(_ call: CAPPluginCall) throws {
        self.icon = try SetIconOptions.getIconFromCall(call)
    }

    private static func getIconFromCall(_ call: CAPPluginCall) throws -> String {
        guard let icon = call.getString("icon") else {
            throw CustomError.iconMissing
        }
        return icon
    }
}
