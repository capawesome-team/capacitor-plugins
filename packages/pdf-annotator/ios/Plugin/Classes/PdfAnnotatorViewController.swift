import Foundation
import QuickLook
import UIKit

@objc public class PdfAnnotatorViewController: QLPreviewController {
    var onClosed: ((PdfAnnotatorViewController, URL?, Error?) -> Void)?

    private let documentsDirectory: URL
    private let url: URL
    private var outputUrl: URL?
    private var saveError: Error?

    init(url: URL, documentsDirectory: URL) {
        self.url = url
        self.documentsDirectory = documentsDirectory
        super.init(nibName: nil, bundle: nil)
        dataSource = self
        delegate = self
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override public func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        guard isBeingDismissed else {
            return
        }
        if let saveError = saveError {
            onClosed?(self, nil, saveError)
        } else if let outputUrl = outputUrl {
            onClosed?(self, outputUrl, nil)
        } else {
            onClosed?(self, nil, CustomError.canceled)
        }
    }
}

extension PdfAnnotatorViewController: QLPreviewControllerDataSource {
    public func numberOfPreviewItems(in controller: QLPreviewController) -> Int {
        return 1
    }

    public func previewController(_ controller: QLPreviewController, previewItemAt index: Int) -> QLPreviewItem {
        return url as NSURL
    }
}

extension PdfAnnotatorViewController: QLPreviewControllerDelegate {
    public func previewController(_ controller: QLPreviewController, editingModeFor previewItem: QLPreviewItem) -> QLPreviewItemEditingMode {
        return .createCopy
    }

    // The edited copy lives in a temporary location that is only guaranteed to exist during this call.
    public func previewController(_ controller: QLPreviewController, didSaveEditedCopyOf previewItem: QLPreviewItem, at modifiedContentsURL: URL) {
        let destinationUrl = documentsDirectory.appendingPathComponent(UUID().uuidString).appendingPathExtension("pdf")
        do {
            try FileManager.default.copyItem(at: modifiedContentsURL, to: destinationUrl)
        } catch {
            saveError = CustomError.saveFailed
            return
        }
        if let previousOutputUrl = outputUrl {
            try? FileManager.default.removeItem(at: previousOutputUrl)
        }
        outputUrl = destinationUrl
        saveError = nil
    }
}
