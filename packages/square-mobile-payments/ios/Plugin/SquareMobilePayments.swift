import Foundation
import Capacitor
import SquareMobilePaymentsSDK

@objc public class SquareMobilePayments: NSObject {
    private weak var plugin: SquareMobilePaymentsPlugin?
    private var pairingHandle: SquareMobilePaymentsSDK.PairingHandle?
    private var paymentHandle: SquareMobilePaymentsSDK.PaymentHandle?
    private var squareApplicationId: String?
    private var locationId: String?

    init(plugin: SquareMobilePaymentsPlugin) {
        self.plugin = plugin
        super.init()
    }

    @objc public func initialize(_ options: InitializeOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        // Store location ID for later use during authorization
        self.locationId = options.locationId

        // Note: According to Square's docs, MobilePaymentsSDK.initialize should be called in AppDelegate
        // For now, we'll just store the location ID and skip actual initialization here
        completion(nil)
    }

    @objc public func authorize(_ options: AuthorizeOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        guard let locationId = self.locationId else {
            throw CustomError.notInitialized
        }

        let accessToken = options.accessToken
        let authManager = MobilePaymentsSDK.shared.authorizationManager

        guard authManager.state == .notAuthorized else {
            // Already authorized
            completion(nil)
            return
        }

        authManager.authorize(withAccessToken: accessToken, locationID: locationId) { error in
            if let error = error {
                completion(error)
            } else {
                // Add observer for reader changes after authorization
                MobilePaymentsSDK.shared.readerManager.add(self)
                // Add observer for available card input methods
                MobilePaymentsSDK.shared.paymentManager.add(self)
                completion(nil)
            }
        }
    }

    @objc public func isAuthorized(completion: @escaping (_ result: IsAuthorizedResult?, _ error: Error?) -> Void) throws {
        let authManager = MobilePaymentsSDK.shared.authorizationManager
        let authorized = authManager.state == .authorized
        let result = IsAuthorizedResult(authorized: authorized)
        completion(result, nil)
    }

    @objc public func deauthorize(completion: @escaping (_ error: Error?) -> Void) throws {
        guard locationId != nil else {
            throw CustomError.notInitialized
        }

        let authManager = MobilePaymentsSDK.shared.authorizationManager

        guard authManager.state != .notAuthorized else {
            // Already deauthorized
            completion(nil)
            return
        }

        // Remove observers
        MobilePaymentsSDK.shared.readerManager.remove(self)
        MobilePaymentsSDK.shared.paymentManager.remove(self)

        authManager.deauthorize {
            completion(nil)
        }
    }

    @objc public func showSettings(completion: @escaping (_ error: Error?) -> Void) throws {
        guard locationId != nil else {
            throw CustomError.notInitialized
        }
        guard MobilePaymentsSDK.shared.authorizationManager.state == .authorized else {
            throw CustomError.notAuthorized
        }

        guard let viewController = plugin?.bridge?.viewController else {
            completion(NSError(domain: "SquareMobilePayments", code: -1, userInfo: [NSLocalizedDescriptionKey: "View controller not available"]))
            return
        }

        MobilePaymentsSDK.shared.settingsManager.presentSettings(with: viewController) { error in
            completion(error)
        }
    }

    @objc public func getSettings(completion: @escaping (_ result: GetSettingsResult?, _ error: Error?) -> Void) throws {
        guard locationId != nil else {
            throw CustomError.notInitialized
        }

        let settings = MobilePaymentsSDK.shared.settingsManager
        let version = settings.sdkSettings.version
        let environment = SquareMobilePaymentsHelper.convertEnvironment(settings.sdkSettings.environment)

        let result = GetSettingsResult(version: version, environment: environment)
        completion(result, nil)
    }

    @objc public func startPairing(completion: @escaping (_ error: Error?) -> Void) throws {
        guard locationId != nil else {
            throw CustomError.notInitialized
        }
        guard MobilePaymentsSDK.shared.authorizationManager.state == .authorized else {
            throw CustomError.notAuthorized
        }

        let readerManager = MobilePaymentsSDK.shared.readerManager
        if readerManager.isPairingInProgress {
            throw CustomError.pairingAlreadyInProgress
        }

        pairingHandle = readerManager.startPairing(with: self)
        completion(nil)
    }

