import Foundation

@objc public class ThermalState: NSObject {
    private let plugin: ThermalStatePlugin

    private var isObserving = false

    init(plugin: ThermalStatePlugin) {
        self.plugin = plugin
        super.init()
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    @objc public func getThermalState(completion: @escaping (GetThermalStateResult?, Error?) -> Void) {
        let state = mapThermalState(ProcessInfo.processInfo.thermalState)
        completion(GetThermalStateResult(state: state), nil)
    }

    func startObserving() {
        guard !isObserving else {
            return
        }
        isObserving = true
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleThermalStateDidChange),
            name: ProcessInfo.thermalStateDidChangeNotification,
            object: nil
        )
    }

    func stopObserving() {
        guard isObserving else {
            return
        }
        isObserving = false
        NotificationCenter.default.removeObserver(self, name: ProcessInfo.thermalStateDidChangeNotification, object: nil)
    }

    @objc private func handleThermalStateDidChange() {
        let state = mapThermalState(ProcessInfo.processInfo.thermalState)
        plugin.notifyThermalStateChangeListeners(ThermalStateChangeEvent(state: state))
    }

    private func mapThermalState(_ state: ProcessInfo.ThermalState) -> String {
        switch state {
        case .critical:
            return "critical"
        case .fair:
            return "fair"
        case .nominal:
            return "nominal"
        case .serious:
            return "serious"
        @unknown default:
            return "nominal"
        }
    }
}
