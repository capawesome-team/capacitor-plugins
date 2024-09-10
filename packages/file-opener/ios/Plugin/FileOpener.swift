import Foundation
import UIKit
import MobileCoreServices

@objc public class FileOpener: NSObject {
    private let plugin: FileOpenerPlugin
    private var interactionController: UIDocumentInteractionController?

    init(plugin: FileOpenerPlugin) {
        self.plugin = plugin
    }

    @objc public func openFile(url: URL, mimeType: String?, completion: @escaping (Bool) -> Void) {
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
            strongSelf.interactionController?.url = url
            strongSelf.interactionController?.uti = uti
            strongSelf.interactionController?.delegate = strongSelf
            let isPresentPreview = strongSelf.interactionController?.presentPreview(animated: true)
            if isPresentPreview == false {
                completion(false)
            } else {
                completion(true)
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
        let keyWindow = UIApplication.shared.windows.filter {$0.isKeyWindow}.first
        if viewController?.view.window != keyWindow {
            viewController = keyWindow?.rootViewController
        }
        return viewController ?? UIViewController()
    }
}
