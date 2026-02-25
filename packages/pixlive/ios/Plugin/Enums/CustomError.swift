import Foundation

enum CustomError: Error {
    case arViewAlreadyExists
    case arViewNotFound
    case contextIdMissing
    case contextNotFound
    case enabledMissing
    case heightMissing
    case languageMissing
    case latitudeMissing
    case longitudeMissing
    case maxLatitudeMissing
    case maxLongitudeMissing
    case minLatitudeMissing
    case minLongitudeMissing
    case notInitialized
    case tagsMissing
    case topMissing
    case bottomMissing
    case leftMissing
    case rightMissing
    case tourIdsMissing
    case contextIdsMissing
    case widthMissing
    case xMissing
    case yMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .arViewAlreadyExists:
            return NSLocalizedString("AR view already exists.", comment: "arViewAlreadyExists")
        case .arViewNotFound:
            return NSLocalizedString("AR view not found.", comment: "arViewNotFound")
        case .contextIdMissing:
            return NSLocalizedString("contextId must be provided.", comment: "contextIdMissing")
        case .contextNotFound:
            return NSLocalizedString("Context not found.", comment: "contextNotFound")
        case .enabledMissing:
            return NSLocalizedString("enabled must be provided.", comment: "enabledMissing")
        case .heightMissing:
            return NSLocalizedString("height must be provided.", comment: "heightMissing")
        case .languageMissing:
            return NSLocalizedString("language must be provided.", comment: "languageMissing")
        case .latitudeMissing:
            return NSLocalizedString("latitude must be provided.", comment: "latitudeMissing")
        case .longitudeMissing:
            return NSLocalizedString("longitude must be provided.", comment: "longitudeMissing")
        case .maxLatitudeMissing:
            return NSLocalizedString("maxLatitude must be provided.", comment: "maxLatitudeMissing")
        case .maxLongitudeMissing:
            return NSLocalizedString("maxLongitude must be provided.", comment: "maxLongitudeMissing")
        case .minLatitudeMissing:
            return NSLocalizedString("minLatitude must be provided.", comment: "minLatitudeMissing")
        case .minLongitudeMissing:
            return NSLocalizedString("minLongitude must be provided.", comment: "minLongitudeMissing")
        case .notInitialized:
            return NSLocalizedString("Plugin is not initialized.", comment: "notInitialized")
        case .tagsMissing:
            return NSLocalizedString("tags must be provided.", comment: "tagsMissing")
        case .topMissing:
            return NSLocalizedString("top must be provided.", comment: "topMissing")
        case .bottomMissing:
            return NSLocalizedString("bottom must be provided.", comment: "bottomMissing")
        case .leftMissing:
            return NSLocalizedString("left must be provided.", comment: "leftMissing")
        case .rightMissing:
            return NSLocalizedString("right must be provided.", comment: "rightMissing")
        case .tourIdsMissing:
            return NSLocalizedString("tourIds must be provided.", comment: "tourIdsMissing")
        case .contextIdsMissing:
            return NSLocalizedString("contextIds must be provided.", comment: "contextIdsMissing")
        case .widthMissing:
            return NSLocalizedString("width must be provided.", comment: "widthMissing")
        case .xMissing:
            return NSLocalizedString("x must be provided.", comment: "xMissing")
        case .yMissing:
            return NSLocalizedString("y must be provided.", comment: "yMissing")
        }
    }
}
