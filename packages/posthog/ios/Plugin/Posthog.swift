import Foundation

@objc public class Posthog: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
