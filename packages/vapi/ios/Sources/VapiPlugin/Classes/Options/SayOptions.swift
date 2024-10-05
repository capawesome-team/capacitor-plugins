import Foundation
import Capacitor

@objc public class SayOptions: NSObject {
    private var message: String

    init(message: String) {
        self.message = message
    }

    func getMessage() -> String {
        return message
    }
}
