import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AppShortcutsPlugin)
public class AppShortcutsPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "AppShortcutsPlugin"
    public let jsName = "AppShortcuts"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "get", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "set", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "clear", returnType: CAPPluginReturnPromise)
    ]

    public static let notificationName = "handleAppShortcutNotification"
    public static let userInfoShortcutItemKey = "shortcutItem"

    public let eventClick = "click"
    public let tag = "AppShortcuts"

    private let implementation = AppShortcuts()

    override public init() {
        super.init()
        log("Initializing plugin with identifier:", identifier)
        NotificationCenter.default.addObserver(self, selector: #selector(self.handleAppShortcutNotification), name: NSNotification.Name(AppShortcutsPlugin.notificationName), object: nil)
    }

    deinit {
        log("Deinitializing plugin")
        NotificationCenter.default.removeObserver(self)
    }

    @objc func get(_ call: CAPPluginCall) {
        log("Executing 'get' method")
        implementation.get(completion: { result in
            if let result = result.toJSObject() as? JSObject {
                log("Successfully retrieved result:", result)
                self.resolveCall(call, result)
            } else {
                log("Failed to retrieve result.")
            }
        })
    }

    @objc func set(_ call: CAPPluginCall) {
        log("Executing 'set' method with options:", call.options ?? "No options provided")
        do {
            let options = try SetOptions(call: call)
            implementation.set(shortcuts: options.getShortcuts, completion: { error in
                if let error = error {
                    log("Error setting shortcuts:", error.localizedDescription)
                    self.rejectCall(call, error)
                } else {
                    log("Shortcuts set successfully")
                    self.resolveCall(call, nil)
                }
            })
        } catch let error {
            log("Error in 'set' method:", error.localizedDescription)
            rejectCall(call, error)
        }
    }

    @objc func clear(_ call: CAPPluginCall) {
        log("Executing 'clear' method")
        implementation.clear(completion: { error in
            if let error = error {
                log("Error clearing shortcuts:", error.localizedDescription)
                self.rejectCall(call, error)
            } else {
                log("Successfully cleared shortcuts")
                self.resolveCall(call, nil)
            }
        })
    }

    @objc private func handleAppShortcutNotification(notification: Notification) {
        log("Handling app shortcut notification")
        guard let userInfo = notification.userInfo, let shortcutItem = userInfo[AppShortcutsPlugin.userInfoShortcutItemKey] as? UIApplicationShortcutItem else {
            log("No shortcut item found in notification")
            return
        }
        let event = ClickEvent(shortcutItem)
        notifyDeviceScannedListeners(event: event)
    }

    func notifyDeviceScannedListeners(event: ClickEvent) {
        log("Notifying listeners of scanned device with event:", event)
        if let event = event.toJSObject() as? JSObject {
            notifyListeners(eventClick, data: event, retainUntilConsumed: true)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        log("[\(self.tag)] Error:", error.localizedDescription)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: JSObject?) {
        if let result {
            log("[\(self.tag)] Resolving call with result:", result)
            call.resolve(result)
        } else {
            log("[\(self.tag)] Resolving call with no result")
            call.resolve()
        }
    }
}
