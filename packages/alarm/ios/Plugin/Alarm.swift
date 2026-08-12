import Foundation
import Capacitor
import AlarmKit
import SwiftUI

@available(iOS 26.0, *)
@objc public class Alarm: NSObject {
    private struct EmptyMetadata: AlarmMetadata {}

    private let labelsUserDefaultsKey = "capawesome_capacitor_alarm_labels"
    private let manager = AlarmKit.AlarmManager.shared
    private let plugin: AlarmPlugin

    init(plugin: AlarmPlugin) {
        self.plugin = plugin
    }

    @objc public func cancelAlarm(_ options: CancelAlarmOptions, completion: @escaping (Error?) -> Void) {
        do {
            try manager.cancel(id: options.id)
            removeLabel(for: options.id)
            completion(nil)
        } catch {
            completion(error)
        }
    }

    @objc public func checkPermissions(completion: @escaping (PermissionStatusResult) -> Void) {
        completion(PermissionStatusResult(alarms: getPermissionState(from: manager.authorizationState)))
    }

    @objc public func createAlarm(_ options: CreateAlarmOptions, completion: @escaping (CreateAlarmResult?, Error?) -> Void) {
        Task {
            do {
                try await requestAuthorizationIfNeeded()
                let id = UUID()
                let attributes = AlarmAttributes<EmptyMetadata>(presentation: makePresentation(label: options.label), tintColor: .accentColor)
                let schedule = makeSchedule(hour: options.hour, minute: options.minute, days: options.days)
                let configuration = AlarmKit.AlarmManager.AlarmConfiguration.alarm(schedule: schedule, attributes: attributes)
                _ = try await manager.schedule(id: id, configuration: configuration)
                setLabel(options.label, for: id)
                completion(CreateAlarmResult(id: id.uuidString), nil)
            } catch {
                completion(nil, error)
            }
        }
    }

    @objc public func getAlarms(completion: @escaping (GetAlarmsResult?, Error?) -> Void) {
        do {
            let alarms = try manager.alarms
            completion(GetAlarmsResult(alarms: alarms, labels: getLabels()), nil)
        } catch {
            completion(nil, error)
        }
    }

    @objc public func isAvailable(completion: @escaping (IsAvailableResult) -> Void) {
        completion(IsAvailableResult(available: true))
    }

    @objc public func requestPermissions(completion: @escaping (PermissionStatusResult?, Error?) -> Void) {
        Task {
            do {
                try ensureUsageDescription()
                let state = try await manager.requestAuthorization()
                completion(PermissionStatusResult(alarms: getPermissionState(from: state)), nil)
            } catch {
                completion(nil, error)
            }
        }
    }

    private func ensureUsageDescription() throws {
        guard Bundle.main.object(forInfoDictionaryKey: "NSAlarmKitUsageDescription") is String else {
            throw CustomError.privacyDescriptionsMissing
        }
    }

    private func getLabels() -> [String: String] {
        return UserDefaults.standard.dictionary(forKey: labelsUserDefaultsKey) as? [String: String] ?? [:]
    }

    private func getPermissionState(from state: AlarmKit.AlarmManager.AuthorizationState) -> String {
        switch state {
        case .authorized:
            return "granted"
        case .denied:
            return "denied"
        case .notDetermined:
            return "prompt"
        @unknown default:
            return "prompt"
        }
    }

    private func makePresentation(label: String?) -> AlarmPresentation {
        let title = LocalizedStringResource("\(label ?? "Alarm")")
        let alert: AlarmPresentation.Alert
        if #available(iOS 26.1, *) {
            alert = AlarmPresentation.Alert(title: title)
        } else {
            alert = AlarmPresentation.Alert(title: title, stopButton: AlarmButton(text: "Stop", textColor: .white, systemImageName: "stop.circle"))
        }
        return AlarmPresentation(alert: alert)
    }

    private func makeSchedule(hour: Int, minute: Int, days: [Locale.Weekday]?) -> AlarmKit.Alarm.Schedule {
        let time = AlarmKit.Alarm.Schedule.Relative.Time(hour: hour, minute: minute)
        var recurrence = AlarmKit.Alarm.Schedule.Relative.Recurrence.never
        if let days = days, !days.isEmpty {
            recurrence = .weekly(days)
        }
        return .relative(AlarmKit.Alarm.Schedule.Relative(time: time, repeats: recurrence))
    }

    private func removeLabel(for id: UUID) {
        var labels = getLabels()
        labels.removeValue(forKey: id.uuidString)
        UserDefaults.standard.set(labels, forKey: labelsUserDefaultsKey)
    }

    private func requestAuthorizationIfNeeded() async throws {
        switch manager.authorizationState {
        case .authorized:
            return
        case .denied:
            throw CustomError.permissionDenied
        case .notDetermined:
            try ensureUsageDescription()
            let state = try await manager.requestAuthorization()
            guard state == .authorized else {
                throw CustomError.permissionDenied
            }
        @unknown default:
            throw CustomError.permissionDenied
        }
    }

    private func setLabel(_ label: String?, for id: UUID) {
        guard let label = label else {
            return
        }
        var labels = getLabels()
        labels[id.uuidString] = label
        UserDefaults.standard.set(labels, forKey: labelsUserDefaultsKey)
    }
}
