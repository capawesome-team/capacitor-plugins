import Capacitor

@objc class CheckAgeSignalsOptions: NSObject {
    var ageGates = [13, 15, 18]

    init(_ call: CAPPluginCall) {
        if let ageGates = call.getArray("ageGates") as? [Int] {
            self.ageGates = ageGates
        }
    }
}
