import Foundation
import UIKit

@objc public class AppLanguage: NSObject {
    private let plugin: AppLanguagePlugin

    init(plugin: AppLanguagePlugin) {
        self.plugin = plugin
    }

    @objc public func getLanguage(completion: @escaping (GetLanguageResult?, Error?) -> Void) {
        let languageTag = resolveLanguageOverride()
        completion(GetLanguageResult(languageTag: languageTag), nil)
    }

    @objc public func openSettings(completion: @escaping (Error?) -> Void) {
        guard let url = URL(string: UIApplication.openSettingsURLString) else {
            completion(nil)
            return
        }
        DispatchQueue.main.async {
            UIApplication.shared.open(url, options: [:]) { _ in
                completion(nil)
            }
        }
    }

    private func resolveLanguageOverride() -> String? {
        guard let bundleIdentifier = Bundle.main.bundleIdentifier,
              let domain = UserDefaults.standard.persistentDomain(forName: bundleIdentifier),
              let languages = domain["AppleLanguages"] as? [String] else {
            return nil
        }
        return languages.first
    }
}
