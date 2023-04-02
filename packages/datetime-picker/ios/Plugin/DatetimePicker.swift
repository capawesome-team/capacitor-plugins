import Foundation

@objc public class DatetimePicker: NSObject {
    private let plugin: DatetimePickerPlugin
    private let config: DatetimePickerConfig

    private let waitForKeyboardCloseSeconds = 0.05

    init(plugin: DatetimePickerPlugin, config: DatetimePickerConfig) {
        self.plugin = plugin
        self.config = config
    }

    @objc public func presentDatetimePicker(date: Date, minDate: Date?, maxDate: Date?, locale: Locale?, cancelButtonText: String, doneButtonText: String, theme: String?, completion: @escaping (Date?, ErrorCode) -> Void) {
        closeKeyboard()
        DispatchQueue.main.asyncAfter(deadline: .now() + waitForKeyboardCloseSeconds) {
            RPicker.selectDate(title: "", cancelText: cancelButtonText, doneText: doneButtonText, datePickerMode: .dateAndTime, selectedDate: date,
                               minDate: minDate, maxDate: maxDate, locale: locale, theme: self.getTheme(unconvertedTheme: theme), completion: { (date, errorCode) in
                                completion(date, errorCode)
                               })
        }
    }

    @objc public func presentDatePicker(date: Date, minDate: Date?, maxDate: Date?, locale: Locale?, cancelButtonText: String, doneButtonText: String, theme: String?, completion: @escaping (Date?, ErrorCode) -> Void) {
        closeKeyboard()
        DispatchQueue.main.asyncAfter(deadline: .now() + waitForKeyboardCloseSeconds) {
            RPicker.selectDate(title: "", cancelText: cancelButtonText, doneText: doneButtonText,
                               datePickerMode: .date, selectedDate: date, minDate: minDate, maxDate: maxDate, locale: locale,
                               theme: self.getTheme(unconvertedTheme: theme), completion: { (date, errorCode) in
                                completion(date, errorCode)
                               })
        }
    }

    @objc public func presentTimePicker(date: Date, locale: Locale?, cancelButtonText: String, doneButtonText: String, theme: String?, completion: @escaping (Date?, ErrorCode) -> Void) {
        closeKeyboard()
        DispatchQueue.main.asyncAfter(deadline: .now() + waitForKeyboardCloseSeconds) {
            RPicker.selectDate(title: "", cancelText: cancelButtonText, doneText: doneButtonText,
                               datePickerMode: .time, selectedDate: date, locale: locale, style: DatetimePickerStyle.wheel,
                               theme: self.getTheme(unconvertedTheme: theme), completion: { (date, errorCode) in
                                completion(date, errorCode)
                               })
        }
    }

    private func closeKeyboard() {
        DispatchQueue.main.async {
            self.plugin.bridge?.viewController?.view.endEditing(true)
        }
    }

    private func getTheme(unconvertedTheme: String?) -> Theme {
        var overrideConfig: Theme?
        if let unconvertedTheme = unconvertedTheme {
            overrideConfig = DatetimePickerHelper.convertStringToTheme(unconvertedTheme)
        }
        var theme = self.config.theme
        if let overrideConfig = overrideConfig {
            theme = overrideConfig
        }
        return theme
    }
}
