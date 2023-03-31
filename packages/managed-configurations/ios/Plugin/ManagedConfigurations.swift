import Foundation

@objc public class ManagedConfigurations: NSObject {
    private let managedConfig: [String: Any?]

    override init() {
        managedConfig = UserDefaults.standard.object(forKey: "com.apple.configuration.managed") as? [String: Any?] ?? [:]
    }

    @objc public func keyExists(_ key: String) -> Bool {
        if managedConfig[key] != nil {
            return true
        }
        return false
    }

    @objc public func getString(_ key: String) -> String {
        // swiftlint:disable:next force_cast
        return managedConfig[key] as! String
    }

    @objc public func getInt(_ key: String) -> Int {
        // swiftlint:disable:next force_cast
        return managedConfig[key] as! Int
    }

    @objc public func getBool(_ key: String) -> Bool {
        // swiftlint:disable:next force_cast
        return managedConfig[key] as! Bool
    }

}
