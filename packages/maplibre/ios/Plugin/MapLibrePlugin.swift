import Capacitor
import Foundation

@objc(MapLibrePlugin)
// swiftlint:disable:next type_body_length
public class MapLibrePlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "MapLibrePlugin"
    public let jsName = "MapLibre"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "addGeoJsonSource", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "addLayer", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "addMarker", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "addMarkers", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "addPolyline", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "addPolylines", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "createMap", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "destroyMap", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "disableUserLocation", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "elementFromPointResult", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "enableUserLocation", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "fitBounds", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getCamera", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeAllMarkers", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeAllPolylines", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeGeoJsonSourceById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeLayerById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeMarkerById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removeMarkersByIds", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removePolylineById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "removePolylinesByIds", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setCamera", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setFrame", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setGesturesEnabled", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setStyle", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "updateGeoJsonSourceById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "updateMarkerById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "updatePolylineById", returnType: CAPPluginReturnPromise)
    ]
    public static let eventCameraIdle = "cameraIdle"
    public static let eventCameraMoveStarted = "cameraMoveStarted"
    public static let eventMapClick = "mapClick"
    public static let eventMarkerClick = "markerClick"
    public static let eventUserLocationChange = "userLocationChange"
    public let tag = "MapLibre"
    private var implementation: MapLibreImpl?

    override public func load() {
        super.load()
        self.implementation = MapLibreImpl(plugin: self)
    }

    @objc func addGeoJsonSource(_ call: CAPPluginCall) {
        do {
            let options = try AddGeoJsonSourceOptions(call)
            implementation?.addGeoJsonSource(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func addLayer(_ call: CAPPluginCall) {
        do {
            let options = try AddLayerOptions(call)
            implementation?.addLayer(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func addMarker(_ call: CAPPluginCall) {
        do {
            let options = try AddMarkerOptions(call)
            implementation?.addMarker(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func addMarkers(_ call: CAPPluginCall) {
        do {
            let options = try AddMarkersOptions(call)
            implementation?.addMarkers(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func addPolyline(_ call: CAPPluginCall) {
        do {
            let options = try AddPolylineOptions(call)
            implementation?.addPolyline(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func addPolylines(_ call: CAPPluginCall) {
        do {
            let options = try AddPolylinesOptions(call)
            implementation?.addPolylines(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        implementation?.checkPermissions(completion: { result, error in self.handleCompletion(call, result, error) })
    }

    @objc func createMap(_ call: CAPPluginCall) {
        do {
            let options = try CreateMapOptions(call)
            implementation?.createMap(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func destroyMap(_ call: CAPPluginCall) {
        do {
            let options = try DestroyMapOptions(call)
            implementation?.destroyMap(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func disableUserLocation(_ call: CAPPluginCall) {
        do {
            let options = try DisableUserLocationOptions(call)
            implementation?.disableUserLocation(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func elementFromPointResult(_ call: CAPPluginCall) {
        rejectCallAsUnavailable(call)
    }

    @objc func enableUserLocation(_ call: CAPPluginCall) {
        do {
            let options = try EnableUserLocationOptions(call)
            implementation?.enableUserLocation(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func fitBounds(_ call: CAPPluginCall) {
        do {
            let options = try FitBoundsOptions(call)
            implementation?.fitBounds(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func getCamera(_ call: CAPPluginCall) {
        do {
            let options = try GetCameraOptions(call)
            implementation?.getCamera(options, completion: { result, error in
                self.handleCompletion(call, result, error)
            })
        } catch {
            rejectCall(call, error)
        }
    }

    public func notifyCameraIdleListeners(_ event: CameraIdleEvent) {
        notifyListeners(Self.eventCameraIdle, data: event.toJSObject() as? [String: Any])
    }

    public func notifyCameraMoveStartedListeners(_ event: CameraMoveStartedEvent) {
        notifyListeners(Self.eventCameraMoveStarted, data: event.toJSObject() as? [String: Any])
    }

    public func notifyMapClickListeners(_ event: MapClickEvent) {
        notifyListeners(Self.eventMapClick, data: event.toJSObject() as? [String: Any])
    }

    public func notifyMarkerClickListeners(_ event: MarkerClickEvent) {
        notifyListeners(Self.eventMarkerClick, data: event.toJSObject() as? [String: Any])
    }

    public func notifyUserLocationChangeListeners(_ event: UserLocationChangeEvent) {
        notifyListeners(Self.eventUserLocationChange, data: event.toJSObject() as? [String: Any])
    }

    @objc func removeAllMarkers(_ call: CAPPluginCall) {
        do {
            let options = try RemoveAllMarkersOptions(call)
            implementation?.removeAllMarkers(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func removeAllPolylines(_ call: CAPPluginCall) {
        do {
            let options = try RemoveAllPolylinesOptions(call)
            implementation?.removeAllPolylines(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func removeGeoJsonSourceById(_ call: CAPPluginCall) {
        do {
            let options = try RemoveGeoJsonSourceByIdOptions(call)
            implementation?.removeGeoJsonSourceById(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func removeLayerById(_ call: CAPPluginCall) {
        do {
            let options = try RemoveLayerByIdOptions(call)
            implementation?.removeLayerById(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func removeMarkerById(_ call: CAPPluginCall) {
        do {
            let options = try RemoveMarkerByIdOptions(call)
            implementation?.removeMarkerById(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func removeMarkersByIds(_ call: CAPPluginCall) {
        do {
            let options = try RemoveMarkersByIdsOptions(call)
            implementation?.removeMarkersByIds(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func removePolylineById(_ call: CAPPluginCall) {
        do {
            let options = try RemovePolylineByIdOptions(call)
            implementation?.removePolylineById(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func removePolylinesByIds(_ call: CAPPluginCall) {
        do {
            let options = try RemovePolylinesByIdsOptions(call)
            implementation?.removePolylinesByIds(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        implementation?.requestPermissions(completion: { result, error in self.handleCompletion(call, result, error) })
    }

    @objc func setCamera(_ call: CAPPluginCall) {
        do {
            let options = try SetCameraOptions(call)
            implementation?.setCamera(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setFrame(_ call: CAPPluginCall) {
        do {
            let options = try SetFrameOptions(call)
            implementation?.setFrame(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setGesturesEnabled(_ call: CAPPluginCall) {
        do {
            let options = try SetGesturesEnabledOptions(call)
            implementation?.setGesturesEnabled(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func setStyle(_ call: CAPPluginCall) {
        do {
            let options = try SetStyleOptions(call)
            implementation?.setStyle(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func updateGeoJsonSourceById(_ call: CAPPluginCall) {
        do {
            let options = try UpdateGeoJsonSourceByIdOptions(call)
            implementation?.updateGeoJsonSourceById(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func updateMarkerById(_ call: CAPPluginCall) {
        do {
            let options = try UpdateMarkerByIdOptions(call)
            implementation?.updateMarkerById(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    @objc func updatePolylineById(_ call: CAPPluginCall) {
        do {
            let options = try UpdatePolylineByIdOptions(call)
            implementation?.updatePolylineById(options, completion: { error in self.handleCompletion(call, error) })
        } catch {
            rejectCall(call, error)
        }
    }

    private func handleCompletion(_ call: CAPPluginCall, _ error: Error?) {
        if let error = error {
            rejectCall(call, error)
        } else {
            resolveCall(call)
        }
    }

    private func handleCompletion(_ call: CAPPluginCall, _ result: Result?, _ error: Error?) {
        if let error = error {
            rejectCall(call, error)
        } else {
            resolveCall(call, result)
        }
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", self.tag, "] ", error)
        call.reject(error.localizedDescription, (error as? CustomError)?.code)
    }

    private func rejectCallAsUnavailable(_ call: CAPPluginCall) {
        call.unavailable("This method is not available on this platform.")
    }

    private func resolveCall(_ call: CAPPluginCall) {
        call.resolve()
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
