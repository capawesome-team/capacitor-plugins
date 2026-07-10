import Foundation
import CoreMotion

@objc public class Shake: NSObject {
    private let plugin: ShakePlugin
    private var lastShakeTimestamp: TimeInterval = 0
    private lazy var motionManager = CMMotionManager()
    private let queue = OperationQueue()
    private let shakeDebounceInterval: TimeInterval = 0.5
    private var shakeThreshold: Double = 0
    private let updateInterval: TimeInterval = 0.1
    private var watching = false

    init(plugin: ShakePlugin) {
        self.plugin = plugin
    }

    @objc public func isAvailable() -> Bool {
        return motionManager.isAccelerometerAvailable
    }

    @objc public func startWatching(_ options: StartWatchingOptions, completion: @escaping (_ error: Error?) -> Void) {
        shakeThreshold = options.shakeThreshold
        if watching {
            completion(nil)
            return
        }
        motionManager.accelerometerUpdateInterval = updateInterval
        motionManager.startAccelerometerUpdates(to: queue) { [weak self] data, _ in
            guard let self = self, let data = data else {
                return
            }
            self.handleAccelerometerData(data)
        }
        watching = true
        completion(nil)
    }

    @objc public func stopWatching(completion: ((_ error: Error?) -> Void)? = nil) {
        if watching {
            motionManager.stopAccelerometerUpdates()
            watching = false
        }
        completion?(nil)
    }

    private func handleAccelerometerData(_ data: CMAccelerometerData) {
        let acceleration = data.acceleration
        let gForce = (acceleration.x * acceleration.x + acceleration.y * acceleration.y + acceleration.z * acceleration.z).squareRoot()
        if gForce < shakeThreshold {
            return
        }
        let now = Date().timeIntervalSince1970
        if now - lastShakeTimestamp < shakeDebounceInterval {
            return
        }
        lastShakeTimestamp = now
        plugin.notifyShakeListeners()
    }
}
