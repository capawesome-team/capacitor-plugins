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
            return UIApplicationShortcutItem(type: type, localizedTitle: title)
        }
    }
}
