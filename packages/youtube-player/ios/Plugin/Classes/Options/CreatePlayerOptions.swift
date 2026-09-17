import Capacitor
import Foundation

@objc public class CreatePlayerOptions: NSObject {
    let autoplay: Bool
    let ccLoadPolicy: Bool
    let controls: Bool
    let end: Int?
    let frame: PlayerFrame
    let fullscreen: Bool
    let id: String
    let ivLoadPolicy: Bool
    let mute: Bool
    let rel: Bool
    let start: Int?
    let videoId: String?

    init(_ call: CAPPluginCall) throws {
        let playerOptions = call.getObject("options")
        self.autoplay = playerOptions?["autoplay"] as? Bool ?? false
        self.ccLoadPolicy = playerOptions?["ccLoadPolicy"] as? Bool ?? false
        self.controls = playerOptions?["controls"] as? Bool ?? true
        self.end = CreatePlayerOptions.getSecondsFromObject(playerOptions, "end")
        self.frame = try PlayerFrame.getFrameFromCall(call)
        self.fullscreen = playerOptions?["fullscreen"] as? Bool ?? false
        self.id = call.getString("id") ?? UUID().uuidString
        self.ivLoadPolicy = playerOptions?["ivLoadPolicy"] as? Bool ?? false
        self.mute = playerOptions?["mute"] as? Bool ?? false
        self.rel = playerOptions?["rel"] as? Bool ?? false
        self.start = CreatePlayerOptions.getSecondsFromObject(playerOptions, "start")
        self.videoId = call.getString("videoId")
    }

    private static func getSecondsFromObject(_ object: JSObject?, _ key: String) -> Int? {
        if let value = object?[key] as? Int {
            return value
        }
        if let value = object?[key] as? Double {
            return Int(value)
        }
        return nil
    }
}
