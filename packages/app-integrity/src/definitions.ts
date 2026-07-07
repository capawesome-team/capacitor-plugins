/**
 * @since 0.1.0
 */
export interface AppIntegrityPlugin {
  /**
   * Attest a key generated with `generateKey()` using Apple's App Attest service.
   *
   * The returned attestation object must be sent to your server, which verifies
   * it with Apple and stores the key identifier for future assertions.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  attestKey(options: AttestKeyOptions): Promise<AttestKeyResult>;
  /**
   * Generate an assertion for the given client data using an attested key.
   *
   * The returned assertion must be sent to your server along with the client
   * data, where it is verified using the previously stored key identifier.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  generateAssertion(
    options: GenerateAssertionOptions,
  ): Promise<GenerateAssertionResult>;
  /**
   * Generate a new App Attest key pair.
   *
   * The private key is stored in the Secure Enclave. Store the returned key
   * identifier in your app to attest the key and generate assertions later.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  generateKey(): Promise<GenerateKeyResult>;
  /**
   * Check whether app integrity attestation is available on this device.
   *
   * On Android, this checks whether Google Play Services is available.
   * On iOS, this checks whether the App Attest service is supported.
   * On iOS, the App Attest service is not supported on simulators.
   *
   * @since 0.1.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Prepare the integrity token provider for standard integrity token requests.
   *
   * Call this method once, for example at app start, before calling
   * `requestIntegrityToken(...)` with a request hash. The preparation can take
   * several seconds, so it should be done well ahead of the first request.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  prepareIntegrityToken(options: PrepareIntegrityTokenOptions): Promise<void>;
  /**
   * Request an integrity token from the Play Integrity API.
   *
   * Provide a `requestHash` for a standard request (recommended, requires a
   * prior call to `prepareIntegrityToken(...)`) or a `nonce` for a classic request.
   *
   * The returned token must be sent to your server, which decrypts and
   * verifies it via Google's servers.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  requestIntegrityToken(
    options: RequestIntegrityTokenOptions,
  ): Promise<RequestIntegrityTokenResult>;
}

/**
 * @since 0.1.0
 */
export interface AttestKeyOptions {
  /**
   * The one-time challenge received from your server, encoded as a
   * base64 string.
   *
   * The challenge is hashed with SHA-256 on the device before it is passed
   * to the App Attest service.
   *
   * @example 'dGhpc2lzYWNoYWxsZW5nZQ=='
   * @since 0.1.0
   */
  challenge: string;
  /**
   * The identifier of the key to attest, as returned by `generateKey()`.
   *
   * @example 'Kh0DIEwVJTDJUyIRZ4M9BvJn/i4RSSGDkFvUZOaSm5g='
   * @since 0.1.0
   */
  keyId: string;
}

/**
 * @since 0.1.0
 */
export interface AttestKeyResult {
  /**
   * The attestation object, encoded as a base64 string.
   *
   * Send this to your server for verification with Apple.
   *
   * @since 0.1.0
   */
  attestationObject: string;
}

/**
 * @since 0.1.0
 */
export interface GenerateAssertionOptions {
  /**
   * The client data to sign, encoded as a base64 string.
   *
   * This is usually a JSON payload that includes a one-time challenge
   * received from your server. The client data is hashed with SHA-256 on the
   * device before it is passed to the App Attest service.
   *
   * @example 'eyJjaGFsbGVuZ2UiOiJkR2hwYzJsellXTm9ZV3hzWlc1blpRPT0ifQ=='
   * @since 0.1.0
   */
  clientData: string;
  /**
   * The identifier of an attested key, as returned by `generateKey()`.
   *
   * @example 'Kh0DIEwVJTDJUyIRZ4M9BvJn/i4RSSGDkFvUZOaSm5g='
   * @since 0.1.0
   */
  keyId: string;
}

/**
 * @since 0.1.0
 */
export interface GenerateAssertionResult {
  /**
   * The assertion object, encoded as a base64 string.
   *
   * Send this to your server for verification.
   *
   * @since 0.1.0
   */
  assertion: string;
}

/**
 * @since 0.1.0
 */
export interface GenerateKeyResult {
  /**
   * The identifier of the generated key pair.
   *
   * @example 'Kh0DIEwVJTDJUyIRZ4M9BvJn/i4RSSGDkFvUZOaSm5g='
   * @since 0.1.0
   */
  keyId: string;
}

/**
 * @since 0.1.0
 */
export interface IsAvailableResult {
  /**
   * Whether app integrity attestation is available on this device.
   *
   * @example true
   * @since 0.1.0
   */
  available: boolean;
}

/**
 * @since 0.1.0
 */
export interface PrepareIntegrityTokenOptions {
  /**
   * The Google Cloud project number of the project that is linked
   * to your Play Console developer account.
   *
   * @example 123456789012
   * @since 0.1.0
   */
  cloudProjectNumber: number;
}

/**
 * @since 0.1.0
 */
export interface RequestIntegrityTokenOptions {
  /**
   * The Google Cloud project number of the project that is linked
   * to your Play Console developer account.
   *
   * Only used for classic requests. It is required if your app is
   * distributed outside of Google Play.
   *
   * @example 123456789012
   * @since 0.1.0
   */
  cloudProjectNumber?: number;
  /**
   * The one-time nonce for a classic request, encoded as a base64
   * web-safe no-wrap string.
   *
   * Provide either `nonce` or `requestHash`.
   *
   * @example 'dGhpc2lzYW5vbmNl'
   * @since 0.1.0
   */
  nonce?: string;
  /**
   * The request hash for a standard request.
   *
   * Requires a prior call to `prepareIntegrityToken(...)`.
   *
   * Provide either `nonce` or `requestHash`.
   *
   * @example '2cp24z...'
   * @since 0.1.0
   */
  requestHash?: string;
}

/**
 * @since 0.1.0
 */
export interface RequestIntegrityTokenResult {
  /**
   * The integrity token.
   *
   * Send this to your server, which decrypts and verifies it via
   * Google's servers.
   *
   * @since 0.1.0
   */
  token: string;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The Play Integrity API is not available on this device.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  ApiNotAvailable = 'API_NOT_AVAILABLE',
  /**
   * The calling app is not installed.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  AppNotInstalled = 'APP_NOT_INSTALLED',
  /**
   * The calling app UID does not match the one from the package manager.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  AppUidMismatch = 'APP_UID_MISMATCH',
  /**
   * The assertion could not be generated.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  AssertionFailed = 'ASSERTION_FAILED',
  /**
   * The attestation could not be generated.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  AttestationFailed = 'ATTESTATION_FAILED',
  /**
   * The app could not bind to the integrity service in the Play Store.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  CannotBindToService = 'CANNOT_BIND_TO_SERVICE',
  /**
   * A transient error occurred on the device. Retry with an exponential backoff.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  ClientTransientError = 'CLIENT_TRANSIENT_ERROR',
  /**
   * The Google server is currently unavailable. Retry with an exponential backoff.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  GoogleServerUnavailable = 'GOOGLE_SERVER_UNAVAILABLE',
  /**
   * The integrity token provider is invalid. Call `prepareIntegrityToken(...)` again.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  IntegrityTokenProviderInvalid = 'INTEGRITY_TOKEN_PROVIDER_INVALID',
  /**
   * An internal error occurred. Retry with an exponential backoff.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  InternalError = 'INTERNAL_ERROR',
  /**
   * The key is invalid or was not recognized by the App Attest service.
   * Generate and attest a new key.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  InvalidKey = 'INVALID_KEY',
  /**
   * No network connection is available. Retry when the device is online.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  NetworkError = 'NETWORK_ERROR',
  /**
   * Google Play Services is not available on this device.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  PlayServicesNotFound = 'PLAY_SERVICES_NOT_FOUND',
  /**
   * Google Play Services needs to be updated.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  PlayServicesVersionOutdated = 'PLAY_SERVICES_VERSION_OUTDATED',
  /**
   * No official Play Store app was found on the device.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  PlayStoreNotFound = 'PLAY_STORE_NOT_FOUND',
  /**
   * The Play Store app needs to be updated.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  PlayStoreVersionOutdated = 'PLAY_STORE_VERSION_OUTDATED',
  /**
   * The App Attest service is currently unavailable. Retry later.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  ServerUnavailable = 'SERVER_UNAVAILABLE',
  /**
   * The calling app is making too many requests and has been throttled.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  TooManyRequests = 'TOO_MANY_REQUESTS',
}
