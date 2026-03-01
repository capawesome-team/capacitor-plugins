/**
 * @since 0.1.0
 */
export interface GoogleSignInPlugin {
  /**
   * Handle the redirect callback from the OAuth provider.
   *
   * This method must be called when the app is redirected back from the OAuth provider.
   * It exchanges the authorization code for tokens and returns the sign-in result.
   *
   * Only available on Web.
   *
   * @since 0.1.0
   */
  handleRedirectCallback(): Promise<SignInResult>;
  /**
   * Initialize the Google Sign-In plugin.
   *
   * This method must be called once before all other methods.
   *
   * @since 0.1.0
   */
  initialize(options: InitializeOptions): Promise<void>;
  /**
   * Start the Google Sign-In flow.
   *
   * On Web, this redirects to the Google OAuth authorization page.
   * The promise will never resolve on Web. After the user signs in,
   * the app will be redirected back to the `redirectUrl`.
   * Use `handleRedirectCallback()` to complete the sign-in flow.
   *
   * @since 0.1.0
   */
  signIn(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Sign out the current user.
   *
   * On Android, this clears the credential state.
   * On iOS, this signs out from the Google Sign-In SDK.
   * On Web, this is a no-op.
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
   * On Android, this is passed as the server client ID to the Credential Manager API
   * and the AuthorizationClient API.
   * On iOS, this is used as the server client ID for the Google Sign-In SDK.
   * On Web, this is used to initialize the Google Sign-In JavaScript API.
   *
   * **Attention**: This must be a web client ID on all platforms, even on Android and iOS.
   *
   * @since 0.1.0
   * @example "123456789-abc.apps.googleusercontent.com"
   */
  clientId: string;
  /**
   * The URL to redirect to after the OAuth flow.
   *
   * Only available on Web.
   *
   * @since 0.1.0
   * @example "http://localhost:4200"
   */
  redirectUrl?: string;
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
