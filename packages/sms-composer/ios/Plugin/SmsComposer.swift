import Foundation
import Capacitor
import MessageUI

@objc public class SmsComposer: NSObject, MFMessageComposeViewControllerDelegate {
    private let plugin: SmsComposerPlugin
    private var composeCompletion: ((ComposeSmsResult?, Error?) -> Void)?

    init(plugin: SmsComposerPlugin) {
        self.plugin = plugin
    }

    @objc public func canComposeSms() -> Bool {
        return MFMessageComposeViewController.canSendText()
    }

    @objc public func composeSms(_ options: ComposeSmsOptions, completion: @escaping (_ result: ComposeSmsResult?, _ error: Error?) -> Void) {
        DispatchQueue.main.async {
            guard let viewController = self.plugin.bridge?.viewController else {
                completion(nil, CustomError.viewControllerUnavailable)
                return
            }
            let composeViewController = MFMessageComposeViewController()
            composeViewController.messageComposeDelegate = self
            if let recipients = options.recipients {
                composeViewController.recipients = recipients
            }
            if let body = options.body {
                composeViewController.body = body
            }
            self.composeCompletion = completion
            viewController.present(composeViewController, animated: true)
        }
    }

    public func messageComposeViewController(_ controller: MFMessageComposeViewController, didFinishWith result: MessageComposeResult) {
        controller.dismiss(animated: true) {
            let completion = self.composeCompletion
            self.composeCompletion = nil
            switch result {
            case .sent:
                completion?(ComposeSmsResult(status: "sent"), nil)
            case .cancelled:
                completion?(ComposeSmsResult(status: "canceled"), nil)
            case .failed:
                completion?(nil, CustomError.composeFailed)
            @unknown default:
                completion?(nil, CustomError.composeFailed)
            }
        }
    }
}
