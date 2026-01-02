import Foundation

public class LiveUpdatePreferences: NSObject {
    private static let userDefaultsPrefix = "CapawesomeLiveUpdate" // DO NOT CHANGE

    private let appIdKey = "appId" // DO NOT CHANGE
    private let blockedBundleIdsKey = "blockedBundleIds" // DO NOT CHANGE
    private let channelKey = "channel" // DO NOT CHANGE
    private let customIdKey = "customId" // DO NOT CHANGE
    private let lastVersionCodeKey = "lastVersionCode" // DO NOT CHANGE
    private let lastVersionNameKey = "lastVersionName" // DO NOT CHANGE
    private let previousBundleIdKey = "previousBundleIdKey" // DO NOT CHANGE

    public func getAppId() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: appIdKey))
    }

    public func getBlockedBundleIds() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: blockedBundleIdsKey))
    }

    public func getChannel() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: channelKey))
    }

    public func getCustomId() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: customIdKey))
    }

    public func getLastVersionCode() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: lastVersionCodeKey))
    }

    public func getLastVersionName() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: lastVersionNameKey))
    }

    public func getPreviousBundleId() -> String? {
        return UserDefaults.standard.string(forKey: applyPrefix(to: previousBundleIdKey))
    }

    public func setAppId(_ value: String?) {
        if let value = value {
            UserDefaults.standard.set(value, forKey: applyPrefix(to: appIdKey))
        } else {
            UserDefaults.standard.removeObject(forKey: applyPrefix(to: appIdKey))
        }
        UserDefaults.standard.synchronize()
    }

    public func setBlockedBundleIds(_ value: String?) {
        if let value = value {
            UserDefaults.standard.set(value, forKey: applyPrefix(to: blockedBundleIdsKey))
        } else {
            UserDefaults.standard.removeObject(forKey: applyPrefix(to: blockedBundleIdsKey))
        }
        UserDefaults.standard.synchronize()
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

    public func setLastVersionCode(_ value: String?) {
        if let value = value {
            UserDefaults.standard.set(value, forKey: applyPrefix(to: lastVersionCodeKey))
        } else {
            UserDefaults.standard.removeObject(forKey: applyPrefix(to: lastVersionCodeKey))
        }
        UserDefaults.standard.synchronize()
    }

    public func setLastVersionName(_ value: String?) {
        if let value = value {
            UserDefaults.standard.set(value, forKey: applyPrefix(to: lastVersionNameKey))
        } else {
            UserDefaults.standard.removeObject(forKey: applyPrefix(to: lastVersionNameKey))
        }
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
        return LiveUpdatePreferences.userDefaultsPrefix + "." + key
    }
}
