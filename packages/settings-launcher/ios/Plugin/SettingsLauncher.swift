import Foundation
import Capacitor
import UIKit

@objc public class SettingsLauncher: NSObject {
    private let plugin: SettingsLauncherPlugin

    init(plugin: SettingsLauncherPlugin) {
        self.plugin = plugin
    }

    @objc public func openAppSettings(completion: @escaping (Error?) -> Void) {
        guard let url = URL(string: UIApplication.openSettingsURLString) else {
            completion(CustomError.openFailed)
            return
        }
        open(url, completion: completion)
    }

    @available(iOS 16.0, *)
    @objc public func openNotificationSettings(completion: @escaping (Error?) -> Void) {
        guard let url = URL(string: UIApplication.openNotificationSettingsURLString) else {
            completion(CustomError.openFailed)
            return
        }
        open(url, completion: completion)
    }

    private func open(_ url: URL, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            UIApplication.shared.open(url, options: [:]) { success in
                completion(success ? nil : CustomError.openFailed)
            }
        }
    }
}
