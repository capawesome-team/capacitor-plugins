import Foundation

@objc public class AgeSignals: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
