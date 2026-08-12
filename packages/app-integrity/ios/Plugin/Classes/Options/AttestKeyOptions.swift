import Foundation
import Capacitor

@objc public class AttestKeyOptions: NSObject {
    let challenge: Data
    let keyId: String

    init(_ call: CAPPluginCall) throws {
        self.challenge = try AttestKeyOptions.getChallengeFromCall(call)
        self.keyId = try AttestKeyOptions.getKeyIdFromCall(call)
    }

    private static func getChallengeFromCall(_ call: CAPPluginCall) throws -> Data {
        guard let challenge = call.getString("challenge") else {
            throw CustomError.challengeMissing
        }
        guard let data = Data(base64Encoded: challenge) else {
            throw CustomError.challengeInvalid
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
