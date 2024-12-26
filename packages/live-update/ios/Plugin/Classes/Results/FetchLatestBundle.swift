import Foundation
import Capacitor

@objc public class FetchLatestBundleResult: NSObject, Result {
    private let artifactType: ArtifactType?
    private let bundleId: String?
    private let downloadUrl: String?

    init(artifactType: ArtifactType?, bundleId: String?, downloadUrl: String?) {
        self.artifactType = artifactType
        self.bundleId = bundleId
        self.downloadUrl = downloadUrl
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        if artifactType == .manifest {
            result["artifactType"] = "manifest"
        } else if artifactType == .zip {
            result["artifactType"] = "zip"
        }
        result["bundleId"] = bundleId == nil ? NSNull() : bundleId
        if let downloadUrl = downloadUrl {
            result["downloadUrl"] = downloadUrl
        }
        return result as AnyObject
    }
}
