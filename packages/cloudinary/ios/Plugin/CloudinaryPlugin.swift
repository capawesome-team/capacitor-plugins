import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CloudinaryPlugin)
public class CloudinaryPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "CloudinaryPlugin"
    public let jsName = "Cloudinary"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "uploadResource", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "downloadResource", returnType: CAPPluginReturnPromise)
    ]
    public let errorNotInitialized = "Plugin is not initialized."
    public let errorFileNotFound = "The file was not found."
    public let errorUnknown = "An unknown error occurred."
    public let errorCloudNameMissing = "cloudName must be provided."
    public let errorPathMissing = "path must be provided."
    public let errorResourceTypeMissing = "resourceType must be provided."
    public let errorUploadPresetMissing = "uploadPreset must be provided."
    public let errorUrlMissing = "url must be provided."

    private var implementation: Cloudinary?
    private var initialized = false

    override public func load() {
        implementation = Cloudinary(plugin: self)
    }

    @objc func initialize(_ call: CAPPluginCall) {
        guard let cloudName = call.getString("cloudName") else {
            call.reject(errorCloudNameMissing)
            return
        }
        implementation?.initialize(cloudName)
        initialized = true
        call.resolve()
    }

    @objc func uploadResource(_ call: CAPPluginCall) {
        if !initialized {
            call.reject(errorNotInitialized)
            return
        }
        guard let resourceType = call.getString("resourceType") else {
            call.reject(errorResourceTypeMissing)
            return
        }
        guard let uploadPreset = call.getString("uploadPreset") else {
            call.reject(errorUploadPresetMissing)
            return
        }
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }
        let publicId = call.getString("publicId")
        implementation?.uploadResource(resourceType: resourceType, path: path, uploadPreset: uploadPreset, publicId: publicId, completion: { resultData, errorMessage in
            if let errorMessage = errorMessage {
                call.reject(errorMessage)
                return
            }
            guard let resultData = resultData else {
                call.reject(self.errorUnknown)
                return
            }
            let result = CloudinaryHelper.createUploadResourceResult(resultData)
            call.resolve(result)
        })
    }

    @objc func downloadResource(_ call: CAPPluginCall) {
        if !initialized {
            call.reject(errorNotInitialized)
            return
        }
        guard let url = call.getString("url") else {
            call.reject(errorUrlMissing)
            return
        }
        implementation?.downloadResource(url: url, completion: { path, errorMessage in
            if let errorMessage = errorMessage {
                call.reject(errorMessage)
                return
            }
            guard let path = path else {
                call.reject(self.errorUnknown)
                return
            }
            call.resolve([
                "path": path
            ])
        })
    }
}
