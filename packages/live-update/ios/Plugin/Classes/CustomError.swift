import Foundation

public enum CustomError: Error {
    case appIdMissing
    case bundleAlreadyExists
    case bundleIdMissing
    case bundleIndexHtmlMissing
    case bundleNotFound
    case channelMissing
    case checksumCalculationFailed
    case checksumMismatch
    case customIdMissing
    case downloadFailed
    case signatureMissing
    case signatureVerificationFailed
    case urlMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .appIdMissing:
            return NSLocalizedString("appId must be configured.", comment: "appIdMissing")
        case .bundleAlreadyExists:
            return NSLocalizedString("bundle already exists.", comment: "bundleAlreadyExists")
        case .bundleIdMissing:
            return NSLocalizedString("bundleId must be provided.", comment: "bundleIdMissing")
        case .bundleIndexHtmlMissing:
            return NSLocalizedString("The bundle does not contain an index.html file.", comment: "bundleIndexHtmlMissing")
        case .bundleNotFound:
            return NSLocalizedString("bundle not found.", comment: "bundleNotFound")
        case .channelMissing:
            return NSLocalizedString("channel must be provided.", comment: "channelMissing")
        case .checksumCalculationFailed:
            return NSLocalizedString("Failed to calculate checksum.", comment: "checksumCalculationFailed")
        case .checksumMismatch:
            return NSLocalizedString("Checksum mismatch.", comment: "checksumMismatch")
        case .customIdMissing:
            return NSLocalizedString("customId must be provided.", comment: "customIdMissing")
        case .downloadFailed:
            return NSLocalizedString("Bundle could not be downloaded.", comment: "downloadFailed")
        case .signatureMissing:
            return NSLocalizedString("Bundle does not contain a signature.", comment: "signatureMissing")
        case .signatureVerificationFailed:
            return NSLocalizedString("Signature verification failed.", comment: "signatureVerificationFailed")
        case .urlMissing:
            return NSLocalizedString("url must be provided.", comment: "urlMissing")
        }
    }
}
