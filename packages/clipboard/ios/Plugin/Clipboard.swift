import Foundation
import Capacitor
import UIKit
import UniformTypeIdentifiers

@objc public class Clipboard: NSObject {
    private let plugin: ClipboardPlugin

    init(plugin: ClipboardPlugin) {
        self.plugin = plugin
    }

    @objc public func read(completion: @escaping (ReadResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            let pasteboard = UIPasteboard.general
            if let image = pasteboard.image, let data = image.pngData() {
                let value = "data:image/png;base64," + data.base64EncodedString()
                completion(ReadResult(type: .image, value: value), nil)
                return
            }
            if let data = pasteboard.data(forPasteboardType: UTType.html.identifier), let html = String(data: data, encoding: .utf8) {
                completion(ReadResult(type: .html, value: html), nil)
                return
            }
            if let string = pasteboard.string {
                let type: ClipboardContentType = string.hasPrefix("http://") || string.hasPrefix("https://") ? .url : .text
                completion(ReadResult(type: type, value: string), nil)
                return
            }
            if let url = pasteboard.url {
                completion(ReadResult(type: .url, value: url.absoluteString), nil)
                return
            }
            completion(nil, CustomError.emptyClipboard)
        }
    }

    @objc public func write(_ options: WriteOptions, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            let pasteboard = UIPasteboard.general
            if let image = options.image {
                guard let decodedImage = self.decodeDataUrl(image) else {
                    completion(CustomError.writeFailed)
                    return
                }
                pasteboard.image = decodedImage
            } else if let html = options.html {
                var item: [String: Any] = [UTType.html.identifier: html]
                item[UTType.utf8PlainText.identifier] = options.text ?? html
                pasteboard.items = [item]
            } else if let url = options.url {
                guard let url = URL(string: url) else {
                    completion(CustomError.writeFailed)
                    return
                }
                pasteboard.url = url
            } else if let text = options.text {
                pasteboard.string = text
            }
            completion(nil)
        }
    }

    private func decodeDataUrl(_ dataUrl: String) -> UIImage? {
        var base64 = dataUrl
        if let range = dataUrl.range(of: ",") {
            base64 = String(dataUrl[range.upperBound...])
        }
        guard let data = Data(base64Encoded: base64) else {
            return nil
        }
        return UIImage(data: data)
    }
}
