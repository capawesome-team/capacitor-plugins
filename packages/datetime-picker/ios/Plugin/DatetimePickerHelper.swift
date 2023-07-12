import Foundation

public class DatetimePickerHelper {
    public static func convertStringToDate(_ format: String, _ value: String) -> Date? {
        let dateFormatter = DateFormatter()
        dateFormatter.locale = Locale(identifier: "en_US_POSIX")
        dateFormatter.dateFormat = format
        return dateFormatter.date(from: value)
    }

    public static func convertDateToString(_ format: String, _ date: Date) -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.locale = Locale(identifier: "en_US_POSIX")
        dateFormatter.dateFormat = format
        return dateFormatter.string(from: date)
    }

    public static func convertStringToTheme(_ value: String) -> Theme? {
        if value == "light" || value == "light-spinner" {
            return .light
        } else if value == "dark" || value == "dark-spinner" {
            return .dark
        } else if value == "auto" || value == "auto-spinner" {
            return .auto
        }
        return nil
    }

    public static func convertStringToLocale(_ value: String) -> Locale {
        return Locale(identifier: value)
    }
}
