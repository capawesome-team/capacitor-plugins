#if CAPAWESOME_INCLUDE_IONIC_PROVIDER
import Foundation
import LiveUpdateProvider

/// Ionic Live Update Provider implementation backed by the Capawesome live update plugin.
///
/// Registered with `LiveUpdateProviderRegistry.shared` when the Capacitor plugin loads, so
/// Federated Capacitor can resolve provider id `"capawesome"` and create managers from the
/// per-shell configuration.
public struct LiveUpdateIonicProvider: LiveUpdateProviding {
    public static let id = "capawesome"

    public let id: String = LiveUpdateIonicProvider.id

    private let liveUpdate: LiveUpdate

    public init(liveUpdate: LiveUpdate) {
        self.liveUpdate = liveUpdate
    }

    public func createManager(config: [String: Any]) throws -> any LiveUpdateManaging {
        return try LiveUpdateIonicManager(config: config, liveUpdate: liveUpdate)
    }
}
#endif
