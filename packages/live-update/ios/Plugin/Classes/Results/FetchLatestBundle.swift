import Foundation
import Capacitor

@objc public class FetchLatestBundleResult: NSObject, Result {
    private let artifactType: ArtifactType?
    private let bundleId: String?
    private let checksum: String?
    private let downloadUrl: String?
    private let signature: String?

    init(artifactType: ArtifactType?, bundleId: String?, checksum: String?, downloadUrl: String?, signature: String?) {
        self.artifactType = artifactType
        self.bundleId = bundleId
        self.checksum = checksum
        self.downloadUrl = downloadUrl
        self.signature = signature
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        if artifactType == .manifest {
            result["artifactType"] = "manifest"
        } else if artifactType == .zip {
            result["artifactType"] = "zip"
        }
        result["bundleId"] = bundleId == nil ? NSNull() : bundleId
        if let checksum = checksum {
            result["checksum"] = checksum
        }
        if let downloadUrl = downloadUrl {
            result["downloadUrl"] = downloadUrl
        }
        if let signature = signature {
            result["signature"] = signature
        }
        return result as AnyObject
    }
}
