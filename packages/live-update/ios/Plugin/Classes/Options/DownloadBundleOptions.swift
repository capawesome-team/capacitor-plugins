import Foundation
import Capacitor

@objc public class DownloadBundleOptions: NSObject {
    private var appId: String?
    private var artifactType: ArtifactType
    private var bundleId: String
    private var checksum: String?
    private var signature: String?
    private var url: String
    /// The template for the files of the bundle, never set for a download from JavaScript.
    let urlTemplate: UrlTemplate?

    convenience init(artifactType: String, bundleId: String, checksum: String?, signature: String?, url: String) {
        let artifactType: ArtifactType = artifactType == "manifest" ? .manifest : .zip
        self.init(artifactType: artifactType, bundleId: bundleId, checksum: checksum, signature: signature, url: url)
    }

    init(artifactType: ArtifactType, bundleId: String, checksum: String?, signature: String?, url: String) {
        self.appId = nil
        self.artifactType = artifactType
        self.bundleId = bundleId
        self.checksum = checksum
        self.signature = signature
        self.url = url
        self.urlTemplate = nil
    }

    /// - Parameter appId: The app whose failure is recorded.
    init(bundle: CreateCheckResponse.Bundle, appId: String) {
        self.appId = appId
        self.artifactType = bundle.artifactType
        self.bundleId = bundle.id
        self.checksum = bundle.checksum
        self.signature = bundle.signature
        self.url = bundle.url
        self.urlTemplate = bundle.urlTemplate
    }

    /// - Returns: The app whose failure is recorded, or `nil` for the app of the plugin.
    func getAppId() -> String? {
        return appId
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

    /// The checksum is always set with `urlTemplate`, because a bundle with a template and no checksum is not parsed.
    func requireChecksum() throws -> String {
        guard let checksum = checksum else {
            throw CustomError.checksumMissing
        }
        return checksum
    }
}
