import Foundation
import Capacitor

@objc public class SelectTabByIdOptions: NSObject {
    let id: String

    init(_ call: CAPPluginCall) throws {
        guard let id = call.getString("id") else {
            throw CustomError.idMissing
        }
        self.id = id
    }
}
