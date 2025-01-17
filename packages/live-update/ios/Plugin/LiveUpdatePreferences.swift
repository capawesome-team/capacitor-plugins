import Foundation

public class LiveUpdatePreferences: NSObject {
    private let channelKey = "channel" // DO NOT CHANGE
    private let customIdKey = "customId" // DO NOT CHANGE
    private let lastBundleShortVersionStringKey = "lastBundleShortVersionString" // DO NOT CHANGE
    private let lastBundleVersionKey = "lastBundleVersion" // DO NOT CHANGE

    public func getChannel() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: channelKey))
    }

    public func getCustomId() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: customIdKey))
    }
    
    public func getLastBundleShortVersionString() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: lastBundleShortVersionStringKey))
    }

    public func getLastBundleVersion() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: lastBundleVersionKey))
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
    
    public func setLastBundleShortVersionString(_ value: String?) {
        if let value = value {
            UserDefaults.standard.set(value, forKey: applyPrefix(to: lastBundleShortVersionStringKey))
        } else {
            UserDefaults.standard.removeObject(forKey: applyPrefix(to: lastBundleShortVersionStringKey))
        }
        UserDefaults.standard.synchronize()
    }

    public func setLastBundleVersion(_ value: String?) {
        if let value = value {
            UserDefaults.standard.set(value, forKey: applyPrefix(to: lastBundleVersionKey))
        } else {
            UserDefaults.standard.removeObject(forKey: applyPrefix(to: lastBundleVersionKey))
        }
        UserDefaults.standard.synchronize()
    }

    private func applyPrefix(to key: String) -> String {
        return LiveUpdatePlugin.userDefaultsPrefix + "." + key
    }
}
