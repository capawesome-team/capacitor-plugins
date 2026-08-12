import Foundation
import Capacitor
import AlarmKit

@available(iOS 26.0, *)
@objc public class GetAlarmsResult: NSObject, Result {
    let alarms: [AlarmKit.Alarm]
    let labels: [String: String]

    init(alarms: [AlarmKit.Alarm], labels: [String: String]) {
        self.alarms = alarms
        self.labels = labels
    }

    @objc public func toJSObject() -> AnyObject {
        var alarmsResult = JSArray()
        for alarm in alarms {
            let label = labels[alarm.id.uuidString]
            let time = GetAlarmsResult.getTimeFromAlarm(alarm)
            var alarmResult = JSObject()
            alarmResult["enabled"] = alarm.state == .scheduled || alarm.state == .alerting
            alarmResult["hour"] = time == nil ? NSNull() : time?.hour
            alarmResult["id"] = alarm.id.uuidString
            alarmResult["label"] = label == nil ? NSNull() : label
            alarmResult["minute"] = time == nil ? NSNull() : time?.minute
            alarmsResult.append(alarmResult)
        }
        var result = JSObject()
        result["alarms"] = alarmsResult
        return result as AnyObject
    }

    private static func getTimeFromAlarm(_ alarm: AlarmKit.Alarm) -> (hour: Int, minute: Int)? {
        switch alarm.schedule {
        case .fixed(let date):
            let components = Calendar.current.dateComponents([.hour, .minute], from: date)
            guard let hour = components.hour, let minute = components.minute else {
                return nil
            }
            return (hour, minute)
        case .relative(let relative):
            return (relative.time.hour, relative.time.minute)
        case .none:
            return nil
        @unknown default:
            return nil
        }
    }
}
