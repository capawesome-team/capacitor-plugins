import UIKit

enum Theme: String {
    case auto
    case dark
    case light

    var userInterfaceStyle: UIUserInterfaceStyle {
        switch self {
        case .auto:
            return .unspecified
        case .dark:
            return .dark
        case .light:
            return .light
        }
    }
}
