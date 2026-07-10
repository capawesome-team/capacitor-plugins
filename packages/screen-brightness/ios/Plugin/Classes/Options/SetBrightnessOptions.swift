import Foundation
import Capacitor

@objc public class SetBrightnessOptions: NSObject {
    let brightness: Double

    init(_ call: CAPPluginCall) throws {
        self.brightness = try SetBrightnessOptions.getBrightnessFromCall(call)
    }

    private static func getBrightnessFromCall(_ call: CAPPluginCall) throws -> Double {
        guard let brightness = call.getDouble("brightness") else {
            throw CustomError.brightnessMissing
        }
        if brightness < 0.0 || brightness > 1.0 {
            throw CustomError.brightnessInvalid
        }
        return brightness
    }
}
