import Capacitor
import Foundation

@objc public class SetPlaybackRateOptions: NSObject {
    let id: String
    let rate: Double

    init(_ call: CAPPluginCall) throws {
        self.id = try YoutubePlayerHelper.getPlayerIdFromCall(call)
        self.rate = try SetPlaybackRateOptions.getRateFromCall(call)
    }

    private static func getRateFromCall(_ call: CAPPluginCall) throws -> Double {
        guard let rate = call.getDouble("rate"), YoutubePlayerHelper.allowedPlaybackRates.contains(rate) else {
            throw CustomError.rateInvalid
        }
        return rate
    }
}
