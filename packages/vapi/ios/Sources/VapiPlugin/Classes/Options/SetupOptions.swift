import Foundation
import Capacitor

@objc public class SetupOptions: NSObject {
    private var apiKey: String

    init(apiKey: String) {
        self.apiKey = apiKey
    }

    func getApiKey() -> String {
        return apiKey
    }
}
