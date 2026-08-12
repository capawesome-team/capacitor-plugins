export interface PasskeysPlugin {
  /**
   * Create (register) a new passkey.
   *
   * The options mirror the WebAuthn `PublicKeyCredentialCreationOptions` JSON
   * serialization so that the values provided by any WebAuthn server library
   * can be passed through unchanged.
   *
   * @since 0.1.0
   */
  createPasskey(options: CreatePasskeyOptions): Promise<CreatePasskeyResult>;
  /**
   * Get (authenticate with) an existing passkey.
   *
   * The options mirror the WebAuthn `PublicKeyCredentialRequestOptions` JSON
   * serialization so that the values provided by any WebAuthn server library
   * can be passed through unchanged.
   *
   * @since 0.1.0
   */
  getPasskey(options: GetPasskeyOptions): Promise<GetPasskeyResult>;
  /**
   * Check if passkeys are available on this device.
   *
   * On **Android**, this returns `true` if the device runs Android 9 (API level 28) or higher.
   * On **iOS**, this always returns `true`.
   * On **Web**, this returns `true` if the browser supports WebAuthn and
   * a user-verifying platform authenticator is available.
   *
   * @since 0.1.0
   */
  isAvailable(): Promise<IsAvailableResult>;
}

/**
 * @since 0.1.0
 */
export interface CreatePasskeyOptions {
  /**
   * The attestation conveyance preference.
   *
   * @since 0.1.0
   * @default 'none'
   * @example 'none'
   */
  attestation?: PasskeyAttestation;
  /**
   * Criteria that the authenticator must meet.
   *
   * @since 0.1.0
   */
  authenticatorSelection?: PasskeyAuthenticatorSelection;
  /**
   * The challenge provided by the relying party server as a
   * base64url-encoded string.
   *
   * @since 0.1.0
   * @example 'dGhpc2lzYWNoYWxsZW5nZQ'
   */
  challenge: string;
  /**
   * Credentials that already exist for the user, so that the authenticator
   * does not create a second passkey for the same account.
   *
   * On **iOS**, this option is only applied on iOS 17.4 and later.
   *
   * @since 0.1.0
   */
  excludeCredentials?: PasskeyCredentialDescriptor[];
  /**
   * The public key credential types and algorithms that the relying
   * party server supports, ordered from most to least preferred.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   * @example [{ alg: -7, type: 'public-key' }, { alg: -257, type: 'public-key' }]
   */
  pubKeyCredParams: PasskeyCredentialParameter[];
  /**
   * The relying party for which the passkey is created.
   *
   * @since 0.1.0
   */
  rp: PasskeyRelyingParty;
  /**
   * The time in milliseconds that the caller is willing to wait for the
   * operation to complete.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   * @example 60000
   */
  timeout?: number;
  /**
   * The user account for which the passkey is created.
   *
   * @since 0.1.0
   */
  user: PasskeyUser;
}

/**
 * The response of the authenticator for the registration of a new passkey.
 *
 * This mirrors the WebAuthn `AuthenticatorAttestationResponse` JSON
 * serialization.
 *
 * @since 0.1.0
 */
export interface CreatePasskeyResponse {
  /**
   * The attestation object as a base64url-encoded string.
   *
   * @since 0.1.0
   */
  attestationObject: string;
  /**
   * The authenticator data as a base64url-encoded string.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   */
  authenticatorData?: string;
  /**
   * The client data as a base64url-encoded string.
   *
   * @since 0.1.0
   */
  clientDataJSON: string;
  /**
   * The DER-encoded public key as a base64url-encoded string.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   */
  publicKey?: string;
  /**
   * The COSE algorithm identifier of the public key.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   * @example -7
   */
  publicKeyAlgorithm?: number;
  /**
   * The transports that the authenticator supports.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   * @example ['internal', 'hybrid']
   */
  transports?: PasskeyTransport[];
}

/**
 * The result of the passkey creation.
 *
 * This mirrors the WebAuthn `RegistrationResponseJSON` so it can be
 * passed to any WebAuthn server library for verification.
 *
 * @since 0.1.0
 */
