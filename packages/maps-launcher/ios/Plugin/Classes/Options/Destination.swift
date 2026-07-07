import Foundation
import Capacitor
import CoreLocation

@objc public class Destination: NSObject {
    let address: String?
    let latitude: Double?
    let longitude: Double?

    var coordinate: CLLocationCoordinate2D? {
        guard let latitude = latitude, let longitude = longitude else {
            return nil
        }
        return CLLocationCoordinate2D(latitude: latitude, longitude: longitude)
    }

    init(_ object: JSObject) throws {
        let latitude = (object["latitude"] as? NSNumber)?.doubleValue
        let longitude = (object["longitude"] as? NSNumber)?.doubleValue
        let address = (object["address"] as? String)?.trimmingCharacters(in: .whitespacesAndNewlines)
        let hasCoordinates = latitude != nil && longitude != nil
        let hasAddress = address != nil && !(address?.isEmpty ?? true)
        if hasCoordinates == hasAddress {
            throw CustomError.destinationInvalid
        }
        if hasCoordinates {
            self.latitude = latitude
            self.longitude = longitude
            self.address = nil
        } else {
            self.latitude = nil
            self.longitude = nil
            self.address = address
        }
    }
}
