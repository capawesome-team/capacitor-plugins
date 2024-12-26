import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AppShortcutsPlugin)
public class AppShortcutsPlugin: CAPPlugin {
    public static let notificationName = "handleAppShortcutNotification"
    public static let userInfoShortcutItemKey = "shortcutItem"

    public let eventClick = "click"
    public let tag = "AppShortcuts"

    private let implementation = AppShortcuts()

    override public init() {
        super.init()
        NotificationCenter.default.addObserver(self, selector: #selector(self.handleAppShortcutNotification), name: NSNotification.Name(AppShortcutsPlugin.notificationName), object: nil)
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    @objc func get(_ call: CAPPluginCall) {
        implementation.get(completion: { result in
            if let result = result.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func set(_ call: CAPPluginCall) {
        do {
            let options = try SetOptions(call: call)
            implementation.set(shortcuts: options.getShortcuts, completion: { error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, nil)
                }
            })
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func clear(_ call: CAPPluginCall) {
        implementation.clear(completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    @objc private func handleAppShortcutNotification(notification: Notification) {
        guard let userInfo = notification.userInfo, let shortcutItem = userInfo[AppShortcutsPlugin.userInfoShortcutItemKey] as? UIApplicationShortcutItem else {
            return
        }
        let event = ClickEvent(shortcutItem)
        notifyDeviceScannedListeners(event: event)
    }

    func notifyDeviceScannedListeners(event: ClickEvent) {
        if let event = event.toJSObject() as? JSObject {
            notifyListeners(eventClick, data: event, retainUntilConsumed: true)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: JSObject?) {
        if let result {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
