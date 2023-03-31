import Foundation
import Capacitor

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
            set(count: 0, completion: {})
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

    @objc public func set(count: Int, completion: @escaping () -> Void) {
        DispatchQueue.main.async { [weak self] in
            guard let strongSelf = self else {
                return
            }
            UIApplication.shared.applicationIconBadgeNumber = count
            strongSelf.defaults.set(count, forKey: strongSelf.storageKey)
            completion()
        }
    }

    @objc public func increase(completion: @escaping () -> Void) {
        let count = get()
        set(count: count + 1, completion: completion)
    }

    @objc public func decrease(completion: @escaping () -> Void) {
        let count = get()
        if count < 1 {
            completion()
            return
        }
        set(count: count - 1, completion: completion)
    }

    @objc public func clear(completion: @escaping () -> Void) {
        set(count: 0, completion: completion)
    }

    @objc public func isSupported() -> Bool {
        return true
    }

    @objc private func restore() {
        let count = get()
        if count > 0 {
            set(count: count, completion: {})
        }
    }
}
