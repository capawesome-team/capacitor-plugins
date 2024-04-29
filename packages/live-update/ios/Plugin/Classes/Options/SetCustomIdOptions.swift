import Foundation
import Capacitor

@objc public class SetCustomIdOptions: NSObject {
    private var customId: String

    init(customId: String) {
        self.customId = customId
    }

    func getCustomId() -> String {
        return customId
    }
}
