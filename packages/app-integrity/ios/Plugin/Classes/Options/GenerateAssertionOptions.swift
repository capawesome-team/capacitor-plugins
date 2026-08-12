import Foundation
import Capacitor

@objc public class GenerateAssertionOptions: NSObject {
    let clientData: Data
    let keyId: String

    init(_ call: CAPPluginCall) throws {
        self.clientData = try GenerateAssertionOptions.getClientDataFromCall(call)
        self.keyId = try GenerateAssertionOptions.getKeyIdFromCall(call)
    }

    private static func getClientDataFromCall(_ call: CAPPluginCall) throws -> Data {
        guard let clientData = call.getString("clientData") else {
            throw CustomError.clientDataMissing
        }
        guard let data = Data(base64Encoded: clientData) else {
            throw CustomError.clientDataInvalid
        }
        return data
    }

    private static func getKeyIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let keyId = call.getString("keyId") else {
            throw CustomError.keyIdMissing
        }
        return keyId
    }
}
