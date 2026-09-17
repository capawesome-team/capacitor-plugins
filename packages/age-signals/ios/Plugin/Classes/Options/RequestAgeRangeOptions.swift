import Capacitor

@objc class RequestAgeRangeOptions: NSObject {
    let ageGates: [Int]

    init(_ call: CAPPluginCall) throws {
        let ageGates = call.getArray("ageGates") as? [Int] ?? [13, 15, 18]
        guard (1...3).contains(ageGates.count) else {
            throw CustomError.illegalAgeGates
        }
        self.ageGates = ageGates
    }
}
