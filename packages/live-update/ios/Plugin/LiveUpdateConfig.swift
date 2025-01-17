public struct LiveUpdateConfig {
    var appId: String?
    var autoDeleteBundles = false
    var defaultChannel: String?
    var enabled = true
    var httpTimeout = 60000
    var location: String?
    var publicKey: String?
    var readyTimeout = 10000
    var resetOnUpdate = true
}
