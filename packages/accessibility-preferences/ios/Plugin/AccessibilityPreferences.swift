import Foundation
import UIKit

@objc public class AccessibilityPreferences: NSObject {
    let plugin: AccessibilityPreferencesPlugin

    init(plugin: AccessibilityPreferencesPlugin) {
        self.plugin = plugin
    }

    @objc public func getPreferences(completion: @escaping (GetPreferencesResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            let result = GetPreferencesResult(
                fontScale: self.getFontScale(),
                isReduceMotionEnabled: UIAccessibility.isReduceMotionEnabled,
                isBoldTextEnabled: UIAccessibility.isBoldTextEnabled,
                isInvertColorsEnabled: UIAccessibility.isInvertColorsEnabled,
                isReduceTransparencyEnabled: UIAccessibility.isReduceTransparencyEnabled,
                isHighContrastEnabled: UIAccessibility.isDarkerSystemColorsEnabled
            )
            completion(result, nil)
        }
    }

    private static let fontScaleByContentSizeCategory: [UIContentSizeCategory: Double] = [
        .extraSmall: 0.82,
        .small: 0.88,
        .medium: 0.94,
        .large: 1.0,
        .extraLarge: 1.12,
        .extraExtraLarge: 1.24,
        .extraExtraExtraLarge: 1.35,
        .accessibilityMedium: 1.65,
        .accessibilityLarge: 1.94,
        .accessibilityExtraLarge: 2.35,
        .accessibilityExtraExtraLarge: 2.76,
        .accessibilityExtraExtraExtraLarge: 3.12
    ]

    private func getFontScale() -> Double {
        let category = UIApplication.shared.preferredContentSizeCategory
        return Self.fontScaleByContentSizeCategory[category] ?? 1.0
    }
}
