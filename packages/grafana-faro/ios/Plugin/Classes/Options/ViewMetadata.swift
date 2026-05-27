import Capacitor
import Foundation

@objc public class ViewMetadata: NSObject {
    private let name: String

    init(call: CAPPluginCall) throws {
        guard let name = call.getString("name"), !name.isEmpty else {
            throw CustomError.nameMissing
        }
        self.name = name
    }

    init(source: JSObject) throws {
        guard let name = source["name"] as? String, !name.isEmpty else {
            throw CustomError.nameMissing
        }
        self.name = name
    }

    func getName() -> String {
        return name
    }
}
