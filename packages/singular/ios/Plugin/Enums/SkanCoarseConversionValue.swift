import Foundation

public enum SkanCoarseConversionValue: String {
    case high = "HIGH"
    case low = "LOW"
    case medium = "MEDIUM"

    public init?(skanValue: Int) {
        switch skanValue {
        case 0:
            self = .low
        case 1:
            self = .medium
        case 2:
            self = .high
        default:
            return nil
        }
    }

    public var skanValue: Int {
        switch self {
        case .high:
            return 2
        case .low:
            return 0
        case .medium:
            return 1
        }
    }
}
