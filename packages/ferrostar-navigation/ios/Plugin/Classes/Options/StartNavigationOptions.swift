import Capacitor
import FerrostarCoreFFI
import Foundation

@objc public class StartNavigationOptions: NSObject {
    let arrivalStepAdvanceDistanceMeters: UInt16
    let continueInBackground: Bool
    let courseFiltering: CourseFiltering
    let muteVoice: Bool
    let osrmResponseJson: String
    let polylinePrecision: UInt32
    let routeDeviationThresholdMeters: Double
    let simulateLocation: Bool
    let simulationSpeedMultiplier: UInt64
    let speakInstructions: Bool
    let stepAdvanceEntryDistanceMeters: UInt16
    let stepAdvanceExitDistanceMeters: UInt16
    let voiceLanguage: String?
    let waypointAdvanceDistanceMeters: Double

    init(_ call: CAPPluginCall) throws {
        self.arrivalStepAdvanceDistanceMeters = UInt16(clamping: call.getInt("arrivalStepAdvanceDistanceMeters", 10))
        self.continueInBackground = call.getBool("continueInBackground", false)
        self.courseFiltering = try StartNavigationOptions.getCourseFilteringFromCall(call)
        self.muteVoice = call.getBool("muteVoice", false)
        self.osrmResponseJson = try ReplaceRouteOptions.getOsrmResponseJsonFromCall(call)
        self.polylinePrecision = ReplaceRouteOptions.getPolylinePrecisionFromCall(call)
        self.routeDeviationThresholdMeters = call.getDouble("routeDeviationThresholdMeters", 50)
        self.simulateLocation = call.getBool("simulateLocation", false)
        self.simulationSpeedMultiplier = UInt64(max(1, call.getInt("simulationSpeedMultiplier", 1)))
        self.speakInstructions = call.getBool("speakInstructions", true)
        self.stepAdvanceEntryDistanceMeters = UInt16(clamping: call.getInt("stepAdvanceEntryDistanceMeters", 30))
        self.stepAdvanceExitDistanceMeters = UInt16(clamping: call.getInt("stepAdvanceExitDistanceMeters", 5))
        self.voiceLanguage = call.getString("voiceLanguage")
        self.waypointAdvanceDistanceMeters = call.getDouble("waypointAdvanceDistanceMeters", 100)
    }

    private static func getCourseFilteringFromCall(_ call: CAPPluginCall) throws -> CourseFiltering {
        switch call.getString("courseFiltering", "SNAP_TO_ROUTE") {
        case "RAW":
            return .raw
        case "SNAP_TO_ROUTE":
            return .snapToRoute
        default:
            throw CustomError.courseFilteringInvalid
        }
    }
}
