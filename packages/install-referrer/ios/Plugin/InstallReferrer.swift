import Foundation
import AdServices

@objc public class InstallReferrer: NSObject {
    private let plugin: InstallReferrerPlugin

    init(plugin: InstallReferrerPlugin) {
        self.plugin = plugin
    }

    @objc public func getAttributionToken(completion: @escaping (GetAttributionTokenResult?, Error?) -> Void) {
        do {
            let token = try AAAttribution.attributionToken()
            let result = GetAttributionTokenResult(token: token)
            completion(result, nil)
        } catch {
            completion(nil, CustomError.tokenGenerationFailed)
        }
    }
}
