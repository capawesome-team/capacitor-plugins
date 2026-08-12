import Foundation
import Capacitor

@objc public class PresentContentOptions: NSObject {
    let id: String?
    let ids: [String]?
    let type: String

    init(_ call: CAPPluginCall) throws {
        guard let type = call.getString("type") else {
            throw CustomError.typeInvalid
        }
        self.type = type
        self.id = call.getString("id")
        self.ids = call.getArray("ids", String.self)
        switch type {
        case "article", "carousel", "survey", "conversation":
            if id == nil {
                throw CustomError.idMissing
            }
        case "help-center-collections":
            if ids == nil || ids?.isEmpty == true {
                throw CustomError.idsMissing
            }
        default:
            throw CustomError.typeInvalid
        }
    }
}
