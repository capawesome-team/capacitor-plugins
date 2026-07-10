import AVFoundation
import Contacts
import CoreBluetooth
import CoreLocation
import CoreMotion
import EventKit
import Foundation
import Photos
import UserNotifications

public class PermissionsHelper {
    public static func getBluetoothPermissionState() -> PermissionState {
        switch CBCentralManager.authorization {
        case .allowedAlways:
            return .granted
        case .notDetermined:
            return .prompt
        default:
            return .denied
        }
    }

    public static func getCaptureDevicePermissionState(for mediaType: AVMediaType) -> PermissionState {
        switch AVCaptureDevice.authorizationStatus(for: mediaType) {
        case .authorized:
            return .granted
        case .notDetermined:
            return .prompt
        default:
            return .denied
        }
    }

    public static func getContactsPermissionState() -> PermissionState {
        let status = CNContactStore.authorizationStatus(for: .contacts)
        if #available(iOS 18.0, *) {
            if status == .limited {
                return .limited
            }
        }
        switch status {
        case .authorized:
            return .granted
        case .notDetermined:
            return .prompt
        default:
            return .denied
        }
    }

    public static func getEventKitPermissionState(for entityType: EKEntityType) -> PermissionState {
        let status = EKEventStore.authorizationStatus(for: entityType)
        if #available(iOS 17.0, *) {
            if status == .writeOnly {
                return .limited
            }
        }
        switch status {
        case .notDetermined:
            return .prompt
        case .denied, .restricted:
            return .denied
        default:
            return .granted
        }
    }

    public static func getLocationAlwaysPermissionState(for status: CLAuthorizationStatus) -> PermissionState {
        switch status {
        case .authorizedAlways:
            return .granted
        case .authorizedWhenInUse, .notDetermined:
            return .prompt
        default:
            return .denied
        }
    }

    public static func getLocationPermissionState(for status: CLAuthorizationStatus) -> PermissionState {
        switch status {
        case .authorizedAlways, .authorizedWhenInUse:
            return .granted
        case .notDetermined:
            return .prompt
        default:
            return .denied
        }
    }

    public static func getMotionPermissionState() -> PermissionState {
        switch CMMotionActivityManager.authorizationStatus() {
        case .authorized:
            return .granted
        case .notDetermined:
            return .prompt
        default:
            return .denied
        }
    }

    public static func getNotificationsPermissionState(completion: @escaping (PermissionState) -> Void) {
        UNUserNotificationCenter.current().getNotificationSettings { settings in
            switch settings.authorizationStatus {
            case .authorized, .ephemeral, .provisional:
                completion(.granted)
            case .notDetermined:
                completion(.prompt)
            default:
                completion(.denied)
            }
        }
    }

    public static func getPhotosPermissionState() -> PermissionState {
        switch PHPhotoLibrary.authorizationStatus(for: .readWrite) {
        case .authorized:
            return .granted
        case .limited:
            return .limited
        case .notDetermined:
            return .prompt
        default:
            return .denied
        }
    }

    // swiftlint:disable:next cyclomatic_complexity
    public static func getUsageDescriptionKeys(for permission: Permission) -> [String] {
        switch permission {
        case .bluetooth:
            return ["NSBluetoothAlwaysUsageDescription"]
        case .calendar:
            if #available(iOS 17.0, *) {
                return ["NSCalendarsFullAccessUsageDescription"]
            }
            return ["NSCalendarsUsageDescription"]
        case .camera:
            return ["NSCameraUsageDescription"]
        case .contacts:
            return ["NSContactsUsageDescription"]
        case .location:
            return ["NSLocationWhenInUseUsageDescription"]
        case .locationAlways:
            return ["NSLocationAlwaysAndWhenInUseUsageDescription", "NSLocationWhenInUseUsageDescription"]
        case .microphone:
            return ["NSMicrophoneUsageDescription"]
        case .motion:
            return ["NSMotionUsageDescription"]
        case .notifications:
            return []
        case .photos:
            return ["NSPhotoLibraryUsageDescription"]
        case .reminders:
            if #available(iOS 17.0, *) {
                return ["NSRemindersFullAccessUsageDescription"]
            }
            return ["NSRemindersUsageDescription"]
        }
    }
}
