import Capacitor
import Foundation

@objc public class CueVideoOptions: NSObject {
    let id: String
    let startSeconds: Float
    let videoId: String

    init(_ call: CAPPluginCall) throws {
        self.id = try YoutubePlayerHelper.getPlayerIdFromCall(call)
        self.startSeconds = call.getFloat("startSeconds") ?? 0
        self.videoId = try CueVideoOptions.getVideoIdFromCall(call)
    }

    private static func getVideoIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let videoId = call.getString("videoId") else {
            throw CustomError.videoIdMissing
        }
        return videoId
    }
}
