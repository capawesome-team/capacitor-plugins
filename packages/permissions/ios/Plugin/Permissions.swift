import AVFoundation
import Capacitor
import Contacts
import CoreLocation
import CoreMotion
import EventKit
import Foundation
import Photos
import UserNotifications

@objc public class Permissions: NSObject {
    private var bluetoothPermissionHandler: BluetoothPermissionHandler?
    private let contactStore = CNContactStore()
    private let eventStore = EKEventStore()
    private let locationManager = CLLocationManager()
    private var locationPermissionHandler: LocationPermissionHandler?
    private let motionActivityManager = CMMotionActivityManager()

    @objc public func check(_ options: CheckOptions, completion: @escaping (_ result: CheckResult?, _ error: Error?) -> Void) {
        getPermissionStates(for: options.permissions, statuses: []) { statuses in
            completion(CheckResult(statuses: statuses), nil)
        }
    }

    @objc public func request(_ options: RequestOptions, completion: @escaping (_ result: RequestResult?, _ error: Error?) -> Void) {
        requestPermissions(options.permissions, statuses: []) { statuses, error in
            if let error = error {
                completion(nil, error)
                return
            }
            completion(RequestResult(statuses: statuses ?? []), nil)
        }
    }

    // swiftlint:disable:next cyclomatic_complexity
    private func getPermissionState(of permission: Permission, completion: @escaping (PermissionState) -> Void) {
        switch permission {
        case .bluetooth:
            completion(PermissionsHelper.getBluetoothPermissionState())
        case .calendar:
            completion(PermissionsHelper.getEventKitPermissionState(for: .event))
        case .camera:
            completion(PermissionsHelper.getCaptureDevicePermissionState(for: .video))
        case .contacts:
            completion(PermissionsHelper.getContactsPermissionState())
        case .location:
            completion(PermissionsHelper.getLocationPermissionState(for: locationManager.authorizationStatus))
        case .locationAlways:
            completion(PermissionsHelper.getLocationAlwaysPermissionState(for: locationManager.authorizationStatus))
        case .microphone:
            completion(PermissionsHelper.getCaptureDevicePermissionState(for: .audio))
        case .motion:
            completion(PermissionsHelper.getMotionPermissionState())
        case .notifications:
            PermissionsHelper.getNotificationsPermissionState(completion: completion)
        case .photos:
            completion(PermissionsHelper.getPhotosPermissionState())
        case .reminders:
            completion(PermissionsHelper.getEventKitPermissionState(for: .reminder))
        }
    }

    private func getPermissionStates(
        for permissions: [Permission],
        statuses: [PermissionStatus],
        completion: @escaping ([PermissionStatus]) -> Void
    ) {
        guard let permission = permissions.first else {
            completion(statuses)
            return
        }
        getPermissionState(of: permission) { state in
            let status = PermissionStatus(permission: permission, state: state)
            self.getPermissionStates(for: Array(permissions.dropFirst()), statuses: statuses + [status], completion: completion)
        }
    }

    private func requestBluetoothPermission(completion: @escaping (PermissionState?, Error?) -> Void) {
        let handler = BluetoothPermissionHandler()
        bluetoothPermissionHandler = handler
        handler.requestAuthorization {
            self.bluetoothPermissionHandler = nil
            completion(PermissionsHelper.getBluetoothPermissionState(), nil)
        }
    }

