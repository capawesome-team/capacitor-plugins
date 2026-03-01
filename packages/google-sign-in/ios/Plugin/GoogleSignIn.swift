import Foundation
import Capacitor
import GoogleSignIn

@objc public class GoogleSignInImpl: NSObject {
    private let plugin: GoogleSignInPlugin
    private var scopes: [String]?

    public init(plugin: GoogleSignInPlugin) {
        self.plugin = plugin
    }

    @objc public func initialize(_ options: InitializeOptions, completion: @escaping (_ error: Error?) -> Void) {
        guard let iosClientId = Bundle.main.object(forInfoDictionaryKey: "GIDClientID") as? String else {
            completion(CustomError.iosClientIdMissing)
            return
        }
        let configuration = GIDConfiguration(clientID: iosClientId, serverClientID: options.clientId)
        GIDSignIn.sharedInstance.configuration = configuration
        self.scopes = options.scopes
        completion(nil)
    }

    @objc public func signIn(completion: @escaping (_ result: SignInResult?, _ error: Error?) -> Void) {
        guard let viewController = plugin.bridge?.viewController else {
            completion(nil, CustomError.viewControllerUnavailable)
            return
        }

        let signInCompletion: (GIDSignInResult?, Error?) -> Void = { result, error in
            if let error = error {
                if (error as NSError).code == GIDSignInError.canceled.rawValue {
                    completion(nil, CustomError.signInCanceled)
                } else {
                    completion(nil, error)
                }
                return
            }
            guard let result = result else {
                completion(nil, CustomError.idTokenMissing)
                return
            }
            let user = result.user
            guard let idToken = user.idToken?.tokenString else {
                completion(nil, CustomError.idTokenMissing)
                return
            }
            guard let userId = user.userID else {
                completion(nil, CustomError.userIdMissing)
                return
            }
            let email = user.profile?.email
            let displayName = user.profile?.name
            let givenName = user.profile?.givenName
            let familyName = user.profile?.familyName
            let imageUrl = user.profile?.imageURL(withDimension: 0)?.absoluteString

            var accessToken: String?
            var serverAuthCode: String?
            if self.scopes != nil && !(self.scopes?.isEmpty ?? true) {
                accessToken = user.accessToken.tokenString
                serverAuthCode = result.serverAuthCode
            }

            let signInResult = SignInResult(
                idToken: idToken,
                userId: userId,
                email: email,
                displayName: displayName,
                givenName: givenName,
                familyName: familyName,
                imageUrl: imageUrl,
                accessToken: accessToken,
                serverAuthCode: serverAuthCode
            )
            completion(signInResult, nil)
        }

        if let scopes = self.scopes, !scopes.isEmpty {
            GIDSignIn.sharedInstance.signIn(withPresenting: viewController, hint: nil, additionalScopes: scopes, completion: signInCompletion)
        } else {
            GIDSignIn.sharedInstance.signIn(withPresenting: viewController, completion: signInCompletion)
        }
    }

    @objc public func signOut(completion: @escaping (_ error: Error?) -> Void) {
        GIDSignIn.sharedInstance.signOut()
        completion(nil)
    }
}
