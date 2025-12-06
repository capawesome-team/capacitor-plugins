public struct LiveUpdateConfig {
    var appId: String?
    var autoBlockRolledBackBundles = false
    var autoDeleteBundles = false
    var autoUpdateStrategy = "none"
    var defaultChannel: String?
    var httpTimeout = 60000
    var publicKey: String?
    var readyTimeout = 0
    var serverDomain = "api.cloud.capawesome.io"
}
