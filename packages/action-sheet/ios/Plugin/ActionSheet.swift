import Foundation
import Capacitor
import UIKit

@objc public class ActionSheet: NSObject, UIPopoverPresentationControllerDelegate {
    private let plugin: ActionSheetPlugin
    private var pendingCompletion: ((ShowActionsResult?, Error?) -> Void)?

    init(plugin: ActionSheetPlugin) {
        self.plugin = plugin
    }

    @objc public func showActions(_ options: ShowActionsOptions, completion: @escaping (ShowActionsResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            self.pendingCompletion = completion
            let alertController = UIAlertController(title: options.title, message: options.message, preferredStyle: .actionSheet)
            for (index, button) in options.buttons.enumerated() {
                let action = UIAlertAction(title: button.title, style: self.alertActionStyle(for: button.style)) { _ in
                    self.resolve(index: index, canceled: button.style == ActionSheetButton.styleCancel)
                }
                alertController.addAction(action)
            }
            if let popoverController = alertController.popoverPresentationController, let view = self.plugin.bridge?.viewController?.view {
                popoverController.delegate = self
                popoverController.sourceView = view
                popoverController.sourceRect = CGRect(x: view.bounds.midX, y: view.bounds.midY, width: 0, height: 0)
                popoverController.permittedArrowDirections = []
            }
            self.present(alertController)
        }
    }

    public func popoverPresentationControllerDidDismissPopover(_ popoverPresentationController: UIPopoverPresentationController) {
        resolve(index: -1, canceled: true)
    }

    private func alertActionStyle(for style: String) -> UIAlertAction.Style {
        switch style {
        case ActionSheetButton.styleCancel:
            return .cancel
        case ActionSheetButton.styleDestructive:
            return .destructive
        default:
            return .default
        }
    }

    private func present(_ alertController: UIAlertController) {
        plugin.bridge?.viewController?.present(alertController, animated: true)
    }

    private func resolve(index: Int, canceled: Bool) {
        guard let completion = pendingCompletion else {
            return
        }
        pendingCompletion = nil
        completion(ShowActionsResult(index: index, canceled: canceled), nil)
    }
}
