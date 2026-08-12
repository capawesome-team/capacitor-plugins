import Foundation
import Capacitor

@objc public class SetActiveOptions: NSObject {
    private let active: Bool
    private let notifyOthersOnDeactivation: Bool

    init(_ call: CAPPluginCall) throws {
        self.active = try SetActiveOptions.getActiveFromCall(call)
        self.notifyOthersOnDeactivation = call.getBool("notifyOthersOnDeactivation") ?? true
    }

    public func getActive() -> Bool {
        return active
    }

    public func getNotifyOthersOnDeactivation() -> Bool {
        return notifyOthersOnDeactivation
    }

    private static func getActiveFromCall(_ call: CAPPluginCall) throws -> Bool {
        guard let active = call.getBool("active") else {
            throw CustomError.activeMissing
        }
        return active
    }
}
