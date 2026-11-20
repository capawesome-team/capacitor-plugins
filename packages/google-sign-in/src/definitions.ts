/**
 * @since 0.1.0
 */
export interface GoogleSignInPlugin {
  /**
   * Request an access token for the given scopes.
   *
   * No UI is shown if the user has already granted the scopes.
   * Otherwise, the consent screen is shown.
   * Use this method to get a new access token after the previous one has expired,
   * without having to sign in again.
   *
   * On iOS, the previous sign-in is restored. If there is no previous sign-in,
   * the sign-in screen is shown.
   *
   * Since the consent screen may be shown, this method should only be called
   * while the app is in the foreground.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.5
   */
  authorize(options: AuthorizeOptions): Promise<AuthorizeResult>;
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
 * @since 0.1.5
 */
export interface AuthorizeOptions {
  /**
   * The OAuth scopes to request.
   *
   * @since 0.1.5
   * @example ["https://www.googleapis.com/auth/drive.appdata"]
   */
  scopes: string[];
}

/**
 * @since 0.1.5
 */
export interface AuthorizeResult {
  /**
   * The access token for accessing Google APIs.
   *
   * @since 0.1.5
   */
  accessToken: string;
  /**
   * The server auth code that can be exchanged on your backend
   * for access and refresh tokens.
   *
   * On iOS, this is only available if the consent screen or the sign-in screen was shown.
   *
   * @since 0.1.5
   */
  serverAuthCode: string | null;
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
   * The user canceled the authorization flow.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.5
   */
  AuthorizationCanceled = 'AUTHORIZATION_CANCELED',
  /**
   * No Google account is available on the device.
   *
   * Only available on Android.
   *
   * @since 0.1.3
   */
  NoCredentialAvailable = 'NO_CREDENTIAL_AVAILABLE',
  /**
   * The credential provider is not configured correctly.
   *
   * Only available on Android.
   *
   * @since 0.1.3
   */
  ProviderConfigurationError = 'PROVIDER_CONFIGURATION_ERROR',
  /**
   * The user canceled the sign-in flow.
   *
   * @since 0.1.0
   */
  SignInCanceled = 'SIGN_IN_CANCELED',
}
