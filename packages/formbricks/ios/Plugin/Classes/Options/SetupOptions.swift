import Foundation
import Capacitor

@objc public class SetupOptions: NSObject {
    private let appUrl: String
    private let environmentId: String

    init(call: CAPPluginCall) throws {
        guard let appUrl = call.getString("appUrl") else {
            throw CustomError.appUrlMissing
        }
        guard let environmentId = call.getString("environmentId") else {
            throw CustomError.environmentIdMissing
        }
        self.appUrl = appUrl
        self.environmentId = environmentId
    }

    func getAppUrl() -> String {
        return appUrl
    }

    func getEnvironmentId() -> String {
        return environmentId
    }
}
