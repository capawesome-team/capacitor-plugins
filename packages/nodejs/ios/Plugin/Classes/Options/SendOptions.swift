import Foundation
import Capacitor

@objc public class SendOptions: NSObject {
    let args: [Any]
    let eventName: String

    init(_ call: CAPPluginCall) throws {
        self.args = call.getArray("args") ?? []
        self.eventName = try SendOptions.getEventNameFromCall(call)
    }

    private static func getEventNameFromCall(_ call: CAPPluginCall) throws -> String {
        guard let eventName = call.getString("eventName") else {
            throw CustomError.eventNameMissing
        }
        return eventName
    }
}
