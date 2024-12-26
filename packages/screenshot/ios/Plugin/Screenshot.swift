import Foundation
import WebKit

@objc public class Screenshot: NSObject {
    private let webView: WKWebView

    public init(webView: WKWebView) {
        self.webView = webView
    }

    @objc public func take(completion: @escaping (Result?, Error?) -> Void) {
        let snapshotConfiguration = WKSnapshotConfiguration()
        snapshotConfiguration.rect = webView.bounds
        webView.takeSnapshot(with: snapshotConfiguration) { image, error in
            if let error = error {
                completion(nil, error)
            }

            if let image, let imageData = image.jpegData(compressionQuality: 1.0) {
                let filename = UUID().uuidString + ".jpg"
                guard let cacheDirectory = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first else {
                    completion(nil, CustomError.imageMissing)
                    return
                }
                let fileURL = cacheDirectory.appendingPathComponent(filename)
                do {
                    try imageData.write(to: fileURL)
                    let result = TakeResult(uri: fileURL.absoluteString)
                    completion(result, nil)
                } catch let error {
                    completion(nil, error)
                }
            } else {
                completion(nil, CustomError.imageMissing)
            }
        }
    }
}
