import Foundation
import UIKit

@objc public class Battery: NSObject {
    private let plugin: BatteryPlugin

    private var isObserving = false

    init(plugin: BatteryPlugin) {
        self.plugin = plugin
        super.init()
        DispatchQueue.main.async {
            UIDevice.current.isBatteryMonitoringEnabled = true
        }
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    @objc public func getBatteryLevel(completion: @escaping (GetBatteryLevelResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            UIDevice.current.isBatteryMonitoringEnabled = true
            let level = UIDevice.current.batteryLevel
            if level < 0 {
                completion(nil, CustomError.batteryLevelUnavailable)
                return
            }
            completion(GetBatteryLevelResult(level: level), nil)
        }
    }

    @objc public func getBatteryState(completion: @escaping (GetBatteryStateResult?, Error?) -> Void) {
        DispatchQueue.main.async {
            UIDevice.current.isBatteryMonitoringEnabled = true
            let state = self.mapBatteryState(UIDevice.current.batteryState)
            completion(GetBatteryStateResult(state: state), nil)
        }
    }

    @objc public func isLowPowerModeEnabled(completion: @escaping (IsLowPowerModeEnabledResult?, Error?) -> Void) {
        let enabled = ProcessInfo.processInfo.isLowPowerModeEnabled
        completion(IsLowPowerModeEnabledResult(enabled: enabled), nil)
    }

    func startObserving() {
        DispatchQueue.main.async { [weak self] in
            guard let self = self, !self.isObserving else {
                return
            }
            self.isObserving = true
            UIDevice.current.isBatteryMonitoringEnabled = true
            NotificationCenter.default.addObserver(
                self,
                selector: #selector(self.handleBatteryLevelDidChange),
                name: UIDevice.batteryLevelDidChangeNotification,
                object: nil
            )
            NotificationCenter.default.addObserver(
                self,
                selector: #selector(self.handleBatteryStateDidChange),
                name: UIDevice.batteryStateDidChangeNotification,
                object: nil
            )
            NotificationCenter.default.addObserver(
                self,
                selector: #selector(self.handlePowerStateDidChange),
                name: Notification.Name.NSProcessInfoPowerStateDidChange,
                object: nil
            )
        }
    }

    func stopObserving() {
        DispatchQueue.main.async { [weak self] in
            guard let self = self, self.isObserving else {
                return
            }
            self.isObserving = false
            NotificationCenter.default.removeObserver(self, name: UIDevice.batteryLevelDidChangeNotification, object: nil)
            NotificationCenter.default.removeObserver(self, name: UIDevice.batteryStateDidChangeNotification, object: nil)
            NotificationCenter.default.removeObserver(self, name: Notification.Name.NSProcessInfoPowerStateDidChange, object: nil)
        }
    }

    @objc private func handleBatteryLevelDidChange() {
        let level = UIDevice.current.batteryLevel
        guard level >= 0 else {
            return
        }
        plugin.notifyBatteryLevelChangeListeners(BatteryLevelChangeEvent(level: level))
    }

    @objc private func handleBatteryStateDidChange() {
        let state = mapBatteryState(UIDevice.current.batteryState)
        plugin.notifyBatteryStateChangeListeners(BatteryStateChangeEvent(state: state))
    }

    @objc private func handlePowerStateDidChange() {
        let enabled = ProcessInfo.processInfo.isLowPowerModeEnabled
        plugin.notifyLowPowerModeChangeListeners(LowPowerModeChangeEvent(enabled: enabled))
    }

    private func mapBatteryState(_ state: UIDevice.BatteryState) -> String {
        switch state {
        case .charging:
            return "charging"
        case .full:
            return "full"
        case .unplugged:
            return "unplugged"
        default:
            return "unknown"
        }
    }
}
