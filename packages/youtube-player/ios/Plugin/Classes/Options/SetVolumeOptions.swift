import Capacitor
import Foundation

@objc public class SetVolumeOptions: NSObject {
    let id: String
    let volume: Int

    init(_ call: CAPPluginCall) throws {
        self.id = try YoutubePlayerHelper.getPlayerIdFromCall(call)
        self.volume = try SetVolumeOptions.getVolumeFromCall(call)
    }

    private static func getVolumeFromCall(_ call: CAPPluginCall) throws -> Int {
        guard let volume = call.getInt("volume"), volume >= 0, volume <= 100 else {
            throw CustomError.volumeInvalid
        }
        return volume
    }
}
