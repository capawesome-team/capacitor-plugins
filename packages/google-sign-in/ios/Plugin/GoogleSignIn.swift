import Foundation
import Capacitor
import GoogleSignIn

@objc public class GoogleSignInImpl: NSObject {
    private let plugin: GoogleSignInPlugin
    private var scopes: [String]?

    public init(plugin: GoogleSignInPlugin) {
        self.plugin = plugin
    }

    @objc public func authorize(_ options: AuthorizeOptions, completion: @escaping (_ result: AuthorizeResult?, _ error: Error?) -> Void) {
        DispatchQueue.main.async {
            guard let viewController = self.plugin.bridge?.viewController else {
                completion(nil, CustomError.viewControllerUnavailable)
                return
            }
            self.restorePreviousUser { user, error in
                if let error = error {
                    completion(nil, error)
                    return
                }
                guard let user = user else {
                    GIDSignIn.sharedInstance.signIn(withPresenting: viewController, hint: nil, additionalScopes: options.scopes) { result, error in
                        self.resolveAuthorization(result, error, completion: completion)
                    }
                    return
                }
                self.authorize(user, scopes: options.scopes, presenting: viewController, completion: completion)
            }
        }
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
        let signInCompletion: (GIDSignInResult?, Error?) -> Void = { result, error in
            if let error = error {
                if self.isCanceledError(error) {
                    completion(nil, CustomError.signInCanceled)
                } else {
                    completion(nil, error)
                }
                return
            }
            guard let result = result else {
                completion(nil, CustomError.signInResultMissing)
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

        DispatchQueue.main.async {
            guard let viewController = self.plugin.bridge?.viewController else {
                completion(nil, CustomError.viewControllerUnavailable)
                return
            }
            if let scopes = self.scopes, !scopes.isEmpty {
                GIDSignIn.sharedInstance.signIn(withPresenting: viewController, hint: nil, additionalScopes: scopes, completion: signInCompletion)
            } else {
                GIDSignIn.sharedInstance.signIn(withPresenting: viewController, completion: signInCompletion)
            }
        }
    }

    @objc public func signOut(completion: @escaping (_ error: Error?) -> Void) {
        GIDSignIn.sharedInstance.signOut()
        completion(nil)
    }

    private func authorize(
        _ user: GIDGoogleUser,
        scopes: [String],
        presenting viewController: UIViewController,
        completion: @escaping (_ result: AuthorizeResult?, _ error: Error?) -> Void
    ) {
        let grantedScopes = user.grantedScopes ?? []
        let missingScopes = scopes.filter { !grantedScopes.contains($0) }
        if !missingScopes.isEmpty {
            user.addScopes(missingScopes, presenting: viewController) { result, error in
                self.resolveAuthorization(result, error, completion: completion)
            }
            return
        }
        user.refreshTokensIfNeeded { _, error in
            if let error = error {
                completion(nil, error)
                return
            }
            completion(AuthorizeResult(accessToken: user.accessToken.tokenString, serverAuthCode: nil), nil)
        }
    }

    private func isCanceledError(_ error: Error) -> Bool {
        return (error as NSError).code == GIDSignInError.canceled.rawValue
    }

    private func resolveAuthorization(
        _ result: GIDSignInResult?,
        _ error: Error?,
        completion: @escaping (_ result: AuthorizeResult?, _ error: Error?) -> Void
    ) {
        if let error = error {
            completion(nil, isCanceledError(error) ? CustomError.authorizationCanceled : error)
            return
        }
        guard let result = result else {
            completion(nil, CustomError.signInResultMissing)
            return
        }
        completion(AuthorizeResult(accessToken: result.user.accessToken.tokenString, serverAuthCode: result.serverAuthCode), nil)
    }

    private func restorePreviousUser(completion: @escaping (_ user: GIDGoogleUser?, _ error: Error?) -> Void) {
        if let currentUser = GIDSignIn.sharedInstance.currentUser {
            completion(currentUser, nil)
            return
        }
        guard GIDSignIn.sharedInstance.hasPreviousSignIn() else {
            completion(nil, nil)
            return
        }
        GIDSignIn.sharedInstance.restorePreviousSignIn(completion: completion)
    }
}
