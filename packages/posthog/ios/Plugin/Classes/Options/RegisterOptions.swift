import Foundation

@objc public class RegisterOptions: NSObject {
    private var key: String
    private var value: AnyObject

    init(key: String, value: AnyObject) {
        self.key = key
        self.value = value
    }

    func getKey() -> String {
        return key
    }

    func getValue() -> AnyObject {
        return value
    }
}
