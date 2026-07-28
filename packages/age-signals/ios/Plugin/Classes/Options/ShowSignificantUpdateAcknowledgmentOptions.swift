import Capacitor

@objc class ShowSignificantUpdateAcknowledgmentOptions: NSObject {
    let updateDescription: String

    init(_ call: CAPPluginCall) throws {
        guard let updateDescription = call.getString("updateDescription") else {
            throw CustomError.updateDescriptionMissing
        }
        self.updateDescription = updateDescription
    }
}
