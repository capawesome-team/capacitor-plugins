import Foundation
import Capacitor
import PDFKit
import UIKit

@objc public class PdfAnnotator: NSObject {
    private let documentsDirectoryName = "capawesome_capacitor_pdf_annotator_documents"
    private let plugin: PdfAnnotatorPlugin
    private var viewController: PdfAnnotatorViewController?

    init(plugin: PdfAnnotatorPlugin) {
        self.plugin = plugin
        super.init()
        cleanUpDocumentsDirectory()
    }

    @objc public func isAvailable(completion: @escaping (IsAvailableResult?, Error?) -> Void) {
        completion(IsAvailableResult(available: true), nil)
    }

    @objc public func open(_ options: OpenOptions, completion: @escaping (OpenResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            guard let url = self.getFileUrl(byPath: options.path) else {
                completion(nil, CustomError.fileNotFound)
                return
            }
            guard PDFDocument(url: url) != nil else {
                completion(nil, CustomError.loadFailed)
                return
            }
            self.dismissActiveViewController {
                guard let bridgeViewController = self.plugin.bridge?.viewController else {
                    completion(nil, CustomError.loadFailed)
                    return
                }
                let documentsDirectory: URL
                do {
                    documentsDirectory = try self.getDocumentsDirectory()
                } catch {
                    completion(nil, error)
                    return
                }
                let viewController = PdfAnnotatorViewController(url: url, documentsDirectory: documentsDirectory)
                viewController.onClosed = { [weak self] closedViewController, outputUrl, error in
                    guard let self = self else {
                        return
                    }
                    if self.viewController === closedViewController {
                        self.viewController = nil
                    }
                    guard let outputUrl = outputUrl else {
                        completion(nil, error ?? CustomError.canceled)
                        return
                    }
                    completion(OpenResult(url: outputUrl), nil)
                }
                self.viewController = viewController
                bridgeViewController.present(viewController, animated: true)
            }
        }
    }

    private func cleanUpDocumentsDirectory() {
        guard let cachesDirectory = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first else {
            return
        }
        let directory = cachesDirectory.appendingPathComponent(documentsDirectoryName)
        try? FileManager.default.removeItem(at: directory)
    }

    private func dismissActiveViewController(_ completion: @escaping () -> Void) {
        guard let viewController = self.viewController else {
            completion()
            return
        }
        viewController.dismiss(animated: false) {
            completion()
        }
    }

    private func getDocumentsDirectory() throws -> URL {
        guard let cachesDirectory = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first else {
            throw CustomError.saveFailed
        }
        let directory = cachesDirectory.appendingPathComponent(documentsDirectoryName)
        try FileManager.default.createDirectory(at: directory, withIntermediateDirectories: true)
        return directory
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
