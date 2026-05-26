import Capacitor
import Foundation

@objc(GrafanaFaroPlugin)
public class GrafanaFaroPlugin: CAPPlugin, CAPBridgedPlugin {
    public static let tag = "GrafanaFaro"

    public static let sdkName = "@capawesome/capacitor-grafana-faro"

    public static let levelInfo = "info"

    public static let domainIOS = "ios"

    public let identifier = "GrafanaFaroPlugin"
    public let jsName = "GrafanaFaro"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getSession", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getView", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "pause", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "pushError", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "pushEvent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "pushLog", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "pushMeasurement", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "resetSession", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "resetUser", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setSession", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setUser", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setView", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "unpause", returnType: CAPPluginReturnPromise)
    ]

    private var implementation: GrafanaFaro?

    override public func load() {
        let implementation = GrafanaFaro()
        self.implementation = implementation
        do {
            try implementation.initializeFromConfig(grafanaFaroConfig())
        } catch let error {
            CAPLog.print("[", GrafanaFaroPlugin.tag, "] ", error)
        }
    }

    private func grafanaFaroConfig() -> GrafanaFaroConfig {
        var config = GrafanaFaroConfig()
        config.apiKey = getConfig().getString("apiKey", config.apiKey)
        config.appEnvironment = getConfig().getString("appEnvironment", config.appEnvironment)
        config.appName = getConfig().getString("appName", config.appName)
        config.appNamespace = getConfig().getString("appNamespace", config.appNamespace)
        config.appVersion = getConfig().getString("appVersion", config.appVersion)
        config.url = getConfig().getString("url", config.url)
        if let instrumentations = getConfig().getObject("instrumentations") {
            config.anrTracking = (instrumentations["anrTracking"] as? Bool) ?? config.anrTracking
            config.nativeCrashReporting = (instrumentations["nativeCrashReporting"] as? Bool) ?? config.nativeCrashReporting
        }
        return config
    }

    @objc func getSession(_ call: CAPPluginCall) {
        guard let result = implementation?.getSession() else {
            call.resolve([:])
            return
        }
        call.resolve(result)
    }

    @objc func getView(_ call: CAPPluginCall) {
        guard let result = implementation?.getView() else {
            call.resolve([:])
            return
        }
        call.resolve(result)
    }

    @objc func initialize(_ call: CAPPluginCall) {
        do {
            let options = try InitializeOptions(call: call)
            try implementation?.initialize(options)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func pause(_ call: CAPPluginCall) {
        do {
            try implementation?.pause()
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func pushError(_ call: CAPPluginCall) {
        do {
            let options = try PushErrorOptions(call: call)
            try implementation?.pushError(options)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func pushEvent(_ call: CAPPluginCall) {
        do {
            let options = try PushEventOptions(call: call)
            try implementation?.pushEvent(options)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func pushLog(_ call: CAPPluginCall) {
        do {
            let options = try PushLogOptions(call: call)
            try implementation?.pushLog(options)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func pushMeasurement(_ call: CAPPluginCall) {
        do {
            let options = try PushMeasurementOptions(call: call)
            try implementation?.pushMeasurement(options)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func resetSession(_ call: CAPPluginCall) {
        do {
            try implementation?.resetSession()
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func resetUser(_ call: CAPPluginCall) {
        do {
            try implementation?.resetUser()
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func setSession(_ call: CAPPluginCall) {
        do {
            let options = SetSessionOptions(call: call)
            try implementation?.setSession(options)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func setUser(_ call: CAPPluginCall) {
        do {
            let user = UserMetadata(call: call)
            try implementation?.setUser(user)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func setView(_ call: CAPPluginCall) {
        do {
            let view = try ViewMetadata(call: call)
            try implementation?.setView(view)
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func unpause(_ call: CAPPluginCall) {
        do {
            try implementation?.unpause()
            call.resolve()
        } catch let error {
            rejectCall(call, error)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", GrafanaFaroPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }
}
