import Foundation

@objc public class Permissions: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
