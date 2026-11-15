/// The body of a check. The encoder leaves out every field without a value.
struct CreateCheckRequest: Encodable {
    struct Failure: Encodable {
        let bundleId: String
        let reason: FailureReason
    }

    let appVersionCode: String
    let appVersionName: String
    let bundleId: String?
    let channelName: String?
    let customId: String?
    let deviceId: String
    let failure: Failure?
    let osVersion: String
    let platform: String
    let pluginVersion: String
    let runtime: String
}
