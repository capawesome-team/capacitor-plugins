import Foundation
import Capacitor

@objc public class SetVolumeOptions: NSObject {
    let volume: Float

    init(_ call: CAPPluginCall) throws {
        self.volume = try SetVolumeOptions.getVolumeFromCall(call)
    }

    private static func getVolumeFromCall(_ call: CAPPluginCall) throws -> Float {
        guard let volume = call.getFloat("volume") else {
            throw CustomError.volumeMissing
        }
        guard volume >= 0, volume <= 1 else {
            throw CustomError.volumeInvalid
        }
        return volume
    }
}
