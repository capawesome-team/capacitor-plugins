import Foundation

@objc public class AppShortcut: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
