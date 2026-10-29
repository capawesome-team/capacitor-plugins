import Foundation
import Capacitor
import WebViewNativeUIEngine

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
    private static let eventProjectionUpdated = "nativeProjectionUpdated"
    private static let eventProjectionShown = "nativeProjectionShown"
    private static let eventProjectionHidden = "nativeProjectionHidden"
    private var projection: NativeUIProjection?
    private var implementation: TabBar?

    override public func load() {
        self.implementation = TabBar(plugin: self, config: getTabBarConfig())
        guard let selector = getConfig().getString("projectionSelector") else { return }
        do {
            projection = try NativeUIProjection(
                webView: bridge?.webView,
                plugin: jsName,
                projectionSelector: selector,
                receiveListeners: .init(
                    update: [Self.eventTabSelected, Self.eventProjectionUpdated],
                    projection: .init(native: [Self.eventProjectionShown], none: [Self.eventProjectionHidden])
                ),
                // Allow Liquid Glass selection animations to settle before preparing pixels.
                captureDelayMs: 600,
                view: { [weak self] in self?.implementation?.projectionView }
            )
        } catch {
            CAPLog.print("[", Self.tag, "] Native projection unavailable: ", error)
        }
    }

    @objc func hide(_ call: CAPPluginCall) {
        implementation?.hide(completion: { error in
            if error == nil { self.notifyListeners(Self.eventProjectionHidden, data: [:]) }
            self.handleCompletion(call, error)
        })
    }

    @objc public func notifyTabSelectedListeners(_ event: TabSelectedEvent) {
        notifyListeners(TabBarPlugin.eventTabSelected, data: event.toJSObject() as? [String: Any])
    }

    @objc func selectTabById(_ call: CAPPluginCall) {
        do {
            let options = try SelectTabByIdOptions(call)
            implementation?.selectTabById(options, completion: { error in
                if error == nil { self.notifyListeners(Self.eventProjectionUpdated, data: [:]) }
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setColors(_ call: CAPPluginCall) {
        do {
            let options = try SetColorsOptions(call)
            implementation?.setColors(options, completion: { error in
                if error == nil { self.notifyListeners(Self.eventProjectionUpdated, data: [:]) }
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setTabs(_ call: CAPPluginCall) {
        do {
            let options = try SetTabsOptions(call)
            implementation?.setTabs(options, completion: { error in
                if error == nil { self.notifyListeners(Self.eventProjectionUpdated, data: [:]) }
                self.handleCompletion(call, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func show(_ call: CAPPluginCall) {
        implementation?.show(completion: { error in
            if error == nil { self.notifyListeners(Self.eventProjectionShown, data: [:]) }
            self.handleCompletion(call, error)
        })
    }

    override public func notifyListeners(_ eventName: String, data: [String: Any]?, retainUntilConsumed: Bool) {
        projection?.receive(eventName, data: data)
        super.notifyListeners(eventName, data: data, retainUntilConsumed: retainUntilConsumed)
    }

    deinit {
        projection?.destroy()
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

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", TabBarPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }
}
