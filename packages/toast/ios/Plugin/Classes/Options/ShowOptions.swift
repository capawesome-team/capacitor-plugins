import Foundation
import Capacitor

@objc public class ShowOptions: NSObject {
    let duration: String
    let position: String
    let text: String

    init(_ call: CAPPluginCall) throws {
        guard let text = call.getString("text") else {
            throw CustomError.textMissing
        }
        self.text = text
        self.duration = call.getString("duration", "SHORT")
        self.position = call.getString("position", "BOTTOM")
    }
}
