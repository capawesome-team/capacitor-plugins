import Foundation

enum CustomError: Error {
    case dataNotReceived
    case notAvailable
    case privacyDescriptionsMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .dataNotReceived:
            return NSLocalizedString("No gyroscope data received.", comment: "dataNotReceived")
        case .notAvailable:
            return NSLocalizedString("Not available on this device.", comment: "notAvailable")
        case .privacyDescriptionsMissing:
            return NSLocalizedString("One or more privacy descriptions are missing.", comment: "privacyDescriptionsMissing")
        }
    }
}
