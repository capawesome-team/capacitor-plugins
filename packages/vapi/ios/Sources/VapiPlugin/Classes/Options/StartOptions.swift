import Foundation
import Capacitor

@objc public class StartOptions: NSObject {
    private var assistantId: String

    init(assistantId: String) {
        self.assistantId = assistantId
    }
    
    func getAssistantId() -> String {
        return assistantId
    }
}
