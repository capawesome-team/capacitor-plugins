import Foundation
import Capacitor

@objc public class PlayPatternOptions: NSObject {
    struct Event {
        let duration: Double?
        let intensity: Float
        let sharpness: Float
        let time: Double
    }

    let events: [Event]

    init(_ call: CAPPluginCall) throws {
        self.events = try PlayPatternOptions.getEventsFromCall(call)
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
}
