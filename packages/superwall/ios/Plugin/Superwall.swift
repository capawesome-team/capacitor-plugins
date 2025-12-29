import Foundation

@objc public class Superwall: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