    @objc public func stopPairing(completion: @escaping (_ error: Error?) -> Void) throws {
        guard locationId != nil else {
            throw CustomError.notInitialized
        }

        pairingHandle?.stop()
        pairingHandle = nil
        completion(nil)
    }

    @objc public func isPairingInProgress(completion: @escaping (_ result: IsPairingInProgressResult?, _ error: Error?) -> Void) throws {
        guard locationId != nil else {
            throw CustomError.notInitialized
        }

        let inProgress = MobilePaymentsSDK.shared.readerManager.isPairingInProgress
        let result = IsPairingInProgressResult(inProgress: inProgress)
        completion(result, nil)
    }

    @objc public func getReaders(completion: @escaping (_ result: GetReadersResult?, _ error: Error?) -> Void) throws {
        guard locationId != nil else {
            throw CustomError.notInitialized
        }

        let sdkReaders = MobilePaymentsSDK.shared.readerManager.readers
        let readers = sdkReaders.map { convertSdkReaderToReaderInfo($0) }

        let result = GetReadersResult(readers: readers)
        completion(result, nil)
    }

    @objc public func forgetReader(_ options: ForgetReaderOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        guard locationId != nil else {
            throw CustomError.notInitialized
        }

        let serialNumber = options.serialNumber
        let readerManager = MobilePaymentsSDK.shared.readerManager

        guard let reader = readerManager.readers.first(where: { $0.serialNumber == serialNumber }) else {
            throw CustomError.readerNotFound
        }

        readerManager.forget(reader)
        completion(nil)
    }

    @objc public func retryConnection(_ options: RetryConnectionOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        guard locationId != nil else {
            throw CustomError.notInitialized
        }

        let serialNumber = options.serialNumber
        let readerManager = MobilePaymentsSDK.shared.readerManager

        guard let reader = readerManager.readers.first(where: { $0.serialNumber == serialNumber }) else {
            throw CustomError.readerNotFound
        }

        readerManager.retryConnection(reader)
        completion(nil)
    }

    @objc public func startPayment(_ options: StartPaymentOptions, completion: @escaping (_ error: Error?) -> Void) throws {
        guard locationId != nil else {
            throw CustomError.notInitialized
        }
        guard MobilePaymentsSDK.shared.authorizationManager.state == .authorized else {
            throw CustomError.notAuthorized
        }

        guard let viewController = plugin?.bridge?.viewController else {
            completion(NSError(domain: "SquareMobilePayments", code: -1, userInfo: [NSLocalizedDescriptionKey: "View controller not available"]))
            return
        }

        let params = options.paymentParameters
        let promptParams = options.promptParameters

        // Create Money object
        let currency = SquareMobilePaymentsHelper.convertToCurrency(params.amountMoney.currency)
        let money = SquareMobilePaymentsSDK.Money(amount: UInt(params.amountMoney.amount), currency: currency)

        // Create PaymentParameters
        let processingMode = SquareMobilePaymentsHelper.convertToProcessingMode(params.processingMode)
        let paymentParams = SquareMobilePaymentsSDK.PaymentParameters(
            paymentAttemptID: params.paymentAttemptId,
            amountMoney: money,
            processingMode: processingMode
        )

        // Set optional parameters
        if let referenceId = params.referenceId {
            paymentParams.referenceID = referenceId
        }
        if let note = params.note {
            paymentParams.note = note
        }
        if let orderId = params.orderId {
            paymentParams.orderID = orderId
        }
        if let tipMoney = params.tipMoney {
            let tipCurrency = SquareMobilePaymentsHelper.convertToCurrency(tipMoney.currency)
            paymentParams.tipMoney = SquareMobilePaymentsSDK.Money(amount: UInt(tipMoney.amount), currency: tipCurrency)
        }
        if let applicationFee = params.applicationFee {
            let feeCurrency = SquareMobilePaymentsHelper.convertToCurrency(applicationFee.currency)
            paymentParams.appFeeMoney = SquareMobilePaymentsSDK.Money(amount: UInt(applicationFee.amount), currency: feeCurrency)
        }
        if let autocomplete = params.autocomplete {
            paymentParams.autocomplete = autocomplete
        }
        if let delayDuration = params.delayDuration {
            // Parse ISO 8601 duration string (e.g., "PT1H" = 1 hour = 3600 seconds)
            paymentParams.delayDuration = parseISO8601Duration(delayDuration)
        }
        if let delayAction = params.delayAction,
           let action = SquareMobilePaymentsHelper.convertToDelayAction(delayAction) {
            paymentParams.delayAction = action
        }

        // Create PromptParameters
        let mode = SquareMobilePaymentsHelper.convertToPromptMode(promptParams.mode)
        let additionalMethods = SquareMobilePaymentsHelper.convertToAdditionalMethods(promptParams.additionalMethods)
        let squarePromptParams = SquareMobilePaymentsSDK.PromptParameters(
            mode: mode,
            additionalMethods: additionalMethods
        )

        // Start payment
        paymentHandle = MobilePaymentsSDK.shared.paymentManager.startPayment(
            paymentParams,
            promptParameters: squarePromptParams,
            from: viewController,
            delegate: self
        )

        completion(nil)
    }

