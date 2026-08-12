import CoreLocation
import Foundation
import UIKit

class LocationPermissionHandler: NSObject, CLLocationManagerDelegate {
    private var completion: (() -> Void)?
    private var hasRequestedAuthorization = false
    private var locationManager: CLLocationManager?
    private var requestAlways = false

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
        if hasRequestedAuthorization {
            complete()
            return
        }
        hasRequestedAuthorization = true
        if requestAlways {
            manager.requestAlwaysAuthorization()
        } else {
            manager.requestWhenInUseAuthorization()
        }
        scheduleFallbackCompletion()
    }

    func requestAuthorization(always: Bool, completion: @escaping () -> Void) {
        self.completion = completion
        self.requestAlways = always
        DispatchQueue.main.async {
            let locationManager = CLLocationManager()
            locationManager.delegate = self
            self.locationManager = locationManager
        }
    }

    private func complete() {
        guard let completion = completion else {
            return
        }
        self.completion = nil
        locationManager?.delegate = nil
        locationManager = nil
        completion()
    }

    @objc private func handleDidBecomeActive() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.3) {
            self.complete()
        }
    }

    private func scheduleFallbackCompletion() {
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleDidBecomeActive),
            name: UIApplication.didBecomeActiveNotification,
            object: nil
        )
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            if UIApplication.shared.applicationState == .active {
                self.complete()
            }
        }
    }
}
