import Foundation
import UIKit

@objc public class AppIcon: NSObject {
    let plugin: AppIconPlugin

    init(plugin: AppIconPlugin) {
        self.plugin = plugin
    }

    @objc public func getCurrentIcon() -> GetCurrentIconResult {
        let icon = UIApplication.shared.alternateIconName
        return GetCurrentIconResult(icon: icon)
    }

    @objc public func isAvailable() -> IsAvailableResult {
        let available = UIApplication.shared.supportsAlternateIcons
        return IsAvailableResult(available: available)
    }

    @objc public func resetIcon(completion: @escaping (Error?) -> Void) {
        setAlternateIconName(nil, failure: CustomError.changeFailed, completion: completion)
    }

    @objc public func setIcon(_ options: SetIconOptions, completion: @escaping (Error?) -> Void) {
        setAlternateIconName(options.icon, failure: CustomError.iconNotFound, completion: completion)
    }

    private func setAlternateIconName(_ name: String?, failure: CustomError, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            UIApplication.shared.setAlternateIconName(name) { error in
                if error != nil {
                    completion(failure)
                    return
                }
                completion(nil)
            }
        }
    }
}
