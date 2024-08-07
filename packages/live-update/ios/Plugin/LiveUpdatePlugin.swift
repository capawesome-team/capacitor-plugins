import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(LiveUpdatePlugin)
public class LiveUpdatePlugin: CAPPlugin {
    public static let tag = "LiveUpdate"
    public static let version = "6.3.0"
    public static let userDefaultsPrefix = "CapawesomeLiveUpdate" // DO NOT CHANGE

    private var config: LiveUpdateConfig?
    private var implementation: LiveUpdate?

    override public func load() {
        self.config = liveUpdateConfig()
        self.implementation = LiveUpdate(config: config!, plugin: self)
    }

    @objc func deleteBundle(_ call: CAPPluginCall) {
        guard let bundleId = call.getString("bundleId") else {
            call.reject(CustomError.bundleIdMissing.localizedDescription)
            return
        }

        let options = DeleteBundleOptions(bundleId: bundleId)

        implementation?.deleteBundle(options, completion: { error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func downloadBundle(_ call: CAPPluginCall) {
        guard let bundleId = call.getString("bundleId") else {
            call.reject(CustomError.bundleIdMissing.localizedDescription)
            return
        }
        let checksum = call.getString("checksum")
        guard let url = call.getString("url") else {
            call.reject(CustomError.urlMissing.localizedDescription)
            return
        }

        let options = DownloadBundleOptions(bundleId: bundleId, checksum: checksum, url: url)

        implementation?.downloadBundle(options, completion: { error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func getBundle(_ call: CAPPluginCall) {
        implementation?.getBundle(completion: { result, error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func getBundles(_ call: CAPPluginCall) {
        implementation?.getBundles(completion: { result, error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func getChannel(_ call: CAPPluginCall) {
        implementation?.getChannel(completion: { result, error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func getCustomId(_ call: CAPPluginCall) {
        implementation?.getCustomId(completion: { result, error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func getDeviceId(_ call: CAPPluginCall) {
        implementation?.getDeviceId(completion: { result, error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func getVersionCode(_ call: CAPPluginCall) {
        implementation?.getVersionCode(completion: { result, error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func getVersionName(_ call: CAPPluginCall) {
        implementation?.getVersionName(completion: { result, error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func ready(_ call: CAPPluginCall) {
        implementation?.ready()
        call.resolve()
    }

    @objc func reload(_ call: CAPPluginCall) {
        implementation?.reload()
        call.resolve()
    }

    @objc func reset(_ call: CAPPluginCall) {
        implementation?.reset()
        call.resolve()
    }

    @objc func setBundle(_ call: CAPPluginCall) {
        guard let bundleId = call.getString("bundleId") else {
            call.reject(CustomError.bundleIdMissing.localizedDescription)
            return
        }

        let options = SetBundleOptions(bundleId: bundleId)

        implementation?.setBundle(options, completion: { error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
        })
        call.resolve()
    }

    @objc func setChannel(_ call: CAPPluginCall) {
        let channel = call.getString("channel")

        let options = SetChannelOptions(channel: channel)

        implementation?.setChannel(options, completion: { error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
        })
        call.resolve()
    }

    @objc func setCustomId(_ call: CAPPluginCall) {
        guard let customId = call.getString("customId") else {
            call.reject(CustomError.customIdMissing.localizedDescription)
            return
        }

        let options = SetCustomIdOptions(customId: customId)

        implementation?.setCustomId(options, completion: { error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
        })
        call.resolve()
    }

    @objc func sync(_ call: CAPPluginCall) {
        guard let _ = config?.appId else {
            call.reject(CustomError.appIdMissing.localizedDescription)
            return
        }
        implementation?.sync(completion: { result, error in
            if let error = error {
                CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    private func liveUpdateConfig() -> LiveUpdateConfig {
        var config = LiveUpdateConfig()

        config.appId = getConfig().getString("appId", config.appId)
        config.autoDeleteBundles = getConfig().getBoolean("autoDeleteBundles", config.autoDeleteBundles)
        config.defaultChannel = getConfig().getString("defaultChannel", config.defaultChannel)
        config.enabled = getConfig().getBoolean("enabled", config.enabled)
        config.location = getConfig().getString("location", config.location)
        config.publicKey = getConfig().getString("publicKey", config.publicKey)
        config.readyTimeout = getConfig().getInt("readyTimeout", config.readyTimeout)
        config.resetOnUpdate = getConfig().getBoolean("resetOnUpdate", config.resetOnUpdate)

        return config
    }
}
