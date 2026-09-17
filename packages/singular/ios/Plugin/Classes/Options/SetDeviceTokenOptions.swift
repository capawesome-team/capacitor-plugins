import Foundation
import Capacitor

@objc public class SetDeviceTokenOptions: NSObject {
    let token: Data

    init(_ call: CAPPluginCall) throws {
        guard let token = call.getString("token") else {
            throw CustomError.tokenMissing
        }
        self.token = try SetDeviceTokenOptions.createDataFromHexString(token)
    }

    private static func createDataFromHexString(_ hexString: String) throws -> Data {
        guard !hexString.isEmpty, hexString.count % 2 == 0 else {
            throw CustomError.tokenInvalid
        }
        var data = Data(capacity: hexString.count / 2)
        var index = hexString.startIndex
        while index < hexString.endIndex {
            let nextIndex = hexString.index(index, offsetBy: 2)
            guard let byte = UInt8(hexString[index..<nextIndex], radix: 16) else {
                throw CustomError.tokenInvalid
            }
            data.append(byte)
            index = nextIndex
        }
        return data
    }
}
