import Foundation
import Crisp

public class CrispHelper {
    public static func getMessageContent(_ message: Message) -> String? {
        switch message.content {
        case .text(let text):
            return text
        case .textWithAttachment(let text, _, _):
            return text
        case .textWithVideoAttachment(let text, _, _):
            return text
        default:
            return nil
        }
    }
}
