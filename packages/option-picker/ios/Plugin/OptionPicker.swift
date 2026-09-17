import Foundation
import Capacitor
import UIKit

@objc public class OptionPicker: NSObject {
    private let plugin: OptionPickerPlugin
    private var activeViewController: OptionPickerViewController?

    init(plugin: OptionPickerPlugin) {
        self.plugin = plugin
    }

    @objc public func present(_ options: PresentOptions, completion: @escaping (PresentResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            if self.activeViewController != nil {
                completion(nil, CustomError.pickerAlreadyPresented)
                return
            }
            guard let presentingViewController = self.plugin.bridge?.viewController else {
                completion(nil, CustomError.pickerUnavailable)
                return
            }
            let viewController = OptionPickerViewController(options: options) { result, error in
                self.activeViewController = nil
                completion(result, error)
            }
            self.activeViewController = viewController
            presentingViewController.present(viewController, animated: true)
        }
    }
}
