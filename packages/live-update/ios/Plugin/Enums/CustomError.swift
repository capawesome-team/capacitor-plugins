import Foundation

public enum CustomError: Error {
    case appIdMissing
    case bundleAlreadyExists
    case bundleDirectoryNotFound
    case bundleIdMissing
    case bundleIndexHtmlMissing
    case bundleNotFound
    case channelDiscoveryNotEnabled
    case channelMissing
    case checksumCalculationFailed
    case checksumMismatch
    case configurationValueInvalid(key: String)
    case customIdMissing
    case downloadUrlMissing
    case pluginNotInitialized
    case requestFailed(message: String)
    case signatureMissing
    case signatureVerificationFailed
    case syncInProgress
    case unknown
    case urlMissing

    var code: String? {
        switch self {
        case .bundleAlreadyExists:
            return "BUNDLE_ALREADY_EXISTS"
        case .bundleNotFound:
            return "BUNDLE_NOT_FOUND"
        case .requestFailed:
            return "REQUEST_FAILED"
        case .syncInProgress:
            return "SYNC_IN_PROGRESS"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .appIdMissing:
            return NSLocalizedString("appId must be configured.", comment: "appIdMissing")
        case .bundleAlreadyExists:
            return NSLocalizedString("bundle already exists.", comment: "bundleAlreadyExists")
        case .bundleDirectoryNotFound:
            return NSLocalizedString("Bundle directory could not be resolved.", comment: "bundleDirectoryNotFound")
        case .bundleIdMissing:
            return NSLocalizedString("bundleId must be provided.", comment: "bundleIdMissing")
        case .bundleIndexHtmlMissing:
            return NSLocalizedString("The bundle does not contain an index.html file.", comment: "bundleIndexHtmlMissing")
        case .bundleNotFound:
            return NSLocalizedString("bundle not found.", comment: "bundleNotFound")
        case .channelDiscoveryNotEnabled:
            return NSLocalizedString("Unauthorized. Channel Discovery may not be enabled for this app.", comment: "channelDiscoveryNotEnabled")
        case .channelMissing:
            return NSLocalizedString("channel must be provided.", comment: "channelMissing")
        case .checksumCalculationFailed:
            return NSLocalizedString("Failed to calculate checksum.", comment: "checksumCalculationFailed")
        case .checksumMismatch:
            return NSLocalizedString("Checksum mismatch.", comment: "checksumMismatch")
        case .configurationValueInvalid(let key):
            let format = NSLocalizedString("%@ has an invalid type.", comment: "configurationValueInvalid")
            return String(format: format, key)
        case .customIdMissing:
            return NSLocalizedString("customId must be provided.", comment: "customIdMissing")
        case .downloadUrlMissing:
            return NSLocalizedString("Bundle does not have a valid download URL.", comment: "downloadUrlMissing")
        case .pluginNotInitialized:
            return NSLocalizedString("Plugin is not initialized.", comment: "pluginNotInitialized")
        case .requestFailed(let message):
            return message
        case .signatureMissing:
            return NSLocalizedString("Bundle does not contain a signature.", comment: "signatureMissing")
        case .signatureVerificationFailed:
            return NSLocalizedString("Signature verification failed.", comment: "signatureVerificationFailed")
        case .syncInProgress:
            return NSLocalizedString("Sync is already in progress.", comment: "syncInProgress")
        case .unknown:
            return NSLocalizedString("An unknown error occurred.", comment: "unknown")
        case .urlMissing:
            return NSLocalizedString("url must be provided.", comment: "urlMissing")
        }
    }
}
