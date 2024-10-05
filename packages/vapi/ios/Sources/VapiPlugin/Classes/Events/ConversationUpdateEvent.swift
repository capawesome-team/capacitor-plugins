import Foundation
import Vapi
import Capacitor

@objc public class ConversationUpdateEvent: NSObject, Result {
    private var data: ConversationUpdate

    init(data: ConversationUpdate) {
        self.data = data
    }

    public func toJSObject() -> AnyObject {
        var messagesResult = JSArray()
        for item in data.conversation {
            var messageResult = JSObject()
            messageResult["content"] = item.content
            messageResult["role"] = convertRoleToString(item.role)
        }
        
        var result = JSObject()
        result["messages"] = messagesResult
        return result as AnyObject
    }

    private func convertRoleToString(_ role: Message.Role) -> String {
        switch role {
        case .assistant:
            return "assistant"
        case .system:
            return "system"
        case .user:
            return "user"
        }
    }
}
