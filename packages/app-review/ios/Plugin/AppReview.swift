import Foundation
import Capacitor
import StoreKit

@objc public class AppReview: NSObject {
    private let plugin: AppReviewPlugin

    init(plugin: AppReviewPlugin) {
        self.plugin = plugin
    }

    @objc public func openAppStore(completion: @escaping (Error?) -> Void) {
        if let info = Bundle.main.infoDictionary,
           let bundleId = info["CFBundleIdentifier"] as? String,
           let url = URL(string: "https://apps.apple.com/app/id\(bundleId)?action=write-review") {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
            completion(nil)
        } else {
            completion(CustomError.bundleIdNotFound)
        }
    }

    @objc public func requestReview(completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            if let scene = UIApplication.shared.connectedScenes.first as? UIWindowScene {
                if #available(iOS 18, *) {
                    AppStore.requestReview(in: scene)
                    completion(nil)
                } else if #available(iOS 14.0, *) {
                    SKStoreReviewController.requestReview(in: scene)
                    completion(nil)
                } else {
                    completion(CustomError.unavailable)
                }
            }
        }
    }
}
