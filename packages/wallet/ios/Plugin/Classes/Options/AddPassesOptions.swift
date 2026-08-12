import Foundation
import Capacitor

@objc public class AddPassesOptions: NSObject {
    let passes: [String]

    init(_ call: CAPPluginCall) throws {
        self.passes = try AddPassesOptions.getPassesFromCall(call)
    }

    private static func getPassesFromCall(_ call: CAPPluginCall) throws -> [String] {
        guard let passes = call.getArray("passes", String.self), !passes.isEmpty else {
            throw CustomError.passesMissing
        }
        return passes
    }
}
