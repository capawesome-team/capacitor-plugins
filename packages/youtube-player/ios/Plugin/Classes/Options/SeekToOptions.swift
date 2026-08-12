import Capacitor
import Foundation

@objc public class SeekToOptions: NSObject {
    let id: String
    let seconds: Float

    init(_ call: CAPPluginCall) throws {
        self.id = try YoutubePlayerHelper.getPlayerIdFromCall(call)
        self.seconds = try SeekToOptions.getSecondsFromCall(call)
    }

    private static func getSecondsFromCall(_ call: CAPPluginCall) throws -> Float {
        guard let seconds = call.getDouble("seconds") else {
            throw CustomError.secondsMissing
        }
        return Float(seconds)
    }
}
