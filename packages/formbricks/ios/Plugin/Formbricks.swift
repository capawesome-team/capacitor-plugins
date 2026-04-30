import Foundation

@objc public class Formbricks: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
