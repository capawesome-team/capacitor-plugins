import Foundation
import Capacitor

@objc public class StartPaymentOptions: NSObject {
    let paymentParameters: PaymentParameters
    let promptParameters: PromptParameters

    init(_ call: CAPPluginCall) throws {
        guard let paymentParametersObj = call.getObject("paymentParameters") else {
            throw CustomError.paymentParametersMissing
        }
        self.paymentParameters = try PaymentParameters(paymentParametersObj)

        guard let promptParametersObj = call.getObject("promptParameters") else {
            throw CustomError.promptParametersMissing
        }
        self.promptParameters = try PromptParameters(promptParametersObj)
    }
}
