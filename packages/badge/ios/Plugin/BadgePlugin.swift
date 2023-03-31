import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(BadgePlugin)
public class BadgePlugin: CAPPlugin {
    private var implementation: Badge?

    override public func load() {
        NotificationCenter.default.addObserver(self, selector: #selector(onResume), name: UIApplication.willEnterForegroundNotification, object: nil)
        self.implementation = Badge(config: badgeConfig())
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        implementation?.requestPermissions(completion: { granted, error in
            guard error == nil else {
                call.reject(error!.localizedDescription)
                return
            }
            call.resolve(["display": granted ? "granted" : "denied"])
        })
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        implementation?.checkPermissions(completion: { permission in
            call.resolve([
                "display": permission
            ])
        })
    }

    @objc func get(_ call: CAPPluginCall) {
        let count = implementation?.get()
        call.resolve([
            "count": count ?? 0
        ])
    }

    @objc func set(_ call: CAPPluginCall) {
        implementation?.requestPermissions(completion: { [weak self] _, error in
            guard let strongSelf = self else {
                return
            }
            guard error == nil else {
                call.reject(error!.localizedDescription)
                return
            }
            let count = call.getInt("count") ?? 0
            strongSelf.implementation?.set(count: count, completion: {
                call.resolve()
            })
        })
    }

    @objc func increase(_ call: CAPPluginCall) {
        implementation?.requestPermissions(completion: { [weak self] _, error in
            guard let strongSelf = self else {
                return
            }
            guard error == nil else {
                call.reject(error!.localizedDescription)
                return
            }
            strongSelf.implementation?.increase(completion: {
                call.resolve()
            })
        })
    }

    @objc func decrease(_ call: CAPPluginCall) {
        implementation?.requestPermissions(completion: { [weak self] _, error in
            guard let strongSelf = self else {
                return
            }
            guard error == nil else {
                call.reject(error!.localizedDescription)
                return
            }
            strongSelf.implementation?.decrease(completion: {
                call.resolve()
            })
        })
    }

    @objc func clear(_ call: CAPPluginCall) {
        implementation?.requestPermissions(completion: { [weak self] _, error in
            guard let strongSelf = self else {
                return
            }
            guard error == nil else {
                call.reject(error!.localizedDescription)
                return
            }
            strongSelf.implementation?.clear(completion: {
                call.resolve()
            })
        })
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
}
