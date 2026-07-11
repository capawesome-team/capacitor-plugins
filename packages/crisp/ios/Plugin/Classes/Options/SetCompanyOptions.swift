import Foundation
import Capacitor

@objc public class SetCompanyOptions: NSObject {
    let companyDescription: String?
    let employmentRole: String?
    let employmentTitle: String?
    let geolocationCity: String?
    let geolocationCountry: String?
    let name: String
    let url: String?

    init(_ call: CAPPluginCall) throws {
        guard let name = call.getString("name") else {
            throw CustomError.nameMissing
        }
        self.name = name
        self.companyDescription = call.getString("description")
        self.url = call.getString("url")
        let employment = call.getObject("employment")
        self.employmentRole = employment?["role"] as? String
        self.employmentTitle = employment?["title"] as? String
        let geolocation = call.getObject("geolocation")
        self.geolocationCity = geolocation?["city"] as? String
        self.geolocationCountry = geolocation?["country"] as? String
    }
}
