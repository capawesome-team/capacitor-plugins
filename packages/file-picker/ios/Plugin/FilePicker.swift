import Foundation
import PhotosUI
import Photos
import Capacitor
import UIKit
import MobileCoreServices

@objc public class FilePicker: NSObject {
    private var plugin: FilePickerPlugin?

    init(_ plugin: FilePickerPlugin?) {
        super.init()
        self.plugin = plugin
    }

    public func convertHeicToJpeg(_ sourceUrl: URL) throws -> URL? {
        let heicImage = UIImage(named: sourceUrl.path)
        guard let heicImage = heicImage else {
            return nil
        }
        let jpegImageData = heicImage.jpegData(compressionQuality: 0.9)
        let directory = try self.createUniqueTemporaryDirectory()
        let filenameWithoutExtension = sourceUrl.deletingPathExtension().lastPathComponent
        let targetUrl = directory.appendingPathComponent("\(filenameWithoutExtension).jpeg")
        do {
            try deleteFile(targetUrl)
        }
        try jpegImageData?.write(to: targetUrl)
        return targetUrl
    }

    public func openDocumentPicker(multiple: Bool, documentTypes: [String]) {
        DispatchQueue.main.async {
            let picker = UIDocumentPickerViewController(documentTypes: documentTypes, in: .import)
            picker.delegate = self
            picker.allowsMultipleSelection = multiple
            picker.modalPresentationStyle = .fullScreen
            self.presentViewController(picker)
        }
    }

