import Foundation

@objc public class RealtimeKit: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
