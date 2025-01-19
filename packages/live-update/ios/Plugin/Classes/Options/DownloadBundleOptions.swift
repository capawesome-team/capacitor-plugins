import Foundation
import Capacitor

@objc public class DownloadBundleOptions: NSObject {
    private var artifactType: ArtifactType
    private var bundleId: String
    private var url: String

    init(artifactType: String, bundleId: String, url: String) {
        if artifactType == "manifest" {
            self.artifactType = .manifest
        } else {
            self.artifactType = .zip
        }
        self.bundleId = bundleId
        self.url = url
    }

    func getArtifactType() -> ArtifactType {
        return artifactType
    }

    func getBundleId() -> String {
        return bundleId
    }

    func getUrl() -> String {
        return url
    }
}
