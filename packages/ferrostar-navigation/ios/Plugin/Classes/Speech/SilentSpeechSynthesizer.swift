import AVFoundation
import FerrostarCore
import Foundation

class SilentSpeechSynthesizer: SpeechSynthesizer {
    let isSpeaking = false

    func speak(_ utterance: AVSpeechUtterance) {}

    @discardableResult
    func stopSpeaking(at boundary: AVSpeechBoundary) -> Bool {
        return true
    }
}
