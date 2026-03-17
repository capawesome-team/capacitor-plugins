import Foundation

@objc public class SetupOptions: NSObject {
    private var apiKey: String
    private var apiHost: String
    private var enableSessionReplay: Bool
    private var optOut: Bool
    private var sessionReplayConfig: SessionReplayOptions?

    init(apiKey: String, apiHost: String, enableSessionReplay: Bool, optOut: Bool, sessionReplayConfig: [String: Any]?) {
        self.apiKey = apiKey
        self.apiHost = apiHost
        self.enableSessionReplay = enableSessionReplay
        self.optOut = optOut

        if let config = sessionReplayConfig {
            self.sessionReplayConfig = SessionReplayOptions(
                screenshotMode: config["screenshotMode"] as? Bool ?? false,
                maskAllTextInputs: config["maskAllTextInputs"] as? Bool ?? true,
                maskAllImages: config["maskAllImages"] as? Bool ?? true,
                maskAllSandboxedViews: config["maskAllSandboxedViews"] as? Bool ?? true,
                captureNetworkTelemetry: config["captureNetworkTelemetry"] as? Bool ?? true,
                debouncerDelay: config["debouncerDelay"] as? Double ?? 1.0
            )
        }
    }

    func getApiKey() -> String {
        return apiKey
    }

    func getApiHost() -> String {
        return apiHost
    }

    func getEnableSessionReplay() -> Bool {
        return enableSessionReplay
    }

    func getOptOut() -> Bool {
        return optOut
    }

    func getSessionReplayConfig() -> SessionReplayOptions? {
        return sessionReplayConfig
    }
}
