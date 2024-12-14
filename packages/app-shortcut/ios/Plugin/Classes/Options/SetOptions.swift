import Capacitor

@objc public class SetOptions: NSObject {
    private var shortcuts: [UIApplicationShortcutItem] = []

    init(call: CAPPluginCall) throws {
        guard let shortcuts = call.getArray("shortcuts") as? [JSObject] else {
            call.reject(CustomError.shortcutsMissing.localizedDescription)
            return
        }
        self.shortcuts = try SetOptions.getShortcutItemsFromJSArray(shortcuts: shortcuts)
    }

    public var getShortcuts: [UIApplicationShortcutItem] {
        return shortcuts
    }

    private static func getShortcutItemsFromJSArray(shortcuts: [JSObject]) throws -> [UIApplicationShortcutItem] {
        return try shortcuts.map { shortcut in
            guard let type = shortcut["id"] as? String, !type.isEmpty else {
                throw CustomError.idMissing
            }
            guard let title = shortcut["title"] as? String, !title.isEmpty else {
                throw CustomError.titleMissing
            }
            let icon = shortcut["iosIcon"] as? Int
            let subtittle = shortcut["description"] as? String

            if icon == nil {
                return UIApplicationShortcutItem(type: type, localizedTitle: title)
            } else {
                return UIApplicationShortcutItem(
                    type: type,
                    localizedTitle: title,
                    localizedSubtitle: subtittle,
                    icon: UIApplicationShortcutIcon(type: UIApplicationShortcutIcon.IconType(rawValue: icon ?? 0) ?? UIApplicationShortcutIcon.IconType.compose)
                )
            }
        }
    }
}
