import Capacitor
import Foundation

@objc public class PushLogOptions: NSObject {
    private let context: [String: String]?
    private let level: String
    private let message: String

    init(call: CAPPluginCall) throws {
        guard let message = call.getString("message") else {
            throw CustomError.messageMissing
        }
        self.context = call.getObject("context") as? [String: String]
        self.level = call.getString("level") ?? GrafanaFaroPlugin.levelInfo
        self.message = message
    }

    func getContext() -> [String: String]? {
        return context
    }

    func getLevel() -> String {
        return level
    }

    func getMessage() -> String {
        return message
    }
}
