import Capacitor

@objc public class SetInterfaceLanguageOptions: NSObject {
    let language: String

    init(_ call: CAPPluginCall) throws {
        self.language = try SetInterfaceLanguageOptions.getLanguageFromCall(call)
    }

    private static func getLanguageFromCall(_ call: CAPPluginCall) throws -> String {
        guard let language = call.getString("language") else {
            throw CustomError.languageMissing
        }
        return language
    }
}
