import Foundation
import CoreLocation

@objc public class Compass: NSObject, CLLocationManagerDelegate {
    private var isRunning = false
    private let locationManager = CLLocationManager()
    private var oneShotCompletions: [(Heading?, Error?) -> Void] = []
    private let plugin: CompassPlugin

    init(plugin: CompassPlugin) {
        self.plugin = plugin
        super.init()
        self.locationManager.headingFilter = 1
        self.locationManager.delegate = self
    }

    @objc public func getHeading(completion: @escaping (Heading?, Error?) -> Void) {
        oneShotCompletions.append(completion)
        locationManager.startUpdatingHeading()
    }

    @objc public func isAvailable() -> Bool {
        return CLLocationManager.headingAvailable()
    }

    @objc public func startHeadingUpdates(completion: @escaping (Error?) -> Void) {
        isRunning = true
        locationManager.startUpdatingHeading()
        completion(nil)
    }

    @objc public func stopHeadingUpdates(completion: ((Error?) -> Void)? = nil) {
        isRunning = false
        stopUpdatingHeadingIfPossible()
        completion?(nil)
    }

    public func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        let completions = oneShotCompletions
        oneShotCompletions.removeAll()
        for completion in completions {
            completion(nil, error)
        }
        stopUpdatingHeadingIfPossible()
    }

    public func locationManager(_ manager: CLLocationManager, didUpdateHeading newHeading: CLHeading) {
        let heading = Compass.createHeading(from: newHeading)
        if isRunning {
            plugin.notifyHeadingChangeListeners(HeadingChangeEvent(heading: heading))
        }
        let completions = oneShotCompletions
        oneShotCompletions.removeAll()
        for completion in completions {
            completion(heading, nil)
        }
        stopUpdatingHeadingIfPossible()
    }

    private static func createHeading(from heading: CLHeading) -> Heading {
        let magneticHeading = (heading.magneticHeading * 100).rounded() / 100
        let trueHeading: Double? = heading.trueHeading >= 0 ? (heading.trueHeading * 100).rounded() / 100 : nil
        let accuracy: Double? = heading.headingAccuracy >= 0 ? (heading.headingAccuracy * 100).rounded() / 100 : nil
        return Heading(magneticHeading: magneticHeading, trueHeading: trueHeading, accuracy: accuracy)
    }

    private func stopUpdatingHeadingIfPossible() {
        if !isRunning && oneShotCompletions.isEmpty {
            locationManager.stopUpdatingHeading()
        }
    }
}
