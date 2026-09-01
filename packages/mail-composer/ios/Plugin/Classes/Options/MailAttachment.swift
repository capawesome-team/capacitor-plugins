import Foundation
import Capacitor

@objc public class MailAttachment: NSObject {
    let data: Data?
    let name: String?
    let path: String?

    init(_ object: JSObject) throws {
        self.name = MailAttachment.getName(from: object)
        self.path = object["path"] as? String
        if self.path == nil {
            guard let data = object["data"] as? String else {
                throw CustomError.attachmentDataOrPathMissing
            }
            if self.name == nil {
                throw CustomError.attachmentNameMissing
            }
            self.data = try MailAttachment.decodeData(data)
        } else {
            self.data = nil
        }
    }

    private static func decodeData(_ data: String) throws -> Data {
        guard let decodedData = Data(base64Encoded: data) else {
            throw CustomError.attachmentDataInvalid
        }
        return decodedData
    }

    private static func getName(from object: JSObject) -> String? {
        guard let name = object["name"] as? String else {
            return nil
        }
        // Strip directory components so that the attachment file name matches the one used on Android.
        return (name as NSString).lastPathComponent
    }
}
