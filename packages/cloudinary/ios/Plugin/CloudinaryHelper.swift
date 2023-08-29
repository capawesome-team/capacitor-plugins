import Foundation
import Cloudinary
import Capacitor

public class CloudinaryHelper {

    public static func createUploadResourceResult(_ resultData: CLDUploadResult) -> JSObject {
        var result = JSObject()
        result["createdAt"] = resultData.createdAt
        result["duration"] = resultData.duration
        result["format"] = resultData.format
        result["height"] = resultData.height
        result["originalFilename"] = resultData.originalFilename
        result["resourceType"] = resultData.resourceType
        result["publicId"] = resultData.publicId
        result["url"] = resultData.url
        result["secureUrl"] = resultData.secureUrl
        result["width"] = resultData.width
        return result
    }
}