    @objc public func cancelPayment(completion: @escaping (_ error: Error?) -> Void) throws {
        guard locationId != nil else {
            throw CustomError.notInitialized
        }

        guard let handle = paymentHandle else {
            throw CustomError.noPaymentInProgress
        }

        if handle.isPaymentCancelable {
            handle.cancelPayment()
            completion(nil)
        } else {
            completion(NSError(domain: "SquareMobilePayments", code: -1, userInfo: [NSLocalizedDescriptionKey: "Payment cannot be canceled at this time"]))
        }
    }

    @objc public func getAvailableCardInputMethods(completion: @escaping (_ result: GetAvailableCardInputMethodsResult?, _ error: Error?) -> Void) throws {
        guard locationId != nil else {
            throw CustomError.notInitialized
        }

        let methods = MobilePaymentsSDK.shared.paymentManager.availableCardInputMethods
        let cardInputMethods = SquareMobilePaymentsHelper.convertCardInputMethods(methods)

        let result = GetAvailableCardInputMethodsResult(cardInputMethods: cardInputMethods)
        completion(result, nil)
    }

    // MARK: - Helper Methods

    private func convertSdkReaderToReaderInfo(_ sdkReader: SquareMobilePaymentsSDK.ReaderInfo) -> ReaderInfo {
        let serialNumber = sdkReader.serialNumber ?? ""
        let model = SquareMobilePaymentsHelper.convertReaderModel(sdkReader.model)
        let status = SquareMobilePaymentsHelper.convertReaderStatus(sdkReader.statusInfo.status)
        let firmwareVersion = sdkReader.firmwareInfo?.version
        let batteryLevel = sdkReader.batteryStatus?.percentage != nil ? Int(sdkReader.batteryStatus!.percentage) : nil
        let isCharging = sdkReader.batteryStatus?.isCharging
        let supportedCardInputMethods = SquareMobilePaymentsHelper.convertCardInputMethods(sdkReader.supportedInputMethods)

        var unavailableReasonInfo: UnavailableReasonInfo?
        if let reasonInfo = sdkReader.statusInfo.unavailableReasonInfo {
            let reason = SquareMobilePaymentsHelper.convertUnavailableReason(reasonInfo.reason)
            let message = reasonInfo.title
            unavailableReasonInfo = UnavailableReasonInfo(reason: reason, message: message)
        }

        return ReaderInfo(
            serialNumber: serialNumber,
            model: model,
            status: status,
            firmwareVersion: firmwareVersion,
            batteryLevel: batteryLevel,
            isCharging: isCharging,
            supportedCardInputMethods: supportedCardInputMethods,
            unavailableReasonInfo: unavailableReasonInfo
        )
    }