    public func openImagePicker(multiple: Bool, skipTranscoding: Bool) {
        DispatchQueue.main.async {
            if #available(iOS 14, *) {
                var configuration = PHPickerConfiguration(photoLibrary: PHPhotoLibrary.shared())
                configuration.selectionLimit = multiple ? 0 : 1
                configuration.filter = .images
                configuration.preferredAssetRepresentationMode = skipTranscoding ? .current : .automatic
                let picker = PHPickerViewController(configuration: configuration)
                picker.delegate = self
                picker.modalPresentationStyle = .fullScreen
                self.presentViewController(picker)
            } else {
                let picker = UIImagePickerController()
                picker.delegate = self
                picker.sourceType = .photoLibrary
                picker.modalPresentationStyle = .fullScreen
                self.presentViewController(picker)
            }
        }
    }

    public func openMediaPicker(multiple: Bool, skipTranscoding: Bool) {
        DispatchQueue.main.async {
            if #available(iOS 14, *) {
                var configuration = PHPickerConfiguration(photoLibrary: PHPhotoLibrary.shared())
                configuration.selectionLimit = multiple ? 0 : 1
                configuration.preferredAssetRepresentationMode = skipTranscoding ? .current : .automatic
                let picker = PHPickerViewController(configuration: configuration)
                picker.delegate = self
                picker.modalPresentationStyle = .fullScreen
                self.presentViewController(picker)
            } else {
                let picker = UIImagePickerController()
                picker.delegate = self
                picker.mediaTypes = ["public.movie", "public.image"]
                picker.modalPresentationStyle = .fullScreen
                self.presentViewController(picker)
            }
        }
    }

    public func openVideoPicker(multiple: Bool, skipTranscoding: Bool) {
        DispatchQueue.main.async {
            if #available(iOS 14, *) {
                var configuration = PHPickerConfiguration(photoLibrary: PHPhotoLibrary.shared())
                configuration.selectionLimit = multiple ? 0 : 1
                configuration.filter = .videos
                configuration.preferredAssetRepresentationMode = skipTranscoding ? .current : .automatic
                let picker = PHPickerViewController(configuration: configuration)
                picker.delegate = self
                picker.modalPresentationStyle = .fullScreen
                self.presentViewController(picker)
            } else {
                let picker = UIImagePickerController()
                picker.delegate = self
                picker.mediaTypes = ["public.movie"]
                picker.modalPresentationStyle = .fullScreen
                self.presentViewController(picker)
            }
        }
    }

    public func getPathFromUrl(_ url: URL) -> String {
        return url.absoluteString
    }

    public func getNameFromUrl(_ url: URL) -> String {
        return url.lastPathComponent
    }

    public func getDataFromUrl(_ url: URL) throws -> String {
        let data = try Data(contentsOf: url)
        return data.base64EncodedString()
    }

    public func getModifiedAtFromUrl(_ url: URL) -> Int? {
        do {
            let attributes = try FileManager.default.attributesOfItem(atPath: url.path)
            if let modifiedDateInSec = (attributes[.modificationDate] as? Date)?.timeIntervalSince1970 {
                return Int(modifiedDateInSec * 1000.0)
            } else {
                return nil
            }
        } catch let error as NSError {
            CAPLog.print("getModifiedAtFromUrl failed.", error.localizedDescription)
            return nil
        }
    }

    public func getMimeTypeFromUrl(_ url: URL) -> String {
        let fileExtension = url.pathExtension as CFString
        guard let extUTI = UTTypeCreatePreferredIdentifierForTag(kUTTagClassFilenameExtension, fileExtension, nil)?.takeUnretainedValue() else {
            return ""
        }
        guard let mimeUTI = UTTypeCopyPreferredTagWithClass(extUTI, kUTTagClassMIMEType) else {
            return ""
        }
        return mimeUTI.takeRetainedValue() as String
    }

    public func getSizeFromUrl(_ url: URL) throws -> Int {
        let values = try url.resourceValues(forKeys: [.fileSizeKey])
        return values.fileSize ?? 0
    }

    public func getDurationFromUrl(_ url: URL) -> Int? {
        if isVideoUrl(url) {
            let asset = AVAsset(url: url)
            let duration = asset.duration
            let durationTime = CMTimeGetSeconds(duration)
            return Int(round(durationTime))
        }
        return nil
    }

    public func getHeightAndWidthFromUrl(_ url: URL) -> (Int?, Int?) {
        if isImageUrl(url) {
            if let imageSource = CGImageSourceCreateWithURL(url as CFURL, nil) {
                if let imageProperties = CGImageSourceCopyPropertiesAtIndex(imageSource, 0, nil) as Dictionary? {
                    return getHeightAndWidthFromImageProperties(imageProperties)
                }
            }
        } else if isVideoUrl(url) {
            guard let track = AVURLAsset(url: url).tracks(withMediaType: AVMediaType.video).first else { return (nil, nil) }
            let size = track.naturalSize.applying(track.preferredTransform)
            let height = abs(Int(size.height))
            let width = abs(Int(size.width))
            return (height, width)
        }
        return (nil, nil)
    }

    public func getHeightAndWidthFromImageProperties(_ properties: [NSObject: AnyObject]) -> (Int?, Int?) {
        let width = properties[kCGImagePropertyPixelWidth] as? Int
        let height = properties[kCGImagePropertyPixelHeight] as? Int
        let orientation = properties[kCGImagePropertyOrientation] as? Int ?? UIImage.Orientation.up.rawValue
        switch orientation {
        case UIImage.Orientation.left.rawValue, UIImage.Orientation.right.rawValue, UIImage.Orientation.leftMirrored.rawValue, UIImage.Orientation.rightMirrored.rawValue:
            return (width, height)
        default:
            return (height, width)
        }
    }

    public func getFileUrlByPath(_ path: String) -> URL? {
        guard let url = URL.init(string: path) else {
            return nil
        }
        if FileManager.default.fileExists(atPath: url.path) {
            return url
        } else {
            return nil
        }
    }

    private func presentViewController(_ viewControllerToPresent: UIViewController) {
        self.plugin?.bridge?.viewController?.present(viewControllerToPresent, animated: true, completion: nil)
    }

    private func dismissViewController(_ viewControllerToPresent: UIViewController, completion: (() -> Void)? = nil) {
        viewControllerToPresent.dismiss(animated: true, completion: completion)
        plugin?.notifyPickerDismissedListener()
    }

    private func isImageUrl(_ url: URL) -> Bool {
        let mimeType = self.getMimeTypeFromUrl(url)
        return mimeType.hasPrefix("image")
    }

    private func isVideoUrl(_ url: URL) -> Bool {
        let mimeType = self.getMimeTypeFromUrl(url)
        return mimeType.hasPrefix("video")
    }

    private func saveTemporaryFile(_ sourceUrl: URL) throws -> URL {
        let directory = try self.createUniqueTemporaryDirectory()
        let targetUrl = directory.appendingPathComponent(sourceUrl.lastPathComponent)
        do {
            try deleteFile(targetUrl)
        }
        try FileManager.default.copyItem(at: sourceUrl, to: targetUrl)
        return targetUrl
    }
    
    private func createUniqueTemporaryDirectory() throws -> URL {
        let uniqueFolderName = UUID().uuidString
        var directory = URL(fileURLWithPath: NSTemporaryDirectory()).appendingPathComponent(uniqueFolderName)
        if let cachesDirectory = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first {
            directory = cachesDirectory.appendingPathComponent(uniqueFolderName)
        }
        try FileManager.default.createDirectory(at: directory, withIntermediateDirectories: true, attributes: nil)
        return directory
    }

    private func deleteFile(_ url: URL) throws {
        if FileManager.default.fileExists(atPath: url.path) {
            try FileManager.default.removeItem(atPath: url.path)
        }
    }
}