    private func requestCalendarPermission(completion: @escaping (PermissionState?, Error?) -> Void) {
        let handleResult: (Bool, Error?) -> Void = { _, error in
            if error != nil {
                completion(nil, CustomError.requestFailed)
                return
            }
            completion(PermissionsHelper.getEventKitPermissionState(for: .event), nil)
        }
        if #available(iOS 17.0, *) {
            eventStore.requestFullAccessToEvents(completion: handleResult)
        } else {
            eventStore.requestAccess(to: .event, completion: handleResult)
        }
    }

    private func requestCameraPermission(completion: @escaping (PermissionState?, Error?) -> Void) {
        AVCaptureDevice.requestAccess(for: .video) { _ in
            completion(PermissionsHelper.getCaptureDevicePermissionState(for: .video), nil)
        }
    }

    private func requestContactsPermission(completion: @escaping (PermissionState?, Error?) -> Void) {
        contactStore.requestAccess(for: .contacts) { _, _ in
            completion(PermissionsHelper.getContactsPermissionState(), nil)
        }
    }

    private func requestLocationPermission(always: Bool, completion: @escaping (PermissionState?, Error?) -> Void) {
        let handler = LocationPermissionHandler()
        locationPermissionHandler = handler
        handler.requestAuthorization(always: always) {
            self.locationPermissionHandler = nil
            let status = self.locationManager.authorizationStatus
            completion(
                always
                    ? PermissionsHelper.getLocationAlwaysPermissionState(for: status)
                    : PermissionsHelper.getLocationPermissionState(for: status),
                nil
            )
        }
    }

    private func requestMicrophonePermission(completion: @escaping (PermissionState?, Error?) -> Void) {
        AVCaptureDevice.requestAccess(for: .audio) { _ in
            completion(PermissionsHelper.getCaptureDevicePermissionState(for: .audio), nil)
        }
    }

    private func requestMotionPermission(completion: @escaping (PermissionState?, Error?) -> Void) {
        motionActivityManager.queryActivityStarting(from: Date(), to: Date(), to: OperationQueue.main) { _, _ in
            completion(PermissionsHelper.getMotionPermissionState(), nil)
        }
    }

    private func requestNotificationsPermission(completion: @escaping (PermissionState?, Error?) -> Void) {
        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { _, error in
            if error != nil {
                completion(nil, CustomError.requestFailed)
                return
            }
            PermissionsHelper.getNotificationsPermissionState { state in
                completion(state, nil)
            }
        }
    }

    // swiftlint:disable:next cyclomatic_complexity
    private func requestPermission(of permission: Permission, completion: @escaping (PermissionState?, Error?) -> Void) {
        getPermissionState(of: permission) { state in
            guard state == .prompt else {
                completion(state, nil)
                return
            }
            for key in PermissionsHelper.getUsageDescriptionKeys(for: permission)
            where Bundle.main.object(forInfoDictionaryKey: key) == nil {
                completion(nil, CustomError.usageDescriptionMissing(key: key))
                return
            }
            switch permission {
            case .bluetooth:
                self.requestBluetoothPermission(completion: completion)
            case .calendar:
                self.requestCalendarPermission(completion: completion)
            case .camera:
                self.requestCameraPermission(completion: completion)
            case .contacts:
                self.requestContactsPermission(completion: completion)
            case .location:
                self.requestLocationPermission(always: false, completion: completion)
            case .locationAlways:
                self.requestLocationPermission(always: true, completion: completion)
            case .microphone:
                self.requestMicrophonePermission(completion: completion)
            case .motion:
                self.requestMotionPermission(completion: completion)
            case .notifications:
                self.requestNotificationsPermission(completion: completion)
            case .photos:
                self.requestPhotosPermission(completion: completion)
            case .reminders:
                self.requestRemindersPermission(completion: completion)
            }
        }
    }

    private func requestPermissions(
        _ permissions: [Permission],
        statuses: [PermissionStatus],
        completion: @escaping ([PermissionStatus]?, Error?) -> Void
    ) {
        guard let permission = permissions.first else {
            completion(statuses, nil)
            return
        }
        requestPermission(of: permission) { state, error in
            if let error = error {
                completion(nil, error)
                return
            }
            let status = PermissionStatus(permission: permission, state: state ?? .prompt)
            self.requestPermissions(Array(permissions.dropFirst()), statuses: statuses + [status], completion: completion)
        }
    }

    private func requestPhotosPermission(completion: @escaping (PermissionState?, Error?) -> Void) {
        PHPhotoLibrary.requestAuthorization(for: .readWrite) { _ in
            completion(PermissionsHelper.getPhotosPermissionState(), nil)
        }
    }

    private func requestRemindersPermission(completion: @escaping (PermissionState?, Error?) -> Void) {
        let handleResult: (Bool, Error?) -> Void = { _, error in
            if error != nil {
                completion(nil, CustomError.requestFailed)
                return
            }
            completion(PermissionsHelper.getEventKitPermissionState(for: .reminder), nil)
        }
        if #available(iOS 17.0, *) {
            eventStore.requestFullAccessToReminders(completion: handleResult)
        } else {
            eventStore.requestAccess(to: .reminder, completion: handleResult)
        }
    }
}
