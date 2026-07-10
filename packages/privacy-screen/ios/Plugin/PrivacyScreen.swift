import Foundation
import UIKit
import Capacitor

@objc public class PrivacyScreen: NSObject {
    private let plugin: PrivacyScreenPlugin

    private var enabled = false
    private var isObserving = false
    private var originalWindowSuperlayer: CALayer?
    private var overlayView: UIView?
    private var preventScreenshots = false
    private var secureTextField: UITextField?

    init(plugin: PrivacyScreenPlugin) {
        self.plugin = plugin
        super.init()
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    @objc public func disable(completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async { [weak self] in
            guard let self = self else {
                completion(nil)
                return
            }
            self.enabled = false
            self.preventScreenshots = false
            NotificationCenter.default.removeObserver(self, name: UIApplication.willResignActiveNotification, object: nil)
            NotificationCenter.default.removeObserver(self, name: UIApplication.didBecomeActiveNotification, object: nil)
            self.removeOverlay()
            self.removeScreenshotPrevention()
            completion(nil)
        }
    }

    @objc public func enable(_ options: EnableOptions, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async { [weak self] in
            guard let self = self else {
                completion(nil)
                return
            }
            self.enabled = true
            self.preventScreenshots = options.preventScreenshots
            NotificationCenter.default.removeObserver(self, name: UIApplication.willResignActiveNotification, object: nil)
            NotificationCenter.default.removeObserver(self, name: UIApplication.didBecomeActiveNotification, object: nil)
            NotificationCenter.default.addObserver(
                self,
                selector: #selector(self.handleWillResignActive),
                name: UIApplication.willResignActiveNotification,
                object: nil
            )
            NotificationCenter.default.addObserver(
                self,
                selector: #selector(self.handleDidBecomeActive),
                name: UIApplication.didBecomeActiveNotification,
                object: nil
            )
            if options.preventScreenshots {
                self.addScreenshotPrevention()
            } else {
                self.removeScreenshotPrevention()
            }
            completion(nil)
        }
    }

    @objc public func isEnabled(completion: @escaping (IsEnabledResult) -> Void) {
        completion(IsEnabledResult(enabled: enabled))
    }

    func startObserving() {
        DispatchQueue.main.async { [weak self] in
            guard let self = self, !self.isObserving else {
                return
            }
            self.isObserving = true
            NotificationCenter.default.addObserver(
                self,
                selector: #selector(self.handleScreenshotTaken),
                name: UIApplication.userDidTakeScreenshotNotification,
                object: nil
            )
        }
    }

    func stopObserving() {
        DispatchQueue.main.async { [weak self] in
            guard let self = self, self.isObserving else {
                return
            }
            self.isObserving = false
            NotificationCenter.default.removeObserver(self, name: UIApplication.userDidTakeScreenshotNotification, object: nil)
        }
    }

    private func addScreenshotPrevention() {
        guard let window = keyWindow, secureTextField == nil else {
            return
        }
        let field = UITextField()
        field.isSecureTextEntry = true
        field.isUserInteractionEnabled = false
        field.translatesAutoresizingMaskIntoConstraints = false
        window.addSubview(field)
        NSLayoutConstraint.activate([
            field.centerXAnchor.constraint(equalTo: window.centerXAnchor),
            field.centerYAnchor.constraint(equalTo: window.centerYAnchor)
        ])
        guard let secureLayer = field.layer.sublayers?.first else {
            field.removeFromSuperview()
            return
        }
        originalWindowSuperlayer = window.layer.superlayer
        secureLayer.addSublayer(window.layer)
        secureTextField = field
    }

    @objc private func handleDidBecomeActive() {
        removeOverlay()
    }

    @objc private func handleScreenshotTaken() {
        plugin.notifyScreenshotTakenListeners()
    }

    @objc private func handleWillResignActive() {
        guard enabled, let window = keyWindow, overlayView == nil else {
            return
        }
        let overlay = UIVisualEffectView(effect: UIBlurEffect(style: .regular))
        overlay.frame = window.bounds
        overlay.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        window.addSubview(overlay)
        overlayView = overlay
    }

    private var keyWindow: UIWindow? {
        return UIApplication.shared.connectedScenes
            .compactMap { $0 as? UIWindowScene }
            .flatMap { $0.windows }
            .first { $0.isKeyWindow }
    }

    private func removeOverlay() {
        overlayView?.removeFromSuperview()
        overlayView = nil
    }

    private func removeScreenshotPrevention() {
        guard let field = secureTextField else {
            originalWindowSuperlayer = nil
            return
        }
        if let window = keyWindow {
            originalWindowSuperlayer?.addSublayer(window.layer)
        }
        originalWindowSuperlayer = nil
        field.removeFromSuperview()
        secureTextField = nil
    }
}
