import Foundation

enum CustomError: Error {
    case assertionFailed
    case attestationFailed
    case challengeInvalid
    case challengeMissing
    case clientDataInvalid
    case clientDataMissing
    case invalidKey
    case keyGenerationFailed
    case keyIdMissing
    case serverUnavailable

    var code: String? {
        switch self {
        case .assertionFailed:
            return "ASSERTION_FAILED"
        case .attestationFailed:
            return "ATTESTATION_FAILED"
        case .invalidKey:
            return "INVALID_KEY"
        case .serverUnavailable:
            return "SERVER_UNAVAILABLE"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .assertionFailed:
            return NSLocalizedString("The assertion could not be generated.", comment: "assertionFailed")
        case .attestationFailed:
            return NSLocalizedString("The attestation could not be generated.", comment: "attestationFailed")
        case .challengeInvalid:
            return NSLocalizedString("challenge must be a valid base64 string.", comment: "challengeInvalid")
        case .challengeMissing:
            return NSLocalizedString("challenge must be provided.", comment: "challengeMissing")
        case .clientDataInvalid:
            return NSLocalizedString("clientData must be a valid base64 string.", comment: "clientDataInvalid")
        case .clientDataMissing:
            return NSLocalizedString("clientData must be provided.", comment: "clientDataMissing")
        case .invalidKey:
            return NSLocalizedString(
                "The key is invalid or was not recognized by the App Attest service. Generate and attest a new key.",
                comment: "invalidKey"
            )
        case .keyGenerationFailed:
            return NSLocalizedString("The key could not be generated.", comment: "keyGenerationFailed")
        case .keyIdMissing:
            return NSLocalizedString("keyId must be provided.", comment: "keyIdMissing")
        case .serverUnavailable:
            return NSLocalizedString("The App Attest service is currently unavailable. Retry later.", comment: "serverUnavailable")
        }
    }
}
