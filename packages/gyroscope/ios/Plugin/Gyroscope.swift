import Foundation
import CoreMotion

@objc public class Gyroscope: NSObject {
    private var isRunning = false
    private var lastMeasurement: Measurement?
    private lazy var motionManager = CMMotionManager()
    private let plugin: GyroscopePlugin
    private let queue = OperationQueue()
    private var updateInterval: TimeInterval = 0.25

    init(plugin: GyroscopePlugin) {
        self.plugin = plugin
    }

    @objc func getMeasurement(completion: @escaping (Measurement?, Error?) -> Void) {
        guard motionManager.isGyroAvailable else {
            completion(nil, CustomError.notAvailable)
            return
        }

        if isRunning, let lastMeasurement = lastMeasurement {
            completion(lastMeasurement, nil)
            return
        }

        motionManager.gyroUpdateInterval = updateInterval
        motionManager.startGyroUpdates(to: queue) { data, error in
            if let error = error {
                self.motionManager.stopGyroUpdates()
                completion(nil, error)
                return
            }

            guard let data = data else {
                self.motionManager.stopGyroUpdates()
                completion(nil, CustomError.dataNotReceived)
                return
            }

            self.lastMeasurement = Measurement(data)
            self.motionManager.stopGyroUpdates()
            completion(self.lastMeasurement, nil)
        }
    }

    @objc func isAvailable(completion: @escaping (IsAvailableResult?, Error?) -> Void) {
        completion(IsAvailableResult(available: motionManager.isGyroAvailable), nil)
    }

    @objc public func startMeasurementUpdates(completion: @escaping (Error?) -> Void) {
        guard motionManager.isGyroAvailable else {
            completion(CustomError.notAvailable)
            return
        }
        guard !isRunning else {
            completion(nil)
            return
        }

        motionManager.gyroUpdateInterval = updateInterval
        motionManager.startGyroUpdates(to: queue) { data, _ in
            if let data = data {
                self.lastMeasurement = Measurement(data)
                if let measurement = self.lastMeasurement {
                    self.plugin.handleMeasurementEvent(measurement)
                }
            }
        }

        isRunning = true
        completion(nil)
    }

    @objc func stopMeasurementUpdates(completion: ((Error?) -> Void)? = nil) {
        guard motionManager.isGyroAvailable else {
            completion?(CustomError.notAvailable)
            return
        }

        motionManager.stopGyroUpdates()
        if let completion = completion {
            isRunning = false
            completion(nil)
        }
    }
}
