import Foundation
import MapLibre

/// Manages the GeoJSON sources of a map and the layers that render them.
public class GeoJsonManager {
    private weak var mapView: MLNMapView?

    init(mapView: MLNMapView) {
        self.mapView = mapView
    }

    func addLayer(_ options: AddLayerOptions) throws {
        let style = try getStyle()
        guard style.layer(withIdentifier: options.layerId) == nil else {
            throw CustomError.layerAlreadyExists
        }
        guard let source = style.source(withIdentifier: options.sourceId) else {
            throw CustomError.sourceNotFound
        }
        let layer = Self.createLayer(options, source)
        if let maxZoom = options.maxZoom {
            layer.maximumZoomLevel = Float(maxZoom)
        }
        if let minZoom = options.minZoom {
            layer.minimumZoomLevel = Float(minZoom)
        }
        guard let belowLayerId = options.belowLayerId else {
            style.addLayer(layer)
            return
        }
        guard let sibling = style.layer(withIdentifier: belowLayerId) else {
            throw CustomError.layerNotFound
        }
        style.insertLayer(layer, below: sibling)
    }

    func addSource(_ options: AddGeoJsonSourceOptions) throws {
        let style = try getStyle()
        guard style.source(withIdentifier: options.sourceId) == nil else {
            throw CustomError.sourceAlreadyExists
        }
        if let url = options.url {
            style.addSource(MLNShapeSource(identifier: options.sourceId, url: url, options: nil))
        } else {
            style.addSource(MLNShapeSource(identifier: options.sourceId, shape: options.shape, options: nil))
        }
    }

    func removeLayer(withIdentifier identifier: String) throws {
        let style = try getStyle()
        guard let layer = style.layer(withIdentifier: identifier) else {
            throw CustomError.layerNotFound
        }
        style.removeLayer(layer)
    }

    func removeSource(withIdentifier identifier: String) throws {
        let style = try getStyle()
        guard let source = style.source(withIdentifier: identifier) else {
            throw CustomError.sourceNotFound
        }
        style.removeSource(source)
    }

    func updateSource(_ options: UpdateGeoJsonSourceByIdOptions) throws {
        let style = try getStyle()
        guard let source = style.source(withIdentifier: options.sourceId) as? MLNShapeSource else {
            throw CustomError.sourceNotFound
        }
        if let url = options.url {
            source.url = url
        } else {
            source.shape = options.shape
        }
    }

    private static func createCircleLayer(_ options: AddLayerOptions, _ source: MLNSource) -> MLNCircleStyleLayer {
        let layer = MLNCircleStyleLayer(identifier: options.layerId, source: source)
        layer.circleColor = Self.createExpression(options.paint?.circleColor)
        layer.circleOpacity = Self.createExpression(options.paint?.circleOpacity)
        layer.circleRadius = Self.createExpression(options.paint?.circleRadius)
        layer.circleStrokeColor = Self.createExpression(options.paint?.circleStrokeColor)
        layer.circleStrokeWidth = Self.createExpression(options.paint?.circleStrokeWidth)
        return layer
    }

    private static func createExpression(_ value: Any?) -> NSExpression? {
        guard let value = value else {
            return nil
        }
        return NSExpression(forConstantValue: value)
    }

    private static func createFillLayer(_ options: AddLayerOptions, _ source: MLNSource) -> MLNFillStyleLayer {
        let layer = MLNFillStyleLayer(identifier: options.layerId, source: source)
        layer.fillColor = Self.createExpression(options.paint?.fillColor)
        layer.fillOpacity = Self.createExpression(options.paint?.fillOpacity)
        layer.fillOutlineColor = Self.createExpression(options.paint?.fillOutlineColor)
        return layer
    }

    private static func createLayer(_ options: AddLayerOptions, _ source: MLNSource) -> MLNStyleLayer {
        switch options.type {
        case .circle:
            return Self.createCircleLayer(options, source)
        case .fill:
            return Self.createFillLayer(options, source)
        case .line:
            return Self.createLineLayer(options, source)
        }
    }

    private static func createLineLayer(_ options: AddLayerOptions, _ source: MLNSource) -> MLNLineStyleLayer {
        let layer = MLNLineStyleLayer(identifier: options.layerId, source: source)
        layer.lineCap = NSExpression(forConstantValue: "round")
        layer.lineColor = Self.createExpression(options.paint?.lineColor)
        layer.lineJoin = NSExpression(forConstantValue: "round")
        layer.lineOpacity = Self.createExpression(options.paint?.lineOpacity)
        layer.lineWidth = Self.createExpression(options.paint?.lineWidth)
        return layer
    }

    private func getStyle() throws -> MLNStyle {
        guard let style = mapView?.style else {
            throw CustomError.styleNotLoaded
        }
        return style
    }
}
