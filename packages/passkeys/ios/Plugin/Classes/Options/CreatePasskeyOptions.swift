import Foundation
import Capacitor

@objc public class CreatePasskeyOptions: NSObject {
    let attestation: String?
    let challenge: Data
    let excludeCredentialIds: [Data]
    let rpId: String
    let userId: Data
    let userName: String
    let userVerification: String?

    init(_ call: CAPPluginCall) throws {
        self.attestation = CreatePasskeyOptions.getAttestationFromCall(call)
        self.challenge = try CreatePasskeyOptions.getChallengeFromCall(call)
        self.excludeCredentialIds = try CreatePasskeyOptions.getExcludeCredentialIdsFromCall(call)
        self.rpId = try CreatePasskeyOptions.getRpIdFromCall(call)
        self.userId = try CreatePasskeyOptions.getUserIdFromCall(call)
        self.userName = try CreatePasskeyOptions.getUserNameFromCall(call)
        self.userVerification = CreatePasskeyOptions.getUserVerificationFromCall(call)
    }

    private static func getAttestationFromCall(_ call: CAPPluginCall) -> String? {
        return call.getString("attestation")
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

    private static func getExcludeCredentialIdsFromCall(_ call: CAPPluginCall) throws -> [Data] {
        guard let excludeCredentials = call.getArray("excludeCredentials") else {
            return []
        }
        var ids: [Data] = []
        for element in excludeCredentials {
            guard let descriptor = element as? [String: Any], let id = descriptor["id"] as? String else {
                continue
            }
            guard let data = PasskeysHelper.dataFromBase64Url(id) else {
                throw CustomError.excludeCredentialsInvalid
            }
            ids.append(data)
        }
        return ids
    }

    private static func getRpIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let rpId = call.getObject("rp")?["id"] as? String else {
            throw CustomError.rpIdMissing
        }
        return rpId
    }

    private static func getUserIdFromCall(_ call: CAPPluginCall) throws -> Data {
        guard let userId = call.getObject("user")?["id"] as? String else {
            throw CustomError.userIdMissing
        }
        guard let data = PasskeysHelper.dataFromBase64Url(userId) else {
            throw CustomError.userIdInvalid
        }
        return data
    }

    private static func getUserNameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let userName = call.getObject("user")?["name"] as? String else {
            throw CustomError.userNameMissing
        }
        return userName
    }

    private static func getUserVerificationFromCall(_ call: CAPPluginCall) -> String? {
        return call.getObject("authenticatorSelection")?["userVerification"] as? String
    }
}
