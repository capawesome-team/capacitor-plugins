import Foundation
import Capacitor

@objc public class NavigateOptions: NSObject {
    let app: String?
    let destination: Destination
    let start: Destination?
    let travelMode: String?

    init(_ call: CAPPluginCall) throws {
        guard let destinationObject = call.getObject("destination") else {
            throw CustomError.destinationMissing
        }
        self.destination = try Destination(destinationObject)
        if let startObject = call.getObject("start") {
            self.start = try Destination(startObject)
        } else {
            self.start = nil
        }
        self.app = call.getString("app")
        self.travelMode = call.getString("travelMode")
    }
}
