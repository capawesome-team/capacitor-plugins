import Foundation

@objc public class AssetManager: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
