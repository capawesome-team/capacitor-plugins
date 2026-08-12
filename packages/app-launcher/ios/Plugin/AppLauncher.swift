import Foundation
import Capacitor
import UIKit

@objc public class AppLauncher: NSObject {
    private let plugin: AppLauncherPlugin

    init(plugin: AppLauncherPlugin) {
        self.plugin = plugin
    }

    @objc public func canOpenUrl(_ options: CanOpenUrlOptions, completion: @escaping (CanOpenUrlResult?, Error?) -> Void) {
        guard let url = URL(string: options.url) else {
            completion(CanOpenUrlResult(value: false), nil)
            return
        }
        let value = UIApplication.shared.canOpenURL(url)
        completion(CanOpenUrlResult(value: value), nil)
    }

    @objc public func openUrl(_ options: OpenUrlOptions, completion: @escaping (OpenUrlResult?, Error?) -> Void) {
        guard let url = URL(string: options.url) else {
            completion(OpenUrlResult(completed: false), nil)
            return
        }
        DispatchQueue.main.async {
            UIApplication.shared.open(url, options: [:]) { success in
                completion(OpenUrlResult(completed: success), nil)
            }
        }
    }
}
