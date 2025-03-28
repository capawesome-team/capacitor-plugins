import Capacitor

public class AppShortcutsPluginHelper {
    static func getShortcutItemsFromJSArray(shortcuts: [JSObject]) throws -> [UIApplicationShortcutItem] {
        return try shortcuts.map { shortcut in
            guard let type = shortcut["id"] as? String, !type.isEmpty else {
                throw CustomError.idMissing
            }
            guard let title = shortcut["title"] as? String, !title.isEmpty else {
                throw CustomError.titleMissing
            }
            let description = shortcut["description"] as? String
            let iosIconInt = shortcut["iosIcon"] as? Int
            let iosIconString = shortcut["iosIcon"] as? String
            let iconInt = shortcut["icon"] as? Int
            let iconString = shortcut["icon"] as? String

            let icon: UIApplicationShortcutIcon? = {
                if let iosIconString = shortcut["iosIcon"] as? String {
                    return UIImage(systemName: iosIconString) != nil
                        ? UIApplicationShortcutIcon(systemImageName: iosIconString)
                        : UIApplicationShortcutIcon(templateImageName: iosIconString)
                }

                if let iosIconInt = shortcut["iosIcon"] as? Int, let iconType = UIApplicationShortcutIcon.IconType(rawValue: iosIconInt) {
                    return UIApplicationShortcutIcon(type: iconType)
                }

                if let iconString = shortcut["icon"] as? String {
                    return UIImage(systemName: iconString) != nil
                        ? UIApplicationShortcutIcon(systemImageName: iconString)
                        : UIApplicationShortcutIcon(templateImageName: iconString)
                }

                if let iconInt = shortcut["icon"] as? Int, let iconType = UIApplicationShortcutIcon.IconType(rawValue: iconInt) {
                    return UIApplicationShortcutIcon(type: iconType)
                }

                return nil
            }()

            return UIApplicationShortcutItem(
                type: type,
                localizedTitle: title,
                localizedSubtitle: description,
                icon: icon
            )
        }
    }
}
