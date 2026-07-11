import Foundation
import Capacitor

@objc public class OpenHelpdeskArticleOptions: NSObject {
    let category: String?
    let id: String
    let locale: String
    let title: String?

    init(_ call: CAPPluginCall) throws {
        guard let id = call.getString("id") else {
            throw CustomError.idMissing
        }
        guard let locale = call.getString("locale") else {
            throw CustomError.localeMissing
        }
        self.id = id
        self.locale = locale
        self.category = call.getString("category")
        self.title = call.getString("title")
    }
}
