import Foundation
import Capacitor

@objc public class SetSessionSegmentOptions: NSObject {
    let segment: String

    init(_ call: CAPPluginCall) throws {
        guard let segment = call.getString("segment") else {
            throw CustomError.segmentMissing
        }
        self.segment = segment
    }
}
