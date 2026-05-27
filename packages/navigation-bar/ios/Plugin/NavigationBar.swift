import Foundation

@objc public class NavigationBar: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
