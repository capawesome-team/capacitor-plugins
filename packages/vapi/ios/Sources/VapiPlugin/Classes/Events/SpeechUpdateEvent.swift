import Foundation
import Vapi
import Capacitor

@objc public class SpeechUpdateEvent: NSObject, Result {
    private var data: SpeechUpdate

    init(data: SpeechUpdate) {
        self.data = data
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["role"] = convertRoleToString(data.role)
        result["status"] = convertStatusToString(data.status)
        return result as AnyObject
    }

    private func convertRoleToString(_ role: SpeechUpdate.Role) -> String {
        switch role {
        case .assistant:
            return "assistant"
        case .user:
            return "user"
        }
    }

    private func convertStatusToString(_ status: SpeechUpdate.Status) -> String {
        switch status {
        case .started:
            return "started"
        case .stopped:
            return "stopped"
        }
    }
}
