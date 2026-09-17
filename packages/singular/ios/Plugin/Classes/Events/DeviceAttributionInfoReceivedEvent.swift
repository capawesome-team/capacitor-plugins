import Foundation
import Capacitor

@objc public class DeviceAttributionInfoReceivedEvent: NSObject, Result {
    private let info: [AnyHashable: Any]

    init(info: [AnyHashable: Any]) {
        self.info = info
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        if let campaignId = info["campaign_id"] as? String {
            result["campaignId"] = campaignId
        }
        if let campaignName = info["campaign_name"] as? String {
            result["campaignName"] = campaignName
        }
        if let clickTimestamp = info["click_timestamp"] as? NSNumber {
            result["clickTimestamp"] = Int(clickTimestamp.int64Value / 1000)
        }
        if let creativeId = info["creative_id"] as? String {
            result["creativeId"] = creativeId
        }
        if let creativeName = info["creative_name"] as? String {
            result["creativeName"] = creativeName
        }
        if let matchType = info["match_type"] as? String {
            result["matchType"] = matchType
        }
        result["network"] = info["network"] as? String ?? ""
        if let passthrough = info["passthrough"] as? String {
            result["passthrough"] = passthrough
        }
        if let subcampaignId = info["subcampaign_id"] as? String {
            result["subcampaignId"] = subcampaignId
        }
        if let subcampaignName = info["subcampaign_name"] as? String {
            result["subcampaignName"] = subcampaignName
        }
        return result as AnyObject
    }
}
