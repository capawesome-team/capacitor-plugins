import Foundation
import Capacitor
import UIKit

@objc public class Dialog: NSObject {
    private let plugin: DialogPlugin

    init(plugin: DialogPlugin) {
        self.plugin = plugin
    }

    @objc public func alert(_ options: AlertOptions, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            let alertController = UIAlertController(title: options.title, message: options.message, preferredStyle: .alert)
            alertController.addAction(UIAlertAction(title: options.buttonTitle, style: .default) { _ in
                completion(nil)
            })
            self.present(alertController)
        }
    }

    @objc public func confirm(_ options: ConfirmOptions, completion: @escaping (ConfirmResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            let alertController = UIAlertController(title: options.title, message: options.message, preferredStyle: .alert)
            alertController.addAction(UIAlertAction(title: options.cancelButtonTitle, style: .cancel) { _ in
                completion(ConfirmResult(value: false), nil)
            })
            alertController.addAction(UIAlertAction(title: options.okButtonTitle, style: .default) { _ in
                completion(ConfirmResult(value: true), nil)
            })
            self.present(alertController)
        }
    }

    @objc public func prompt(_ options: PromptOptions, completion: @escaping (PromptResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            let alertController = UIAlertController(title: options.title, message: options.message, preferredStyle: .alert)
            alertController.addTextField { textField in
                textField.placeholder = options.inputPlaceholder
                textField.text = options.inputText
            }
            alertController.addAction(UIAlertAction(title: options.cancelButtonTitle, style: .cancel) { _ in
                let value = alertController.textFields?.first?.text ?? ""
                completion(PromptResult(value: value, canceled: true), nil)
            })
            alertController.addAction(UIAlertAction(title: options.okButtonTitle, style: .default) { _ in
                let value = alertController.textFields?.first?.text ?? ""
                completion(PromptResult(value: value, canceled: false), nil)
            })
            self.present(alertController)
        }
    }

    private func present(_ alertController: UIAlertController) {
        plugin.bridge?.viewController?.present(alertController, animated: true)
    }
}
