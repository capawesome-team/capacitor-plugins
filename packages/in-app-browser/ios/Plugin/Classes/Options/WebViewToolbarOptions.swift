import Foundation
import Capacitor
import UIKit

@objc public class WebViewToolbarOptions: NSObject {
    let backgroundColor: UIColor?
    let closeButtonText: String
    let color: UIColor?
    let showNavigationButtons: Bool
    let showUrl: Bool
    let title: String?
    let visible: Bool

    init(_ object: JSObject?) throws {
        self.backgroundColor = try WebViewToolbarOptions.getColorFromObject(
            object,
            key: "backgroundColor",
            invalidError: CustomError.backgroundColorInvalid
        )
        self.closeButtonText = object?["closeButtonText"] as? String ?? "Close"
        self.color = try WebViewToolbarOptions.getColorFromObject(object, key: "color", invalidError: CustomError.colorInvalid)
        self.showNavigationButtons = object?["showNavigationButtons"] as? Bool ?? false
        self.showUrl = object?["showUrl"] as? Bool ?? false
        self.title = object?["title"] as? String
        self.visible = object?["visible"] as? Bool ?? true
    }

    private static func getColorFromObject(_ object: JSObject?, key: String, invalidError: Error) throws -> UIColor? {
        guard let value = object?[key] as? String else {
            return nil
        }
        guard let color = InAppBrowserHelper.parseColor(value) else {
            throw invalidError
        }
        return color
    }
}
