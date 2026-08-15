import Foundation
import UIKit

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

extension UIViewController {

    var topMostViewController: UIViewController {
        if let nc = self as? UINavigationController {
            if let last = nc.viewControllers.last {
                return last.topMostViewController
            }
            return nc
        }
        if let tabController = self as? UITabBarController, let selected = tabController.selectedViewController {
            return selected.topMostViewController
        }
        if let presented = presentedViewController {
            return presented.topMostViewController
        }
        return self
    }
}
