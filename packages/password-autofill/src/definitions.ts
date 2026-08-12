export interface PasswordAutofillPlugin {
  /**
   * Save a username and password to the platform credential store.
   *
   * On iOS, the credential is saved to the iCloud Keychain via the
   * Shared Web Credentials API. This requires the Associated Domains
   * entitlement with a `webcredentials:` entry and a matching
   * `apple-app-site-association` file hosted on your domain.
   *
   * On Android, the credential is saved to the Google Password Manager
   * (or another provider) via the Credential Manager API. This presents
   * the system "Save password?" prompt to the user.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  savePassword(options: SavePasswordOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface SavePasswordOptions {
  /**
   * The domain to associate the credential with.
   *
   * This must match one of the domains in your app's Associated Domains
   * entitlement (without the `webcredentials:` prefix).
   *
   * Only available on iOS (required).
   *
   * @example 'example.com'
   * @since 0.1.0
   */
  domain?: string;
  /**
   * The password to save.
   *
   * @since 0.1.0
   */
  password: string;
  /**
   * The username to save.
   *
   * @example 'jane.doe@example.com'
   * @since 0.1.0
   */
  username: string;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The user canceled the save operation.
   *
   * @since 0.1.0
   */
  Canceled = 'CANCELED',
  /**
   * The credential could not be saved.
   *
   * @since 0.1.0
   */
  SaveFailed = 'SAVE_FAILED',
}
