import Foundation

@objc public class Vapi: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
