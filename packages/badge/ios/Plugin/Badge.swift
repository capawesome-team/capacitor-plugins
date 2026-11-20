import Foundation
import Capacitor
import UserNotifications

@objc public class Badge: NSObject {
    private var config: BadgeConfig
    private let storageKey = "capacitor.badge"
    private var defaults: UserDefaults {
        return UserDefaults.standard
    }

    init(config: BadgeConfig) {
        self.config = config
        super.init()
        if config.persist {
            restore()
        }
    }

    @objc public func handleOnResume() {
        if config.autoClear {
            set(count: 0, completion: { _ in })
        }
    }

    @objc public func requestPermissions(completion: @escaping (_ granted: Bool, _ error: Error?) -> Void) {
        UNUserNotificationCenter.current().requestAuthorization(options: .badge) { granted, error in
            completion(granted, error)
        }
    }

    @objc public func checkPermissions(completion: @escaping (_ status: String) -> Void) {
        UNUserNotificationCenter.current().getNotificationSettings { settings in
            let permission: String

            switch settings.authorizationStatus {
            case .authorized, .ephemeral, .provisional:
                permission = "granted"
            case .denied:
                permission = "denied"
            case .notDetermined:
                permission = "prompt"
            @unknown default:
                permission = "prompt"
            }

            completion(permission)
        }
    }

    @objc public func get() -> Int {
        return defaults.integer(forKey: storageKey)
    }

    @objc public func set(count: Int, completion: @escaping (Error?) -> Void) {
        requestPermissionsIfNeeded(count: count, completion: { [weak self] error in
            if let error = error {
                completion(error)
                return
            }
            self?.setBadgeCount(count, completion: completion)
        })
    }

    @objc public func increase(completion: @escaping (Error?) -> Void) {
        set(count: get() + 1, completion: completion)
    }

    @objc public func decrease(completion: @escaping (Error?) -> Void) {
        let count = get()
        if count < 1 {
            completion(nil)
            return
        }
        set(count: count - 1, completion: completion)
    }

    @objc public func clear(completion: @escaping (Error?) -> Void) {
        set(count: 0, completion: completion)
    }

    @objc public func isSupported() -> Bool {
        return true
    }

    // Clearing the badge works without permission, so only prompt when a badge will be shown
    private func requestPermissionsIfNeeded(count: Int, completion: @escaping (Error?) -> Void) {
        guard count > 0 else {
            completion(nil)
            return
        }
        requestPermissions(completion: { _, error in
            completion(error)
        })
    }

    @objc private func restore() {
        let count = get()
        if count > 0 {
            setBadgeCount(count, completion: { _ in })
        }
    }

    private func setBadgeCount(_ count: Int, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async { [weak self] in
            guard let strongSelf = self else {
                return
            }
            if #available(iOS 16.0, *) {
                let center = UNUserNotificationCenter.current()
                center.setBadgeCount(count) { error in
                    if let error = error {
                        completion(error)
                        return
                    }
                    strongSelf.defaults.set(count, forKey: strongSelf.storageKey)
                    completion(nil)
                }
            } else {
                UIApplication.shared.applicationIconBadgeNumber = count
                strongSelf.defaults.set(count, forKey: strongSelf.storageKey)
                completion(nil)
            }
        }
    }
}