export interface CreatePasskeyResult {
  /**
   * The attachment of the authenticator that created the passkey.
   *
   * @since 0.1.0
   * @example 'platform'
   */
  authenticatorAttachment?: PasskeyAuthenticatorAttachment;
  /**
   * The credential identifier as a base64url-encoded string.
   *
   * @since 0.1.0
   */
  id: string;
  /**
   * The raw credential identifier as a base64url-encoded string.
   *
   * @since 0.1.0
   */
  rawId: string;
  /**
   * The response of the authenticator.
   *
   * @since 0.1.0
   */
  response: CreatePasskeyResponse;
  /**
   * The credential type.
   *
   * @since 0.1.0
   * @example 'public-key'
   */
  type: 'public-key';
}

/**
 * @since 0.1.0
 */
export interface GetPasskeyOptions {
  /**
   * The credentials that are acceptable to the relying party server.
   *
   * If not provided, the user can select from any discoverable
   * credential (passkey) of the relying party.
   *
   * @since 0.1.0
   */
  allowCredentials?: PasskeyCredentialDescriptor[];
  /**
   * The challenge provided by the relying party server as a
   * base64url-encoded string.
   *
   * @since 0.1.0
   * @example 'dGhpc2lzYWNoYWxsZW5nZQ'
   */
  challenge: string;
  /**
   * The identifier of the relying party.
   *
   * @since 0.1.0
   * @example 'example.com'
   */
  rpId: string;
  /**
   * The time in milliseconds that the caller is willing to wait for the
   * operation to complete.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   * @example 60000
   */
  timeout?: number;
  /**
   * The user verification requirement.
   *
   * @since 0.1.0
   * @default 'preferred'
   * @example 'required'
   */
  userVerification?: PasskeyUserVerification;
}

/**
 * The response of the authenticator for the authentication with an
 * existing passkey.
 *
 * This mirrors the WebAuthn `AuthenticatorAssertionResponse` JSON
 * serialization.
 *
 * @since 0.1.0
 */
export interface GetPasskeyResponse {
  /**
   * The authenticator data as a base64url-encoded string.
   *
   * @since 0.1.0
   */
  authenticatorData: string;
  /**
   * The client data as a base64url-encoded string.
   *
   * @since 0.1.0
   */
  clientDataJSON: string;
  /**
   * The signature as a base64url-encoded string.
   *
   * @since 0.1.0
   */
  signature: string;
  /**
   * The user handle (the `user.id` provided during creation) as a
   * base64url-encoded string.
   *
   * @since 0.1.0
   */
  userHandle?: string;
}

/**
 * The result of the passkey authentication.
 *
 * This mirrors the WebAuthn `AuthenticationResponseJSON` so it can be
 * passed to any WebAuthn server library for verification.
 *
 * @since 0.1.0
 */
export interface GetPasskeyResult {
  /**
   * The attachment of the authenticator that provided the passkey.
   *
   * @since 0.1.0
   * @example 'platform'
   */
  authenticatorAttachment?: PasskeyAuthenticatorAttachment;
  /**
   * The credential identifier as a base64url-encoded string.
   *
   * @since 0.1.0
   */
  id: string;
  /**
   * The raw credential identifier as a base64url-encoded string.
   *
   * @since 0.1.0
   */
  rawId: string;
  /**
   * The response of the authenticator.
   *
   * @since 0.1.0
   */
  response: GetPasskeyResponse;
  /**
   * The credential type.
   *
   * @since 0.1.0
   * @example 'public-key'
   */
  type: 'public-key';
}

/**
 * @since 0.1.0
 */
export interface IsAvailableResult {
  /**
   * Whether or not passkeys are available on this device.
   *
   * @since 0.1.0
   * @example true
   */
  available: boolean;
}

/**
 * Criteria that the authenticator must meet to create a passkey.
 *
 * @since 0.1.0
 */
export interface PasskeyAuthenticatorSelection {
  /**
   * The authenticator attachment modality.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   * @example 'platform'
   */
  authenticatorAttachment?: PasskeyAuthenticatorAttachment;
  /**
   * Whether or not a discoverable credential (passkey) is required.
   *
   * This property is retained for backwards compatibility with
   * WebAuthn Level 1. Prefer `residentKey`.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   * @example true
   */
  requireResidentKey?: boolean;
  /**
   * The extent to which the relying party desires to create a
   * discoverable credential (passkey).
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   * @example 'required'
   */
  residentKey?: PasskeyResidentKey;
  /**
   * The user verification requirement.
   *
   * @since 0.1.0
   * @default 'preferred'
   * @example 'required'
   */
  userVerification?: PasskeyUserVerification;
}

