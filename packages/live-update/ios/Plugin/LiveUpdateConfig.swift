public struct LiveUpdateConfig {
    var appId: String?
    var autoDeleteBundles = false
    var defaultChannel: String?
    var httpTimeout = 60000
    var publicKey: String?
    var readyTimeout = 0
    var serverDomain = "api.cloud.capawesome.io"
}
