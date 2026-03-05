import Foundation
import Capacitor

@objc public class SignInResult: NSObject, Result {
    let idToken: String
    let userId: String
    let email: String?
    let displayName: String?
    let givenName: String?
    let familyName: String?
    let imageUrl: String?
    let accessToken: String?
    let serverAuthCode: String?

    init(
        idToken: String,
        userId: String,
        email: String?,
        displayName: String?,
        givenName: String?,
        familyName: String?,
        imageUrl: String?,
        accessToken: String?,
        serverAuthCode: String?
    ) {
        self.idToken = idToken
        self.userId = userId
        self.email = email
        self.displayName = displayName
        self.givenName = givenName
        self.familyName = familyName
        self.imageUrl = imageUrl
        self.accessToken = accessToken
        self.serverAuthCode = serverAuthCode
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["idToken"] = idToken
        result["userId"] = userId
        result["email"] = email == nil ? NSNull() : email
        result["displayName"] = displayName == nil ? NSNull() : displayName
        result["givenName"] = givenName == nil ? NSNull() : givenName
        result["familyName"] = familyName == nil ? NSNull() : familyName
        result["imageUrl"] = imageUrl == nil ? NSNull() : imageUrl
        result["accessToken"] = accessToken == nil ? NSNull() : accessToken
        result["serverAuthCode"] = serverAuthCode == nil ? NSNull() : serverAuthCode
        return result as AnyObject
    }
}
