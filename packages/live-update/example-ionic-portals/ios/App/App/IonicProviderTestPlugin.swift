import Foundation
import Capacitor
import LiveUpdateProvider

/// Capacitor plugin for the example app that exposes the Ionic Live Update
/// Provider SDK to JavaScript so the integration can be tested end-to-end
/// without needing a real Federated Capacitor or Portals host.
@objc(IonicProviderTestPlugin)
public class IonicProviderTestPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "IonicProviderTestPlugin"
    public let jsName = "IonicProviderTest"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "isProviderRegistered", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getLatestAppDirectory", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "syncManager", returnType: CAPPluginReturnPromise)
    ]

    private static let providerId = "capawesome"

    @objc func isProviderRegistered(_ call: CAPPluginCall) {
        let provider = LiveUpdateProviderRegistry.shared.resolve(IonicProviderTestPlugin.providerId)
        call.resolve(["registered": provider != nil])
    }

    @objc func getLatestAppDirectory(_ call: CAPPluginCall) {
        do {
            let manager = try createManager(from: call)
            let path = manager.latestAppDirectory?.path
            call.resolve(["latestAppDirectory": path ?? NSNull()])
        } catch let error as PluginCallError {
            call.reject(error.message)
        } catch {
            call.reject(error.localizedDescription)
        }
    }

    @objc func syncManager(_ call: CAPPluginCall) {
        let manager: any LiveUpdateManaging
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
                if let fedCap = result as? FederatedCapacitorSyncResult, let metadata = fedCap.metadata {
                    ret["metadata"] = metadata
                }
                call.resolve(ret as PluginCallResultData)
            } catch {
                call.reject(error.localizedDescription)
            }
        }
    }

    private func createManager(from call: CAPPluginCall) throws -> any LiveUpdateManaging {
        guard let managerKey = call.getString("managerKey"), !managerKey.isEmpty else {
            throw PluginCallError("managerKey must be provided")
        }
        guard let provider = LiveUpdateProviderRegistry.shared.resolve(IonicProviderTestPlugin.providerId) else {
            throw PluginCallError(
                "Provider '\(IonicProviderTestPlugin.providerId)' is not registered. " +
                "Make sure the IonicProvider subspec is used in the Podfile."
            )
        }
        var config: [String: Any] = ["managerKey": managerKey]
        if let appId = call.getString("appId") {
            config["appId"] = appId
        }
        if let channel = call.getString("channel") {
            config["channel"] = channel
        }
        return try provider.createManager(config: config)
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
