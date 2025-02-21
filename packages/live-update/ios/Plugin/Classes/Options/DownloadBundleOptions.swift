import Foundation
import Capacitor

@objc public class DownloadBundleOptions: NSObject {
    private var artifactType: ArtifactType
    private var bundleId: String
    private var checksum: String?
    private var signature: String?
    private var url: String

    init(artifactType: String, bundleId: String, checksum: String?, signature: String?, url: String) {
        if artifactType == "manifest" {
            self.artifactType = .manifest
        } else {
            self.artifactType = .zip
        }
        self.bundleId = bundleId
        self.checksum = checksum
        self.signature = signature
        self.url = url
    }

    func getArtifactType() -> ArtifactType {
        return artifactType
    }

    func getBundleId() -> String {
        return bundleId
    }

    func getChecksum() -> String? {
        return checksum
    }

    func getSignature() -> String? {
        return signature
    }

    func getUrl() -> String {
        return url
    }
}
