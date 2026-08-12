import Capacitor
import Foundation

@objc public class AppMetadata: NSObject {
    private let environment: String?
    private let name: String
    private let namespace: String?
    private let version: String?

    init(source: JSObject) throws {
        guard let name = source["name"] as? String, !name.isEmpty else {
            throw CustomError.appNameMissing
        }
        self.environment = source["environment"] as? String
        self.name = name
        self.namespace = source["namespace"] as? String
        self.version = source["version"] as? String
    }

    func getEnvironment() -> String? {
        return environment
    }

    func getName() -> String {
        return name
    }

    func getNamespace() -> String? {
        return namespace
    }

    func getVersion() -> String? {
        return version
    }
}
