import Foundation
import Capacitor

@objc public class StartSessionRecordingOptions: NSObject {
    private var linkedFlag: Bool?
    private var sampling: Double?

    init(linkedFlag: Bool?, sampling: Double?) {
        self.linkedFlag = linkedFlag
        self.sampling = sampling
    }

    func getLinkedFlag() -> Bool? {
        return linkedFlag
    }

    func getSampling() -> Double? {
        return sampling
    }
}
