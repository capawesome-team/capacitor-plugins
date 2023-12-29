import Foundation
import Capacitor
import UIKit
import MobileCoreServices

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FilePickerPlugin)
public class FilePickerPlugin: CAPPlugin {
    public let errorPathMissing = "path must be provided."
    public let errorFileNotExist = "File does not exist."
    public let errorConvertFailed = "File could not be converted."
    public let errorPickFileCanceled = "pickFiles canceled."
    public let errorUnknown = "Unknown error occurred."
    public let errorTemporaryCopyFailed = "An unknown error occurred while creating a temporary copy of the file."
    public let errorUnsupportedFileTypeIdentifier = "Unsupported file type identifier."
    public let pickerDismissedEvent = "pickerDismissed"
    private var implementation: FilePicker?
    private var savedCall: CAPPluginCall?

    override public func load() {
        self.implementation = FilePicker(self)
    }

    @objc func convertHeicToJpeg(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }
        guard let url = implementation?.getFileUrlByPath(path) else {
            call.reject(errorFileNotExist)
            return
        }

        do {
            let jpegUrl = try implementation?.convertHeicToJpeg(url)
            guard let jpegUrl = jpegUrl else {
                call.reject(errorConvertFailed)
                return
            }

            var result = JSObject()
            result["path"] = jpegUrl.absoluteString
            call.resolve(result)
        } catch let error as NSError {
            call.reject(error.localizedDescription, nil, error)
        }
    }

    @objc func pickFiles(_ call: CAPPluginCall) {
        savedCall = call

        let multiple = call.getBool("multiple", false)
        let types = call.getArray("types", String.self) ?? []
        let parsedTypes = parseTypesOption(types)
        let documentTypes = parsedTypes.isEmpty ? ["public.data"] : parsedTypes

        implementation?.openDocumentPicker(multiple: multiple, documentTypes: documentTypes)
    }

    @objc func pickImages(_ call: CAPPluginCall) {
        savedCall = call

        let multiple = call.getBool("multiple", false)
        let skipTranscoding = call.getBool("skipTranscoding", false)
        let limit = call.getInt("limit")

        implementation?.openImagePicker(multiple: multiple, skipTranscoding: skipTranscoding, limit: limit)
    }

    @objc func pickMedia(_ call: CAPPluginCall) {
        savedCall = call

        let multiple = call.getBool("multiple", false)
        let skipTranscoding = call.getBool("skipTranscoding", false)
        let limit = call.getInt("limit")

        implementation?.openMediaPicker(multiple: multiple, skipTranscoding: skipTranscoding, limit: limit)
    }

    @objc func pickVideos(_ call: CAPPluginCall) {
        savedCall = call

        let multiple = call.getBool("multiple", false)
        let skipTranscoding = call.getBool("skipTranscoding", false)
        let limit = call.getInt("limit")

        implementation?.openVideoPicker(multiple: multiple, skipTranscoding: skipTranscoding, limit: limit)
    }

    @objc func notifyPickerDismissedListener() {
        notifyListeners(pickerDismissedEvent, data: nil)
    }

    @objc func handleDocumentPickerResult(urls: [URL]?, error: String?) {
        guard let savedCall = savedCall else {
            return
        }
        if let error = error {
            savedCall.reject(error)
            return
        }
        guard let urls = urls else {
            savedCall.reject(errorPickFileCanceled)
            return
        }
        let readData = savedCall.getBool("readData", false)
        do {
            var result = JSObject()
            let filesResult = try urls.map {(url: URL) -> JSObject in
                var file = JSObject()
                if readData == true {
                    file["data"] = try implementation?.getDataFromUrl(url) ?? ""
                }
                let duration = implementation?.getDurationFromUrl(url)
                if let duration = duration {
                    file["duration"] = duration
                }
                let (height, width) = implementation?.getHeightAndWidthFromUrl(url) ?? (nil, nil)
                if let height = height {
                    file["height"] = height
                }
                if let width = width {
                    file["width"] = width
                }
                file["modifiedAt"] = implementation?.getModifiedAtFromUrl(url) ?? 0
                file["mimeType"] = implementation?.getMimeTypeFromUrl(url) ?? ""
                file["name"] = implementation?.getNameFromUrl(url) ?? ""
                file["path"] = implementation?.getPathFromUrl(url) ?? ""
                file["size"] = try implementation?.getSizeFromUrl(url) ?? -1
                return file
            }
            result["files"] = filesResult
            savedCall.resolve(result)
        } catch let error as NSError {
            savedCall.reject(error.localizedDescription, nil, error)
            return
        }
    }

    private func parseTypesOption(_ types: [String]) -> [String] {
        var parsedTypes: [String] = []
        for (_, type) in types.enumerated() {
            guard let utType: String = UTTypeCreatePreferredIdentifierForTag(kUTTagClassMIMEType, type as CFString, nil)?.takeRetainedValue() as String? else {
                continue
            }
            parsedTypes.append(utType)
        }
        return parsedTypes
    }
}
