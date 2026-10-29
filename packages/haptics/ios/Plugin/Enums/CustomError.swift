import Foundation

enum CustomError: Error {
    case controlPointsMissing
    case durationInvalid
    case eventsMissing
    case intensityInvalid
    case intensityMissing
    case parameterInvalid
    case playbackFailed
    case sharpnessInvalid
    case styleInvalid
    case timeInvalid
    case timeMissing
    case typeInvalid
    case valueInvalid
    case valueMissing

    var code: String? {
        switch self {
        case .playbackFailed:
            return "PLAYBACK_FAILED"
        default:
            return nil
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .controlPointsMissing:
            return NSLocalizedString("controlPoints must be provided.", comment: "controlPointsMissing")
        case .durationInvalid:
            return NSLocalizedString("duration must be greater than 0.", comment: "durationInvalid")
        case .eventsMissing:
            return NSLocalizedString("events must be provided.", comment: "eventsMissing")
        case .intensityInvalid:
            return NSLocalizedString("intensity must be between 0 and 1.", comment: "intensityInvalid")
        case .intensityMissing:
            return NSLocalizedString("intensity must be provided.", comment: "intensityMissing")
        case .parameterInvalid:
            return NSLocalizedString("parameter must be one of the supported values.", comment: "parameterInvalid")
        case .playbackFailed:
            return NSLocalizedString("The haptic pattern could not be played.", comment: "playbackFailed")
        case .sharpnessInvalid:
            return NSLocalizedString("sharpness must be between 0 and 1.", comment: "sharpnessInvalid")
        case .styleInvalid:
            return NSLocalizedString("style must be one of the supported values.", comment: "styleInvalid")
        case .timeInvalid:
            return NSLocalizedString("time must be greater than or equal to 0.", comment: "timeInvalid")
        case .timeMissing:
            return NSLocalizedString("time must be provided.", comment: "timeMissing")
        case .typeInvalid:
            return NSLocalizedString("type must be one of the supported values.", comment: "typeInvalid")
        case .valueInvalid:
            return NSLocalizedString("value must be between 0 and 1 for INTENSITY and between -1 and 1 for SHARPNESS.", comment: "valueInvalid")
        case .valueMissing:
            return NSLocalizedString("value must be provided.", comment: "valueMissing")
        }
    }
}
