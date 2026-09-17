import Foundation
import MapLibre
import UIKit

/// A single map, its native map view and everything that is rendered on it.
public class MapInstance: NSObject, MLNMapViewDelegate, UIGestureRecognizerDelegate {
    let geoJsonManager: GeoJsonManager
    let mapId: String
    let mapView: MLNMapView
    let markerManager: MarkerManager
    let polylineManager: PolylineManager

    private static let cameraIdleDelay = 0.15
    private static let markerHitAreaSize = 44.0

    private weak var plugin: MapLibrePlugin?
    private var cameraIdleWorkItem: DispatchWorkItem?
    private var isCameraMoving = false
    private var isUserLocationEnabled = false
    private var lastAppliedEdgePadding = UIEdgeInsets.zero
    private var styleLoadCompletion: ((_ error: Error?) -> Void)?

    init(mapId: String, mapView: MLNMapView, iconLoader: MarkerIconLoader, plugin: MapLibrePlugin) {
        self.geoJsonManager = GeoJsonManager(mapView: mapView)
        self.mapId = mapId
        self.mapView = mapView
        self.markerManager = MarkerManager(mapView: mapView, iconLoader: iconLoader)
        self.plugin = plugin
        self.polylineManager = PolylineManager(mapView: mapView)
        super.init()
        mapView.delegate = self
        addTapRecognizer(to: mapView)
    }

    func destroy() {
        cameraIdleWorkItem?.cancel()
        markerManager.destroy()
        mapView.delegate = nil
        mapView.removeFromSuperview()
        styleLoadCompletion = nil
    }

    func disableUserLocation() {
        isUserLocationEnabled = false
        mapView.showsUserLocation = false
        mapView.userTrackingMode = .none
    }

    func enableUserLocation(trackingMode: UserTrackingMode) {
        isUserLocationEnabled = true
        mapView.showsUserLocation = true
        mapView.userTrackingMode = trackingMode.toMapLibreUserTrackingMode
    }

    func fitBounds(_ options: FitBoundsOptions) {
        let edgePadding = options.padding.toEdgeInsets()
        let camera = mapView.cameraThatFitsCoordinateBounds(options.bounds.toCoordinateBounds(), edgePadding: edgePadding)
        if let maxZoom = options.maxZoom, getZoomLevel(of: camera) > maxZoom {
            camera.altitude = getAltitude(forZoomLevel: maxZoom, pitch: camera.pitch, latitude: camera.centerCoordinate.latitude)
        }
        applyCamera(camera, edgePadding: edgePadding, animate: options.animate, animationDuration: options.animationDuration)
    }

    func getCamera() -> Camera {
        return Camera(mapView)
    }

    public func gestureRecognizer(
        _ gestureRecognizer: UIGestureRecognizer,
        shouldRecognizeSimultaneouslyWith otherGestureRecognizer: UIGestureRecognizer
    ) -> Bool {
        return true
    }

    public func mapView(_ mapView: MLNMapView, didFinishLoading style: MLNStyle) {
        markerManager.handleStyleLoaded(style)
        polylineManager.handleStyleLoaded()
        completeStyleLoad(nil)
    }

    public func mapView(_ mapView: MLNMapView, didUpdate userLocation: MLNUserLocation?) {
        guard isUserLocationEnabled, let location = userLocation?.location else {
            return
        }
        plugin?.notifyUserLocationChangeListeners(
            UserLocationChangeEvent(location: location, heading: userLocation?.heading, mapId: mapId)
        )
    }

    public func mapView(_ mapView: MLNMapView, regionDidChangeWith reason: MLNCameraChangeReason, animated: Bool) {
        scheduleCameraIdle()
    }

    public func mapView(_ mapView: MLNMapView, regionWillChangeWith reason: MLNCameraChangeReason, animated: Bool) {
        cameraIdleWorkItem?.cancel()
        guard !isCameraMoving else {
            return
        }
        isCameraMoving = true
        plugin?.notifyCameraMoveStartedListeners(
            CameraMoveStartedEvent(mapId: mapId, reason: MapLibreHelper.getCameraMoveReason(reason))
        )
    }

    public func mapViewDidFailLoadingMap(_ mapView: MLNMapView, withError error: Error) {
        completeStyleLoad(CustomError.styleLoadFailed)
    }

