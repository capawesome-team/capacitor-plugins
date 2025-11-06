import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(LiveUpdatePlugin)
public class LiveUpdatePlugin: CAPPlugin, CAPBridgedPlugin {
    public static let tag = "LiveUpdate"
    public static let version = "7.2.2"
    public static let userDefaultsPrefix = "CapawesomeLiveUpdate" // DO NOT CHANGE

    public let identifier = "LiveUpdatePlugin"
    public let jsName = "LiveUpdate"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "deleteBundle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "downloadBundle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "fetchLatestBundle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getBundles", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getChannel", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCurrentBundle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCustomId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getDeviceId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getNextBundle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getVersionCode", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getVersionName", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "ready", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "reload", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "reset", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setChannel", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setCustomId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setNextBundle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "sync", returnType: CAPPluginReturnPromise)
    ]

    private let eventDownloadBundleProgess = "downloadBundleProgress"

    private var config: LiveUpdateConfig?
    private var implementation: LiveUpdate?
    private var syncInProgress = false

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
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    @objc func downloadBundle(_ call: CAPPluginCall) {
        Task {
            do {
                let artifactType = call.getString("artifactType", "zip")
                guard let bundleId = call.getString("bundleId") else {
                    call.reject(CustomError.bundleIdMissing.localizedDescription)
                    return
                }
                let checksum = call.getString("checksum")
                let signature = call.getString("signature")
                guard let url = call.getString("url") else {
                    call.reject(CustomError.urlMissing.localizedDescription)
                    return
                }

                let options = DownloadBundleOptions(artifactType: artifactType, bundleId: bundleId, checksum: checksum, signature: signature, url: url)

                try await implementation?.downloadBundle(options)
                self.resolveCall(call)
            } catch {
                rejectCall(call, error)
            }
        }
    }

    @objc func fetchLatestBundle(_ call: CAPPluginCall) {
        Task {
            do {
                guard let appId = config?.appId, !appId.isEmpty else {
                    call.reject(CustomError.appIdMissing.localizedDescription)
                    return
                }

                let options = FetchLatestBundleOptions(call)
                let result = try await implementation?.fetchLatestBundle(options)
                if let result = result?.toJSObject() as? JSObject {
                    self.resolveCall(call, result)
                }
            } catch {
                rejectCall(call, error)
            }
        }
    }

    @objc func getBundles(_ call: CAPPluginCall) {
        implementation?.getBundles(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getChannel(_ call: CAPPluginCall) {
        implementation?.getChannel(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getCurrentBundle(_ call: CAPPluginCall) {
        implementation?.getCurrentBundle(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getCustomId(_ call: CAPPluginCall) {
        implementation?.getCustomId(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getDeviceId(_ call: CAPPluginCall) {
        implementation?.getDeviceId(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getNextBundle(_ call: CAPPluginCall) {
        implementation?.getNextBundle(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getVersionCode(_ call: CAPPluginCall) {
        implementation?.getVersionCode(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func getVersionName(_ call: CAPPluginCall) {
        implementation?.getVersionName(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func ready(_ call: CAPPluginCall) {
        implementation?.ready(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        })
    }

    @objc func reload(_ call: CAPPluginCall) {
        implementation?.reload()
        resolveCall(call)
    }

    @objc func reset(_ call: CAPPluginCall) {
        implementation?.reset()
        resolveCall(call)
    }

    @objc func setChannel(_ call: CAPPluginCall) {
        let channel = call.getString("channel")

        let options = SetChannelOptions(channel: channel)

        implementation?.setChannel(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    @objc func setCustomId(_ call: CAPPluginCall) {
        guard let customId = call.getString("customId") else {
            call.reject(CustomError.customIdMissing.localizedDescription)
            return
        }

        let options = SetCustomIdOptions(customId: customId)

        implementation?.setCustomId(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    @objc func setNextBundle(_ call: CAPPluginCall) {
        let options = SetNextBundleOptions(call)

        implementation?.setNextBundle(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
                return
            }
            self.resolveCall(call)
        })
    }

    @objc func sync(_ call: CAPPluginCall) {
        Task {
            do {
                guard let appId = config?.appId, !appId.isEmpty else {
                    call.reject(CustomError.appIdMissing.localizedDescription)
                    return
                }

                guard !syncInProgress else {
                    call.reject(CustomError.syncInProgress.localizedDescription)
                    return
                }
                syncInProgress = true

                let options = SyncOptions(call)
                let result = try await implementation?.sync(options)
                self.syncInProgress = false
                if let result = result?.toJSObject() as? JSObject {
                    resolveCall(call, result)
                }
            } catch {
                self.syncInProgress = false
                rejectCall(call, error)
            }
        }
    }

    func notifyDownloadBundleProgressListeners(_ event: DownloadBundleProgressEvent) {
        if let event = event.toJSObject() as? JSObject {
            notifyListeners(eventDownloadBundleProgess, data: event, retainUntilConsumed: false)
        }
    }

    private func liveUpdateConfig() -> LiveUpdateConfig {
        var config = LiveUpdateConfig()

        config.appId = getConfig().getString("appId", config.appId)
        config.autoDeleteBundles = getConfig().getBoolean("autoDeleteBundles", config.autoDeleteBundles)
        config.defaultChannel = getConfig().getString("defaultChannel", config.defaultChannel)
        config.httpTimeout = getConfig().getInt("httpTimeout", config.httpTimeout)
        config.publicKey = getConfig().getString("publicKey", config.publicKey)
        config.readyTimeout = getConfig().getInt("readyTimeout", config.readyTimeout)
        config.serverDomain = getConfig().getString("serverDomain", config.serverDomain) ?? config.serverDomain

        return config
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", LiveUpdatePlugin.tag, "] ", error)
        var message = error.localizedDescription
        if let urlError = error as? URLError {
            if urlError.code == .timedOut {
                message = CustomError.httpTimeout.localizedDescription
            }
        }
        call.reject(message)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: JSObject) {
        call.resolve(result)
    }
}
