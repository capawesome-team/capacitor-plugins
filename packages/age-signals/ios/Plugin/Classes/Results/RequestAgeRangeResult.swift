import Capacitor

@objc class RequestAgeRangeResult: NSObject, Result {
    private let ageRange: AgeRange?
    private let status: AgeRangeStatus

    init(status: AgeRangeStatus, ageRange: AgeRange? = nil) {
        self.status = status
        self.ageRange = ageRange
    }

    func toJSObject() -> AnyObject {
        var result = JSObject()
        result["ageRange"] = ageRange?.toJSObject()
        result["status"] = status.rawValue
        return result as AnyObject
    }
}
