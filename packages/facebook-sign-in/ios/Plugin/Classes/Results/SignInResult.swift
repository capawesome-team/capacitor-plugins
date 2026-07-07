import Foundation
import Capacitor
import FBSDKCoreKit

@objc public class SignInResult: NSObject, Result {
    let accessToken: AccessToken?
    let authenticationToken: String?
    let profileEmail: String?
    let profileId: String
    let profileImageUrl: String?
    let profileName: String?

    init(
        accessToken: AccessToken?,
        authenticationToken: String?,
        profileId: String,
        profileName: String?,
        profileEmail: String?,
        profileImageUrl: String?
    ) {
        self.accessToken = accessToken
        self.authenticationToken = authenticationToken
        self.profileId = profileId
        self.profileName = profileName
        self.profileEmail = profileEmail
        self.profileImageUrl = profileImageUrl
    }

    @objc public func toJSObject() -> AnyObject {
        var profile = JSObject()
        profile["email"] = profileEmail == nil ? NSNull() : profileEmail
        profile["id"] = profileId
        profile["imageUrl"] = profileImageUrl == nil ? NSNull() : profileImageUrl
        profile["name"] = profileName == nil ? NSNull() : profileName

        var result = JSObject()
        if let accessToken = accessToken {
            result["accessToken"] = FacebookSignInHelper.createAccessTokenJSObject(accessToken)
        } else {
            result["accessToken"] = NSNull()
        }
        result["authenticationToken"] = authenticationToken == nil ? NSNull() : authenticationToken
        result["profile"] = profile
        return result as AnyObject
    }
}
