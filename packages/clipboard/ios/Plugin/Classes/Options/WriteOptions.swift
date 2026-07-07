import Foundation
import Capacitor

@objc public class WriteOptions: NSObject {
    let html: String?
    let image: String?
    let text: String?
    let url: String?

    init(_ call: CAPPluginCall) throws {
        self.html = call.getString("html")
        self.image = call.getString("image")
        self.text = call.getString("text")
        self.url = call.getString("url")
        if html == nil && image == nil && text == nil && url == nil {
            throw CustomError.contentMissing
        }
    }
}