    private func convertSdkPaymentToPayment(_ sdkPayment: SquareMobilePaymentsSDK.Payment) -> Payment {
        let id: String?
        let status: String
        var cardDetails: CardPaymentDetails?

        // Handle OnlinePayment vs OfflinePayment
        if let onlinePayment = sdkPayment as? SquareMobilePaymentsSDK.OnlinePayment {
            id = onlinePayment.id
            status = SquareMobilePaymentsHelper.convertPaymentStatus(onlinePayment.status)

            if let details = onlinePayment.cardDetails {
                if let cardInfo = details.card {
                    let card = Card(
                        brand: SquareMobilePaymentsHelper.convertCardBrand(cardInfo.cardBrand),
                        lastFourDigits: cardInfo.last4 ?? "",
                        cardholderName: cardInfo.cardholderName,
                        expirationMonth: Int(cardInfo.expMonth),
                        expirationYear: Int(cardInfo.expYear)
                    )

                    cardDetails = CardPaymentDetails(
                        card: card,
                        entryMethod: SquareMobilePaymentsHelper.convertCardInputMethodFromString(nil),
                        authorizationCode: details.authResultCode,
                        applicationName: details.applicationName,
                        applicationId: details.applicationIdentifier
                    )
                }
            }
        } else if let offlinePayment = sdkPayment as? SquareMobilePaymentsSDK.OfflinePayment {
            id = offlinePayment.id
            // OfflineStatus is different, map to our PaymentStatus
            switch offlinePayment.status {
            case .queued: status = "PENDING"
            case .uploaded: status = "PENDING"
            case .processed: status = "COMPLETED"
            case .failedToUpload: status = "FAILED"
            case .failedToProcess: status = "FAILED"
            case .unknown: status = "PENDING"
            @unknown default: status = "PENDING"
            }

            // Note: OfflinePayment has OfflineCardPaymentDetails, which may have a different structure
            // For now, we'll skip card details for offline payments
        } else {
            id = nil
            status = "PENDING"
        }

        let type = SquareMobilePaymentsHelper.convertPaymentType(sdkPayment)

        let amountMoney = MoneyResult(
            amount: Int(sdkPayment.totalMoney.amount),
            currency: getCurrencyString(sdkPayment.totalMoney.currency)
        )

        var tipMoney: MoneyResult?
        if let tip = sdkPayment.tipMoney {
            tipMoney = MoneyResult(amount: Int(tip.amount), currency: getCurrencyString(tip.currency))
        }

        var applicationFee: MoneyResult?
        if let fee = sdkPayment.appFeeMoney {
            applicationFee = MoneyResult(amount: Int(fee.amount), currency: getCurrencyString(fee.currency))
        }

        let referenceId = sdkPayment.referenceID
        let orderId = sdkPayment.orderID

        let createdAt = sdkPayment.createdAt.iso8601String
        let updatedAt = sdkPayment.updatedAt.iso8601String

        return Payment(
            id: id,
            type: type,
            status: status,
            amountMoney: amountMoney,
            tipMoney: tipMoney,
            applicationFee: applicationFee,
            referenceId: referenceId,
            orderId: orderId,
            cardDetails: cardDetails,
            createdAt: createdAt,
            updatedAt: updatedAt
        )
    }

    // MARK: - Helper Functions

    private func getCurrencyString(_ currency: SquareMobilePaymentsSDK.Currency) -> String {
        switch currency {
        case .USD: return "USD"
        case .CAD: return "CAD"
        case .AUD: return "AUD"
        case .GBP: return "GBP"
        case .EUR: return "EUR"
        case .JPY: return "JPY"
        @unknown default: return "USD"
        }
    }

    private func parseISO8601Duration(_ duration: String) -> TimeInterval {
        // Simple parser for ISO 8601 durations (e.g., "PT1H" = 1 hour)
        // Format: P[n]Y[n]M[n]DT[n]H[n]M[n]S
        var seconds: TimeInterval = 0

        let pattern = "PT(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?"
        if let regex = try? NSRegularExpression(pattern: pattern, options: []),
           let match = regex.firstMatch(in: duration, options: [], range: NSRange(duration.startIndex..., in: duration)) {

            if let hoursRange = Range(match.range(at: 1), in: duration),
               let hours = Double(duration[hoursRange]) {
                seconds += hours * 3600
            }
            if let minutesRange = Range(match.range(at: 2), in: duration),
               let minutes = Double(duration[minutesRange]) {
                seconds += minutes * 60
            }
            if let secondsRange = Range(match.range(at: 3), in: duration),
               let secs = Double(duration[secondsRange]) {
                seconds += secs
            }
        }

        return seconds > 0 ? seconds : 60 // Default to 60 seconds minimum
    }
}

