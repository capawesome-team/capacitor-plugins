import Foundation
import Capacitor
import SquareMobilePaymentsSDK

public class SquareMobilePaymentsHelper {

    // MARK: - Currency Conversion

    public static func convertToCurrency(_ code: String) -> SquareMobilePaymentsSDK.Currency {
        switch code.uppercased() {
        case "USD": return .USD
        case "CAD": return .CAD
        case "AUD": return .AUD
        case "GBP": return .GBP
        case "EUR": return .EUR
        case "JPY": return .JPY
        default: return .USD
        }
    }

    // MARK: - ProcessingMode Conversion

    public static func convertToProcessingMode(_ mode: String?) -> SquareMobilePaymentsSDK.ProcessingMode {
        guard let mode = mode else {
            return .autoDetect
        }

        switch mode.uppercased() {
        case "AUTO_DETECT": return .autoDetect
        case "ONLINE_ONLY": return .onlineOnly
        case "OFFLINE_ONLY": return .offlineOnly
        default: return .autoDetect
        }
    }

    // MARK: - PromptMode Conversion

    public static func convertToPromptMode(_ mode: String?) -> SquareMobilePaymentsSDK.PromptMode {
        guard let mode = mode else {
            return .default
        }

        switch mode.uppercased() {
        case "DEFAULT": return .default
        case "CUSTOM": return .custom
        default: return .default
        }
    }

    // MARK: - AdditionalMethods Conversion

    public static func convertToAdditionalMethods(_ methods: [String]?) -> SquareMobilePaymentsSDK.AdditionalPaymentMethods {
        guard let methods = methods, !methods.isEmpty else {
            return AdditionalPaymentMethods()
        }

        var methodsArray: [AdditionalPaymentMethods] = []

        for method in methods {
            switch method.uppercased() {
            case "KEYED": methodsArray.append(.keyed)
            case "CASH": methodsArray.append(.cash)
            default: break
            }
        }

        return AdditionalPaymentMethods(methodsArray)
    }

    // MARK: - DelayAction Conversion

    public static func convertToDelayAction(_ action: String?) -> SquareMobilePaymentsSDK.DelayAction? {
        guard let action = action else {
            return nil
        }

        switch action.uppercased() {
        case "COMPLETE": return .complete
        case "CANCEL": return .cancel
        default: return nil
        }
    }

    // MARK: - Reader Model Conversion

    public static func convertReaderModel(_ model: SquareMobilePaymentsSDK.ReaderModel) -> String {
        switch model {
        case .contactlessAndChip: return "CONTACTLESS_AND_CHIP"
        case .magstripe: return "MAGSTRIPE"
        case .stand: return "STAND"
        @unknown default: return "UNKNOWN"
        }
    }

    // MARK: - Reader Status Conversion

    public static func convertReaderStatus(_ status: SquareMobilePaymentsSDK.ReaderStatus) -> String {
        switch status {
        case .ready: return "READY"
        case .connectingToSquare: return "CONNECTING_TO_SQUARE"
        case .connectingToDevice: return "CONNECTING_TO_DEVICE"
        case .faulty: return "FAULTY"
        case .readerUnavailable: return "READER_UNAVAILABLE"
        @unknown default: return "READER_UNAVAILABLE"
        }
    }

    // MARK: - Reader Change Conversion

    public static func convertReaderChange(_ change: SquareMobilePaymentsSDK.ReaderChange) -> String {
        switch change {
        case .batteryDidBeginCharging: return "BATTERY_DID_BEGIN_CHARGING"
        case .batteryDidEndCharging: return "BATTERY_DID_END_CHARGING"
        case .batteryLevelDidChange: return "BATTERY_LEVEL_DID_CHANGE"
        case .readerStatusDidChange: return "STATUS_DID_CHANGE"
        @unknown default: return "STATUS_DID_CHANGE"
        }
    }

    // MARK: - Unavailable Reason Conversion

    public static func convertUnavailableReason(_ reason: SquareMobilePaymentsSDK.ReaderUnavailableReason) -> String {
        switch reason {
        case .bluetoothFailure: return "BLUETOOTH_ERROR"
        case .blockingFirmwareUpdate: return "FIRMWARE_UPDATE"
        case .internalError: return "UNKNOWN"
        @unknown default: return "UNKNOWN"
        }
    }

    // MARK: - Card Input Method Conversion

    public static func convertCardInputMethods(_ methods: SquareMobilePaymentsSDK.CardInputMethods) -> [String] {
        var result: [String] = []

        if methods.contains(.contactless) { result.append("TAP") }
        if methods.contains(.chip) { result.append("DIP") }
        if methods.contains(.swipe) { result.append("SWIPE") }

        return result
    }

    public static func convertCardInputMethodFromString(_ methodString: String?) -> String {
        guard let methodString = methodString else {
            return "TAP"
        }

        let upperMethod = methodString.uppercased()
        if upperMethod.contains("CONTACTLESS") || upperMethod.contains("TAP") {
            return "TAP"
        } else if upperMethod.contains("CHIP") || upperMethod.contains("DIP") || upperMethod.contains("EMV") {
            return "DIP"
        } else if upperMethod.contains("SWIPE") || upperMethod.contains("MAG") {
            return "SWIPE"
        } else if upperMethod.contains("KEYED") || upperMethod.contains("MANUAL") {
            return "KEYED"
        }
        return "TAP"
    }

    // MARK: - Card Brand Conversion

    public static func convertCardBrand(_ brand: SquareMobilePaymentsSDK.CardBrand) -> String {
        switch brand {
        case .visa: return "VISA"
        case .mastercard: return "MASTERCARD"
        case .americanExpress: return "AMERICAN_EXPRESS"
        case .discover: return "DISCOVER"
        case .discoverDiners: return "DISCOVER_DINERS"
        case .jcb: return "JCB"
        case .chinaUnionPay: return "UNION_PAY"
        case .interac: return "INTERAC"
        case .eftpos: return "EFTPOS"
        case .unknown: return "OTHER"
        @unknown default: return "OTHER"
        }
    }

    // MARK: - Payment Type Conversion

    public static func convertPaymentType(_ payment: SquareMobilePaymentsSDK.Payment) -> String {
        if payment is SquareMobilePaymentsSDK.OnlinePayment {
            return "ONLINE"
        } else if payment is SquareMobilePaymentsSDK.OfflinePayment {
            return "OFFLINE"
        }
        return "ONLINE"
    }

    // MARK: - Payment Status Conversion

    public static func convertPaymentStatus(_ status: SquareMobilePaymentsSDK.PaymentStatus) -> String {
        switch status {
        case .completed: return "COMPLETED"
        case .approved: return "APPROVED"
        case .canceled: return "CANCELED"
        case .failed: return "FAILED"
        case .pending: return "PENDING"
        @unknown default: return "PENDING"
        }
    }

    // MARK: - Environment Conversion

    public static func convertEnvironment(_ environment: SquareMobilePaymentsSDK.Environment) -> String {
        switch environment {
        case .production: return "production"
        case .sandbox: return "sandbox"
        @unknown default: return "production"
        }
    }
}
