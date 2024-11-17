import Foundation
import Capacitor
import StoreKit

@objc public class AppReview: NSObject {
    private let plugin: AppReviewPlugin
    private let requestCountKey = "reviewRequestCount"

    init(plugin: AppReviewPlugin) {
        self.plugin = plugin
    }

    @objc public func openAppStore(appID: String) {
        if let url = URL(string: "https://apps.apple.com/app/id\(appID)?action=write-review") {
            UIApplication.shared.open(url, options: [:], completionHandler: nil)
        } else {
            CAPLog.print("Could not open App Store with appID: \(appID)")
        }

    }

    @objc public func requestReview() {
        DispatchQueue.main.async {
            if let scene = UIApplication.shared.connectedScenes.first as? UIWindowScene {
                if #available(iOS 18, *) {
                    AppStore.requestReview(in: scene)
                } else if #available(iOS 14.0, *) {
                    SKStoreReviewController.requestReview(in: scene)
                } else {
                    CAPLog.print("Review request is not available on this device")
                }
            }
        }
    }
}