// MARK: - ReaderPairingDelegate

extension SquareMobilePayments: ReaderPairingDelegate {
    public func readerPairingDidBegin() {
        plugin?.notifyListeners("readerPairingDidBegin", data: [:])
    }

    public func readerPairingDidSucceed() {
        pairingHandle = nil
        plugin?.notifyListeners("readerPairingDidSucceed", data: [:])
    }

    public func readerPairingDidFail(with error: Error) {
        pairingHandle = nil
        var data: [String: Any] = ["message": error.localizedDescription]
        if let nsError = error as NSError? {
            data["code"] = nsError.domain
        }
        plugin?.notifyListeners("readerPairingDidFail", data: data)
    }
}

// MARK: - ReaderObserver

extension SquareMobilePayments: ReaderObserver {
    public func readerWasAdded(_ readerInfo: SquareMobilePaymentsSDK.ReaderInfo) {
        let reader = convertSdkReaderToReaderInfo(readerInfo)
        if let data = reader.toJSObject() as? JSObject {
            plugin?.notifyListeners("readerWasAdded", data: ["reader": data])
        }
    }

    public func readerWasRemoved(_ readerInfo: SquareMobilePaymentsSDK.ReaderInfo) {
        let reader = convertSdkReaderToReaderInfo(readerInfo)
        if let data = reader.toJSObject() as? JSObject {
            plugin?.notifyListeners("readerWasRemoved", data: ["reader": data])
        }
    }

    public func readerDidChange(_ readerInfo: SquareMobilePaymentsSDK.ReaderInfo, change: SquareMobilePaymentsSDK.ReaderChange) {
        let reader = convertSdkReaderToReaderInfo(readerInfo)
        let changeStr = SquareMobilePaymentsHelper.convertReaderChange(change)
        if let data = reader.toJSObject() as? JSObject {
            plugin?.notifyListeners("readerDidChange", data: ["reader": data, "change": changeStr])
        }
    }
}

// MARK: - AvailableCardInputMethodsObserver

extension SquareMobilePayments: AvailableCardInputMethodsObserver {
    public func availableCardInputMethodsDidChange(_ cardInputMethods: SquareMobilePaymentsSDK.CardInputMethods) {
        let methods = SquareMobilePaymentsHelper.convertCardInputMethods(cardInputMethods)
        plugin?.notifyListeners("availableCardInputMethodsDidChange", data: ["cardInputMethods": methods])
    }
}

// MARK: - PaymentManagerDelegate

extension SquareMobilePayments: PaymentManagerDelegate {
    public func paymentManager(_ paymentManager: SquareMobilePaymentsSDK.PaymentManager, didFinish payment: SquareMobilePaymentsSDK.Payment) {
        paymentHandle = nil
        let paymentResult = convertSdkPaymentToPayment(payment)
        if let data = paymentResult.toJSObject() as? JSObject {
            plugin?.notifyListeners("paymentDidFinish", data: ["payment": data])
        }
    }

    public func paymentManager(_ paymentManager: SquareMobilePaymentsSDK.PaymentManager, didFail payment: SquareMobilePaymentsSDK.Payment, withError error: Error) {
        paymentHandle = nil
        let paymentResult = convertSdkPaymentToPayment(payment)
        var data: [String: Any] = ["message": error.localizedDescription]
        if let paymentData = paymentResult.toJSObject() as? JSObject {
            data["payment"] = paymentData
        }
        if let nsError = error as NSError? {
            data["code"] = nsError.domain
        }
        plugin?.notifyListeners("paymentDidFail", data: data)
    }

    public func paymentManager(_ paymentManager: SquareMobilePaymentsSDK.PaymentManager, didCancel payment: SquareMobilePaymentsSDK.Payment) {
        paymentHandle = nil
        let paymentResult = convertSdkPaymentToPayment(payment)
        var data: [String: Any] = [:]
        if let paymentData = paymentResult.toJSObject() as? JSObject {
            data["payment"] = paymentData
        }
        plugin?.notifyListeners("paymentDidCancel", data: data)
    }
}

// MARK: - Date Extension

extension Date {
    var iso8601String: String {
        let formatter = ISO8601DateFormatter()
        return formatter.string(from: self)
    }
}
