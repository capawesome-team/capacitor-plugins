import Foundation
import AudioToolbox
import UIKit

@objc public class SilentMode: NSObject {
    private let plugin: SilentModePlugin
    private var hasSound = false
    private var isObserving = false
    private var lastSilent: Bool?
    private let pollingIntervalInSeconds: TimeInterval = 1.5
    private let queue = DispatchQueue(label: "io.capawesome.capacitorjs.plugins.silentmode")
    private let soundDurationInSeconds: TimeInterval = 0.5
    private var soundId: SystemSoundID = 0
    private var timer: Timer?

    init(plugin: SilentModePlugin) {
        self.plugin = plugin
        super.init()
        self.prepareSound()
    }

    deinit {
        if hasSound {
            AudioServicesDisposeSystemSoundID(soundId)
        }
        NotificationCenter.default.removeObserver(self)
    }

    @objc public func isSilent(completion: @escaping (IsSilentResult?, Error?) -> Void) {
        measureSilent { silent in
            completion(IsSilentResult(silent: silent), nil)
        }
    }

    func startObserving() {
        DispatchQueue.main.async { [weak self] in
            guard let self = self, !self.isObserving else {
                return
            }
            self.isObserving = true
            NotificationCenter.default.addObserver(
                self,
                selector: #selector(self.handleDidBecomeActive),
                name: UIApplication.didBecomeActiveNotification,
                object: nil
            )
            NotificationCenter.default.addObserver(
                self,
                selector: #selector(self.handleDidEnterBackground),
                name: UIApplication.didEnterBackgroundNotification,
                object: nil
            )
            if UIApplication.shared.applicationState != .background {
                self.startTimer()
            }
        }
    }

    func stopObserving() {
        DispatchQueue.main.async { [weak self] in
            guard let self = self, self.isObserving else {
                return
            }
            self.isObserving = false
            self.lastSilent = nil
            NotificationCenter.default.removeObserver(self, name: UIApplication.didBecomeActiveNotification, object: nil)
            NotificationCenter.default.removeObserver(self, name: UIApplication.didEnterBackgroundNotification, object: nil)
            self.stopTimer()
        }
    }

    private func createSilentSoundFile() -> URL? {
        let sampleRate = 44100
        let numberOfChannels = 1
        let bitsPerSample = 16
        let numberOfSamples = Int(Double(sampleRate) * soundDurationInSeconds)
        let blockAlign = numberOfChannels * bitsPerSample / 8
        let byteRate = sampleRate * blockAlign
        let dataSize = numberOfSamples * blockAlign

        var data = Data()
        func appendUInt16(_ value: UInt16) {
            var littleEndian = value.littleEndian
            withUnsafeBytes(of: &littleEndian) { data.append(contentsOf: $0) }
        }
        func appendUInt32(_ value: UInt32) {
            var littleEndian = value.littleEndian
            withUnsafeBytes(of: &littleEndian) { data.append(contentsOf: $0) }
        }

        data.append(contentsOf: Array("RIFF".utf8))
        appendUInt32(UInt32(36 + dataSize))
        data.append(contentsOf: Array("WAVE".utf8))
        data.append(contentsOf: Array("fmt ".utf8))
        appendUInt32(16)
        appendUInt16(1)
        appendUInt16(UInt16(numberOfChannels))
        appendUInt32(UInt32(sampleRate))
        appendUInt32(UInt32(byteRate))
        appendUInt16(UInt16(blockAlign))
        appendUInt16(UInt16(bitsPerSample))
        data.append(contentsOf: Array("data".utf8))
        appendUInt32(UInt32(dataSize))
        data.append(Data(count: dataSize))

        let url = FileManager.default.temporaryDirectory.appendingPathComponent("capawesome_capacitor_silent_mode.wav")
        do {
            try data.write(to: url)
            return url
        } catch {
            return nil
        }
    }

    @objc private func handleDidBecomeActive() {
        if isObserving {
            startTimer()
        }
    }

    @objc private func handleDidEnterBackground() {
        stopTimer()
    }

    private func handleSilentModeCheck() {
        measureSilent { [weak self] silent in
            DispatchQueue.main.async {
                guard let self = self else {
                    return
                }
                let previousSilent = self.lastSilent
                self.lastSilent = silent
                if let previousSilent = previousSilent, previousSilent != silent {
                    self.plugin.notifySilentModeChangeListeners(SilentModeChangeEvent(silent: silent))
                }
            }
        }
    }

    private func measureSilent(completion: @escaping (Bool) -> Void) {
        queue.async { [weak self] in
            guard let self = self, self.hasSound else {
                completion(false)
                return
            }
            let semaphore = DispatchSemaphore(value: 0)
            let startTime = Date()
            AudioServicesPlaySystemSoundWithCompletion(self.soundId) {
                let elapsed = Date().timeIntervalSince(startTime)
                completion(elapsed < self.soundDurationInSeconds / 2)
                semaphore.signal()
            }
            semaphore.wait()
        }
    }

    private func prepareSound() {
        guard let url = createSilentSoundFile() else {
            return
        }
        let status = AudioServicesCreateSystemSoundID(url as CFURL, &soundId)
        hasSound = status == kAudioServicesNoError
    }

    private func startTimer() {
        stopTimer()
        timer = Timer.scheduledTimer(withTimeInterval: pollingIntervalInSeconds, repeats: true) { [weak self] _ in
            self?.handleSilentModeCheck()
        }
        handleSilentModeCheck()
    }

    private func stopTimer() {
        timer?.invalidate()
        timer = nil
    }
}
