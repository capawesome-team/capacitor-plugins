import Foundation
import Capacitor

@objc public class SetBottomPaddingOptions: NSObject {
    let padding: CGFloat

    init(_ call: CAPPluginCall) throws {
        guard call.hasOption("padding"), let padding = call.getDouble("padding") else {
            throw CustomError.paddingMissing
        }
        self.padding = CGFloat(padding)
    }
}
