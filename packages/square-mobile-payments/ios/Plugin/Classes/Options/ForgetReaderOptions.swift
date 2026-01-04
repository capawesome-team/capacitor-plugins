import Foundation
import Capacitor

@objc public class ForgetReaderOptions: NSObject {
    let serialNumber: String

    init(_ call: CAPPluginCall) throws {
        self.serialNumber = try ForgetReaderOptions.getSerialNumberFromCall(call)
    }

    private static func getSerialNumberFromCall(_ call: CAPPluginCall) throws -> String {
        guard let serialNumber = call.getString("serialNumber") else {
            throw CustomError.serialNumberMissing
        }
        return serialNumber
    }
}
