import Foundation

@objc public class EdgeToEdge: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
