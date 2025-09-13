import Foundation

@objc public class SetupOptions: NSObject {
    private var apiKey: String
    private var host: String
    private var config: [String: Any]?

    init(apiKey: String, host: String) {
        self.apiKey = apiKey
        self.host = host
        self.config = nil
    }

    init(apiKey: String, host: String, config: [String: Any]?) {
        self.apiKey = apiKey
        self.host = host
        self.config = config
    }

    func getApiKey() -> String {
        return apiKey
    }

    func getHost() -> String {
        return host
    }

    func getConfig() -> [String: Any]? {
        return config
    }
}
