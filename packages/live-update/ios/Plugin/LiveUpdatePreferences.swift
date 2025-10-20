import Foundation

public class LiveUpdatePreferences: NSObject {
    private let channelKey = "channel" // DO NOT CHANGE
    private let customIdKey = "customId" // DO NOT CHANGE
    private let previousBundleIdKey = "previousBundleIdKey" // DO NOT CHANGE

    public func getChannel() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: channelKey))
    }

    public func getCustomId() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: customIdKey))
    }

    public func getPreviousBundleId() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: previousBundleIdKey))
    }

    public func setChannel(_ value: String?) {
        if let value = value {
            UserDefaults.standard.set(value, forKey: applyPrefix(to: channelKey))
        } else {
            UserDefaults.standard.removeObject(forKey: applyPrefix(to: channelKey))
        }
        UserDefaults.standard.synchronize()
    }

    public func setCustomId(_ value: String) {
        UserDefaults.standard.set(value, forKey: applyPrefix(to: customIdKey))
        UserDefaults.standard.synchronize()
    }

    public func setPreviousBundleId(_ value: String?) {
        if let value = value {
            UserDefaults.standard.set(value, forKey: applyPrefix(to: previousBundleIdKey))
        } else {
            UserDefaults.standard.removeObject(forKey: applyPrefix(to: previousBundleIdKey))
        }
        UserDefaults.standard.synchronize()
    }

    private func applyPrefix(to key: String) -> String {
        return LiveUpdatePlugin.userDefaultsPrefix + "." + key
    }
}
