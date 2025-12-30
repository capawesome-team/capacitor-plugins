import Foundation
import Capacitor
import SuperwallKit

@objc public class PaywallDismissedEvent: NSObject {
    private let paywallInfo: PaywallInfo

    init(paywallInfo: PaywallInfo) {
        self.paywallInfo = paywallInfo
    }

    public func toJSObject() -> JSObject {
        var event = JSObject()
        event["paywallInfo"] = convertPaywallInfoToJSObject(paywallInfo)
        return event
    }

    private func convertPaywallInfoToJSObject(_ paywallInfo: PaywallInfo) -> JSObject {
        var jsObject = JSObject()
        jsObject["id"] = paywallInfo.identifier
        jsObject["placement"] = paywallInfo.name

        if let experiment = paywallInfo.experiment {
            jsObject["experiment"] = convertExperimentToJSObject(experiment)
        } else {
            jsObject["experiment"] = NSNull()
        }

        var data = JSObject()
        jsObject["data"] = data

        return jsObject
    }

    private func convertExperimentToJSObject(_ experiment: Experiment) -> JSObject {
        var jsObject = JSObject()
        jsObject["id"] = experiment.id
        jsObject["variant"] = experiment.variant.id
        return jsObject
    }
}
