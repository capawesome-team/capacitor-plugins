import Foundation
import Capacitor
import UIKit

@objc public class Toast: NSObject {
    private static let durationLongInSeconds: TimeInterval = 3.5
    private static let durationShortInSeconds: TimeInterval = 2.0
    private static let fadeDurationInSeconds: TimeInterval = 0.3

    private let plugin: ToastPlugin
    private weak var currentToastView: UIView?

    init(plugin: ToastPlugin) {
        self.plugin = plugin
    }

    @objc public func show(_ options: ShowOptions, completion: @escaping (Error?) -> Void) {
        let text = options.text
        let duration = durationInSeconds(for: options.duration)
        let position = options.position
        DispatchQueue.main.async {
            guard let containerView = self.plugin.bridge?.viewController?.view else {
                completion(nil)
                return
            }
            self.removeCurrentToast()
            let toastView = self.createToastView(text: text)
            containerView.addSubview(toastView)
            self.applyConstraints(to: toastView, in: containerView, position: position)
            self.currentToastView = toastView
            UIView.animate(withDuration: Self.fadeDurationInSeconds) {
                toastView.alpha = 1
            }
            DispatchQueue.main.asyncAfter(deadline: .now() + duration) {
                self.dismiss(toastView)
            }
            completion(nil)
        }
    }

    private func applyConstraints(to toastView: UIView, in containerView: UIView, position: String) {
        let safeArea = containerView.safeAreaLayoutGuide
        var constraints: [NSLayoutConstraint] = [
            toastView.centerXAnchor.constraint(equalTo: containerView.centerXAnchor),
            toastView.leadingAnchor.constraint(greaterThanOrEqualTo: safeArea.leadingAnchor, constant: 16),
            toastView.trailingAnchor.constraint(lessThanOrEqualTo: safeArea.trailingAnchor, constant: -16)
        ]
        switch position {
        case "TOP":
            constraints.append(toastView.topAnchor.constraint(equalTo: safeArea.topAnchor, constant: 24))
        case "CENTER":
            constraints.append(toastView.centerYAnchor.constraint(equalTo: containerView.centerYAnchor))
        default:
            constraints.append(toastView.bottomAnchor.constraint(equalTo: safeArea.bottomAnchor, constant: -24))
        }
        NSLayoutConstraint.activate(constraints)
    }

    private func createToastView(text: String) -> UIView {
        let toastView = UIView()
        toastView.translatesAutoresizingMaskIntoConstraints = false
        toastView.backgroundColor = UIColor(white: 0, alpha: 0.8)
        toastView.layer.cornerRadius = 20
        toastView.clipsToBounds = true
        toastView.alpha = 0
        let label = UILabel()
        label.translatesAutoresizingMaskIntoConstraints = false
        label.text = text
        label.textColor = .white
        label.font = .systemFont(ofSize: 14)
        label.numberOfLines = 0
        label.textAlignment = .center
        toastView.addSubview(label)
        NSLayoutConstraint.activate([
            label.topAnchor.constraint(equalTo: toastView.topAnchor, constant: 12),
            label.bottomAnchor.constraint(equalTo: toastView.bottomAnchor, constant: -12),
            label.leadingAnchor.constraint(equalTo: toastView.leadingAnchor, constant: 20),
            label.trailingAnchor.constraint(equalTo: toastView.trailingAnchor, constant: -20)
        ])
        return toastView
    }

    private func dismiss(_ toastView: UIView) {
        guard toastView.superview != nil else {
            return
        }
        UIView.animate(
            withDuration: Self.fadeDurationInSeconds,
            animations: {
                toastView.alpha = 0
            },
            completion: { _ in
                toastView.removeFromSuperview()
            }
        )
    }

    private func durationInSeconds(for value: String) -> TimeInterval {
        return value == "LONG" ? Self.durationLongInSeconds : Self.durationShortInSeconds
    }

    private func removeCurrentToast() {
        currentToastView?.removeFromSuperview()
        currentToastView = nil
    }
}
