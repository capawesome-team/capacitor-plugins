import Foundation
import LiveUpdateProvider

extension LiveUpdatePlugin: LiveUpdateProvider {
    /// Creates a manager for the Ionic Live Update Provider SDK. Invoked natively by
    /// Federated Capacitor after resolving this plugin by its Capacitor plugin name.
    public func createManager(configuration: [String: Any]) throws -> any ProviderManager {
        guard let implementation = implementation else {
            throw ProviderError.invalidConfiguration(message: CustomError.pluginNotInitialized.localizedDescription)
        }
        return try LiveUpdateIonicManager(configuration: configuration, liveUpdate: implementation)
    }
}
