import Foundation
import Capacitor
import UIKit

@objc public class ImpactOptions: NSObject {
    let style: UIImpactFeedbackGenerator.FeedbackStyle

    init(_ call: CAPPluginCall) throws {
        self.style = try ImpactOptions.getStyleFromCall(call)
    }

    private static func getStyleFromCall(_ call: CAPPluginCall) throws -> UIImpactFeedbackGenerator.FeedbackStyle {
        let style = call.getString("style", "MEDIUM")
        switch style {
        case "HEAVY":
            return .heavy
        case "LIGHT":
            return .light
        case "MEDIUM":
            return .medium
        case "RIGID":
            return .rigid
        case "SOFT":
            return .soft
        default:
            throw CustomError.styleInvalid
        }
    }
}
