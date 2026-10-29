import Foundation
import Capacitor
import CoreHaptics

@objc public class PlayPatternOptions: NSObject {
    struct Event {
        let duration: Double?
        let intensity: Float
        let sharpness: Float
        let time: Double
    }

    struct ParameterCurve {
        let controlPoints: [CHHapticParameterCurve.ControlPoint]
        let parameter: CHHapticDynamicParameter.ID
    }

    let events: [Event]
    let parameterCurves: [ParameterCurve]

    init(_ call: CAPPluginCall) throws {
        self.events = try PlayPatternOptions.getEventsFromCall(call)
        self.parameterCurves = try PlayPatternOptions.getParameterCurvesFromCall(call)
    }

    private static func getEventsFromCall(_ call: CAPPluginCall) throws -> [Event] {
        guard let eventObjects = call.getArray("events", JSObject.self), !eventObjects.isEmpty else {
            throw CustomError.eventsMissing
        }
        var events: [Event] = []
        for eventObject in eventObjects {
            guard let time = (eventObject["time"] as? NSNumber)?.doubleValue else {
                throw CustomError.timeMissing
            }
            guard time >= 0 else {
                throw CustomError.timeInvalid
            }
            guard let intensity = (eventObject["intensity"] as? NSNumber)?.floatValue else {
                throw CustomError.intensityMissing
            }
            guard intensity >= 0, intensity <= 1 else {
                throw CustomError.intensityInvalid
            }
            var sharpness: Float = 0.5
            if let value = (eventObject["sharpness"] as? NSNumber)?.floatValue {
                guard value >= 0, value <= 1 else {
                    throw CustomError.sharpnessInvalid
                }
                sharpness = value
            }
            var duration: Double?
            if let value = (eventObject["duration"] as? NSNumber)?.doubleValue {
                guard value > 0 else {
                    throw CustomError.durationInvalid
                }
                duration = value
            }
            events.append(Event(duration: duration, intensity: intensity, sharpness: sharpness, time: time))
        }
        return events
    }

    private static func getParameterCurvesFromCall(_ call: CAPPluginCall) throws -> [ParameterCurve] {
        guard let curveObjects = call.getArray("parameterCurves", JSObject.self) else {
            return []
        }
        return try curveObjects.map(getParameterCurve)
    }

    private static func getParameterCurve(_ curveObject: JSObject) throws -> ParameterCurve {
        let parameter: CHHapticDynamicParameter.ID
        let valueRange: ClosedRange<Float>
        switch curveObject["parameter"] as? String {
        case "INTENSITY":
            parameter = .hapticIntensityControl
            valueRange = 0...1
        case "SHARPNESS":
            parameter = .hapticSharpnessControl
            valueRange = -1...1
        default:
            throw CustomError.parameterInvalid
        }
        guard let pointObjects = curveObject["controlPoints"] as? [JSObject], !pointObjects.isEmpty else {
            throw CustomError.controlPointsMissing
        }
        let controlPoints = try pointObjects
            .map { try getControlPoint($0, valueRange: valueRange) }
            .sorted { $0.relativeTime < $1.relativeTime }
        return ParameterCurve(controlPoints: controlPoints, parameter: parameter)
    }

    private static func getControlPoint(_ pointObject: JSObject, valueRange: ClosedRange<Float>) throws -> CHHapticParameterCurve.ControlPoint {
        guard let time = (pointObject["time"] as? NSNumber)?.doubleValue else {
            throw CustomError.timeMissing
        }
        guard time >= 0 else {
            throw CustomError.timeInvalid
        }
        guard let value = (pointObject["value"] as? NSNumber)?.floatValue else {
            throw CustomError.valueMissing
        }
        guard valueRange.contains(value) else {
            throw CustomError.valueInvalid
        }
        return CHHapticParameterCurve.ControlPoint(relativeTime: time, value: value)
    }
}
