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
        layer.predicate = options.filter
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
        layer.circleBlur = options.paint?.circleBlur
        layer.circleColor = options.paint?.circleColor
        layer.circleOpacity = options.paint?.circleOpacity
        layer.circleRadius = options.paint?.circleRadius
        layer.circleStrokeColor = options.paint?.circleStrokeColor
        layer.circleStrokeWidth = options.paint?.circleStrokeWidth
        return layer
    }

    private static func createFillLayer(_ options: AddLayerOptions, _ source: MLNSource) -> MLNFillStyleLayer {
        let layer = MLNFillStyleLayer(identifier: options.layerId, source: source)
        layer.fillColor = options.paint?.fillColor
        layer.fillOpacity = options.paint?.fillOpacity
        layer.fillOutlineColor = options.paint?.fillOutlineColor
        return layer
    }

    private static func createHeatmapLayer(_ options: AddLayerOptions, _ source: MLNSource) -> MLNHeatmapStyleLayer {
        let layer = MLNHeatmapStyleLayer(identifier: options.layerId, source: source)
        layer.heatmapColor = options.paint?.heatmapColor
        layer.heatmapIntensity = options.paint?.heatmapIntensity
        layer.heatmapOpacity = options.paint?.heatmapOpacity
        layer.heatmapRadius = options.paint?.heatmapRadius
        layer.heatmapWeight = options.paint?.heatmapWeight
        return layer
    }

    private static func createLayer(_ options: AddLayerOptions, _ source: MLNSource) -> MLNVectorStyleLayer {
        switch options.type {
        case .circle:
            return Self.createCircleLayer(options, source)
        case .fill:
            return Self.createFillLayer(options, source)
        case .heatmap:
            return Self.createHeatmapLayer(options, source)
        case .line:
            return Self.createLineLayer(options, source)
        }
    }

    private static func createLineLayer(_ options: AddLayerOptions, _ source: MLNSource) -> MLNLineStyleLayer {
        let layer = MLNLineStyleLayer(identifier: options.layerId, source: source)
        layer.lineCap = NSExpression(forConstantValue: "round")
        layer.lineColor = options.paint?.lineColor
        layer.lineJoin = NSExpression(forConstantValue: "round")
        layer.lineOpacity = options.paint?.lineOpacity
        layer.lineWidth = options.paint?.lineWidth
        return layer
    }

    private func getStyle() throws -> MLNStyle {
        guard let style = mapView?.style else {
            throw CustomError.styleNotLoaded
        }
        return style
    }
}
