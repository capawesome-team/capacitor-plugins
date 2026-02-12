import Foundation

@objc public class Pixlive: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
