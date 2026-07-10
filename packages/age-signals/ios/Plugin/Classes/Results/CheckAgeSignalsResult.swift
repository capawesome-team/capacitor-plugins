import Capacitor

@objc class CheckAgeSignalsResult: NSObject, Result {
    let userStatus: UserStatus
    let ageLower: Int?
    let ageUpper: Int?
    let mostRecentApprovalDate: String?
    let installId: String?
    let ageRangeDeclaration: AgeRangeDeclaration?

    init(
        userStatus: UserStatus,
        ageLower: Int? = nil,
        ageUpper: Int? = nil,
        mostRecentApprovalDate: String? = nil,
        installId: String? = nil,
        ageRangeDeclaration: AgeRangeDeclaration? = nil
    ) {
        self.userStatus = userStatus
        self.ageLower = ageLower
        self.ageUpper = ageUpper
        self.mostRecentApprovalDate = mostRecentApprovalDate
        self.installId = installId
        self.ageRangeDeclaration = ageRangeDeclaration
    }

    func toJSObject() -> AnyObject {
        var result = JSObject()
        result["userStatus"] = userStatus.rawValue
        result["ageLower"] = ageLower
        result["ageUpper"] = ageUpper
        result["mostRecentApprovalDate"] = mostRecentApprovalDate
        result["installId"] = installId
        result["ageRangeDeclaration"] = ageRangeDeclaration?.rawValue
        return result as AnyObject
    }
}
