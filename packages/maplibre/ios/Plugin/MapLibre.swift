import Capacitor
import Foundation
import MapLibre
import UIKit
import WebKit

// swiftlint:disable:next type_body_length
@objc public class MapLibreImpl: NSObject {
    private let iconLoader = MarkerIconLoader()
    private let locationPermissionManager = LocationPermissionManager()
    private let plugin: MapLibrePlugin
    private var mapsById = [String: MapInstance]()
    private var webViewAppearance: WebViewAppearance?

    init(plugin: MapLibrePlugin) {
        self.plugin = plugin
        super.init()
    }

    @objc public func addGeoJsonSource(_ options: AddGeoJsonSourceOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            try map.geoJsonManager.addSource(options)
            completion(nil)
        }
    }

    @objc public func addLayer(_ options: AddLayerOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            try map.geoJsonManager.addLayer(options)
            completion(nil)
        }
    }

    @objc public func addMarker(_ options: AddMarkerOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.markerManager.addMarkers([options.marker], completion: completion)
        }
    }

    @objc public func addMarkers(_ options: AddMarkersOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.markerManager.addMarkers(options.markers, completion: completion)
        }
    }

    @objc public func addPolyline(_ options: AddPolylineOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.polylineManager.addPolylines([options.polyline])
            completion(nil)
        }
    }

    @objc public func addPolylines(_ options: AddPolylinesOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.polylineManager.addPolylines(options.polylines)
            completion(nil)
        }
    }

    @objc public func checkPermissions(completion: @escaping (_ result: Result?, _ error: Error?) -> Void) {
        completion(PermissionStatusResult(location: locationPermissionManager.getPermissionState()), nil)
    }

    @objc public func createMap(_ options: CreateMapOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            guard self.mapsById[options.mapId] == nil else {
                completion(CustomError.mapAlreadyExists)
                return
            }
            guard let webView = self.plugin.bridge?.webView else {
                completion(CustomError.webViewMissing)
                return
            }
            MapContainer.findWhenLaidOut(in: webView, matching: options.contentSize) { container in
                guard let container = container else {
                    completion(CustomError.mapContainerNotFound)
                    return
                }
                let map = MapInstance(
                    mapId: options.mapId,
                    mapView: Self.createMapView(options),
                    iconLoader: self.iconLoader,
                    plugin: self.plugin
                )
                map.setGesturesEnabled(options.gestures)
                map.whenStyleLoaded { error in
                    if error != nil {
                        self.removeMap(map)
                    }
                    completion(error)
                }
                self.mapsById[options.mapId] = map
                self.makeWebViewTransparent(webView)
                MapContainer.attach(map.mapView, to: container)
            }
        }
    }

    @objc public func destroyMap(_ options: DestroyMapOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            self.removeMap(map)
            completion(nil)
        }
    }

    @objc public func disableUserLocation(_ options: DisableUserLocationOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.disableUserLocation()
            completion(nil)
        }
    }

    @objc public func enableUserLocation(_ options: EnableUserLocationOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            guard self.locationPermissionManager.isPrivacyDescriptionDefined else {
                completion(CustomError.privacyDescriptionsMissing)
                return
            }
            self.locationPermissionManager.requestAuthorization {
                guard self.locationPermissionManager.isAuthorized else {
                    completion(CustomError.locationPermissionDenied)
                    return
                }
                map.enableUserLocation(trackingMode: options.trackingMode)
                completion(nil)
            }
        }
    }

    @objc public func fitBounds(_ options: FitBoundsOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.fitBounds(options)
            completion(nil)
        }
    }

    @objc public func getCamera(_ options: GetCameraOptions, completion: @escaping (_ result: Result?, _ error: Error?) -> Void) {
        DispatchQueue.main.async {
            guard let map = self.mapsById[options.mapId] else {
                completion(nil, CustomError.mapNotFound)
                return
            }
            completion(GetCameraResult(camera: map.getCamera()), nil)
        }
    }

    @objc public func removeAllMarkers(_ options: RemoveAllMarkersOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.markerManager.removeAllMarkers()
            completion(nil)
        }
    }

    @objc public func removeAllPolylines(_ options: RemoveAllPolylinesOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.polylineManager.removeAllPolylines()
            completion(nil)
        }
    }

    @objc public func removeGeoJsonSourceById(_ options: RemoveGeoJsonSourceByIdOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            try map.geoJsonManager.removeSource(withIdentifier: options.sourceId)
            completion(nil)
        }
    }

    @objc public func removeLayerById(_ options: RemoveLayerByIdOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            try map.geoJsonManager.removeLayer(withIdentifier: options.layerId)
            completion(nil)
        }
    }

    @objc public func removeMarkerById(_ options: RemoveMarkerByIdOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            try map.markerManager.removeMarkers(withIdentifiers: [options.markerId])
            completion(nil)
        }
    }

    @objc public func removeMarkersByIds(_ options: RemoveMarkersByIdsOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            try map.markerManager.removeMarkers(withIdentifiers: options.markerIds)
            completion(nil)
        }
    }

    @objc public func removePolylineById(_ options: RemovePolylineByIdOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            try map.polylineManager.removePolylines(withIdentifiers: [options.polylineId])
            completion(nil)
        }
    }

    @objc public func removePolylinesByIds(_ options: RemovePolylinesByIdsOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            try map.polylineManager.removePolylines(withIdentifiers: options.polylineIds)
            completion(nil)
        }
    }

    @objc public func requestPermissions(completion: @escaping (_ result: Result?, _ error: Error?) -> Void) {
        guard locationPermissionManager.isPrivacyDescriptionDefined else {
            completion(nil, CustomError.privacyDescriptionsMissing)
            return
        }
        locationPermissionManager.requestAuthorization {
            completion(PermissionStatusResult(location: self.locationPermissionManager.getPermissionState()), nil)
        }
    }

    @objc public func setCamera(_ options: SetCameraOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.setCamera(options)
            completion(nil)
        }
    }

    @objc public func setFrame(_ options: SetFrameOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.setFrame(options.frame)
            self.reattachIfNeeded(map, contentSize: options.contentSize)
            completion(nil)
        }
    }

    @objc public func setGesturesEnabled(_ options: SetGesturesEnabledOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.setGesturesEnabled(options.gestures)
            completion(nil)
        }
    }

    @objc public func setStyle(_ options: SetStyleOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.setStyle(options, completion: completion)
        }
    }

    @objc public func updateGeoJsonSourceById(_ options: UpdateGeoJsonSourceByIdOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            try map.geoJsonManager.updateSource(options)
            completion(nil)
        }
    }

    @objc public func updateMarkerById(_ options: UpdateMarkerByIdOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            map.markerManager.updateMarker(options, completion: completion)
        }
    }

    @objc public func updatePolylineById(_ options: UpdatePolylineByIdOptions, completion: @escaping (_ error: Error?) -> Void) {
        runOnMap(options.mapId, completion) { map, completion in
            try map.polylineManager.updatePolyline(options)
            completion(nil)
        }
    }

    private static func createMapView(_ options: CreateMapOptions) -> MLNMapView {
        let mapView: MLNMapView
        if let styleJson = options.styleJson {
            mapView = MLNMapView(frame: options.frame.toCGRect(), styleJSON: styleJson)
        } else {
            mapView = MLNMapView(frame: options.frame.toCGRect(), styleURL: options.styleUrl)
        }
        if let maxZoom = options.maxZoom {
            mapView.maximumZoomLevel = maxZoom
        }
        if let minZoom = options.minZoom {
            mapView.minimumZoomLevel = minZoom
        }
        mapView.direction = options.bearing
        mapView.zoomLevel = options.zoom
        if let center = options.center {
            mapView.centerCoordinate = center.toCoordinate()
        }
        if options.pitch != 0 {
            let pitch = CGFloat(options.pitch)
            let altitude = MLNAltitudeForZoomLevel(options.zoom, pitch, mapView.centerCoordinate.latitude, mapView.frame.size)
            let camera = MLNMapCamera(
                lookingAtCenter: mapView.centerCoordinate,
                altitude: altitude,
                pitch: pitch,
                heading: options.bearing
            )
            mapView.setCamera(camera, animated: false)
        }
        return mapView
    }

    private func makeWebViewTransparent(_ webView: WKWebView) {
        guard webViewAppearance == nil else {
            return
        }
        webViewAppearance = WebViewAppearance(
            backgroundColor: webView.backgroundColor,
            isOpaque: webView.isOpaque,
            scrollViewBackgroundColor: webView.scrollView.backgroundColor
        )
        webView.backgroundColor = .clear
        webView.isOpaque = false
        webView.scrollView.backgroundColor = .clear
    }

    private func reattachIfNeeded(_ map: MapInstance, contentSize: MapContentSize?) {
        guard let contentSize = contentSize else {
            return
        }
        if let container = map.mapView.superview as? UIScrollView, contentSize.matches(container.contentSize) {
            container.isScrollEnabled = false
            return
        }
        guard let webView = plugin.bridge?.webView,
              let container = MapContainer.find(in: webView, matching: contentSize) else {
            return
        }
        MapContainer.attach(map.mapView, to: container)
    }

    private func removeMap(_ map: MapInstance) {
        (map.mapView.superview as? UIScrollView)?.isScrollEnabled = true
        map.destroy()
        mapsById.removeValue(forKey: map.mapId)
        if mapsById.isEmpty {
            restoreWebViewAppearance()
        }
    }

    private func restoreWebViewAppearance() {
        guard let appearance = webViewAppearance else {
            return
        }
        webViewAppearance = nil
        guard let webView = plugin.bridge?.webView else {
            return
        }
        webView.backgroundColor = appearance.backgroundColor
        webView.isOpaque = appearance.isOpaque
        webView.scrollView.backgroundColor = appearance.scrollViewBackgroundColor
    }

    private func runOnMap(
        _ mapId: String,
        _ completion: @escaping (_ error: Error?) -> Void,
        _ action: @escaping (_ map: MapInstance, _ completion: @escaping (_ error: Error?) -> Void) throws -> Void
    ) {
        DispatchQueue.main.async {
            guard let map = self.mapsById[mapId] else {
                completion(CustomError.mapNotFound)
                return
            }
            do {
                try action(map, completion)
            } catch {
                completion(error)
            }
        }
    }
}

private struct WebViewAppearance {
    let backgroundColor: UIColor?
    let isOpaque: Bool
    let scrollViewBackgroundColor: UIColor?
}
