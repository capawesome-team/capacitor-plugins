import UIKit

@objc public enum Theme: Int {
    case light
    case dark
    case auto

    var userInterfaceStyle: UIUserInterfaceStyle {
        switch self {
        case .light:
            return .light
        case .dark:
            return .dark
        case .auto:
            return .unspecified
        }
    }
}
