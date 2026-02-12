import Foundation
import AuthenticationServices

@objc public class AppleSignIn: NSObject {

    private var completion: ((_ result: SignInResult?, _ error: Error?) -> Void)?

    @objc public func signIn(_ options: SignInOptions, presentationContextProvider: ASAuthorizationControllerPresentationContextProviding, completion: @escaping (_ result: SignInResult?, _ error: Error?) -> Void) {
        self.completion = completion

        let provider = ASAuthorizationAppleIDProvider()
        let request = provider.createRequest()
        if !options.scopes.isEmpty {
            request.requestedScopes = options.scopes
        }
        if let nonce = options.nonce {
            request.nonce = nonce
        }

        let controller = ASAuthorizationController(authorizationRequests: [request])
        controller.delegate = self
        controller.presentationContextProvider = presentationContextProvider
        controller.performRequests()
    }
}

extension AppleSignIn: ASAuthorizationControllerDelegate {
    public func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        guard let credential = authorization.credential as? ASAuthorizationAppleIDCredential else {
            completion?(nil, CustomError.signInFailed)
            completion = nil
            return
        }
        let result = SignInResult(credential: credential)
        completion?(result, nil)
        completion = nil
    }

    public func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        let asError = error as? ASAuthorizationError
        if asError?.code == .canceled {
            completion?(nil, CustomError.signInCanceled)
        } else {
            completion?(nil, CustomError.signInFailed)
        }
        completion = nil
    }
}
