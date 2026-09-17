import Foundation
import Capacitor

@objc public class SetInAppMessagesVisibleOptions: NSObject {
    let visible: Bool

    init(_ call: CAPPluginCall) throws {
        guard call.hasOption("visible"), let visible = call.getBool("visible") else {
            throw CustomError.visibleMissing
        }
        self.visible = visible
    }
}
