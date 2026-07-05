import Foundation
import Capacitor

@objc public class StartOptions: NSObject {
    let args: [String]
    let env: [String: String]
    let script: String?

    override init() {
        self.args = []
        self.env = [:]
        self.script = nil
    }

    init(_ call: CAPPluginCall) {
        self.args = call.getArray("args", String.self) ?? []
        self.env = StartOptions.getEnvFromCall(call)
        self.script = call.getString("script")
    }

    private static func getEnvFromCall(_ call: CAPPluginCall) -> [String: String] {
        var env: [String: String] = [:]
        if let envObject = call.getObject("env") {
            for (key, value) in envObject {
                if let value = value as? String {
                    env[key] = value
                }
            }
        }
        return env
    }
}
