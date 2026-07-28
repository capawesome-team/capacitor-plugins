import Capacitor

@objc class GetRegulatoryRequirementsResult: NSObject, Result {
    private let ageAssuranceRequired: Bool
    private let regulatoryFeatures: [RegulatoryFeature]

    init(ageAssuranceRequired: Bool, regulatoryFeatures: [RegulatoryFeature]) {
        self.ageAssuranceRequired = ageAssuranceRequired
        self.regulatoryFeatures = regulatoryFeatures
    }

    func toJSObject() -> AnyObject {
        var result = JSObject()
        result["ageAssuranceRequired"] = ageAssuranceRequired
        result["regulatoryFeatures"] = regulatoryFeatures.map { $0.rawValue } as JSArray
        return result as AnyObject
    }
}
