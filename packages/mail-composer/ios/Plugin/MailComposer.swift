import Foundation
import Capacitor
import MessageUI
import UniformTypeIdentifiers

@objc public class MailComposer: NSObject {
    private let plugin: MailComposerPlugin
    private var composeCompletion: ((ComposeMailResult?, Error?) -> Void)?

    init(plugin: MailComposerPlugin) {
        self.plugin = plugin
    }

    @objc public func canComposeMail(completion: @escaping (CanComposeMailResult?, Error?) -> Void) {
        let result = CanComposeMailResult(canCompose: MFMailComposeViewController.canSendMail())
        completion(result, nil)
    }

    @objc public func composeMail(_ options: ComposeMailOptions, completion: @escaping (ComposeMailResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            guard MFMailComposeViewController.canSendMail() else {
                completion(nil, CustomError.mailServicesUnavailable)
                return
            }
            let controller = MFMailComposeViewController()
            controller.mailComposeDelegate = self
            controller.setToRecipients(options.toRecipients)
            controller.setCcRecipients(options.ccRecipients)
            controller.setBccRecipients(options.bccRecipients)
            if let subject = options.subject {
                controller.setSubject(subject)
            }
            if let body = options.body {
                controller.setMessageBody(body, isHTML: options.isHtml)
            }
            for attachment in options.attachments {
                let url = Self.createFileUrl(from: attachment)
                guard let data = try? Data(contentsOf: url) else {
                    completion(nil, CustomError.attachmentNotFound)
                    return
                }
                controller.addAttachmentData(data, mimeType: Self.mimeType(for: url), fileName: url.lastPathComponent)
            }
            self.composeCompletion = completion
            self.plugin.bridge?.viewController?.present(controller, animated: true)
        }
    }

    private static func createFileUrl(from path: String) -> URL {
        if path.hasPrefix("file://") {
            return URL(string: path) ?? URL(fileURLWithPath: path)
        }
        return URL(fileURLWithPath: path)
    }

    private static func mapResult(_ result: MFMailComposeResult) -> String {
        switch result {
        case .sent:
            return "sent"
        case .saved:
            return "saved"
        case .cancelled:
            return "canceled"
        default:
            return "unknown"
        }
    }

    private static func mimeType(for url: URL) -> String {
        if let type = UTType(filenameExtension: url.pathExtension), let mimeType = type.preferredMIMEType {
            return mimeType
        }
        return "application/octet-stream"
    }
}

extension MailComposer: MFMailComposeViewControllerDelegate {
    public func mailComposeController(_ controller: MFMailComposeViewController, didFinishWith result: MFMailComposeResult, error: Error?) {
        controller.dismiss(animated: true)
        let completion = composeCompletion
        composeCompletion = nil
        if result == .failed {
            completion?(nil, CustomError.composeFailed)
            return
        }
        completion?(ComposeMailResult(status: Self.mapResult(result)), nil)
    }
}
