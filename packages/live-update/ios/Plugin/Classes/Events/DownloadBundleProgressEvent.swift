import Foundation
import Capacitor

@objc public class DownloadBundleProgressEvent: NSObject, Result {
    let bundleId: String
    let downloadedBytes: Int64
    let progress: Double
    let totalBytes: Int64

    init(bundleId: String, downloadedBytes: Int64, totalBytes: Int64) {
        self.bundleId = bundleId
        self.downloadedBytes = downloadedBytes
        self.progress = Double(downloadedBytes) / Double(totalBytes)
        self.totalBytes = totalBytes
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["bundleId"] = bundleId
        result["downloadedBytes"] = Int(truncatingIfNeeded: downloadedBytes)
        result["progress"] = progress
        result["totalBytes"] = Int(truncatingIfNeeded: totalBytes)
        return result as AnyObject
    }
}
