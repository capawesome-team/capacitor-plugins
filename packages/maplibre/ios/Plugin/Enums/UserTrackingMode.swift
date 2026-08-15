import Foundation
import MapLibre

public enum UserTrackingMode: String {
    case follow
    case followWithCourse
    case followWithHeading
    case none

    var toMapLibreUserTrackingMode: MLNUserTrackingMode {
        switch self {
        case .follow:
            return .follow
        case .followWithCourse:
            return .followWithCourse
        case .followWithHeading:
            return .followWithHeading
        case .none:
            return .none
        }
    }
}
