import Foundation
import Capacitor

@objc public class PostMessageOptions: NSObject {
    let data: JSObject

    init(_ call: CAPPluginCall) throws {
        self.data = try PostMessageOptions.getDataFromCall(call)
    }

    private static func getDataFromCall(_ call: CAPPluginCall) throws -> JSObject {
        guard let data = call.getObject("data") else {
            throw CustomError.dataMissing
        }
        return data
    }
}
