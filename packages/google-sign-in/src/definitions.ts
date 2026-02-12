/**
 * @since 0.1.0
 */
export interface GoogleSignInPlugin {
  /**
   * Initialize the Google Sign-In plugin.
   *
   * This method must be called once before all other methods.
   *
   * @since 0.1.0
   */
  initialize(options?: InitializeOptions): Promise<void>;
  /**
   * Start the Google Sign-In flow.
   *
   * @since 0.1.0
   */
  signIn(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Sign out the current user.
   *
   * On Android, this clears the credential state.
   * On iOS, this signs out from the Google Sign-In SDK.
   * On Web, this disables auto-select for One Tap.
   *
   * @since 0.1.0
   */
  signOut(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface InitializeOptions {
  /**
   * The web client ID from Google Cloud Console.
   *
   * Required on Android and Web.
   * On Android, this is passed as the server client ID to the Credential Manager API
   * and the AuthorizationClient API.
   * On iOS, this is used as the server client ID for the Google Sign-In SDK.
   * On Web, this is used to initialize the Google Sign-In JavaScript API.
   *
   * @since 0.1.0
   * @example "123456789-abc.apps.googleusercontent.com"
   */
  clientId?: string;
  /**
   * The iOS client ID from Google Cloud Console.
   *
   * Only available on iOS.
   * If not provided, the plugin falls back to the `GIDClientID` value in `Info.plist`.
   *
   * @since 0.1.0
   * @example "123456789-xyz.apps.googleusercontent.com"
   */
  iosClientId?: string;
  /**
   * The OAuth scopes to request.
   *
   * If provided, the plugin will request authorization in addition to authentication.
   * This enables `accessToken` and `serverAuthCode` in the sign-in result.
   *
   * @since 0.1.0
   * @example ["https://www.googleapis.com/auth/drive.file"]
   */
  scopes?: string[];
}

/**
 * @since 0.1.0
 */
export interface SignInOptions {
  /**
   * A nonce to prevent replay attacks.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   */
  nonce?: string;
}

/**
 * @since 0.1.0
 */
export interface SignInResult {
  /**
   * The ID token (JWT) returned by Google.
   *
   * This token can be sent to your backend for verification.
   *
   * @since 0.1.0
   */
  idToken: string;
  /**
   * The unique identifier of the user's Google Account.
   *
   * @since 0.1.0
   */
  userId: string;
  /**
   * The user's email address.
   *
   * @since 0.1.0
   */
  email: string | null;
  /**
   * The user's display name (full name).
   *
   * @since 0.1.0
   */
  displayName: string | null;
  /**
   * The user's given name (first name).
   *
   * @since 0.1.0
   */
  givenName: string | null;
  /**
   * The user's family name (last name).
   *
   * @since 0.1.0
   */
  familyName: string | null;
  /**
   * The URL of the user's profile picture.
   *
   * @since 0.1.0
   */
  imageUrl: string | null;
  /**
   * The access token for accessing Google APIs.
   *
   * Only available when `scopes` are configured in `initialize()`.
   *
   * @since 0.1.0
   */
  accessToken: string | null;
  /**
   * The server auth code that can be exchanged on your backend
   * for access and refresh tokens.
   *
   * Only available on Android and iOS when `scopes` are configured in `initialize()`.
   *
   * @since 0.1.0
   */
  serverAuthCode: string | null;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The user canceled the sign-in flow.
   *
   * @since 0.1.0
   */
  SignInCanceled = 'SIGN_IN_CANCELED',
}
