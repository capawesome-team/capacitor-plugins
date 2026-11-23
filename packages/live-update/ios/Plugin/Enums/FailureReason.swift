import Foundation

enum FailureReason: String, Codable {
    case bundleInvalid = "bundle_invalid"
    case checksumMismatched = "checksum_mismatched"
    case downloadFailed = "download_failed"
    case readinessTimedOut = "readiness_timed_out"
    case signatureVerificationFailed = "signature_verification_failed"

    /// Returns `nil` for an error that is not reported to the server.
    init?(error: Error) {
        switch error {
        case CustomError.bundleIndexHtmlMissing, CustomError.bundleUnzipFailed, CustomError.manifestInvalid:
            self = .bundleInvalid
        case CustomError.checksumMismatch:
            self = .checksumMismatched
        case CustomError.requestFailed:
            self = .downloadFailed
        case CustomError.signatureMissing, CustomError.signatureVerificationFailed:
            self = .signatureVerificationFailed
        default:
            return nil
        }
    }
}
