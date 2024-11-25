import Foundation
import Capacitor
import StoreKit

@objc public class AppReview: NSObject {
    private let plugin: AppReviewPlugin

    init(plugin: AppReviewPlugin) {
        self.plugin = plugin
    }

    @objc public func openAppStore(_ options: OpenAppStoreOptions, completion: @escaping (Error?) -> Void) {
        guard let url = URL(string: "https://apps.apple.com/app/id\(options.appId)?action=write-review") else {
            completion(CustomError.appIdInvalid)
            return
        }
        DispatchQueue.main.async {
            UIApplication.shared.open(url) { (_) in
                completion(nil)
            }
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
