import Capacitor
import Foundation

@objc public class UnmuteOptions: NSObject {
    let id: String

    init(_ call: CAPPluginCall) throws {
        self.id = try YoutubePlayerHelper.getPlayerIdFromCall(call)
    }
}
