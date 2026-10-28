import Foundation
import Capacitor

@objc(TabBarPlugin)
public class TabBarPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "TabBarPlugin"
    public let jsName = "TabBar"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "hide", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "selectTabById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setColors", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setTabs", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "show", returnType: CAPPluginReturnPromise)
    ]
    public static let eventTabSelected = "tabSelected"
    public static let tag = "TabBarPlugin"
    private var implementation: TabBar?

    override public func load() {
        if #available(iOS 18.0, *) {
            self.implementation = TabBar(plugin: self, config: getTabBarConfig())
        }
    }

    @objc func hide(_ call: CAPPluginCall) {
        guard let implementation = implementation else {
            return rejectCallAsUnavailable(call)
        }
        implementation.hide(completion: { error in
            self.handleCompletion(call, error)
        })
    }

    @objc public func notifyTabSelectedListeners(_ event: TabSelectedEvent) {
        notifyListeners(TabBarPlugin.eventTabSelected, data: event.toJSObject() as? [String: Any])
    }

    @objc func selectTabById(_ call: CAPPluginCall) {
        guard let implementation = implementation else {
            return rejectCallAsUnavailable(call)
        }
        do {
            let options = try SelectTabByIdOptions(call)
            implementation.selectTabById(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setColors(_ call: CAPPluginCall) {
        guard let implementation = implementation else {
            return rejectCallAsUnavailable(call)
        }
        do {
            let options = try SetColorsOptions(call)
            implementation.setColors(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setTabs(_ call: CAPPluginCall) {
        guard let implementation = implementation else {
            return rejectCallAsUnavailable(call)
        }
        do {
            let options = try SetTabsOptions(call)
            implementation.setTabs(options, completion: { error in
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func show(_ call: CAPPluginCall) {
        guard let implementation = implementation else {
            return rejectCallAsUnavailable(call)
        }
        implementation.show(completion: { error in
            self.handleCompletion(call, error)
        })
    }

    private func getTabBarConfig() -> TabBarConfig? {
        do {
            return try TabBarConfig(getConfig())
        } catch {
            CAPLog.print("[", TabBarPlugin.tag, "] ", error)
            return nil
        }
    }

    private func handleCompletion(_ call: CAPPluginCall, _ error: Error?) {
        if let error = error {
            rejectCall(call, error)
        } else {
            resolveCall(call)
        }
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is only available on iOS 18 and later.")
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", TabBarPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }
}
