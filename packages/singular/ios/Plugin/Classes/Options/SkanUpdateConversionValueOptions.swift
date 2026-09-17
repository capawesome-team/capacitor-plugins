import Foundation
import Capacitor

@objc public class SkanUpdateConversionValueOptions: NSObject {
    let coarseValue: SkanCoarseConversionValue?
    let lockWindow: Bool?
    let value: Int

    init(_ call: CAPPluginCall) throws {
        guard let value = call.getInt("value") else {
            throw CustomError.valueMissing
        }
        self.value = value
        self.coarseValue = try SkanUpdateConversionValueOptions.createCoarseValueFromCall(call)
        self.lockWindow = call.getBool("lockWindow")
    }

    private static func createCoarseValueFromCall(_ call: CAPPluginCall) throws -> SkanCoarseConversionValue? {
        guard let coarseValue = call.getString("coarseValue") else {
            return nil
        }
        guard let value = SkanCoarseConversionValue(rawValue: coarseValue) else {
            throw CustomError.coarseValueInvalid
        }
        return value
    }
}
