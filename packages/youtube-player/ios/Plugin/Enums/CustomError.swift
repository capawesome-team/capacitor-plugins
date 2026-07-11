import Foundation

public enum CustomError: Error {
    case createFailed
    case frameInvalid
    case frameMissing
    case frameTooSmall
    case playerAlreadyExists
    case playerIdInvalid
    case playerIdMissing
    case rateInvalid
    case secondsMissing
    case videoIdMissing
    case volumeInvalid

    public var code: String? {
        switch self {
        case .createFailed:
            return "CREATE_FAILED"
        case .frameInvalid, .frameTooSmall:
            return "FRAME_INVALID"
        case .frameMissing:
            return "FRAME_MISSING"
        case .playerAlreadyExists:
            return nil
        case .playerIdInvalid:
            return "PLAYER_ID_INVALID"
        case .playerIdMissing:
            return "PLAYER_ID_MISSING"
        case .rateInvalid:
            return "RATE_INVALID"
        case .secondsMissing:
            return "SECONDS_MISSING"
        case .videoIdMissing:
            return "VIDEO_ID_MISSING"
        case .volumeInvalid:
            return "VOLUME_INVALID"
        }
    }
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .createFailed:
            return NSLocalizedString("The player could not be created.", comment: "createFailed")
        case .frameInvalid:
            return NSLocalizedString("frame must contain numeric x, y, width and height values.", comment: "frameInvalid")
        case .frameMissing:
            return NSLocalizedString("frame must be provided.", comment: "frameMissing")
        case .frameTooSmall:
            return NSLocalizedString("frame must be at least 200×200 pixels.", comment: "frameTooSmall")
        case .playerAlreadyExists:
            return NSLocalizedString("a player with the provided id already exists.", comment: "playerAlreadyExists")
        case .playerIdInvalid:
            return NSLocalizedString("no player found with the provided id.", comment: "playerIdInvalid")
        case .playerIdMissing:
            return NSLocalizedString("id must be provided.", comment: "playerIdMissing")
        case .rateInvalid:
            return NSLocalizedString("rate must be one of 0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75 or 2.", comment: "rateInvalid")
        case .secondsMissing:
            return NSLocalizedString("seconds must be provided.", comment: "secondsMissing")
        case .videoIdMissing:
            return NSLocalizedString("videoId must be provided.", comment: "videoIdMissing")
        case .volumeInvalid:
            return NSLocalizedString("volume must be a number between 0 and 100.", comment: "volumeInvalid")
        }
    }
}
