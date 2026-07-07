import Foundation
import Capacitor
import CoreLocation
import MapKit
import UIKit

@objc public class MapsLauncher: NSObject {
    static let appAppleMaps = "appleMaps"
    static let appGoogleMaps = "googleMaps"
    static let appWaze = "waze"
    static let schemeGoogleMaps = "comgooglemaps"
    static let schemeWaze = "waze"

    private let plugin: MapsLauncherPlugin

    init(plugin: MapsLauncherPlugin) {
        self.plugin = plugin
    }

    @objc public func getAvailableApps(completion: @escaping (GetAvailableAppsResult?, Error?) -> Void) {
        var apps = [Self.appAppleMaps]
        if canOpen(Self.schemeGoogleMaps) {
            apps.append(Self.appGoogleMaps)
        }
        if canOpen(Self.schemeWaze) {
            apps.append(Self.appWaze)
        }
        completion(GetAvailableAppsResult(apps: apps), nil)
    }

    @objc public func navigate(_ options: NavigateOptions, completion: @escaping (Error?) -> Void) {
        switch options.app {
        case nil, Self.appAppleMaps:
            openAppleMaps(options, completion: completion)
        case Self.appGoogleMaps:
            openGoogleMaps(options, completion: completion)
        case Self.appWaze:
            openWaze(options, completion: completion)
        default:
            completion(CustomError.appNotAvailable)
        }
    }

    private func appleMapsDirectionsMode(_ travelMode: String?) -> String {
        switch travelMode {
        case "walking":
            return MKLaunchOptionsDirectionsModeWalking
        case "transit":
            return MKLaunchOptionsDirectionsModeTransit
        case "driving":
            return MKLaunchOptionsDirectionsModeDriving
        default:
            return MKLaunchOptionsDirectionsModeDefault
        }
    }

    private func appleMapsDirflg(_ travelMode: String?) -> String? {
        switch travelMode {
        case "walking":
            return "w"
        case "transit":
            return "r"
        case "driving":
            return "d"
        default:
            return nil
        }
    }

    private func buildAppleMapsUrl(_ options: NavigateOptions) -> URL? {
        var components = URLComponents(string: "https://maps.apple.com/")
        var queryItems = [URLQueryItem(name: "daddr", value: formatDestination(options.destination))]
        if let start = options.start {
            queryItems.append(URLQueryItem(name: "saddr", value: formatDestination(start)))
        }
        if let dirflg = appleMapsDirflg(options.travelMode) {
            queryItems.append(URLQueryItem(name: "dirflg", value: dirflg))
        }
        components?.queryItems = queryItems
        return components?.url
    }

    private func buildUrl(scheme: String, queryItems: [URLQueryItem]) -> URL? {
        var components = URLComponents()
        components.scheme = scheme
        components.host = ""
        components.queryItems = queryItems
        return components.url
    }

    private func canOpen(_ scheme: String) -> Bool {
        guard let url = URL(string: scheme + "://") else {
            return false
        }
        return UIApplication.shared.canOpenURL(url)
    }

    private func formatDestination(_ destination: Destination) -> String {
        if let coordinate = destination.coordinate {
            return "\(coordinate.latitude),\(coordinate.longitude)"
        }
        return destination.address ?? ""
    }

    private func googleDirectionsMode(_ travelMode: String?) -> String {
        switch travelMode {
        case "walking", "bicycling", "transit":
            return travelMode ?? "driving"
        default:
            return "driving"
        }
    }

    private func open(_ url: URL, completion: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            UIApplication.shared.open(url, options: [:]) { success in
                completion(success ? nil : CustomError.launchFailed)
            }
        }
    }

    private func openAppleMaps(_ options: NavigateOptions, completion: @escaping (Error?) -> Void) {
        let canUseMapItems = options.destination.coordinate != nil && (options.start == nil || options.start?.coordinate != nil)
        if canUseMapItems, let destinationCoordinate = options.destination.coordinate {
            let destinationItem = MKMapItem(placemark: MKPlacemark(coordinate: destinationCoordinate))
            var items: [MKMapItem] = []
            if let startCoordinate = options.start?.coordinate {
                items.append(MKMapItem(placemark: MKPlacemark(coordinate: startCoordinate)))
            } else {
                items.append(MKMapItem.forCurrentLocation())
            }
            items.append(destinationItem)
            let launchOptions = [MKLaunchOptionsDirectionsModeKey: appleMapsDirectionsMode(options.travelMode)]
            DispatchQueue.main.async {
                MKMapItem.openMaps(with: items, launchOptions: launchOptions)
                completion(nil)
            }
        } else {
            guard let url = buildAppleMapsUrl(options) else {
                completion(CustomError.launchFailed)
                return
            }
            open(url, completion: completion)
        }
    }

    private func openGoogleMaps(_ options: NavigateOptions, completion: @escaping (Error?) -> Void) {
        guard canOpen(Self.schemeGoogleMaps) else {
            completion(CustomError.appNotAvailable)
            return
        }
        var queryItems = [URLQueryItem(name: "daddr", value: formatDestination(options.destination))]
        if let start = options.start {
            queryItems.append(URLQueryItem(name: "saddr", value: formatDestination(start)))
        }
        queryItems.append(URLQueryItem(name: "directionsmode", value: googleDirectionsMode(options.travelMode)))
        guard let url = buildUrl(scheme: Self.schemeGoogleMaps, queryItems: queryItems) else {
            completion(CustomError.launchFailed)
            return
        }
        open(url, completion: completion)
    }

    private func openWaze(_ options: NavigateOptions, completion: @escaping (Error?) -> Void) {
        guard canOpen(Self.schemeWaze) else {
            completion(CustomError.appNotAvailable)
            return
        }
        var queryItems: [URLQueryItem] = []
        if let coordinate = options.destination.coordinate {
            queryItems.append(URLQueryItem(name: "ll", value: "\(coordinate.latitude),\(coordinate.longitude)"))
        } else if let address = options.destination.address {
            queryItems.append(URLQueryItem(name: "q", value: address))
        }
        queryItems.append(URLQueryItem(name: "navigate", value: "yes"))
        guard let url = buildUrl(scheme: Self.schemeWaze, queryItems: queryItems) else {
            completion(CustomError.launchFailed)
            return
        }
        open(url, completion: completion)
    }
}
