import Foundation
import MapLibre

/// Renders every polyline of a map as its own line layer.
public class PolylineManager {
    private var polylinesById = [String: Polyline]()
    private weak var mapView: MLNMapView?

    init(mapView: MLNMapView) {
        self.mapView = mapView
    }

    func addPolylines(_ polylines: [Polyline]) {
        for polyline in polylines {
            polylinesById[polyline.identifier] = polyline
            render(polyline)
        }
    }

    func handleStyleLoaded() {
        polylinesById.removeAll()
    }

    func removeAllPolylines() {
        for identifier in polylinesById.keys {
            removeLayerAndSource(of: identifier)
        }
        polylinesById.removeAll()
    }

    func removePolylines(withIdentifiers identifiers: [String]) throws {
        for identifier in identifiers where polylinesById[identifier] == nil {
            throw CustomError.polylineNotFound
        }
        for identifier in identifiers {
            removeLayerAndSource(of: identifier)
            polylinesById.removeValue(forKey: identifier)
        }
    }

    func updatePolyline(_ options: UpdatePolylineByIdOptions) throws {
        guard let polyline = polylinesById[options.polylineId] else {
            throw CustomError.polylineNotFound
        }
        polyline.color = options.color ?? polyline.color
        polyline.coordinates = options.coordinates ?? polyline.coordinates
        polyline.opacity = options.opacity ?? polyline.opacity
        polyline.width = options.width ?? polyline.width
        render(polyline)
    }

    private static func createLayerIdentifier(_ identifier: String) -> String {
        return "capawesome-maplibre-polyline-layer-\(identifier)"
    }

    private static func createSourceIdentifier(_ identifier: String) -> String {
        return "capawesome-maplibre-polyline-source-\(identifier)"
    }

    private func removeLayerAndSource(of identifier: String) {
        guard let style = mapView?.style else {
            return
        }
        if let layer = style.layer(withIdentifier: Self.createLayerIdentifier(identifier)) {
            style.removeLayer(layer)
        }
        if let source = style.source(withIdentifier: Self.createSourceIdentifier(identifier)) {
            style.removeSource(source)
        }
    }

    private func render(_ polyline: Polyline) {
        guard let style = mapView?.style else {
            return
        }
        var coordinates = polyline.coordinates.map { $0.toCoordinate() }
        let feature = MLNPolylineFeature(coordinates: &coordinates, count: UInt(coordinates.count))
        let layerIdentifier = Self.createLayerIdentifier(polyline.identifier)
        let sourceIdentifier = Self.createSourceIdentifier(polyline.identifier)
        if let source = style.source(withIdentifier: sourceIdentifier) as? MLNShapeSource {
            source.shape = feature
        } else {
            let source = MLNShapeSource(identifier: sourceIdentifier, shape: feature, options: nil)
            style.addSource(source)
            let layer = MLNLineStyleLayer(identifier: layerIdentifier, source: source)
            layer.lineCap = NSExpression(forConstantValue: "round")
            layer.lineJoin = NSExpression(forConstantValue: "round")
            if let markerLayer = style.layer(withIdentifier: MarkerManager.layerIdentifier) {
                style.insertLayer(layer, below: markerLayer)
            } else {
                style.addLayer(layer)
            }
        }
        guard let layer = style.layer(withIdentifier: layerIdentifier) as? MLNLineStyleLayer else {
            return
        }
        layer.lineColor = NSExpression(forConstantValue: polyline.color)
        layer.lineOpacity = NSExpression(forConstantValue: polyline.opacity)
        layer.lineWidth = NSExpression(forConstantValue: polyline.width)
    }
}
