import Foundation
import Capacitor

@objc public class DownloadBundleOptions: NSObject {
    private var bundleId: String
    private var url: String

    init(bundleId: String, url: String) {
        self.bundleId = bundleId
        self.url = url
    }

    func getBundleId() -> String {
        return bundleId
    }

    func getUrl() -> String {
        return url
    }
}
