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
        if value == "light" {
            return .light
        } else if value == "dark" {
            return .dark
        } else if value == "auto" {
            return .auto
        }
        return nil
    }

    public static func convertStringToLocale(_ value: String) -> Locale {
        return Locale(identifier: value)
    }
}
