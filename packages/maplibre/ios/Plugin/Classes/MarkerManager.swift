import Foundation
import MapLibre

/// Renders the markers of a map as a single symbol layer.
public class MarkerManager {
    static let layerIdentifier = "capawesome-maplibre-markers-layer"

    private static let sourceIdentifier = "capawesome-maplibre-markers-source"

    private let iconLoader: MarkerIconLoader
    private var animationsByMarkerId = [String: MarkerAnimation]()
    private var markersById = [String: Marker]()
    private weak var mapView: MLNMapView?

    init(mapView: MLNMapView, iconLoader: MarkerIconLoader) {
        self.iconLoader = iconLoader
        self.mapView = mapView
    }

    func addMarkers(_ markers: [Marker], completion: @escaping (_ error: Error?) -> Void) {
        iconLoader.loadIcons(for: markers) { [weak self] error in
            if let error = error {
                completion(error)
                return
            }
            for marker in markers {
                self?.animationsByMarkerId.removeValue(forKey: marker.identifier)?.cancel()
                self?.markersById[marker.identifier] = marker
            }
            self?.registerIcons(of: markers)
            self?.updateSource()
            completion(nil)
        }
    }

    func destroy() {
        for animation in animationsByMarkerId.values {
            animation.cancel()
        }
        animationsByMarkerId.removeAll()
        markersById.removeAll()
    }

    func findMarker(withIdentifier identifier: String) -> Marker? {
        return markersById[identifier]
    }

    func handleStyleLoaded(_ style: MLNStyle) {
        destroy()
        let source = MLNShapeSource(identifier: Self.sourceIdentifier, shape: nil, options: nil)
        style.addSource(source)
        let layer = MLNSymbolStyleLayer(identifier: Self.layerIdentifier, source: source)
        layer.iconAllowsOverlap = NSExpression(forConstantValue: true)
        layer.iconAnchor = NSExpression(forKeyPath: "anchor")
        layer.iconIgnoresPlacement = NSExpression(forConstantValue: true)
        layer.iconImageName = NSExpression(forKeyPath: "icon")
        layer.iconOpacity = NSExpression(forKeyPath: "opacity")
        layer.iconRotation = NSExpression(forKeyPath: "rotation")
        layer.iconRotationAlignment = NSExpression(forConstantValue: "map")
        style.addLayer(layer)
    }

    func removeAllMarkers() {
        destroy()
        updateSource()
    }

    func removeMarkers(withIdentifiers identifiers: [String]) throws {
        for identifier in identifiers where markersById[identifier] == nil {
            throw CustomError.markerNotFound
        }
        for identifier in identifiers {
            animationsByMarkerId.removeValue(forKey: identifier)?.cancel()
            markersById.removeValue(forKey: identifier)
        }
        updateSource()
    }

    func updateMarker(_ options: UpdateMarkerByIdOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard let marker = markersById[options.markerId] else {
            completion(CustomError.markerNotFound)
            return
        }
        animationsByMarkerId.removeValue(forKey: marker.identifier)?.cancel()
        marker.iconAnchor = options.iconAnchor ?? marker.iconAnchor
        marker.iconSize = options.iconSize ?? marker.iconSize
        marker.iconUrl = options.iconUrl ?? marker.iconUrl
        marker.opacity = options.opacity ?? marker.opacity
        iconLoader.loadIcons(for: [marker]) { [weak self] error in
            if let error = error {
                completion(error)
                return
            }
            self?.registerIcons(of: [marker])
            self?.moveMarker(marker, options)
            completion(nil)
        }
    }

    private func moveMarker(_ marker: Marker, _ options: UpdateMarkerByIdOptions) {
        let coordinates = options.coordinates ?? marker.coordinates
        let rotation = options.rotation ?? marker.rotation
        guard options.animate else {
            marker.coordinates = coordinates
            marker.rotation = rotation
            updateSource()
            return
        }
        let animation = MarkerAnimation(
            startCoordinates: marker.coordinates,
            endCoordinates: coordinates,
            startRotation: marker.rotation,
            endRotation: rotation,
            duration: options.animationDuration / 1000,
            onUpdate: { [weak self] coordinates, rotation in
                marker.coordinates = coordinates
                marker.rotation = rotation
                self?.updateSource()
            },
            onFinish: { [weak self] in
                self?.animationsByMarkerId.removeValue(forKey: marker.identifier)
            }
        )
        animationsByMarkerId[marker.identifier] = animation
        animation.start()
    }

    private func registerIcons(of markers: [Marker]) {
        guard let style = mapView?.style else {
            return
        }
        for marker in markers {
            if let image = iconLoader.image(forName: marker.iconName) {
                style.setImage(image, forName: marker.iconName)
            }
        }
    }

    private func updateSource() {
        guard let source = mapView?.style?.source(withIdentifier: Self.sourceIdentifier) as? MLNShapeSource else {
            return
        }
        let features = markersById.values.map { marker -> MLNPointFeature in
            let feature = MLNPointFeature()
            feature.coordinate = marker.coordinates.toCoordinate()
            feature.identifier = marker.identifier
            feature.attributes = [
                "anchor": marker.iconAnchor.rawValue,
                "icon": marker.iconName,
                "id": marker.identifier,
                "opacity": marker.opacity,
                "rotation": marker.rotation
            ]
            return feature
        }
        source.shape = MLNShapeCollectionFeature(shapes: features)
    }
}
