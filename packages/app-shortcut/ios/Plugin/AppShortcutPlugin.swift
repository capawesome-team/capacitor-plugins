import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AppShortcutPlugin)
public class AppShortcutPlugin: CAPPlugin {
    public let tag = "AppShortcuts"
    public static let onAppShortcutEvent = "onAppShortcut"
    public static let userInfoShortcutItemKey = "shortcutItem"
    private let implementation = AppShortcut()

    override public init() {
        super.init()
        NotificationCenter.default.addObserver(self, selector: #selector(self.handleAppShortcutNotification), name: NSNotification.Name(AppShortcutPlugin.onAppShortcutEvent), object: nil)
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    @objc func get(_ call: CAPPluginCall) {
        implementation.get(completion: { result in
            call.resolve(result.toJSObject() as! JSObject)
        })
    }

    @objc func set(_ call: CAPPluginCall) {
        do {
            let options = try SetOptions(call: call)
            implementation.set(shortcuts: options.getShortcuts, completion: { error in
                if let error = error {
                    CAPLog.print("[", self.tag, "] ", error)
                    call.reject(error.localizedDescription)
                    return
                }
                call.resolve()
            })
        } catch let error {
            CAPLog.print("[", self.tag, "] ", error)
            call.reject(error.localizedDescription)
        }
    }

    @objc func clear(_ call: CAPPluginCall) {
        implementation.clear(completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc private func handleAppShortcutNotification(notification: Notification) {
        guard let userInfo = notification.userInfo, let shortcutItem = userInfo[AppShortcutPlugin.userInfoShortcutItemKey] as? UIApplicationShortcutItem else {
            return
        }
        notifyonAppShortcutListeners(shortcutItem)
    }

    private func notifyonAppShortcutListeners(_ shortcutItem: UIApplicationShortcutItem) {
        self.notifyListeners(AppShortcutPlugin.onAppShortcutEvent, data: [
            "id": shortcutItem.type
        ])
    }
}
