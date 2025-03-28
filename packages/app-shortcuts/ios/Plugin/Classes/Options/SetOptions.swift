import Capacitor

@objc public class SetOptions: NSObject {
    private var shortcuts: [UIApplicationShortcutItem] = []

    init(call: CAPPluginCall) throws {
        guard let shortcuts = call.getArray("shortcuts") as? [JSObject] else {
            call.reject(CustomError.shortcutsMissing.localizedDescription)
            return
        }
        self.shortcuts = try AppShortcutsPluginHelper.getShortcutItemsFromJSArray(shortcuts: shortcuts)
    }

    public var getShortcuts: [UIApplicationShortcutItem] {
        return shortcuts
    }
}
