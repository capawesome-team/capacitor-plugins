import Foundation
import Capacitor
import LiveUpdateProvider

/// Capacitor plugin for the example app that exposes the Ionic Live Update
/// Provider SDK to JavaScript so the integration can be tested end-to-end
/// without needing a real Federated Capacitor or Portals host.
@objc(IonicProviderTestPlugin)
public class IonicProviderTestPlugin: CAPPlugin, CAPBridgedPlugin {
    public static let errorManagerKeyMissing = "managerKey must be provided."
    public static let errorProviderNotAvailable = "The LiveUpdate plugin does not conform to LiveUpdateProvider."

    public let identifier = "IonicProviderTestPlugin"
    public let jsName = "IonicProviderTest"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "getLatestAppDirectory", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isProviderAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "syncManager", returnType: CAPPluginReturnPromise)
    ]

    @objc func getLatestAppDirectory(_ call: CAPPluginCall) {
        do {
            let manager = try createManager(from: call)
            call.resolve(["latestAppDirectory": manager.latestAppDirectory?.path ?? NSNull()])
        } catch let error as PluginCallError {
            call.reject(error.message)
        } catch {
            call.reject(error.localizedDescription)
        }
    }

    @objc func isProviderAvailable(_ call: CAPPluginCall) {
        call.resolve(["available": resolveProvider() != nil])
    }

    @objc func syncManager(_ call: CAPPluginCall) {
        let manager: any ProviderManager
        do {
            manager = try createManager(from: call)
        } catch let error as PluginCallError {
            call.reject(error.message)
            return
        } catch {
            call.reject(error.localizedDescription)
            return
        }

        Task {
            do {
                let result = try await manager.sync()
                var ret: [String: Any] = [:]
                ret["latestAppDirectory"] = manager.latestAppDirectory?.path ?? NSNull()
                if let metadataResult = result as? MetadataSyncResult {
                    ret["metadata"] = metadataResult.metadata
                }
                call.resolve(ret as PluginCallResultData)
            } catch {
                call.reject(error.localizedDescription)
            }
        }
    }

    private func createManager(from call: CAPPluginCall) throws -> any ProviderManager {
        guard let managerKey = call.getString("managerKey"), !managerKey.isEmpty else {
            throw PluginCallError(IonicProviderTestPlugin.errorManagerKeyMissing)
        }
        guard let provider = resolveProvider() else {
            throw PluginCallError(IonicProviderTestPlugin.errorProviderNotAvailable)
        }
        var configuration: [String: Any] = ["managerKey": managerKey]
        if let appId = call.getString("appId") {
            configuration["appId"] = appId
        }
        if let channel = call.getString("channel") {
            configuration["channel"] = channel
        }
        return try provider.createManager(configuration: configuration)
    }

    /// Resolves the provider the same way Federated Capacitor does: look up the
    /// Capacitor plugin by name and check that it conforms to `LiveUpdateProvider`.
    private func resolveProvider() -> (any LiveUpdateProvider)? {
        return bridge?.plugin(withName: "LiveUpdate") as? LiveUpdateProvider
    }
}

/// Internal error type so we can carry a user-facing message back to the
/// `CAPPluginCall.reject` callsite.
private struct PluginCallError: Error {
    let message: String

    init(_ message: String) {
        self.message = message
    }
}
