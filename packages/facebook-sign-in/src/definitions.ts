/**
 * @since 0.1.0
 */
export interface FacebookSignInPlugin {
  /**
   * Get the current access token.
   *
   * @since 0.1.0
   */
  getCurrentAccessToken(): Promise<GetCurrentAccessTokenResult>;
  /**
   * Initialize the Facebook Sign-In plugin.
   *
   * This method must be called once before all other methods.
   *
   * @since 0.1.0
   */
  initialize(options?: InitializeOptions): Promise<void>;
  /**
   * Start the Facebook Sign-In flow.
   *
   * @since 0.1.0
   */
  signIn(options?: SignInOptions): Promise<SignInResult>;
  /**
   * Sign out the current user.
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
   * The Facebook App ID from the Meta App Dashboard.
   *
   * On Android and iOS, this overrides the value from the native configuration
   * and is usually not needed.
   *
   * **Attention**: This option is required on Web.
   *
   * @since 0.1.0
   * @example "1234567890123456"
   */
  appId?: string;
  /**
   * The Facebook Client Token from the Meta App Dashboard.
   *
   * This overrides the value from the native configuration
   * and is usually not needed.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   * @example "abc123def456ghi789jkl012mno345pq"
   */
  clientToken?: string;
}

/**
 * @since 0.1.0
 */
export interface SignInOptions {
  /**
   * Whether or not to use [Limited Login](https://developers.facebook.com/docs/facebook-login/limited-login/)
   * instead of classic login.
   *
   * With Limited Login, no access token is returned. Instead, an
   * authentication token (JWT) is returned that can be verified
   * on your backend.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   * @default false
   */
  limitedLogin?: boolean;
  /**
   * A nonce to prevent replay attacks.
   *
   * The nonce is included in the authentication token
   * and can be verified on your backend.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  nonce?: string;
  /**
   * The permissions to request from the user.
   *
   * @since 0.1.0
   * @default ["public_profile", "email"]
   * @example ["public_profile", "email", "user_birthday"]
   */
  permissions?: string[];
}

/**
 * @since 0.1.0
 */
export interface SignInResult {
  /**
   * The access token for accessing the Facebook Graph API.
   *
   * This is `null` on iOS when Limited Login is used.
   *
   * @since 0.1.0
   */
  accessToken: AccessToken | null;
  /**
   * The authentication token (JWT) returned by Facebook.
   *
   * This token can be sent to your backend for verification.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  authenticationToken: string | null;
  /**
   * The profile of the signed-in user.
   *
   * @since 0.1.0
   */
  profile: Profile;
}

/**
 * @since 0.1.0
 */
export interface GetCurrentAccessTokenResult {
  /**
   * The current access token.
   *
   * This is `null` if no user is signed in
   * or the access token has expired.
   *
   * @since 0.1.0
   */
  accessToken: AccessToken | null;
}

/**
 * @since 0.1.0
 */
export interface AccessToken {
  /**
   * The timestamp (in milliseconds since the Unix epoch)
   * when the access token expires.
   *
   * @since 0.1.0
   * @example 1755013504000
   */
  expiresAt: number;
  /**
   * The permissions granted to the access token.
   *
   * @since 0.1.0
   * @example ["public_profile", "email"]
   */
  permissions: string[];
  /**
   * The access token string.
   *
   * @since 0.1.0
   */
  token: string;
  /**
   * The unique identifier of the user's Facebook account.
   *
   * @since 0.1.0
   */
  userId: string;
}

/**
 * @since 0.1.0
 */
export interface Profile {
  /**
   * The user's email address.
   *
   * This is `null` if the `email` permission was not granted.
   *
   * @since 0.1.0
   */
  email: string | null;
  /**
   * The unique identifier of the user's Facebook account.
   *
   * @since 0.1.0
   */
  id: string;
  /**
   * The URL of the user's profile picture.
   *
   * @since 0.1.0
   */
  imageUrl: string | null;
  /**
   * The user's display name (full name).
   *
   * @since 0.1.0
   */
  name: string | null;
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
