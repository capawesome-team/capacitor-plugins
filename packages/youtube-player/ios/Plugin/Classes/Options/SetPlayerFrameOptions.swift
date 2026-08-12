import Capacitor
import Foundation

@objc public class SetPlayerFrameOptions: NSObject {
    let frame: PlayerFrame
    let id: String

    init(_ call: CAPPluginCall) throws {
        self.frame = try PlayerFrame.getFrameFromCall(call)
        self.id = try YoutubePlayerHelper.getPlayerIdFromCall(call)
    }
}
