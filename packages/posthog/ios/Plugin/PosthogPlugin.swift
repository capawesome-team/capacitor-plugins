import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(PosthogPlugin)
public class PosthogPlugin: CAPPlugin, CAPBridgedPlugin {
    public static let tag = "Posthog"

    public let identifier = "PosthogPlugin" // DO NOT CHANGE THIS
    public let jsName = "Posthog" // DO NOT CHANGE THIS
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "alias", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "capture", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "flush", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getDistinctId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getFeatureFlag", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getFeatureFlagPayload", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "group", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "identify", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isFeatureEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isOptOut", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "optIn", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "optOut", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "register", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "reloadFeatureFlags", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "reset", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "screen", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setup", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startSessionRecording", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopSessionRecording", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "unregister", returnType: CAPPluginReturnPromise)
    ]

    private var implementation: Posthog?

    override public func load() {
        self.implementation = Posthog(config: posthogConfig(), plugin: self)
    }

    @objc func alias(_ call: CAPPluginCall) {
        guard let alias = call.getString("alias") else {
            call.reject(CustomError.aliasMissing.localizedDescription)
            return
        }

        let options = AliasOptions(alias: alias)

        implementation?.alias(options)
        call.resolve()
    }

    @objc func capture(_ call: CAPPluginCall) {
        guard let event = call.getString("event") else {
            call.reject(CustomError.eventMissing.localizedDescription)
            return
        }
        let properties = call.getObject("properties")

        let options = CaptureOptions(event: event, properties: properties)

        implementation?.capture(options)
        call.resolve()
    }

    @objc func flush(_ call: CAPPluginCall) {
        implementation?.flush()
        call.resolve()
    }

    @objc func getDistinctId(_ call: CAPPluginCall) {
        let result = implementation?.getDistinctId()
        if let result = result?.toJSObject() as? JSObject {
            self.resolveCall(call, result)
        }
    }

    @objc func getFeatureFlag(_ call: CAPPluginCall) {
        do {
            let options = try GetFeatureFlagOptions(call: call)
            let result = implementation?.getFeatureFlag(options)
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func getFeatureFlagPayload(_ call: CAPPluginCall) {
        do {
            let options = try GetFeatureFlagPayloadOptions(call: call)
            let result = implementation?.getFeatureFlagPayload(options)
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func group(_ call: CAPPluginCall) {
        guard let type = call.getString("type") else {
            call.reject(CustomError.typeMissing.localizedDescription)
            return
        }
        guard let key = call.getString("key") else {
            call.reject(CustomError.keyMissing.localizedDescription)
            return
        }
        let groupProperties = call.getObject("groupProperties")

        let options = GroupOptions(type: type, key: key, groupProperties: groupProperties)

        implementation?.group(options)
        call.resolve()
    }

    @objc func identify(_ call: CAPPluginCall) {
        guard let distinctId = call.getString("distinctId") else {
            call.reject(CustomError.distinctIdMissing.localizedDescription)
            return
        }
        let userProperties = call.getObject("userProperties")

        let options = IdentifyOptions(distinctId: distinctId, userProperties: userProperties)

        implementation?.identify(options)
        call.resolve()
    }

    @objc func isFeatureEnabled(_ call: CAPPluginCall) {
        do {
            let options = try IsFeatureEnabledOptions(call: call)
            let result = implementation?.isFeatureEnabled(options)
            if let result = result?.toJSObject() as? JSObject {
                self.resolveCall(call, result)
            }
        } catch let error {
            rejectCall(call, error)
        }
    }

    @objc func isOptOut(_ call: CAPPluginCall) {
        let optedOut = implementation?.isOptOut() ?? false
        call.resolve(["optedOut": optedOut])
    }

    @objc func optIn(_ call: CAPPluginCall) {
        implementation?.optIn()
        call.resolve()
    }

    @objc func optOut(_ call: CAPPluginCall) {
        implementation?.optOut()
        call.resolve()
    }

    @objc func register(_ call: CAPPluginCall) {
        guard let key = call.getString("key") else {
            call.reject(CustomError.keyMissing.localizedDescription)
            return
        }
        guard let value = call.options["value"] as AnyObject? else {
            call.reject(CustomError.valueMissing.localizedDescription)
            return
        }

        let options = RegisterOptions(key: key, value: value)

        implementation?.register(options)
        call.resolve()
    }

    @objc func reloadFeatureFlags(_ call: CAPPluginCall) {
        implementation?.reloadFeatureFlags()
        call.resolve()
    }

    @objc func reset(_ call: CAPPluginCall) {
        implementation?.reset()
        call.resolve()
    }

    @objc func screen(_ call: CAPPluginCall) {
        guard let screenTitle = call.getString("screenTitle") else {
            call.reject(CustomError.screenTitleMissing.localizedDescription)
            return
        }
        let properties = call.getObject("properties")

        let options = ScreenOptions(screenTitle: screenTitle, properties: properties)

        implementation?.screen(options)
        call.resolve()
    }

    @objc func setup(_ call: CAPPluginCall) {
        guard let apiKey = call.getString("apiKey") else {
            call.reject(CustomError.apiKeyMissing.localizedDescription)
            return
        }
        let host = call.getString("host", "https://us.i.posthog.com")
        let enableSessionReplay = call.getBool("enableSessionReplay", false)
        let optOut = call.getBool("optOut", false)
        let sessionReplayConfig = call.getObject("sessionReplayConfig")

        let options = SetupOptions(apiKey: apiKey, host: host, enableSessionReplay: enableSessionReplay, optOut: optOut, sessionReplayConfig: sessionReplayConfig)

        implementation?.setup(options)
        call.resolve()
    }

    @objc func startSessionRecording(_ call: CAPPluginCall) {
        implementation?.startSessionRecording()
        call.resolve()
    }

    @objc func stopSessionRecording(_ call: CAPPluginCall) {
        implementation?.stopSessionRecording()
        call.resolve()
    }

    @objc func unregister(_ call: CAPPluginCall) {
        guard let key = call.getString("key") else {
            call.reject(CustomError.keyMissing.localizedDescription)
            return
        }

        let options = UnregisterOptions(key: key)

        implementation?.unregister(options)
        call.resolve()
    }

    private func posthogConfig() -> PosthogConfig {
        var config = PosthogConfig()

        config.apiKey = getConfig().getString("apiKey", config.apiKey)
        config.host = getConfig().getString("host") ?? config.host
        config.enableSessionReplay = getConfig().getBoolean("enableSessionReplay", config.enableSessionReplay)

        if let sessionReplayConfigDict = getConfig().getObject("sessionReplayConfig") as? [String: Any] {
            config.sessionReplayConfig = SessionReplayOptions(
                screenshotMode: sessionReplayConfigDict["screenshotMode"] as? Bool ?? false,
                maskAllTextInputs: sessionReplayConfigDict["maskAllTextInputs"] as? Bool ?? true,
                maskAllImages: sessionReplayConfigDict["maskAllImages"] as? Bool ?? true,
                maskAllSandboxedViews: sessionReplayConfigDict["maskAllSandboxedViews"] as? Bool ?? true,
                captureNetworkTelemetry: sessionReplayConfigDict["captureNetworkTelemetry"] as? Bool ?? true,
                debouncerDelay: sessionReplayConfigDict["debouncerDelay"] as? Double ?? 1.0
            )
        }

        return config
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", PosthogPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: JSObject?) {
        if let result = result {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
