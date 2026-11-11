import Capacitor
import FerrostarCoreFFI
import Foundation

@objc public class NavigationStateChangedEvent: NSObject, Result {
    let tripState: TripState?

    init(tripState: TripState?) {
        self.tripState = tripState
    }

    var isOffRoute: Bool {
        return FerrostarNavigationHelper.getOffRouteDeviationMeters(tripState) != nil
    }

    var status: NavigationStatus {
        switch tripState {
        case .navigating:
            return .navigating
        case .complete:
            return .complete
        default:
            return .idle
        }
    }

    @objc public func toJSObject() -> AnyObject {
        var result: JSObject = [
            "status": status.rawValue,
            "instruction": NSNull(),
            "distanceToNextManeuverMeters": NSNull(),
            "distanceRemainingMeters": NSNull(),
            "durationRemainingSeconds": NSNull(),
            "distanceTraveledMeters": NSNull(),
            "location": NSNull(),
            "isOffRoute": isOffRoute,
            "spokenInstructionText": NSNull()
        ]
        switch tripState {
        case let .navigating(_, _, snappedUserLocation, _, _, progress, summary, _, visualInstruction, spokenInstruction, _):
            if let visualInstruction {
                result["instruction"] = createInstructionJSObject(visualInstruction)
            }
            result["distanceToNextManeuverMeters"] = progress.distanceToNextManeuver
            result["distanceRemainingMeters"] = progress.distanceRemaining
            result["durationRemainingSeconds"] = progress.durationRemaining
            result["distanceTraveledMeters"] = summary.snappedDistanceTraveled
            result["location"] = FerrostarNavigationHelper.createLocationJSObject(snappedUserLocation)
            if let spokenInstruction {
                result["spokenInstructionText"] = spokenInstruction.text
            }
        case let .complete(userLocation, summary):
            result["distanceTraveledMeters"] = summary.snappedDistanceTraveled
            result["location"] = FerrostarNavigationHelper.createLocationJSObject(userLocation)
        case let .idle(userLocation):
            if let userLocation {
                result["location"] = FerrostarNavigationHelper.createLocationJSObject(userLocation)
            }
        case nil:
            break
        }
        return result as AnyObject
    }

    private func createInstructionJSObject(_ instruction: VisualInstruction) -> JSObject {
        let content = instruction.primaryContent
        var result = JSObject()
        result["text"] = content.text
        result["secondaryText"] = instruction.secondaryContent?.text ?? NSNull()
        result["maneuverType"] = content.maneuverType.map(FerrostarNavigationHelper.getManeuverTypeValue) ?? NSNull()
        result["maneuverModifier"] = content.maneuverModifier.map(FerrostarNavigationHelper.getManeuverModifierValue) ?? NSNull()
        return result
    }
}
