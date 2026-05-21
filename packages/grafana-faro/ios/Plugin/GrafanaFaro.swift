import Foundation

@objc public class GrafanaFaro: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
