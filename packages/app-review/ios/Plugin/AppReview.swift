import Foundation

@objc public class AppReview: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
