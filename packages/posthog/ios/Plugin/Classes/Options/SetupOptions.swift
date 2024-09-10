import Foundation

@objc public class SetupOptions: NSObject {
    private var apiKey: String
    private var host: String

    init(apiKey: String, host: String) {
        self.apiKey = apiKey
        self.host = host
    }

    func getApiKey() -> String {
        return apiKey
    }

    func getHost() -> String {
        return host
    }
}
