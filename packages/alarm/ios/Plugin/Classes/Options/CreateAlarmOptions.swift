import Foundation
import Capacitor

@available(iOS 26.0, *)
@objc public class CreateAlarmOptions: NSObject {
    let days: [Locale.Weekday]?
    let hour: Int
    let label: String?
    let minute: Int

    init(_ call: CAPPluginCall) throws {
        self.days = try CreateAlarmOptions.getDaysFromCall(call)
        self.hour = try CreateAlarmOptions.getHourFromCall(call)
        self.label = call.getString("label")
        self.minute = try CreateAlarmOptions.getMinuteFromCall(call)
    }

    private static func getDaysFromCall(_ call: CAPPluginCall) throws -> [Locale.Weekday]? {
        guard let days = call.getArray("days") else {
            return nil
        }
        return try days.map { day in
            guard let weekday = day as? String else {
                throw CustomError.daysInvalid
            }
            return try CreateAlarmOptions.getWeekdayFromString(weekday)
        }
    }

    private static func getHourFromCall(_ call: CAPPluginCall) throws -> Int {
        guard let hour = call.getInt("hour") else {
            throw CustomError.hourMissing
        }
        guard (0...23).contains(hour) else {
            throw CustomError.hourInvalid
        }
        return hour
    }

    private static func getMinuteFromCall(_ call: CAPPluginCall) throws -> Int {
        guard let minute = call.getInt("minute") else {
            throw CustomError.minuteMissing
        }
        guard (0...59).contains(minute) else {
            throw CustomError.minuteInvalid
        }
        return minute
    }

    private static func getWeekdayFromString(_ weekday: String) throws -> Locale.Weekday {
        switch weekday {
        case "FRIDAY":
            return .friday
        case "MONDAY":
            return .monday
        case "SATURDAY":
            return .saturday
        case "SUNDAY":
            return .sunday
        case "THURSDAY":
            return .thursday
        case "TUESDAY":
            return .tuesday
        case "WEDNESDAY":
            return .wednesday
        default:
            throw CustomError.daysInvalid
        }
    }
}
