import Foundation
import Capacitor

@objc public class PromptParameters: NSObject {
    let mode: String?
    let additionalMethods: [String]

    init(_ obj: JSObject) throws {
        self.mode = obj["mode"] as? String

        if let additionalMethodsArray = obj["additionalMethods"] as? [String] {
            self.additionalMethods = additionalMethodsArray
        } else {
            self.additionalMethods = []
        }
    }
}
