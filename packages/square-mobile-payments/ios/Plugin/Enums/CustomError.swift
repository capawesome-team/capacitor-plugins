import Foundation

enum CustomError: Error {
    case locationIdMissing
    case accessTokenMissing
    case serialNumberMissing
    case paymentParametersMissing
    case promptParametersMissing
    case amountMoneyMissing
    case paymentAttemptIdMissing
    case notInitialized
    case notAuthorized
    case pairingAlreadyInProgress
    case noPaymentInProgress
    case readerNotFound
    case privacyDescriptionsMissing

    var code: String? {
        switch self {
        case .locationIdMissing:
            return "LOCATION_ID_MISSING"
        case .accessTokenMissing:
            return "ACCESS_TOKEN_MISSING"
        case .serialNumberMissing:
            return "SERIAL_NUMBER_MISSING"
        case .paymentParametersMissing:
            return "PAYMENT_PARAMETERS_MISSING"
        case .promptParametersMissing:
            return "PROMPT_PARAMETERS_MISSING"
        case .amountMoneyMissing:
            return "AMOUNT_MONEY_MISSING"
        case .paymentAttemptIdMissing:
            return "PAYMENT_ATTEMPT_ID_MISSING"
        case .notInitialized:
            return "NOT_INITIALIZED"
        case .notAuthorized:
            return "NOT_AUTHORIZED"
        case .pairingAlreadyInProgress:
            return "PAIRING_ALREADY_IN_PROGRESS"
        case .noPaymentInProgress:
            return "NO_PAYMENT_IN_PROGRESS"
        case .readerNotFound:
            return "READER_NOT_FOUND"
        case .privacyDescriptionsMissing:
            return "PRIVACY_DESCRIPTIONS_MISSING"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .locationIdMissing:
            return NSLocalizedString("locationId must be provided.", comment: "locationIdMissing")
        case .accessTokenMissing:
            return NSLocalizedString("accessToken must be provided.", comment: "accessTokenMissing")
        case .serialNumberMissing:
            return NSLocalizedString("serialNumber must be provided.", comment: "serialNumberMissing")
        case .paymentParametersMissing:
            return NSLocalizedString("paymentParameters must be provided.", comment: "paymentParametersMissing")
        case .promptParametersMissing:
            return NSLocalizedString("promptParameters must be provided.", comment: "promptParametersMissing")
        case .amountMoneyMissing:
            return NSLocalizedString("amountMoney must be provided.", comment: "amountMoneyMissing")
        case .paymentAttemptIdMissing:
            return NSLocalizedString("paymentAttemptId must be provided.", comment: "paymentAttemptIdMissing")
        case .notInitialized:
            return NSLocalizedString("The SDK is not initialized. Call initialize() first.", comment: "notInitialized")
        case .notAuthorized:
            return NSLocalizedString("The SDK is not authorized. Call authorize() first.", comment: "notAuthorized")
        case .pairingAlreadyInProgress:
            return NSLocalizedString("A pairing process is already in progress.", comment: "pairingAlreadyInProgress")
        case .noPaymentInProgress:
            return NSLocalizedString("No payment is currently in progress.", comment: "noPaymentInProgress")
        case .readerNotFound:
            return NSLocalizedString("Reader not found.", comment: "readerNotFound")
        case .privacyDescriptionsMissing:
            return NSLocalizedString("Privacy descriptions are missing in Info.plist.", comment: "privacyDescriptionsMissing")
        }
    }
}
