import Foundation
import Capacitor

@objc public class ExecuteScriptOptions: NSObject {
    let script: String

    init(_ call: CAPPluginCall) throws {
        self.script = try ExecuteScriptOptions.getScriptFromCall(call)
    }

    private static func getScriptFromCall(_ call: CAPPluginCall) throws -> String {
        guard let script = call.getString("script"), !script.isEmpty else {
            throw CustomError.scriptMissing
        }
        return script
    }
}
