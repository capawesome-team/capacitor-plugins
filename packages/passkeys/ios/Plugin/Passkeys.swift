import Foundation
import AuthenticationServices

@objc public class Passkeys: NSObject {

    private var authorizationController: ASAuthorizationController?
    private var createPasskeyCompletion: ((_ result: CreatePasskeyResult?, _ error: Error?) -> Void)?
    private var getPasskeyCompletion: ((_ result: GetPasskeyResult?, _ error: Error?) -> Void)?

    @objc public func createPasskey(_ options: CreatePasskeyOptions, presentationContextProvider: ASAuthorizationControllerPresentationContextProviding, completion: @escaping (_ result: CreatePasskeyResult?, _ error: Error?) -> Void) {
        self.createPasskeyCompletion = completion

        let provider = ASAuthorizationPlatformPublicKeyCredentialProvider(relyingPartyIdentifier: options.rpId)
        let request = provider.createCredentialRegistrationRequest(challenge: options.challenge, name: options.userName, userID: options.userId)
        if let attestation = options.attestation {
            request.attestationPreference = ASAuthorizationPublicKeyCredentialAttestationKind(rawValue: attestation)
        }
        if let userVerification = options.userVerification {
            request.userVerificationPreference = ASAuthorizationPublicKeyCredentialUserVerificationPreference(rawValue: userVerification)
        }
        if #available(iOS 17.4, *) {
            if !options.excludeCredentialIds.isEmpty {
                request.excludedCredentials = options.excludeCredentialIds.map {
                    ASAuthorizationPlatformPublicKeyCredentialDescriptor(credentialID: $0)
                }
            }
        }

        performRequest(request, presentationContextProvider: presentationContextProvider)
    }

    @objc public func getPasskey(_ options: GetPasskeyOptions, presentationContextProvider: ASAuthorizationControllerPresentationContextProviding, completion: @escaping (_ result: GetPasskeyResult?, _ error: Error?) -> Void) {
        self.getPasskeyCompletion = completion

        let provider = ASAuthorizationPlatformPublicKeyCredentialProvider(relyingPartyIdentifier: options.rpId)
        let request = provider.createCredentialAssertionRequest(challenge: options.challenge)
        if !options.allowCredentialIds.isEmpty {
            request.allowedCredentials = options.allowCredentialIds.map {
                ASAuthorizationPlatformPublicKeyCredentialDescriptor(credentialID: $0)
            }
        }
        if let userVerification = options.userVerification {
            request.userVerificationPreference = ASAuthorizationPublicKeyCredentialUserVerificationPreference(rawValue: userVerification)
        }

        performRequest(request, presentationContextProvider: presentationContextProvider)
    }

    @objc public func isAvailable(completion: @escaping (_ result: IsAvailableResult?, _ error: Error?) -> Void) {
        let result = IsAvailableResult(available: true)
        completion(result, nil)
    }

    private func mapError(_ error: Error) -> Error {
        guard let authorizationError = error as? ASAuthorizationError else {
            return error
        }
        if authorizationError.code == .canceled {
            return CustomError.canceled
        }
        if error.localizedDescription.contains("is not associated with domain") {
            return CustomError.domainNotAssociated
        }
        return error
    }

    private func performRequest(_ request: ASAuthorizationRequest, presentationContextProvider: ASAuthorizationControllerPresentationContextProviding) {
        DispatchQueue.main.async {
            let controller = ASAuthorizationController(authorizationRequests: [request])
            controller.delegate = self
            controller.presentationContextProvider = presentationContextProvider
            self.authorizationController = controller
            controller.performRequests()
        }
    }
}

extension Passkeys: ASAuthorizationControllerDelegate {
    public func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        authorizationController = nil
        switch authorization.credential {
        case let credential as ASAuthorizationPlatformPublicKeyCredentialRegistration:
            if let attestationObject = credential.rawAttestationObject {
                let result = CreatePasskeyResult(
                    attestationObject: attestationObject,
                    clientDataJSON: credential.rawClientDataJSON,
                    credentialId: credential.credentialID
                )
                createPasskeyCompletion?(result, nil)
            } else {
                createPasskeyCompletion?(nil, CustomError.createFailed)
            }
        case let credential as ASAuthorizationPlatformPublicKeyCredentialAssertion:
            let result = GetPasskeyResult(
                authenticatorData: credential.rawAuthenticatorData,
                clientDataJSON: credential.rawClientDataJSON,
                credentialId: credential.credentialID,
                signature: credential.signature,
                userHandle: credential.userID
            )
            getPasskeyCompletion?(result, nil)
        default:
            createPasskeyCompletion?(nil, CustomError.createFailed)
            getPasskeyCompletion?(nil, CustomError.getFailed)
        }
        createPasskeyCompletion = nil
        getPasskeyCompletion = nil
    }

    public func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        authorizationController = nil
        let mappedError = mapError(error)
        createPasskeyCompletion?(nil, mappedError)
        getPasskeyCompletion?(nil, mappedError)
        createPasskeyCompletion = nil
        getPasskeyCompletion = nil
    }
}
