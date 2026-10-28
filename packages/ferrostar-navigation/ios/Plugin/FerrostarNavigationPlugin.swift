import Capacitor
import CoreLocation
import Foundation

@objc(FerrostarNavigationPlugin)
public class FerrostarNavigationPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "FerrostarNavigationPlugin"
    public let jsName = "FerrostarNavigation"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "muteVoice", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "replaceRoute", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "startNavigation", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "stopNavigation", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "unmuteVoice", returnType: CAPPluginReturnPromise)
    ]
    public static let eventNavigationCompleted = "navigationCompleted"
    public static let eventNavigationStateChanged = "navigationStateChanged"
    public static let eventRouteDeviationDetected = "routeDeviationDetected"
    public static let tag = "FerrostarNavigation"
    private var implementation: FerrostarNavigation?
    private var locationManager: CLLocationManager?
    private var locationPermissionCall: CAPPluginCall?

    override public func load() {
        super.load()
        self.implementation = FerrostarNavigation(plugin: self)
    }

    @objc func muteVoice(_ call: CAPPluginCall) {
        implementation?.muteVoice(completion: { error in self.handleCompletion(call, error) })
    }

    public func notifyNavigationCompletedListeners() {
        notifyListeners(Self.eventNavigationCompleted, data: nil)
    }

    public func notifyNavigationStateChangedListeners(_ event: NavigationStateChangedEvent) {
        notifyListeners(Self.eventNavigationStateChanged, data: event.toJSObject() as? [String: Any])
    }

    public func notifyRouteDeviationDetectedListeners(_ event: RouteDeviationDetectedEvent) {
        notifyListeners(Self.eventRouteDeviationDetected, data: event.toJSObject() as? [String: Any])
    }

    @objc func replaceRoute(_ call: CAPPluginCall) {
        do {
            let options = try ReplaceRouteOptions(call)
            implementation?.replaceRoute(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func startNavigation(_ call: CAPPluginCall) {
        do {
            let options = try StartNavigationOptions(call)
            if !options.simulateLocation && !isLocationPermissionGranted() {
                requestLocationPermission(call)
                return
            }
            implementation?.startNavigation(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func stopNavigation(_ call: CAPPluginCall) {
        implementation?.stopNavigation(completion: { error in self.handleCompletion(call, error) })
    }

    @objc func unmuteVoice(_ call: CAPPluginCall) {
        implementation?.unmuteVoice(completion: { error in self.handleCompletion(call, error) })
    }

    private func getLocationAuthorizationStatus() -> CLAuthorizationStatus {
        return CLLocationManager().authorizationStatus
    }

    private func handleCompletion(_ call: CAPPluginCall, _ error: Error?) {
        if let error = error {
            rejectCall(call, error)
        } else {
            resolveCall(call)
        }
    }

    private func handleLocationPermissionCallback(_ call: CAPPluginCall) {
        if !isLocationPermissionGranted() {
            rejectCall(call, CustomError.locationPermissionDenied)
            return
        }

        switch call.methodName {
        case "startNavigation":
            startNavigation(call)
        default:
            break
        }
    }

    private func isLocationPermissionGranted() -> Bool {
        let status = getLocationAuthorizationStatus()
        return status == .authorizedAlways || status == .authorizedWhenInUse
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", Self.tag, "] ", error)
        call.reject(error.localizedDescription, (error as? CustomError)?.code)
    }

    private func requestLocationPermission(_ call: CAPPluginCall) {
        guard Bundle.main.object(forInfoDictionaryKey: "NSLocationWhenInUseUsageDescription") != nil else {
            rejectCall(call, CustomError.privacyDescriptionsMissing)
            return
        }
        guard getLocationAuthorizationStatus() == .notDetermined else {
            handleLocationPermissionCallback(call)
            return
        }
        DispatchQueue.main.async {
            let locationManager = CLLocationManager()
            locationManager.delegate = self
            self.locationManager = locationManager
            self.locationPermissionCall = call
            locationManager.requestWhenInUseAuthorization()
        }
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }
}

extension FerrostarNavigationPlugin: CLLocationManagerDelegate {
    public func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
        guard manager.authorizationStatus != .notDetermined, let call = locationPermissionCall else {
            return
        }
        locationManager = nil
        locationPermissionCall = nil
        handleLocationPermissionCallback(call)
    }
}
