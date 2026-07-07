import Foundation
import Capacitor

@objc public class CreatePasskeyResult: NSObject, Result {
    let attestationObject: Data
    let clientDataJSON: Data
    let credentialId: Data

    init(attestationObject: Data, clientDataJSON: Data, credentialId: Data) {
        self.attestationObject = attestationObject
        self.clientDataJSON = clientDataJSON
        self.credentialId = credentialId
    }

    @objc public func toJSObject() -> AnyObject {
        let credentialId = PasskeysHelper.base64UrlFromData(self.credentialId)
        var response = JSObject()
        response["attestationObject"] = PasskeysHelper.base64UrlFromData(attestationObject)
        response["clientDataJSON"] = PasskeysHelper.base64UrlFromData(clientDataJSON)
        var result = JSObject()
        result["authenticatorAttachment"] = "platform"
        result["id"] = credentialId
        result["rawId"] = credentialId
        result["response"] = response
        result["type"] = "public-key"
        return result as AnyObject
    }
}
