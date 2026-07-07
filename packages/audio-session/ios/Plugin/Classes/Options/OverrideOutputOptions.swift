import Foundation
import AVFoundation
import Capacitor

@objc public class OverrideOutputOptions: NSObject {
    private let portOverride: AVAudioSession.PortOverride

    init(_ call: CAPPluginCall) {
        self.portOverride = OverrideOutputOptions.getPortOverrideFromCall(call)
    }

    public func getPortOverride() -> AVAudioSession.PortOverride {
        return portOverride
    }

    private static func getPortOverrideFromCall(_ call: CAPPluginCall) -> AVAudioSession.PortOverride {
        switch call.getString("type") {
        case "speaker":
            return .speaker
        default:
            return .none
        }
    }
}
