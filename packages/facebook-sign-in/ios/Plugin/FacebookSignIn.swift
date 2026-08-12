import Foundation
import Capacitor
import FBSDKCoreKit
import FBSDKLoginKit

@objc public class FacebookSignInImpl: NSObject {
    private let loginManager = LoginManager()
    private let plugin: FacebookSignInPlugin

    public init(plugin: FacebookSignInPlugin) {
        self.plugin = plugin
        super.init()
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handleOpenUrl(_:)),
            name: Notification.Name.capacitorOpenURL,
            object: nil
        )
    }

    @objc public func getCurrentAccessToken(completion: @escaping (_ result: GetCurrentAccessTokenResult?, _ error: Error?) -> Void) {
        var accessToken = AccessToken.current
        if accessToken?.isExpired == true {
            accessToken = nil
        }
        completion(GetCurrentAccessTokenResult(accessToken: accessToken), nil)
    }

    @objc public func initialize(_ options: InitializeOptions, completion: @escaping (_ error: Error?) -> Void) {
        DispatchQueue.main.async {
            if let appId = options.appId {
                Settings.shared.appID = appId
            }
            if let clientToken = options.clientToken {
                Settings.shared.clientToken = clientToken
            }
            _ = ApplicationDelegate.shared.application(UIApplication.shared, didFinishLaunchingWithOptions: nil)
            completion(nil)
        }
    }

    @objc public func signIn(_ options: SignInOptions, completion: @escaping (_ result: SignInResult?, _ error: Error?) -> Void) {
        let tracking: LoginTracking = options.limitedLogin ? .limited : .enabled
        let configuration: LoginConfiguration?
        if let nonce = options.nonce {
            configuration = LoginConfiguration(permissions: options.permissions, tracking: tracking, nonce: nonce)
        } else {
            configuration = LoginConfiguration(permissions: options.permissions, tracking: tracking)
        }
        guard let configuration = configuration else {
            completion(nil, CustomError.invalidLoginConfiguration)
            return
        }
        DispatchQueue.main.async {
            let viewController = self.plugin.bridge?.viewController
            self.loginManager.logIn(viewController: viewController, configuration: configuration) { result in
                switch result {
                case .cancelled:
                    completion(nil, CustomError.signInCanceled)
                case .failed(let error):
                    completion(nil, error)
                case .success(_, _, let accessToken):
                    self.handleSignInSuccess(accessToken: accessToken, completion: completion)
                }
            }
        }
    }

    @objc public func signOut(completion: @escaping (_ error: Error?) -> Void) {
        loginManager.logOut()
        completion(nil)
    }

    @objc private func handleOpenUrl(_ notification: Notification) {
        guard let object = notification.object as? [String: Any], let url = object["url"] as? URL else {
            return
        }
        let options = object["options"] as? [UIApplication.OpenURLOptionsKey: Any] ?? [:]
        DispatchQueue.main.async {
            _ = ApplicationDelegate.shared.application(UIApplication.shared, open: url, options: options)
        }
    }

    private func handleSignInSuccess(accessToken: AccessToken?, completion: @escaping (_ result: SignInResult?, _ error: Error?) -> Void) {
        let authenticationToken = AuthenticationToken.current?.tokenString
        if let accessToken = accessToken {
            let parameters = ["fields": "id,name,email,picture.width(720).height(720)"]
            let request = GraphRequest(graphPath: "me", parameters: parameters)
            request.start { _, result, error in
                if let error = error {
                    completion(nil, error)
                    return
                }
                let values = result as? [String: Any]
                var profileImageUrl: String?
                if let picture = values?["picture"] as? [String: Any], let data = picture["data"] as? [String: Any] {
                    profileImageUrl = data["url"] as? String
                }
                let signInResult = SignInResult(
                    accessToken: accessToken,
                    authenticationToken: authenticationToken,
                    profileId: values?["id"] as? String ?? accessToken.userID,
                    profileName: values?["name"] as? String,
                    profileEmail: values?["email"] as? String,
                    profileImageUrl: profileImageUrl
                )
                completion(signInResult, nil)
            }
        } else {
            guard let profile = Profile.current else {
                completion(nil, CustomError.profileMissing)
                return
            }
            let signInResult = SignInResult(
                accessToken: nil,
                authenticationToken: authenticationToken,
                profileId: profile.userID,
                profileName: profile.name,
                profileEmail: profile.email,
                profileImageUrl: profile.imageURL?.absoluteString
            )
            completion(signInResult, nil)
        }
    }
}
