public struct LiveUpdateConfig {
    var appId: String?
    var autoDeleteBundles = false
    var enabled = true
    var location: String?
    var publicKey: String?
    var readyTimeout = 10000
    var resetOnUpdate = true
}
