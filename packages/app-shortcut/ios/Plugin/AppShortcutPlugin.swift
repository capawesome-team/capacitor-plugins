import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AppShortcutPlugin)
public class AppShortcutPlugin: CAPPlugin, UIApplicationDelegate {
    private let implementation = AppShortcut()

    @objc func get(_ call: CAPPluginCall) {
        let result = implementation.get()
        call.resolve(result.toJSObject() as! JSObject)
    }

    @objc func set(_ call: CAPPluginCall) {
        do {
            let options = try SetOptions(call: call)
            implementation.set(options.getShortcuts)
            call.resolve()
        } catch let error {
            call.reject(error.localizedDescription)
        }
    }

    @objc func clear(_ call: CAPPluginCall) {
        implementation.clear()
        call.resolve()
    }

    public func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        if let shortcutItem = launchOptions?[.shortcutItem] as? UIApplicationShortcutItem {
            self.onAppShortcut(shortcutItem)
            return false
        }
        return true
    }

    public func application(_ application: UIApplication, performActionFor shortcutItem: UIApplicationShortcutItem, completionHandler: @escaping (Bool) -> Void) {
        self.onAppShortcut(shortcutItem)
        completionHandler(true)
    }

    public func scene(_ scene: UIScene, performActionFor shortcutItem: UIApplicationShortcutItem, completionHandler: @escaping (Bool) -> Void) {
        self.onAppShortcut(shortcutItem)
        completionHandler(true)
    }

    private func onAppShortcut(_ shortcutItem: UIApplicationShortcutItem) {
        notifyListeners("onAppShortcut", data: ["id": shortcutItem.type])
    }
}
