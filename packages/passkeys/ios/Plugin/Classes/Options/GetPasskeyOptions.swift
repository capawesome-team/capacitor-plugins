import Foundation
import Capacitor

@objc public class GetPasskeyOptions: NSObject {
    let allowCredentialIds: [Data]
    let challenge: Data
    let rpId: String
    let userVerification: String?

    init(_ call: CAPPluginCall) throws {
        self.allowCredentialIds = try GetPasskeyOptions.getAllowCredentialIdsFromCall(call)
        self.challenge = try GetPasskeyOptions.getChallengeFromCall(call)
        self.rpId = try GetPasskeyOptions.getRpIdFromCall(call)
        self.userVerification = GetPasskeyOptions.getUserVerificationFromCall(call)
    }

    private static func getAllowCredentialIdsFromCall(_ call: CAPPluginCall) throws -> [Data] {
        guard let allowCredentials = call.getArray("allowCredentials") else {
            return []
        }
        var ids: [Data] = []
        for element in allowCredentials {
            guard let descriptor = element as? [String: Any], let id = descriptor["id"] as? String else {
                continue
            }
            guard let data = PasskeysHelper.dataFromBase64Url(id) else {
                throw CustomError.allowCredentialsInvalid
            }
            ids.append(data)
        }
        return ids
    }

    private static func getChallengeFromCall(_ call: CAPPluginCall) throws -> Data {
        guard let challenge = call.getString("challenge") else {
            throw CustomError.challengeMissing
        }
        guard let data = PasskeysHelper.dataFromBase64Url(challenge) else {
            throw CustomError.challengeInvalid
        }
        return data
    }

    private static func getRpIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let rpId = call.getString("rpId") else {
            throw CustomError.rpIdTopLevelMissing
        }
        return rpId
    }

    private static func getUserVerificationFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("userVerification")
    }
}
