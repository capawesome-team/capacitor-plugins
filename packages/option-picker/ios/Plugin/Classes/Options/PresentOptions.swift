import Foundation
import Capacitor

@objc public class PresentOptions: NSObject {
    let cancelButtonText: String
    let doneButtonText: String
    let options: [PickerOption]
    let theme: Theme
    let title: String?
    let value: String?

    init(_ call: CAPPluginCall) throws {
        self.cancelButtonText = call.getString("cancelButtonText", "Cancel")
        self.doneButtonText = call.getString("doneButtonText", "Ok")
        self.options = try PresentOptions.getOptionsFromCall(call)
        self.theme = try PresentOptions.getThemeFromCall(call)
        self.title = call.getString("title")
        self.value = call.getString("value")
    }

    private static func getOptionsFromCall(_ call: CAPPluginCall) throws -> [PickerOption] {
        guard let rawOptions = call.getArray("options", JSObject.self) else {
            throw CustomError.optionsMissing
        }
        if rawOptions.isEmpty {
            throw CustomError.optionsEmpty
        }
        return try rawOptions.map { try PickerOption($0) }
    }

    private static func getThemeFromCall(_ call: CAPPluginCall) throws -> Theme {
        guard let theme = Theme(rawValue: call.getString("theme", "auto")) else {
            throw CustomError.themeInvalid
        }
        return theme
    }
}
