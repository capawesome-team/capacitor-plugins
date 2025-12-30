import Foundation
import Capacitor
import SuperwallKit

@objc public class GetPresentationResultResult: NSObject, Result {
    let type: String
    let experiment: Experiment?

    init(type: String, experiment: Experiment?) {
        self.type = type
        self.experiment = experiment
    }

    @objc public func toJSObject() -> AnyObject {
        var jsResult = JSObject()
        jsResult["type"] = type

        if let experiment = experiment {
            var experimentObj = JSObject()
            experimentObj["id"] = experiment.id
            experimentObj["variant"] = experiment.variant.id
            jsResult["experiment"] = experimentObj
        }

        return jsResult as AnyObject
    }
}
