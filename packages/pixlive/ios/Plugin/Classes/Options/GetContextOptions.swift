import Capacitor

@objc public class GetContextOptions: NSObject {
    let contextId: String

    init(_ call: CAPPluginCall) throws {
        self.contextId = try GetContextOptions.getContextIdFromCall(call)
    }

    private static func getContextIdFromCall(_ call: CAPPluginCall) throws -> String {
        guard let contextId = call.getString("contextId") else {
            throw CustomError.contextIdMissing
        }
        return contextId
    }
}
