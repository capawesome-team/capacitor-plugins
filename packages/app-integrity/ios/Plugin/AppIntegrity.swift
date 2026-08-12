import CryptoKit
import DeviceCheck
import Foundation

@objc public class AppIntegrity: NSObject {
    private let plugin: AppIntegrityPlugin
    private let service = DCAppAttestService.shared

    init(plugin: AppIntegrityPlugin) {
        self.plugin = plugin
    }

    @objc public func attestKey(_ options: AttestKeyOptions, completion: @escaping (AttestKeyResult?, Error?) -> Void) {
        let clientDataHash = Data(SHA256.hash(data: options.challenge))
        service.attestKey(options.keyId, clientDataHash: clientDataHash) { attestation, error in
            if let error = error {
                completion(nil, self.mapError(error))
                return
            }
            guard let attestation = attestation else {
                completion(nil, CustomError.attestationFailed)
                return
            }
            completion(AttestKeyResult(attestationObject: attestation), nil)
        }
    }

    @objc public func generateAssertion(_ options: GenerateAssertionOptions, completion: @escaping (GenerateAssertionResult?, Error?) -> Void) {
        let clientDataHash = Data(SHA256.hash(data: options.clientData))
        service.generateAssertion(options.keyId, clientDataHash: clientDataHash) { assertion, error in
            if let error = error {
                completion(nil, self.mapError(error))
                return
            }
            guard let assertion = assertion else {
                completion(nil, CustomError.assertionFailed)
                return
            }
            completion(GenerateAssertionResult(assertion: assertion), nil)
        }
    }

    @objc public func generateKey(completion: @escaping (GenerateKeyResult?, Error?) -> Void) {
        service.generateKey { keyId, error in
            if let error = error {
                completion(nil, self.mapError(error))
                return
            }
            guard let keyId = keyId else {
                completion(nil, CustomError.keyGenerationFailed)
                return
            }
            completion(GenerateKeyResult(keyId: keyId), nil)
        }
    }

    @objc public func isAvailable(completion: @escaping (IsAvailableResult?, Error?) -> Void) {
        completion(IsAvailableResult(available: service.isSupported), nil)
    }

    @objc public func isSupported() -> Bool {
        return service.isSupported
    }

    private func mapError(_ error: Error) -> Error {
        guard let dcError = error as? DCError else {
            return error
        }
        switch dcError.code {
        case .invalidKey:
            return CustomError.invalidKey
        case .serverUnavailable:
            return CustomError.serverUnavailable
        default:
            return error
        }
    }
}
