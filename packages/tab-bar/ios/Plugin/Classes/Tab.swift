import Foundation
import Capacitor

@objc public class Tab: NSObject {
    let badge: String?
    let id: String
    let image: UIImage
    let title: String

    init(_ object: JSObject) throws {
        self.badge = object["badge"] as? String
        self.id = try Tab.getIdFromObject(object)
        self.image = try Tab.getImageFromObject(object)
        self.title = try Tab.getTitleFromObject(object)
    }

    private static func getIdFromObject(_ object: JSObject) throws -> String {
        guard let id = object["id"] as? String else {
            throw CustomError.idMissing
        }
        return id
    }

    private static func getImageFromObject(_ object: JSObject) throws -> UIImage {
        switch (object["systemImage"] as? String, object["image"] as? String) {
        case let (systemImage?, nil):
            guard let image = UIImage(systemName: systemImage) else {
                throw CustomError.systemImageInvalid
            }
            return image
        case let (nil, name?):
            guard let image = UIImage(named: name) else {
                throw CustomError.imageInvalid
            }
            return image
        default:
            throw CustomError.systemImageOrImageInvalid
        }
    }

    private static func getTitleFromObject(_ object: JSObject) throws -> String {
        guard let title = object["title"] as? String else {
            throw CustomError.titleMissing
        }
        return title
    }
}
