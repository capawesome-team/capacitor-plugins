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
            let iconInt = shortcut["icon"] as? Int
            let iconString = shortcut["icon"] as? String

            var icon: UIApplicationShortcutIcon?

            if let iconString = iconString {
                icon = UIImage(systemName: iconString) != nil
                    ? UIApplicationShortcutIcon(systemImageName: iconString)
                    : UIApplicationShortcutIcon(templateImageName: iconString)
            } else if let iconInt = iconInt, let iconType = UIApplicationShortcutIcon.IconType(rawValue: iconInt) {
                icon = UIApplicationShortcutIcon(type: iconType)
            }

            return UIApplicationShortcutItem(
                type: type,
                localizedTitle: title,
                localizedSubtitle: description,
                icon: icon
            )
        }
    }
}
