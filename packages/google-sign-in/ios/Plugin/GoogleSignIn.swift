import Foundation
import Capacitor
import GoogleSignIn

@objc public class GoogleSignInImpl: NSObject {
    private var isAuthorizationInProgress = false
    private let plugin: GoogleSignInPlugin
    private var scopes: [String]?

    public init(plugin: GoogleSignInPlugin) {
        self.plugin = plugin
    }

    @objc public func authorize(_ options: AuthorizeOptions, completion: @escaping (_ result: AuthorizeResult?, _ error: Error?) -> Void) {
        DispatchQueue.main.async {
            guard let completion = self.reserveAuthorization(completion) else {
                return
            }
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
                    self.authorizeWithSignIn(scopes: options.scopes, presenting: viewController, completion: completion)
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
        DispatchQueue.main.async {
            guard let completion = self.reserveAuthorization(completion) else {
                return
            }
            guard let viewController = self.plugin.bridge?.viewController else {
                completion(nil, CustomError.viewControllerUnavailable)
                return
            }
            let signInCompletion: (GIDSignInResult?, Error?) -> Void = { result, error in
                self.resolveSignIn(result, error, completion: completion)
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
            if let error = error, self.isGrantInvalidError(error) {
                self.authorizeWithSignIn(scopes: scopes, presenting: viewController, completion: completion)
                return
            }
            if let error = error {
                completion(nil, error)
                return
            }
            completion(AuthorizeResult(accessToken: user.accessToken.tokenString, serverAuthCode: nil), nil)
        }
    }

    private func authorizeWithSignIn(
        scopes: [String],
        presenting viewController: UIViewController,
        completion: @escaping (_ result: AuthorizeResult?, _ error: Error?) -> Void
    ) {
        GIDSignIn.sharedInstance.signIn(withPresenting: viewController, hint: nil, additionalScopes: scopes) { result, error in
            self.resolveAuthorization(result, error, completion: completion)
        }
    }

    private func isCanceledError(_ error: Error) -> Bool {
        return (error as? GIDSignInError)?.code == .canceled
    }

    /// Returns `true` if Google rejected the saved grant (e.g. revoked or expired),
    /// so that only a new interactive sign-in can recover.
    private func isGrantInvalidError(_ error: Error) -> Bool {
        // Value of `OIDOAuthTokenErrorDomain` from AppAuth, which is not a direct dependency of this plugin.
        return (error as NSError).domain == "org.openid.appauth.oauth_token"
    }

    /// Reserves the single interactive Google Sign-In flow, because `GIDSignIn` silently drops
    /// the completion of a running flow when a new one starts.
    /// Returns a completion that releases the reservation, or `nil` if another flow is in progress.
    private func reserveAuthorization<T>(
        _ completion: @escaping (_ result: T?, _ error: Error?) -> Void
    ) -> ((_ result: T?, _ error: Error?) -> Void)? {
        if isAuthorizationInProgress {
            completion(nil, CustomError.authorizationInProgress)
            return nil
        }
        isAuthorizationInProgress = true
        return { result, error in
            self.isAuthorizationInProgress = false
            completion(result, error)
        }
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

    private func resolveSignIn(
        _ result: GIDSignInResult?,
        _ error: Error?,
        completion: @escaping (_ result: SignInResult?, _ error: Error?) -> Void
    ) {
        if let error = error {
            if isCanceledError(error) {
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
        if scopes != nil && !(scopes?.isEmpty ?? true) {
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

    private func restorePreviousUser(completion: @escaping (_ user: GIDGoogleUser?, _ error: Error?) -> Void) {
        if let currentUser = GIDSignIn.sharedInstance.currentUser {
            completion(currentUser, nil)
            return
        }
        guard GIDSignIn.sharedInstance.hasPreviousSignIn() else {
            completion(nil, nil)
            return
        }
        GIDSignIn.sharedInstance.restorePreviousSignIn { user, error in
            if let error = error, self.isGrantInvalidError(error) {
                completion(nil, nil)
                return
            }
            completion(user, error)
        }
    }
}
