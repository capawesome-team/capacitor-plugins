import Foundation
import UIKit

@objc public class ProximitySensor: NSObject {
    private var isRunning = false
    private let plugin: ProximitySensorPlugin

    init(plugin: ProximitySensorPlugin) {
        self.plugin = plugin
    }

    @objc public func getMeasurement(completion: @escaping (Measurement?) -> Void) {
        DispatchQueue.main.async {
            let device = UIDevice.current
            let wasEnabled = device.isProximityMonitoringEnabled
            if !wasEnabled {
                device.isProximityMonitoringEnabled = true
            }
            let near = device.proximityState
            if !wasEnabled {
                device.isProximityMonitoringEnabled = false
            }
            completion(Measurement(near: near))
        }
    }

    @objc public func isAvailable() -> Bool {
        if Thread.isMainThread {
            return checkAvailability()
        }
        return DispatchQueue.main.sync {
            checkAvailability()
        }
    }

    @objc public func startMeasurementUpdates(completion: @escaping () -> Void) {
        DispatchQueue.main.async {
            guard !self.isRunning else {
                completion()
                return
            }
            UIDevice.current.isProximityMonitoringEnabled = true
            NotificationCenter.default.addObserver(
                self,
                selector: #selector(self.handleProximityStateDidChange),
                name: UIDevice.proximityStateDidChangeNotification,
                object: nil
            )
            self.isRunning = true
            completion()
        }
    }

    @objc public func stopMeasurementUpdates(completion: (() -> Void)? = nil) {
        DispatchQueue.main.async {
            NotificationCenter.default.removeObserver(
                self,
                name: UIDevice.proximityStateDidChangeNotification,
                object: nil
            )
            UIDevice.current.isProximityMonitoringEnabled = false
            self.isRunning = false
            completion?()
        }
    }

    private func checkAvailability() -> Bool {
        let device = UIDevice.current
        let wasEnabled = device.isProximityMonitoringEnabled
        if !wasEnabled {
            device.isProximityMonitoringEnabled = true
        }
        let available = device.isProximityMonitoringEnabled
        if !wasEnabled {
            device.isProximityMonitoringEnabled = false
        }
        return available
    }

    @objc private func handleProximityStateDidChange() {
        let measurement = Measurement(near: UIDevice.current.proximityState)
        plugin.notifyMeasurementEventListeners(measurement)
    }
}
