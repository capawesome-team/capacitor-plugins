import Foundation
import Capacitor
import AuthenticationServices

@objc public class SignInResult: NSObject, Result {
    private let authorizationCode: String
    private let idToken: String
    private let user: String
    private let email: String?
    private let givenName: String?
    private let familyName: String?
    private let realUserStatus: String

    init(credential: ASAuthorizationAppleIDCredential) {
        self.authorizationCode = String(data: credential.authorizationCode ?? Data(), encoding: .utf8) ?? ""
        self.idToken = String(data: credential.identityToken ?? Data(), encoding: .utf8) ?? ""
        self.user = credential.user
        self.email = credential.email
        self.givenName = credential.fullName?.givenName
        self.familyName = credential.fullName?.familyName
        self.realUserStatus = SignInResult.mapRealUserStatus(credential.realUserStatus)
    }

    @objc public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["authorizationCode"] = authorizationCode
        result["idToken"] = idToken
        result["user"] = user
        result["email"] = email == nil ? NSNull() : email
        result["givenName"] = givenName == nil ? NSNull() : givenName
        result["familyName"] = familyName == nil ? NSNull() : familyName
        result["realUserStatus"] = realUserStatus
        return result as AnyObject
    }

    private static func mapRealUserStatus(_ status: ASUserDetectionStatus) -> String {
        switch status {
        case .likelyReal:
            return "LIKELY_REAL"
        case .unknown:
            return "UNKNOWN"
        default:
            return "UNSUPPORTED"
        }
    }
}
