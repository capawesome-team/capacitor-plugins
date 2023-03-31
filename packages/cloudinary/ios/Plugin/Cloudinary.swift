import Foundation
import Cloudinary

@objc public class Cloudinary: NSObject {
    private let plugin: CloudinaryPlugin
    private var cloudinary: CLDCloudinary?

    init(plugin: CloudinaryPlugin) {
        self.plugin = plugin
    }

    @objc public func initialize(_ cloudName: String) {
        let config = CLDConfiguration(cloudName: cloudName, secure: true)
        self.cloudinary = CLDCloudinary(configuration: config)
    }

    @objc public func uploadResource(resourceType: String, path: String, uploadPreset: String, publicId: String?, completion: @escaping (CLDUploadResult?, String?) -> Void) {
        guard let url = self.getFileUrlByPath(path) else {
            completion(nil, plugin.errorFileNotFound)
            return
        }
        let params: CLDUploadRequestParams = CLDUploadRequestParams()
        params.setResourceType(resourceType)
        if let publicId = publicId {
            params.setPublicId(publicId)
        }
        self.cloudinary?.createUploader().uploadLarge(url: url, uploadPreset: uploadPreset, params: params, preprocessChain: CLDImagePreprocessChain()) { (progress) in
            print(progress.fractionCompleted)
        } completionHandler: { (resultData, error) in
            if let error = error {
                completion(nil, error.description)
                return
            }
            completion(resultData, nil)
        }
    }

    @objc public func downloadResource(url: String, completion: @escaping (String?, String?) -> Void) {
        let secureUrl = url.replacingOccurrences(of: "http://", with: "https://")
        self.cloudinary?.createDownloader().fetchAsset(secureUrl) { (progress) in
            print(progress.fractionCompleted)
        } completionHandler: { (asset, error) in
            if let error = error {
                completion(nil, error.description)
                return
            }
            let fileName = URL(string: url)?.lastPathComponent ?? "unknown"
            let url = self.saveToTemporaryDirectory(data: asset!, fileName: fileName)
            guard let url = url else {
                completion(nil, self.plugin.errorUnknown)
                return
            }
            let path = self.getPathFromUrl(url)
            completion(path, nil)
        }
    }

    @objc private func getFileUrlByPath(_ path: String) -> URL? {
        guard let url = URL.init(string: path) else {
            return nil
        }
        if FileManager.default.fileExists(atPath: url.path) {
            return url
        } else {
            return nil
        }
    }

    private func saveToTemporaryDirectory(data: Data, fileName: String) -> URL? {
        let url = FileManager.default.temporaryDirectory.appendingPathComponent(fileName)
        do {
            try data.write(to: url, options: .atomic)
            return url
        } catch {
            return nil
        }
    }

    public func getPathFromUrl(_ url: URL) -> String {
        return url.absoluteString
    }
}
