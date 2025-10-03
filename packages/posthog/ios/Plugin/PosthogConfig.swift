public struct PosthogConfig {
    var apiKey: String?
    var host = "https://us.i.posthog.com"
    var enableSessionReplay = false
    var sessionReplaySampling: Double = 1.0
    var sessionReplayLinkedFlag = false
    var enableErrorTracking = false
}
