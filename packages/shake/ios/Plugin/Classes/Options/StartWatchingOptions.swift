import Foundation
import Capacitor

@objc public class StartWatchingOptions: NSObject {
    private static let sensitivityHard = "hard"
    private static let sensitivityLight = "light"
    private static let sensitivityMedium = "medium"

    private static let shakeThresholdHard = 3.4
    private static let shakeThresholdLight = 2.3
    private static let shakeThresholdMedium = 2.7

    let shakeThreshold: Double

    init(_ call: CAPPluginCall) throws {
        let sensitivity = call.getString("sensitivity", StartWatchingOptions.sensitivityMedium)
        self.shakeThreshold = try StartWatchingOptions.shakeThreshold(for: sensitivity)
    }

    private static func shakeThreshold(for sensitivity: String) throws -> Double {
        switch sensitivity {
        case sensitivityHard:
            return shakeThresholdHard
        case sensitivityLight:
            return shakeThresholdLight
        case sensitivityMedium:
            return shakeThresholdMedium
        default:
            throw CustomError.invalidSensitivity
        }
    }
}
