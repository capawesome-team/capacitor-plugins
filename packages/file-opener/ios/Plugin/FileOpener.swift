import Foundation
import UIKit
import MobileCoreServices

@objc public class FileOpener: NSObject {
    private let plugin: FileOpenerPlugin
    private var interactionController: UIDocumentInteractionController?
    private weak var previewPresentingViewController: UIViewController?

    /// The preview is still being presented while the presenting view controller is in a window,
    /// and on screen while it presents the preview. UIKit removes the presenting view from the window
    /// after a full-screen presentation, so both checks are needed. `documentInteractionControllerDidEndPreview`
    /// is not called when the presenting view controller is removed together with the preview,
    /// so both checks failing means the preview is gone.
    private var isPreviewOpen: Bool {
        guard let viewController = previewPresentingViewController else {
            return false
        }
        return viewController.presentedViewController != nil || viewController.view.window != nil
    }

    init(plugin: FileOpenerPlugin) {
        self.plugin = plugin
    }

    @objc public func openFile(url: URL, mimeType: String?, completion: @escaping (Error?) -> Void) {
        if self.interactionController == nil {
            self.interactionController = UIDocumentInteractionController()
        }
        var uti = self.getUtiFromUrl(url)
        if let mimeType = mimeType {
            uti = self.getUtiFromMimeType(mimeType)
        }
        DispatchQueue.main.async { [weak self] in
            guard let strongSelf = self else {
                return
            }
            // Quick Look shows the preview with a delay, so a second call in that time would stack a second preview.
            if strongSelf.isPreviewOpen {
                completion(CustomError.previewAlreadyOpen)
                return
            }
            strongSelf.interactionController?.url = url
            strongSelf.interactionController?.uti = uti
            strongSelf.interactionController?.delegate = strongSelf
            let isPreviewPresented = strongSelf.interactionController?.presentPreview(animated: true) == true
            if isPreviewPresented {
                completion(nil)
            } else {
                strongSelf.previewPresentingViewController = nil
                completion(CustomError.cannotOpenFile)
            }
        }
    }

    @objc public func getFileUrlByPath(_ path: String) -> URL? {
        guard let url = URL.init(string: path) else {
            return nil
        }
        if FileManager.default.fileExists(atPath: url.path) {
            return url
        } else {
            return nil
        }
    }

    private func getUtiFromMimeType(_ mimeType: String) -> String {
        guard let extUTI = UTTypeCreatePreferredIdentifierForTag(kUTTagClassMIMEType, mimeType as CFString, nil)?.takeUnretainedValue() else {
            return ""
        }
        return extUTI as String
    }

    private func getUtiFromUrl(_ url: URL) -> String {
        let fileExtension = url.pathExtension as CFString
        guard let extUTI = UTTypeCreatePreferredIdentifierForTag(kUTTagClassFilenameExtension, fileExtension, nil)?.takeUnretainedValue() else {
            return ""
        }
        return extUTI as String
    }
}

extension FileOpener: UIDocumentInteractionControllerDelegate {
    public func documentInteractionControllerViewControllerForPreview(_ controller: UIDocumentInteractionController) -> UIViewController {
        var viewController = self.plugin.bridge?.viewController
        if viewController?.view.window?.isKeyWindow != true {
            viewController = FileOpener.keyWindow?.rootViewController
        }
        while let presentedViewController = viewController?.presentedViewController, !presentedViewController.isBeingDismissed {
            viewController = presentedViewController
        }
        // A navigation controller would push the preview onto its stack instead of presenting it modally.
        if let navigationController = viewController as? UINavigationController {
            viewController = navigationController.topViewController
        }
        self.previewPresentingViewController = viewController
        return viewController ?? UIViewController()
    }

    public func documentInteractionControllerDidEndPreview(_ controller: UIDocumentInteractionController) {
        self.previewPresentingViewController = nil
    }

    private static var keyWindow: UIWindow? {
        return UIApplication.shared.connectedScenes
            .compactMap { $0 as? UIWindowScene }
            .flatMap(\.windows)
            .first(where: \.isKeyWindow)
    }
}
