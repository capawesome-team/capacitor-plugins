import Capacitor
import FerrostarCoreFFI
import Foundation

class FerrostarNavigationHelper {
    static func createLocationJSObject(_ location: UserLocation) -> JSObject {
        var result = JSObject()
        result["latitude"] = location.coordinates.lat
        result["longitude"] = location.coordinates.lng
        result["courseDegrees"] = location.courseOverGround.map { Int($0.degrees) } ?? NSNull()
        result["horizontalAccuracyMeters"] = location.horizontalAccuracy
        return result
    }

    static func getManeuverModifierValue(_ modifier: ManeuverModifier) -> String {
        switch modifier {
        case .uTurn:
            return "U_TURN"
        case .sharpRight:
            return "SHARP_RIGHT"
        case .right:
            return "RIGHT"
        case .slightRight:
            return "SLIGHT_RIGHT"
        case .straight:
            return "STRAIGHT"
        case .slightLeft:
            return "SLIGHT_LEFT"
        case .left:
            return "LEFT"
        case .sharpLeft:
            return "SHARP_LEFT"
        }
    }

    // swiftlint:disable:next cyclomatic_complexity
    static func getManeuverTypeValue(_ type: ManeuverType) -> String {
        switch type {
        case .turn:
            return "TURN"
        case .newName:
            return "NEW_NAME"
        case .depart:
            return "DEPART"
        case .arrive:
            return "ARRIVE"
        case .merge:
            return "MERGE"
        case .onRamp:
            return "ON_RAMP"
        case .offRamp:
            return "OFF_RAMP"
        case .fork:
            return "FORK"
        case .endOfRoad:
            return "END_OF_ROAD"
        case .continue:
            return "CONTINUE"
        case .roundabout:
            return "ROUNDABOUT"
        case .rotary:
            return "ROTARY"
        case .roundaboutTurn:
            return "ROUNDABOUT_TURN"
        case .notification:
            return "NOTIFICATION"
        case .exitRoundabout:
            return "EXIT_ROUNDABOUT"
        case .exitRotary:
            return "EXIT_ROTARY"
        }
    }

    static func getOffRouteDeviationMeters(_ tripState: TripState?) -> Double? {
        guard case let .navigating(_, _, _, _, _, _, _, deviation, _, _, _) = tripState,
              case let .deviation(kind: .completelyOffRoute(deviationFromRouteLine)) = deviation else {
            return nil
        }
        return deviationFromRouteLine
    }
}
