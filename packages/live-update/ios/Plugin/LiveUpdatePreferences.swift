import Foundation
import Capacitor

public class LiveUpdatePreferences: NSObject {
    private let appIdKey = "appId" // DO NOT CHANGE
    private let blockedBundleIdsKey = "blockedBundleIds" // DO NOT CHANGE
    private let channelKey = "channel" // DO NOT CHANGE
    private let customIdKey = "customId" // DO NOT CHANGE
    private let lastVersionCodeKey = "lastVersionCode" // DO NOT CHANGE
    private let lastVersionNameKey = "lastVersionName" // DO NOT CHANGE
    private let pendingFailureKey = "pendingFailure" // DO NOT CHANGE
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

    /// - Returns: The pending failure, or `nil` if none is stored or the stored value cannot be read.
    func getPendingFailure() -> UpdateFailure? {
        guard let data = UserDefaults.standard.data(forKey: applyPrefix(to: pendingFailureKey)) else {
            return nil
        }
        return try? JSONDecoder().decode(UpdateFailure.self, from: data)
    }

    /// Removes the pending failure only if it is still the given one. A failure that was recorded since then is kept.
    func removeReportedFailure(_ failure: UpdateFailure) {
        guard getPendingFailure() == failure else {
            return
        }
        UserDefaults.standard.removeObject(forKey: applyPrefix(to: pendingFailureKey))
        UserDefaults.standard.synchronize()
    }

    func trySetPendingFailure(_ value: UpdateFailure) {
        let data: Data
        do {
            data = try JSONEncoder().encode(value)
        } catch {
            CAPLog.print("[", LiveUpdatePlugin.tag, "] ", "Failed to record the failed update: ", error.localizedDescription)
            return
        }
        UserDefaults.standard.set(data, forKey: applyPrefix(to: pendingFailureKey))
        UserDefaults.standard.synchronize()
    }

    private func applyPrefix(to key: String) -> String {
        return LiveUpdatePlugin.userDefaultsPrefix + "." + key
    }
}
