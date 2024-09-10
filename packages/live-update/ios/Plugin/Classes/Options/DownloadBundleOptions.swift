import Foundation
import Capacitor

@objc public class DownloadBundleOptions: NSObject {
    private var bundleId: String
    private var checksum: String?
    private var url: String

    init(bundleId: String, checksum: String?, url: String) {
        self.bundleId = bundleId
        self.checksum = checksum
        self.url = url
    }

    func getBundleId() -> String {
        return bundleId
    }

    func getChecksum() -> String? {
        return checksum
    }

    func getUrl() -> String {
        return url
    }
}
