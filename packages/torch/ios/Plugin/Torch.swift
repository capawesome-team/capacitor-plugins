import Foundation
import AVFoundation

@objc public class Torch: NSObject {
    private let plugin: TorchPlugin

    init(plugin: TorchPlugin) {
        self.plugin = plugin
    }

    @objc public func enable() throws {
        guard let device = AVCaptureDevice.default(for: AVMediaType.video) else { return }
        guard device.hasTorch else { return }
        try device.lockForConfiguration()
        try device.setTorchModeOn(level: 1.0)
        device.unlockForConfiguration()
    }

    @objc public func disable() throws {
        guard let device = AVCaptureDevice.default(for: AVMediaType.video) else { return }
        guard device.hasTorch else { return }
        try device.lockForConfiguration()
        device.torchMode = AVCaptureDevice.TorchMode.off
        device.unlockForConfiguration()
    }

    @objc public func toggle() throws {
        if self.isEnabled() {
            try self.disable()
        } else {
            try self.enable()
        }
    }

    @objc public func isAvailable() -> Bool {
        guard let device = AVCaptureDevice.default(for: AVMediaType.video) else {
            return false
        }
        return device.hasTorch
    }

    @objc public func isEnabled() -> Bool {
        guard let device = AVCaptureDevice.default(for: AVMediaType.video) else { return false }
        guard device.hasTorch else { return false }
        return device.torchMode == AVCaptureDevice.TorchMode.on
    }
}