/**
 * A descriptor that identifies a specific credential.
 *
 * @since 0.1.0
 */
export interface PasskeyCredentialDescriptor {
  /**
   * The credential identifier as a base64url-encoded string.
   *
   * @since 0.1.0
   */
  id: string;
  /**
   * The transports that the authenticator of the credential supports.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   * @example ['internal', 'hybrid']
   */
  transports?: PasskeyTransport[];
  /**
   * The credential type.
   *
   * @since 0.1.0
   * @example 'public-key'
   */
  type: 'public-key';
}

/**
 * A public key credential type and algorithm that the relying party
 * server supports.
 *
 * @since 0.1.0
 */
export interface PasskeyCredentialParameter {
  /**
   * The COSE algorithm identifier, e.g. `-7` for ES256 or `-257` for RS256.
   *
   * @since 0.1.0
   * @example -7
   */
  alg: number;
  /**
   * The credential type.
   *
   * @since 0.1.0
   * @example 'public-key'
   */
  type: 'public-key';
}

/**
 * The relying party for which a passkey is created.
 *
 * @since 0.1.0
 */
export interface PasskeyRelyingParty {
  /**
   * The identifier of the relying party.
   *
   * This must be a registrable domain suffix of (or equal to) the
   * domain that the app is associated with.
   *
   * @since 0.1.0
   * @example 'example.com'
   */
  id: string;
  /**
   * The human-readable name of the relying party.
   *
   * @since 0.1.0
   * @example 'Example Inc.'
   */
  name: string;
}

/**
 * The user account for which a passkey is created.
 *
 * @since 0.1.0
 */
export interface PasskeyUser {
  /**
   * The human-readable display name of the user account.
   *
   * @since 0.1.0
   * @example 'Jane Doe'
   */
  displayName: string;
  /**
   * The user handle of the user account as a base64url-encoded string.
   *
   * @since 0.1.0
   * @example 'anVzdGFyYW5kb21pZA'
   */
  id: string;
  /**
   * The human-readable name of the user account, e.g. a username
   * or email address.
   *
   * @since 0.1.0
   * @example 'jane.doe@example.com'
   */
  name: string;
}

/**
 * The attestation conveyance preference.
 *
 * @since 0.1.0
 */
export type PasskeyAttestation = 'direct' | 'enterprise' | 'indirect' | 'none';

/**
 * The authenticator attachment modality.
 *
 * @since 0.1.0
 */
export type PasskeyAuthenticatorAttachment = 'cross-platform' | 'platform';

/**
 * The extent to which the relying party desires to create a
 * discoverable credential (passkey).
 *
 * @since 0.1.0
 */
export type PasskeyResidentKey = 'discouraged' | 'preferred' | 'required';

/**
 * A transport that an authenticator supports.
 *
 * @since 0.1.0
 */
export type PasskeyTransport =
  | 'ble'
  | 'hybrid'
  | 'internal'
  | 'nfc'
  | 'smart-card'
  | 'usb';

/**
 * The user verification requirement.
 *
 * - `discouraged`: The relying party prefers no user verification.
 * - `preferred`: The relying party prefers user verification but will
 *   not fail the operation without it.
 * - `required`: The relying party requires user verification.
 *
 * @since 0.1.0
 */
export type PasskeyUserVerification = 'discouraged' | 'preferred' | 'required';

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The operation was canceled by the user.
   *
   * @since 0.1.0
   */
  Canceled = 'CANCELED',
  /**
   * The passkey could not be created.
   *
   * @since 0.1.0
   */
  CreateFailed = 'CREATE_FAILED',
  /**
   * The app is not associated with the relying party domain.
   *
   * On **Android**, make sure that the Digital Asset Links file is set up
   * correctly. On **iOS**, make sure that the Associated Domains
   * entitlement is set up correctly.
   *
   * @since 0.1.0
   */
  DomainNotAssociated = 'DOMAIN_NOT_ASSOCIATED',
  /**
   * The passkey could not be retrieved.
   *
   * @since 0.1.0
   */
  GetFailed = 'GET_FAILED',
  /**
   * No matching passkey was found.
   *
   * @since 0.1.0
   */
  NoCredential = 'NO_CREDENTIAL',
  /**
   * Passkeys are not supported on this device.
   *
   * @since 0.1.0
   */
  NotSupported = 'NOT_SUPPORTED',
}
