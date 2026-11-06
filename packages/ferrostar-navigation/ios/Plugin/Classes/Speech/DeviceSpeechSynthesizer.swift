import AVFoundation
import FerrostarCore
import Foundation

class DeviceSpeechSynthesizer: SpeechSynthesizer {
    private let synthesizer = AVSpeechSynthesizer()
    private let voice: AVSpeechSynthesisVoice?

    init(voiceLanguage: String?) {
        self.voice = voiceLanguage.flatMap { AVSpeechSynthesisVoice(language: $0) }
    }

    var isSpeaking: Bool {
        return synthesizer.isSpeaking
    }

    func speak(_ utterance: AVSpeechUtterance) {
        if let voice {
            utterance.voice = voice
        }
        synthesizer.speak(utterance)
    }

    @discardableResult
    func stopSpeaking(at boundary: AVSpeechBoundary) -> Bool {
        return synthesizer.stopSpeaking(at: boundary)
    }
}
