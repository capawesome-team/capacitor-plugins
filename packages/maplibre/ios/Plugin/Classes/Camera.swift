import Capacitor
import Foundation
import MapLibre

public class Camera {
    let bearing: Double
    let center: LatLng
    let pitch: Double
    let zoom: Double

    init(_ mapView: MLNMapView) {
        self.bearing = mapView.direction
        self.center = LatLng(mapView.centerCoordinate)
        self.pitch = Double(mapView.camera.pitch)
        self.zoom = mapView.zoomLevel
    }

    func toJSObject() -> JSObject {
        var result = JSObject()
        result["bearing"] = bearing
        result["center"] = center.toJSObject()
        result["pitch"] = pitch
        result["zoom"] = zoom
        return result
    }
}
