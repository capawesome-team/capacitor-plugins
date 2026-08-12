import Foundation
import Capacitor

@objc public class GetPasskeyResult: NSObject, Result {
    let authenticatorData: Data
    let clientDataJSON: Data
    let credentialId: Data
    let signature: Data
    let userHandle: Data

    init(authenticatorData: Data, clientDataJSON: Data, credentialId: Data, signature: Data, userHandle: Data) {
        self.authenticatorData = authenticatorData
        self.clientDataJSON = clientDataJSON
        self.credentialId = credentialId
        self.signature = signature
        self.userHandle = userHandle
    }

    @objc public func toJSObject() -> AnyObject {
        let credentialId = PasskeysHelper.base64UrlFromData(self.credentialId)
        var response = JSObject()
        response["authenticatorData"] = PasskeysHelper.base64UrlFromData(authenticatorData)
        response["clientDataJSON"] = PasskeysHelper.base64UrlFromData(clientDataJSON)
        response["signature"] = PasskeysHelper.base64UrlFromData(signature)
        response["userHandle"] = PasskeysHelper.base64UrlFromData(userHandle)
        var result = JSObject()
        result["authenticatorAttachment"] = "platform"
        result["id"] = credentialId
        result["rawId"] = credentialId
        result["response"] = response
        result["type"] = "public-key"
        return result as AnyObject
    }
}
