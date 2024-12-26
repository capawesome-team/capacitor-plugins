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

            if let image, let imageData = image.jpegData(compressionQuality: 0.9) {
                let result = TakeResult(b64String: imageData.base64EncodedString())
                completion(result, nil)
            } else {
                completion(nil, CustomError.imageMissing)
            }
        }
    }
}
