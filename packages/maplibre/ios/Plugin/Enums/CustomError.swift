import Foundation

public enum CustomError: Error {
    case boundsMissing
    case contentSizeMissing
    case frameMissing
    case iconLoadFailed
    case layerAlreadyExists
    case layerIdMissing
    case layerNotFound
    case layerTypeInvalid
    case locationPermissionDenied
    case mapAlreadyExists
    case mapContainerNotFound
    case mapIdMissing
    case mapNotFound
    case markerIdMissing
    case markerIdsMissing
    case markerMissing
    case markerNotFound
    case markersMissing
    case polylineIdMissing
    case polylineIdsMissing
    case polylineMissing
    case polylineNotFound
    case polylinesMissing
    case privacyDescriptionsMissing
    case sourceAlreadyExists
    case sourceDataMissing
    case sourceIdMissing
    case sourceNotFound
    case styleLoadFailed
    case styleMissing
    case styleNotLoaded
    case webViewMissing

    public var code: String? {
        switch self {
        case .layerNotFound:
            return "LAYER_NOT_FOUND"
        case .locationPermissionDenied:
            return "LOCATION_PERMISSION_DENIED"
        case .mapNotFound:
            return "MAP_NOT_FOUND"
        case .markerNotFound:
            return "MARKER_NOT_FOUND"
        case .polylineNotFound:
            return "POLYLINE_NOT_FOUND"
        case .sourceNotFound:
            return "SOURCE_NOT_FOUND"
        case .styleLoadFailed:
            return "STYLE_LOAD_FAILED"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .boundsMissing:
            return NSLocalizedString("bounds must be provided.", comment: "boundsMissing")
        case .contentSizeMissing:
            return NSLocalizedString("contentSize must be provided.", comment: "contentSizeMissing")
        case .frameMissing:
            return NSLocalizedString("frame must be provided.", comment: "frameMissing")
        case .iconLoadFailed:
            return NSLocalizedString("the icon of the marker could not be loaded.", comment: "iconLoadFailed")
        case .layerAlreadyExists:
            return NSLocalizedString("a layer with the provided layerId already exists.", comment: "layerAlreadyExists")
        case .layerIdMissing:
            return NSLocalizedString("layerId must be provided.", comment: "layerIdMissing")
        case .layerNotFound:
            return NSLocalizedString("layer not found.", comment: "layerNotFound")
        case .layerTypeInvalid:
            return NSLocalizedString("type must be one of circle, fill or line.", comment: "layerTypeInvalid")
        case .locationPermissionDenied:
            return NSLocalizedString("location permission denied.", comment: "locationPermissionDenied")
        case .mapAlreadyExists:
            return NSLocalizedString("a map with the provided mapId already exists.", comment: "mapAlreadyExists")
        case .mapContainerNotFound:
            return NSLocalizedString("the element of the map could not be found in the web view.", comment: "mapContainerNotFound")
        case .mapIdMissing:
            return NSLocalizedString("mapId must be provided.", comment: "mapIdMissing")
        case .mapNotFound:
            return NSLocalizedString("map not found.", comment: "mapNotFound")
        case .markerIdMissing:
            return NSLocalizedString("markerId must be provided.", comment: "markerIdMissing")
        case .markerIdsMissing:
            return NSLocalizedString("markerIds must be provided.", comment: "markerIdsMissing")
        case .markerMissing:
            return NSLocalizedString("marker must be provided.", comment: "markerMissing")
        case .markerNotFound:
            return NSLocalizedString("marker not found.", comment: "markerNotFound")
        case .markersMissing:
            return NSLocalizedString("markers must be provided.", comment: "markersMissing")
        case .polylineIdMissing:
            return NSLocalizedString("polylineId must be provided.", comment: "polylineIdMissing")
        case .polylineIdsMissing:
            return NSLocalizedString("polylineIds must be provided.", comment: "polylineIdsMissing")
        case .polylineMissing:
            return NSLocalizedString("polyline must be provided.", comment: "polylineMissing")
        case .polylineNotFound:
            return NSLocalizedString("polyline not found.", comment: "polylineNotFound")
        case .polylinesMissing:
            return NSLocalizedString("polylines must be provided.", comment: "polylinesMissing")
        case .privacyDescriptionsMissing:
            return NSLocalizedString(
                "the NSLocationWhenInUseUsageDescription key must be defined in the Info.plist file.",
                comment: "privacyDescriptionsMissing"
            )
        case .sourceAlreadyExists:
            return NSLocalizedString("a source with the provided sourceId already exists.", comment: "sourceAlreadyExists")
        case .sourceDataMissing:
            return NSLocalizedString("exactly one of data and url must be provided.", comment: "sourceDataMissing")
        case .sourceIdMissing:
            return NSLocalizedString("sourceId must be provided.", comment: "sourceIdMissing")
        case .sourceNotFound:
            return NSLocalizedString("source not found.", comment: "sourceNotFound")
        case .styleLoadFailed:
            return NSLocalizedString("style load failed.", comment: "styleLoadFailed")
        case .styleMissing:
            return NSLocalizedString("exactly one of json and url must be provided.", comment: "styleMissing")
        case .styleNotLoaded:
            return NSLocalizedString("the style of the map is not loaded yet.", comment: "styleNotLoaded")
        case .webViewMissing:
            return NSLocalizedString("the web view could not be found.", comment: "webViewMissing")
        }
    }
}
