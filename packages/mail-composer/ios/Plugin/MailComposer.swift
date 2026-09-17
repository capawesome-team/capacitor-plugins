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
                guard let resolvedAttachment = Self.resolveAttachment(attachment) else {
                    completion(nil, CustomError.attachmentNotFound)
                    return
                }
                controller.addAttachmentData(
                    resolvedAttachment.data,
                    mimeType: Self.mimeType(forFileName: resolvedAttachment.fileName),
                    fileName: resolvedAttachment.fileName
                )
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

    private static func mimeType(forFileName fileName: String) -> String {
        let fileExtension = (fileName as NSString).pathExtension
        if let type = UTType(filenameExtension: fileExtension), let mimeType = type.preferredMIMEType {
            return mimeType
        }
        return "application/octet-stream"
    }

    private static func resolveAttachment(_ attachment: MailAttachment) -> (data: Data, fileName: String)? {
        if let path = attachment.path {
            let url = createFileUrl(from: path)
            guard let data = try? Data(contentsOf: url) else {
                return nil
            }
            return (data, url.lastPathComponent)
        }
        guard let data = attachment.data, let name = attachment.name else {
            return nil
        }
        return (data, name)
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
