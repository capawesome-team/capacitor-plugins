import Foundation
import Capacitor
import FBSDKCoreKit

public class FacebookSignInHelper {
    public static func createAccessTokenJSObject(_ accessToken: AccessToken) -> JSObject {
        var result = JSObject()
        result["expiresAt"] = Int(accessToken.expirationDate.timeIntervalSince1970 * 1000)
        result["permissions"] = accessToken.permissions.map { $0.name }
        result["token"] = accessToken.tokenString
        result["userId"] = accessToken.userID
        return result
    }
}
