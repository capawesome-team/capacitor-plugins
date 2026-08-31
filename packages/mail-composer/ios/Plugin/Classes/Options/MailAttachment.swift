import Foundation
import Capacitor

@objc public class MailAttachment: NSObject {
    let data: Data?
    let name: String?
    let path: String?

    init(_ object: JSObject) throws {
        let data = object["data"] as? String
        self.name = object["name"] as? String
        self.path = object["path"] as? String
        if data == nil && self.path == nil {
            throw CustomError.attachmentDataOrPathMissing
        }
        if let data = data {
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
}
