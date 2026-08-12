import Capacitor

struct AgeRange {
    let activeParentalControls: [ParentalControl]
    let ageRangeDeclaration: AgeRangeDeclaration?
    let lowerBound: Int?
    let upperBound: Int?

    func toJSObject() -> JSObject {
        var result = JSObject()
        result["activeParentalControls"] = activeParentalControls.map { $0.rawValue } as JSArray
        result["ageRangeDeclaration"] = ageRangeDeclaration?.rawValue
        result["lowerBound"] = lowerBound
        result["upperBound"] = upperBound
        return result
    }
}