    func setCamera(_ options: SetCameraOptions) {
        let centerCoordinate = options.center?.toCoordinate() ?? mapView.centerCoordinate
        let pitch = CGFloat(options.pitch ?? Double(mapView.camera.pitch))
        let camera = MLNMapCamera(
            lookingAtCenter: centerCoordinate,
            altitude: getAltitude(forZoomLevel: options.zoom ?? mapView.zoomLevel, pitch: pitch, latitude: centerCoordinate.latitude),
            pitch: pitch,
            heading: options.bearing ?? mapView.direction
        )
        applyCamera(
            camera,
            edgePadding: options.padding?.toEdgeInsets() ?? lastAppliedEdgePadding,
            animate: options.animate,
            animationDuration: options.animationDuration
        )
    }

    func setFrame(_ frame: MapFrame) {
        mapView.frame = frame.toCGRect()
    }

    func setGesturesEnabled(_ gestures: GestureSettings) {
        mapView.isPitchEnabled = gestures.tilt ?? mapView.isPitchEnabled
        mapView.isRotateEnabled = gestures.rotate ?? mapView.isRotateEnabled
        mapView.isScrollEnabled = gestures.pan ?? mapView.isScrollEnabled
        mapView.isZoomEnabled = gestures.zoom ?? mapView.isZoomEnabled
    }

    func setStyle(_ options: SetStyleOptions, completion: @escaping (_ error: Error?) -> Void) {
        styleLoadCompletion = completion
        if let url = options.url {
            mapView.styleURL = url
        } else if let json = options.json {
            mapView.styleJSON = json
        }
    }

    func whenStyleLoaded(completion: @escaping (_ error: Error?) -> Void) {
        styleLoadCompletion = completion
    }

    private func completeStyleLoad(_ error: Error?) {
        guard let completion = styleLoadCompletion else {
            return
        }
        styleLoadCompletion = nil
        completion(error)
    }

    private func getAltitude(forZoomLevel zoomLevel: Double, pitch: CGFloat, latitude: CLLocationDegrees) -> CLLocationDistance {
        return MLNAltitudeForZoomLevel(zoomLevel, pitch, latitude, mapView.frame.size)
    }

    private func getZoomLevel(of camera: MLNMapCamera) -> Double {
        return MLNZoomLevelForAltitude(camera.altitude, camera.pitch, camera.centerCoordinate.latitude, mapView.frame.size)
    }

    @objc private func handleTap(_ recognizer: UITapGestureRecognizer) {
        let point = recognizer.location(in: mapView)
        let hitArea = CGRect(
            x: point.x - Self.markerHitAreaSize / 2,
            y: point.y - Self.markerHitAreaSize / 2,
            width: Self.markerHitAreaSize,
            height: Self.markerHitAreaSize
        )
        let features = mapView.visibleFeatures(in: hitArea, styleLayerIdentifiers: [MarkerManager.layerIdentifier])
        if let markerId = features.compactMap({ $0.attribute(forKey: "id") as? String }).first,
           let marker = markerManager.findMarker(withIdentifier: markerId) {
            plugin?.notifyMarkerClickListeners(
                MarkerClickEvent(coordinates: marker.coordinates, mapId: mapId, markerId: markerId)
            )
            return
        }
        plugin?.notifyMapClickListeners(
            MapClickEvent(coordinates: LatLng(mapView.convert(point, toCoordinateFrom: mapView)), mapId: mapId, point: point)
        )
    }

    private func scheduleCameraIdle() {
        cameraIdleWorkItem?.cancel()
        let workItem = DispatchWorkItem { [weak self] in
            guard let self = self else {
                return
            }
            self.isCameraMoving = false
            self.plugin?.notifyCameraIdleListeners(CameraIdleEvent(camera: self.getCamera(), mapId: self.mapId))
        }
        cameraIdleWorkItem = workItem
        DispatchQueue.main.asyncAfter(deadline: .now() + Self.cameraIdleDelay, execute: workItem)
    }

    private func addTapRecognizer(to mapView: MLNMapView) {
        let tapRecognizer = UITapGestureRecognizer(target: self, action: #selector(handleTap))
        tapRecognizer.delegate = self
        // The map zooms in on a double tap, which must not be reported as two taps.
        for recognizer in mapView.gestureRecognizers ?? [] {
            if let doubleTapRecognizer = recognizer as? UITapGestureRecognizer, doubleTapRecognizer.numberOfTapsRequired == 2 {
                tapRecognizer.require(toFail: doubleTapRecognizer)
            }
        }
        mapView.addGestureRecognizer(tapRecognizer)
    }

    private func applyCamera(_ camera: MLNMapCamera, edgePadding: UIEdgeInsets, animate: Bool, animationDuration: Double) {
        lastAppliedEdgePadding = edgePadding
        mapView.setCamera(
            camera,
            withDuration: animate ? animationDuration / 1000 : 0,
            animationTimingFunction: nil,
            edgePadding: edgePadding,
            completionHandler: nil
        )
    }
}
