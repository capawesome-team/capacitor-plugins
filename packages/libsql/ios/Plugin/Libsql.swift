import Foundation

@objc public class Libsql: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
