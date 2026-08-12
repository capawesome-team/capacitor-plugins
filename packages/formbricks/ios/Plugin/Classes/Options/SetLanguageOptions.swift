import Foundation
import Capacitor

@objc public class SetLanguageOptions: NSObject {
    private let language: String

    init(call: CAPPluginCall) throws {
        guard let language = call.getString("language") else {
            throw CustomError.languageMissing
        }
        self.language = language
    }

    func getLanguage() -> String {
        return language
    }
}
