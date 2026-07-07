import Foundation
import Network

@objc public class Network: NSObject {
    private static let connectionTypeCellular = "CELLULAR"
    private static let connectionTypeEthernet = "ETHERNET"
    private static let connectionTypeNone = "NONE"
    private static let connectionTypeUnknown = "UNKNOWN"
    private static let connectionTypeWifi = "WIFI"

    private let plugin: NetworkPlugin
    private let monitorQueue = DispatchQueue(label: "io.capawesome.capacitorjs.plugins.network.monitor")

    private var lastStatusKey: String?
    private var monitor: NWPathMonitor?

    init(plugin: NetworkPlugin) {
        self.plugin = plugin
        super.init()
    }

    @objc public func getStatus(completion: @escaping (GetStatusResult?, Error?) -> Void) {
        let monitor = NWPathMonitor()
        monitor.pathUpdateHandler = { path in
            monitor.cancel()
            completion(self.createStatusResult(path), nil)
        }
        monitor.start(queue: monitorQueue)
    }

    func startObserving() {
        guard monitor == nil else {
            return
        }
        let monitor = NWPathMonitor()
        monitor.pathUpdateHandler = { [weak self] path in
            self?.handlePathUpdate(path)
        }
        self.monitor = monitor
        monitor.start(queue: monitorQueue)
    }

    func stopObserving() {
        monitor?.cancel()
        monitor = nil
        lastStatusKey = nil
    }

    private func createStatusKey(connected: Bool, connectionType: String) -> String {
        return "\(connected)|\(connectionType)"
    }

    private func createStatusResult(_ path: NWPath) -> GetStatusResult {
        let connected = path.status == .satisfied
        let connectionType = mapConnectionType(path)
        return GetStatusResult(connected: connected, connectionType: connectionType, internetReachable: nil)
    }

    private func handlePathUpdate(_ path: NWPath) {
        let connected = path.status == .satisfied
        let connectionType = mapConnectionType(path)
        let statusKey = createStatusKey(connected: connected, connectionType: connectionType)
        if statusKey == lastStatusKey {
            return
        }
        let isInitial = lastStatusKey == nil
        lastStatusKey = statusKey
        if isInitial {
            return
        }
        plugin.notifyNetworkStatusChangeListeners(GetStatusResult(connected: connected, connectionType: connectionType, internetReachable: nil))
    }

    private func mapConnectionType(_ path: NWPath) -> String {
        if path.status != .satisfied {
            return Network.connectionTypeNone
        }
        if path.usesInterfaceType(.wifi) {
            return Network.connectionTypeWifi
        }
        if path.usesInterfaceType(.cellular) {
            return Network.connectionTypeCellular
        }
        if path.usesInterfaceType(.wiredEthernet) {
            return Network.connectionTypeEthernet
        }
        return Network.connectionTypeUnknown
    }
}
