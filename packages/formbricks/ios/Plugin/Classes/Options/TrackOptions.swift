import Foundation
import Capacitor

@objc public class TrackOptions: NSObject {
    private let action: String

    init(call: CAPPluginCall) throws {
        guard let action = call.getString("action") else {
            throw CustomError.actionMissing
        }
        self.action = action
    }

    func getAction() -> String {
        return action
    }
}
