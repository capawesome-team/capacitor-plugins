import Foundation
import Capacitor
import SuperwallKit

@objc public class SuperwallEvent: NSObject {
    private let eventInfo: SuperwallEventInfo

    init(_ eventInfo: SuperwallEventInfo) {
        self.eventInfo = eventInfo
    }

    public func toJSObject() -> JSObject {
        var event = JSObject()
        event["type"] = convertEventType(eventInfo.event)
        event["data"] = convertEventData(eventInfo)
        return event
    }

    private func convertEventType(_ event: SuperwallKit.SuperwallEvent) -> String {
        switch event {
        case .firstSeen:
            return "FIRST_SEEN"
        case .appOpen:
            return "APP_OPEN"
        case .appLaunch:
            return "APP_LAUNCH"
        case .appClose:
            return "APP_CLOSE"
        case .sessionStart:
            return "SESSION_START"
        case .deepLink:
            return "DEEP_LINK"
        case .triggerFire:
            return "TRIGGER_FIRE"
        case .paywallOpen:
            return "PAYWALL_OPEN"
        case .paywallClose:
            return "PAYWALL_CLOSE"
        case .paywallDecline:
            return "PAYWALL_DECLINE"
        case .transactionStart:
            return "TRANSACTION_START"
        case .transactionComplete:
            return "TRANSACTION_COMPLETE"
        case .transactionFail:
            return "TRANSACTION_FAIL"
        case .transactionAbandon:
            return "TRANSACTION_ABANDON"
        case .transactionRestore:
            return "TRANSACTION_RESTORE"
        case .transactionTimeout:
            return "TRANSACTION_TIMEOUT"
        case .subscriptionStart:
            return "SUBSCRIPTION_START"
        case .freeTrialStart:
            return "FREE_TRIAL_START"
        case .subscriptionStatusDidChange:
            return "SUBSCRIPTION_STATUS_DID_CHANGE"
        default:
            return "UNKNOWN"
        }
    }

    private func convertEventData(_ eventInfo: SuperwallEventInfo) -> JSObject {
        var data = JSObject()
        for (key, value) in eventInfo.params {
            data[key] = value as? JSValue
        }
        return data
    }
}
