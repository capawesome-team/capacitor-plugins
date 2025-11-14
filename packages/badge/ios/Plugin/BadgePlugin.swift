import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(BadgePlugin)
public class BadgePlugin: CAPPlugin, CAPBridgedPlugin {
    public static let tag = "BadgePlugin"

    public let identifier = "BadgePlugin"
    public let jsName = "Badge"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "get", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "set", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "increase", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "decrease", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "clear", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isSupported", returnType: CAPPluginReturnPromise)
    ]
    private var implementation: Badge?

    override public func load() {
        NotificationCenter.default.addObserver(self, selector: #selector(onResume), name: UIApplication.willEnterForegroundNotification, object: nil)
        self.implementation = Badge(config: badgeConfig())
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        do {
            implementation?.requestPermissions(completion: { granted, error in
                if let error = error {
                    self.rejectCall(call, error)
                    return
                }
                call.resolve(["display": granted ? "granted" : "denied"])
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        do {
            implementation?.checkPermissions(completion: { permission in
                call.resolve([
                    "display": permission
                ])
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func get(_ call: CAPPluginCall) {
        do {
            let count = implementation?.get()
            call.resolve([
                "count": count ?? 0
            ])
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func set(_ call: CAPPluginCall) {
        do {
            implementation?.requestPermissions(completion: { [weak self] _, error in
                guard let strongSelf = self else {
                    return
                }
                if let error = error {
                    strongSelf.rejectCall(call, error)
                    return
                }
                let count = call.getInt("count") ?? 0
                strongSelf.implementation?.set(count: count, completion: { error in
                    if let error = error {
                        strongSelf.rejectCall(call, error)
                        return
                    }
                    call.resolve()
                })
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func increase(_ call: CAPPluginCall) {
        do {
            implementation?.requestPermissions(completion: { [weak self] _, error in
                guard let strongSelf = self else {
                    return
                }
                if let error = error {
                    strongSelf.rejectCall(call, error)
                    return
                }
                strongSelf.implementation?.increase(completion: { error in
                    if let error = error {
                        strongSelf.rejectCall(call, error)
                        return
                    }
                    call.resolve()
                })
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func decrease(_ call: CAPPluginCall) {
        do {
            implementation?.requestPermissions(completion: { [weak self] _, error in
                guard let strongSelf = self else {
                    return
                }
                if let error = error {
                    strongSelf.rejectCall(call, error)
                    return
                }
                strongSelf.implementation?.decrease(completion: { error in
                    if let error = error {
                        strongSelf.rejectCall(call, error)
                        return
                    }
                    call.resolve()
                })
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func clear(_ call: CAPPluginCall) {
        do {
            implementation?.requestPermissions(completion: { [weak self] _, error in
                guard let strongSelf = self else {
                    return
                }
                if let error = error {
                    strongSelf.rejectCall(call, error)
                    return
                }
                strongSelf.implementation?.clear(completion: { error in
                    if let error = error {
                        strongSelf.rejectCall(call, error)
                        return
                    }
                    call.resolve()
                })
            })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func isSupported(_ call: CAPPluginCall) {
        let isSupported = implementation?.isSupported()
        call.resolve([
            "isSupported": isSupported ?? false
        ])
    }

    @objc private func onResume() {
        implementation?.handleOnResume()
    }

    private func badgeConfig() -> BadgeConfig {
        var config = BadgeConfig()

        config.persist = getConfig().getBoolean("persist", config.persist)
        config.autoClear = getConfig().getBoolean("autoClear", config.autoClear)

        return config
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", BadgePlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }
}
