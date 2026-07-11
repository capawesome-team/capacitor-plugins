import Foundation
import Capacitor
import PDFKit
import UIKit

@objc public class PdfViewer: NSObject {
    private let plugin: PdfViewerPlugin
    private var viewController: PdfViewerViewController?

    init(plugin: PdfViewerPlugin) {
        self.plugin = plugin
    }

    @objc public func close(completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            guard let viewController = self.viewController else {
                completion(nil)
                return
            }
            viewController.dismiss(animated: true) {
                completion(nil)
            }
        }
    }

    @objc public func open(_ options: OpenOptions, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            guard let url = self.getFileUrl(byPath: options.path) else {
                completion(CustomError.fileNotFound)
                return
            }
            guard let document = PDFDocument(url: url) else {
                completion(CustomError.loadFailed)
                return
            }
            if document.isLocked {
                guard let password = options.password else {
                    completion(CustomError.passwordRequired)
                    return
                }
                guard document.unlock(withPassword: password) else {
                    completion(CustomError.passwordInvalid)
                    return
                }
            }
            self.dismissActiveViewer {
                guard let bridgeViewController = self.plugin.bridge?.viewController else {
                    completion(CustomError.loadFailed)
                    return
                }
                let viewController = PdfViewerViewController(
                    document: document,
                    title: options.title ?? url.lastPathComponent,
                    page: options.page
                )
                viewController.onClosed = { [weak self] closedViewController in
                    guard let self = self else {
                        return
                    }
                    if self.viewController === closedViewController {
                        self.viewController = nil
                    }
                    self.plugin.notifyClosedListeners()
                }
                viewController.onPageChanged = { [weak self] page in
                    self?.plugin.notifyPageChangeListeners(PageChangeEvent(page: page))
                }
                let navigationController = UINavigationController(rootViewController: viewController)
                navigationController.modalPresentationStyle = .overFullScreen
                self.viewController = viewController
                bridgeViewController.present(navigationController, animated: true) {
                    completion(nil)
                }
            }
        }
    }

    private func dismissActiveViewer(_ completion: @escaping () -> Void) {
        guard let viewController = self.viewController else {
            completion()
            return
        }
        viewController.dismiss(animated: false) {
            completion()
        }
    }

    private func getFileUrl(byPath path: String) -> URL? {
        let url: URL
        if path.hasPrefix("file:") {
            guard let fileUrl = URL(string: path) else {
                return nil
            }
            url = fileUrl
        } else {
            url = URL(fileURLWithPath: path)
        }
        guard FileManager.default.fileExists(atPath: url.path) else {
            return nil
        }
        return url
    }
}
