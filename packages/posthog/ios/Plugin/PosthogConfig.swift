import Foundation

public struct PosthogConfig {
    var apiKey: String?
    var apiHost = "https://us.i.posthog.com"
    var enableSessionReplay = false
    var captureApplicationLifecycleEvents = true
    var autoCaptureExceptions = false
    var sessionReplayConfig: SessionReplayOptions?
}
