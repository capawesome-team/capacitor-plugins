import Foundation

@objc public class UnregisterOptions: NSObject {
    private var key: String

    init(key: String) {
        self.key = key
    }

    func getKey() -> String {
        return key
    }
}
