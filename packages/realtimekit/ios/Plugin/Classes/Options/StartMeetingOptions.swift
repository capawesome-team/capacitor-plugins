import Capacitor

@objc public class StartMeetingOptions: NSObject {
    let authToken: String
    let enableAudio: Bool
    let enableVideo: Bool

    init(_ call: CAPPluginCall) throws {
        guard let authToken = call.getString("authToken") else {
            throw CustomError.authTokenMissing
        }
        self.authToken = authToken
        self.enableAudio = call.getBool("enableAudio") ?? true
        self.enableVideo = call.getBool("enableVideo") ?? true
    }
}
