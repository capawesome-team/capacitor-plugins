import Foundation
import Capacitor

@objc public class ShowActionsOptions: NSObject {
    let buttons: [ActionSheetButton]
    let cancelable: Bool
    let message: String?
    let title: String?

    init(_ call: CAPPluginCall) throws {
        guard let rawButtons = call.getArray("options", JSObject.self) else {
            throw CustomError.optionsMissing
        }
        let buttons = try rawButtons.map { try ActionSheetButton($0) }
        if buttons.isEmpty {
            throw CustomError.optionsEmpty
        }
        self.buttons = buttons
        self.cancelable = call.getBool("cancelable", true)
        self.message = call.getString("message")
        self.title = call.getString("title")
    }
}
