import Foundation
import UIKit

@objc public class TextZoom: NSObject {
    private let defaultBodyPointSize: Double = 17.0
    private let plugin: TextZoomPlugin

    init(plugin: TextZoomPlugin) {
        self.plugin = plugin
    }

    @objc public func getPreferredZoom(completion: @escaping (GetPreferredZoomResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            let pointSize = Double(UIFont.preferredFont(forTextStyle: .body).pointSize)
            let zoom = pointSize / self.defaultBodyPointSize
            completion(GetPreferredZoomResult(zoom: zoom), nil)
        }
    }

    @objc public func getZoom(completion: @escaping (GetZoomResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            guard let webView = self.plugin.bridge?.webView else {
                completion(GetZoomResult(zoom: 1.0), nil)
                return
            }
            webView.evaluateJavaScript("document.body.style.webkitTextSizeAdjust") { value, error in
                if let error = error {
                    completion(nil, error)
                    return
                }
                let zoom = TextZoom.parseZoom(from: value as? String)
                completion(GetZoomResult(zoom: zoom), nil)
            }
        }
    }

    @objc public func setZoom(_ options: SetZoomOptions, completion: @escaping (Error?) -> Void) {
        let percentage = Int((options.zoom * 100).rounded())
        DispatchQueue.main.async {
            guard let webView = self.plugin.bridge?.webView else {
                completion(nil)
                return
            }
            webView.evaluateJavaScript("document.body.style.webkitTextSizeAdjust = '\(percentage)%'") { _, error in
                completion(error)
            }
        }
    }

    private static func parseZoom(from value: String?) -> Double {
        guard let value = value, !value.isEmpty else {
            return 1.0
        }
        let trimmed = value.replacingOccurrences(of: "%", with: "")
        guard let percentage = Double(trimmed) else {
            return 1.0
        }
        return percentage / 100.0
    }
}