extension FilePicker: UIDocumentPickerDelegate {
    public func documentPicker(_ controller: UIDocumentPickerViewController, didPickDocumentsAt urls: [URL]) {
        do {
            let temporaryUrls = try urls.map { try saveTemporaryFile($0) }
            plugin?.handleDocumentPickerResult(urls: temporaryUrls, error: nil)
        } catch {
            plugin?.handleDocumentPickerResult(urls: nil, error: self.plugin?.errorTemporaryCopyFailed)
        }
        plugin?.notifyPickerDismissedListener()
    }

    public func documentPickerWasCancelled(_ controller: UIDocumentPickerViewController) {
        plugin?.handleDocumentPickerResult(urls: nil, error: nil)
        plugin?.notifyPickerDismissedListener()
    }
}

extension FilePicker: UIImagePickerControllerDelegate, UINavigationControllerDelegate, UIPopoverPresentationControllerDelegate {
    public func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        dismissViewController(picker)
        plugin?.handleDocumentPickerResult(urls: nil, error: nil)
    }

    public func popoverPresentationControllerDidDismissPopover(_ popoverPresentationController: UIPopoverPresentationController) {
        plugin?.handleDocumentPickerResult(urls: nil, error: nil)
    }

    public func presentationControllerDidDismiss(_ presentationController: UIPresentationController) {
        plugin?.handleDocumentPickerResult(urls: nil, error: nil)
    }

    public func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey: Any]) {
        dismissViewController(picker) {
            if let url = info[.mediaURL] as? URL {
                do {
                    let temporaryUrl = try self.saveTemporaryFile(url)
                    self.plugin?.handleDocumentPickerResult(urls: [temporaryUrl], error: nil)
                } catch {
                    self.plugin?.handleDocumentPickerResult(urls: nil, error: self.plugin?.errorTemporaryCopyFailed)
                }
            } else {
                self.plugin?.handleDocumentPickerResult(urls: nil, error: nil)
            }
        }
    }
}

@available(iOS 14, *)
extension FilePicker: PHPickerViewControllerDelegate {
    public func picker(_ picker: PHPickerViewController, didFinishPicking results: [PHPickerResult]) {
        dismissViewController(picker)
        if results.first == nil {
            self.plugin?.handleDocumentPickerResult(urls: nil, error: nil)
            return
        }
        var temporaryUrls: [URL] = []
        var errorMessage: String?
        let dispatchGroup = DispatchGroup()
        for result in results {
            if errorMessage != nil {
                break
            }
            if result.itemProvider.hasItemConformingToTypeIdentifier(UTType.movie.identifier) {
                dispatchGroup.enter()
                result.itemProvider.loadFileRepresentation(forTypeIdentifier: UTType.movie.identifier, completionHandler: { url, error in
                    defer {
                        dispatchGroup.leave()
                    }
                    if let error = error {
                        errorMessage = error.localizedDescription
                        return
                    }
                    guard let url = url else {
                        errorMessage = self.plugin?.errorUnknown
                        return
                    }
                    do {
                        let temporaryUrl = try self.saveTemporaryFile(url)
                        temporaryUrls.append(temporaryUrl)
                    } catch {
                        errorMessage = self.plugin?.errorTemporaryCopyFailed
                    }
                })
            } else if result.itemProvider.hasItemConformingToTypeIdentifier(UTType.image.identifier) {
                dispatchGroup.enter()
                result.itemProvider.loadFileRepresentation(forTypeIdentifier: UTType.image.identifier, completionHandler: { url, error in
                    defer {
                        dispatchGroup.leave()
                    }
                    if let error = error {
                        errorMessage = error.localizedDescription
                        return
                    }
                    guard let url = url else {
                        errorMessage = self.plugin?.errorUnknown
                        return
                    }
                    do {
                        let temporaryUrl = try self.saveTemporaryFile(url)
                        temporaryUrls.append(temporaryUrl)
                    } catch {
                        errorMessage = self.plugin?.errorTemporaryCopyFailed
                    }
                })
            } else {
                errorMessage = self.plugin?.errorUnsupportedFileTypeIdentifier
            }
        }
        dispatchGroup.notify(queue: .main) {
            if let errorMessage = errorMessage {
                self.plugin?.handleDocumentPickerResult(urls: nil, error: errorMessage)
                return
            }
            self.plugin?.handleDocumentPickerResult(urls: temporaryUrls, error: nil)
        }
    }
}
