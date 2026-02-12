import Foundation

@objc public class GoogleSignIn: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
