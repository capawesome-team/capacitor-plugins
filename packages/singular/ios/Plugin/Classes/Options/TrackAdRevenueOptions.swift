import Foundation
import Capacitor

@objc public class TrackAdRevenueOptions: NSObject {
    let adGroupId: String?
    let adGroupName: String?
    let adGroupPriority: String?
    let adGroupType: String?
    let adPlacementName: String?
    let adPlatform: String
    let adType: String?
    let adUnitId: String?
    let adUnitName: String?
    let currency: String
    let impressionId: String?
    let networkName: String?
    let placementId: String?
    let precision: String?
    let revenue: Double

    init(_ call: CAPPluginCall) throws {
        guard let adPlatform = call.getString("adPlatform") else {
            throw CustomError.adPlatformMissing
        }
        guard let currency = call.getString("currency") else {
            throw CustomError.currencyMissing
        }
        guard let revenue = call.getDouble("revenue") else {
            throw CustomError.revenueMissing
        }
        self.adPlatform = adPlatform
        self.currency = currency
        self.revenue = revenue
        self.adGroupId = call.getString("adGroupId")
        self.adGroupName = call.getString("adGroupName")
        self.adGroupPriority = call.getString("adGroupPriority")
        self.adGroupType = call.getString("adGroupType")
        self.adPlacementName = call.getString("adPlacementName")
        self.adType = call.getString("adType")
        self.adUnitId = call.getString("adUnitId")
        self.adUnitName = call.getString("adUnitName")
        self.impressionId = call.getString("impressionId")
        self.networkName = call.getString("networkName")
        self.placementId = call.getString("placementId")
        self.precision = call.getString("precision")
    }
}
