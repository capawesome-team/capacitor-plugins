import Foundation

@objc public class SetupOptions: NSObject {
    private var apiKey: String
    private var host: String
    private var enableSessionReplay: Bool
    private var sessionReplayConfig: SessionReplayOptions?

    init(apiKey: String, host: String, enableSessionReplay: Bool, sessionReplayConfig: [String: Any]?) {
        self.apiKey = apiKey
        self.host = host
        self.enableSessionReplay = enableSessionReplay

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

    func getHost() -> String {
        return host
    }

    func getEnableSessionReplay() -> Bool {
        return enableSessionReplay
    }

    func getSessionReplayConfig() -> SessionReplayOptions? {
        return sessionReplayConfig
    }
}
