export interface AppleSignInPlugin {
  /**
   * Initialize the plugin.
   *
   * This method must be called before `signIn()` on **Android** and **Web**.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   */
  initialize(options: InitializeOptions): Promise<void>;
  /**
   * Sign in with Apple.
   *
   * @since 0.1.0
   */
  signIn(options?: SignInOptions): Promise<SignInResult>;
}

/**
 * @since 0.1.0
 */
export interface InitializeOptions {
  /**
   * The Apple Service ID to use for sign-in.
   *
   * @since 0.1.0
   * @example "com.example.app.signin"
   */
  clientId: string;
}

/**
 * @since 0.1.0
 */
export interface SignInOptions {
  /**
   * The OAuth redirect URL to use for sign-in.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   * @example "https://example.com/callback"
   */
  redirectUrl?: string;
  /**
   * The scopes to request during sign-in.
   *
   * @since 0.1.0
   */
  scopes?: SignInScope[];
  /**
   * A nonce for replay protection.
   *
   * @since 0.1.0
   */
  nonce?: string;
  /**
   * A state value for CSRF protection.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   */
  state?: string;
}

/**
 * @since 0.1.0
 */
export interface SignInResult {
  /**
   * The authorization code.
   *
   * @since 0.1.0
   */
  authorizationCode: string;
  /**
   * The ID token (JWT).
   *
   * @since 0.1.0
   */
  idToken: string;
  /**
   * The stable Apple user identifier.
   *
   * On **Android** and **Web**, this is decoded from the JWT `sub` claim.
   *
   * @since 0.1.0
   * @example "001234.abcdef1234567890abcdef1234567890.1234"
   */
  user: string;
  /**
   * The user's email address.
   *
   * On **iOS**, this is only provided on the first sign-in.
   *
   * @since 0.1.0
   */
  email: string | null;
  /**
   * The user's given name.
   *
   * On **iOS**, this is only provided on the first sign-in.
   *
   * @since 0.1.0
   */
  givenName: string | null;
  /**
   * The user's family name.
   *
   * On **iOS**, this is only provided on the first sign-in.
   *
   * @since 0.1.0
   */
  familyName: string | null;
  /**
   * The state value from the sign-in request.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   */
  state?: string;
  /**
   * The real user status.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  realUserStatus?: RealUserStatus;
}

/**
 * @since 0.1.0
 */
export enum SignInScope {
  /**
   * Request the user's email address.
   *
   * @since 0.1.0
   */
  Email = 'EMAIL',
  /**
   * Request the user's full name.
   *
   * @since 0.1.0
   */
  FullName = 'FULL_NAME',
}

/**
 * @since 0.1.0
 */
export enum RealUserStatus {
  /**
   * The user appears to be a real person.
   *
   * @since 0.1.0
   */
  LikelyReal = 'LIKELY_REAL',
  /**
   * The system can't determine whether the user is a real person.
   *
   * @since 0.1.0
   */
  Unknown = 'UNKNOWN',
  /**
   * The real user status is not supported on this platform.
   *
   * @since 0.1.0
   */
  Unsupported = 'UNSUPPORTED',
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The sign-in was canceled by the user.
   *
   * @since 0.1.0
   */
  SignInCanceled = 'SIGN_IN_CANCELED',
}
