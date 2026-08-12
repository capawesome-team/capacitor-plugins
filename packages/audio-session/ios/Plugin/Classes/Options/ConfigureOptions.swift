import Foundation
import AVFoundation
import Capacitor

@objc public class ConfigureOptions: NSObject {
    private let category: AVAudioSession.Category
    private let categoryOptions: AVAudioSession.CategoryOptions
    private let mode: AVAudioSession.Mode

    init(_ call: CAPPluginCall) throws {
        self.category = try ConfigureOptions.getCategoryFromCall(call)
        self.mode = ConfigureOptions.getModeFromCall(call)
        self.categoryOptions = ConfigureOptions.getCategoryOptionsFromCall(call)
    }

    public func getCategory() -> AVAudioSession.Category {
        return category
    }

    public func getCategoryOptions() -> AVAudioSession.CategoryOptions {
        return categoryOptions
    }

    public func getMode() -> AVAudioSession.Mode {
        return mode
    }

    private static func getCategoryFromCall(_ call: CAPPluginCall) throws -> AVAudioSession.Category {
        guard let category = call.getString("category") else {
            throw CustomError.categoryMissing
        }
        switch category {
        case "ambient":
            return .ambient
        case "multiRoute":
            return .multiRoute
        case "playAndRecord":
            return .playAndRecord
        case "playback":
            return .playback
        case "record":
            return .record
        case "soloAmbient":
            return .soloAmbient
        default:
            throw CustomError.categoryMissing
        }
    }

    private static func getCategoryOptionsFromCall(_ call: CAPPluginCall) -> AVAudioSession.CategoryOptions {
        guard let options = call.getObject("options") else {
            return []
        }
        var categoryOptions: AVAudioSession.CategoryOptions = []
        if options["mixWithOthers"] as? Bool == true {
            categoryOptions.insert(.mixWithOthers)
        }
        if options["duckOthers"] as? Bool == true {
            categoryOptions.insert(.duckOthers)
        }
        if options["interruptSpokenAudioAndMixWithOthers"] as? Bool == true {
            categoryOptions.insert(.interruptSpokenAudioAndMixWithOthers)
        }
        if options["allowBluetooth"] as? Bool == true {
            categoryOptions.insert(.allowBluetooth)
        }
        if options["allowBluetoothA2DP"] as? Bool == true {
            categoryOptions.insert(.allowBluetoothA2DP)
        }
        if options["allowAirPlay"] as? Bool == true {
            categoryOptions.insert(.allowAirPlay)
        }
        if options["defaultToSpeaker"] as? Bool == true {
            categoryOptions.insert(.defaultToSpeaker)
        }
        return categoryOptions
    }

    private static func getModeFromCall(_ call: CAPPluginCall) -> AVAudioSession.Mode {
        switch call.getString("mode") {
        case "gameChat":
            return .gameChat
        case "measurement":
            return .measurement
        case "moviePlayback":
            return .moviePlayback
        case "spokenAudio":
            return .spokenAudio
        case "videoChat":
            return .videoChat
        case "videoRecording":
            return .videoRecording
        case "voiceChat":
            return .voiceChat
        case "voicePrompt":
            return .voicePrompt
        default:
            return .default
        }
    }
}
