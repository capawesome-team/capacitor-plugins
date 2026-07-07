import Foundation
import AdSupport
import AppTrackingTransparency

@objc public class AppTrackingTransparency: NSObject {
    let plugin: AppTrackingTransparencyPlugin

    init(plugin: AppTrackingTransparencyPlugin) {
        self.plugin = plugin
    }

    @objc public func getAdvertisingIdentifier(completion: @escaping (GetAdvertisingIdentifierResult?, Error?) -> Void) {
        let identifier = ASIdentifierManager.shared().advertisingIdentifier.uuidString
        let advertisingIdentifier = identifier == Self.zeroIdentifier ? nil : identifier
        let result = GetAdvertisingIdentifierResult(advertisingIdentifier: advertisingIdentifier)
        completion(result, nil)
    }

    @objc public func getStatus(completion: @escaping (GetStatusResult?, Error?) -> Void) {
        let status = Self.mapAuthorizationStatus(ATTrackingManager.trackingAuthorizationStatus)
        let result = GetStatusResult(status: status)
        completion(result, nil)
    }

    @objc public func requestPermission(completion: @escaping (RequestPermissionResult?, Error?) -> Void) {
        guard Bundle.main.object(forInfoDictionaryKey: "NSUserTrackingUsageDescription") is String else {
            completion(nil, CustomError.usageDescriptionMissing)
            return
        }
        DispatchQueue.main.async {
            ATTrackingManager.requestTrackingAuthorization { status in
                let result = RequestPermissionResult(status: Self.mapAuthorizationStatus(status))
                completion(result, nil)
            }
        }
    }

    private static let zeroIdentifier = "00000000-0000-0000-0000-000000000000"

    private static func mapAuthorizationStatus(_ status: ATTrackingManager.AuthorizationStatus) -> String {
        switch status {
        case .authorized:
            return "authorized"
        case .denied:
            return "denied"
        case .restricted:
            return "restricted"
        case .notDetermined:
            return "notDetermined"
        @unknown default:
            return "notDetermined"
        }
    }
}
