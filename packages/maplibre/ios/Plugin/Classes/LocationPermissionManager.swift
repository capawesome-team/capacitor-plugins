import CoreLocation
import Foundation

public class LocationPermissionManager: NSObject, CLLocationManagerDelegate {
    private let locationManager = CLLocationManager()
    private var authorizationCompletion: (() -> Void)?

    override init() {
        super.init()
        locationManager.delegate = self
    }

    var isAuthorized: Bool {
        let status = locationManager.authorizationStatus
        return status == .authorizedAlways || status == .authorizedWhenInUse
    }

    var isPrivacyDescriptionDefined: Bool {
        return Bundle.main.object(forInfoDictionaryKey: "NSLocationWhenInUseUsageDescription") != nil
    }

    func getPermissionState() -> String {
        switch locationManager.authorizationStatus {
        case .authorizedAlways, .authorizedWhenInUse:
            return "granted"
        case .denied, .restricted:
            return "denied"
        default:
            return "prompt"
        }
    }

    public func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
        guard manager.authorizationStatus != .notDetermined, let completion = authorizationCompletion else {
            return
        }
        authorizationCompletion = nil
        completion()
    }

    func requestAuthorization(completion: @escaping () -> Void) {
        guard locationManager.authorizationStatus == .notDetermined else {
            completion()
            return
        }
        authorizationCompletion = completion
        locationManager.requestWhenInUseAuthorization()
    }
}
