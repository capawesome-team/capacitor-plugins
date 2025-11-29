import Capacitor

@objc class CheckAgeSignalsResult: NSObject, Result {
    let userStatus: UserStatus
    let ageLower: Int?
    let ageUpper: Int?
    let mostRecentApprovalDate: String?
    let installId: String?

    init(
        userStatus: UserStatus,
        ageLower: Int? = nil,
        ageUpper: Int? = nil,
        mostRecentApprovalDate: String? = nil,
        installId: String? = nil
    ) {
        self.userStatus = userStatus
        self.ageLower = ageLower
        self.ageUpper = ageUpper
        self.mostRecentApprovalDate = mostRecentApprovalDate
        self.installId = installId
    }

    func toJSObject() -> AnyObject {
        var result = JSObject()
        result["userStatus"] = userStatus.rawValue
        result["ageLower"] = ageLower
        result["ageUpper"] = ageUpper
        result["mostRecentApprovalDate"] = mostRecentApprovalDate
        result["installId"] = installId
        return result as AnyObject
    }
}
