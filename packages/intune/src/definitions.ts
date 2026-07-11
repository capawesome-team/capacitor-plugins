import type { PluginListenerHandle } from '@capacitor/core';

export interface IntunePlugin {
  /**
   * Acquire an access token interactively via the Microsoft Authentication
   * Library (MSAL).
   *
   * This presents the Microsoft sign-in UI if necessary. Use the returned
   * `accountId` to enroll the account via `registerAndEnrollAccount(...)`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  acquireToken(options: AcquireTokenOptions): Promise<AcquireTokenResult>;
  /**
   * Acquire an access token silently via the Microsoft Authentication
   * Library (MSAL) for an already signed-in account.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  acquireTokenSilent(
    options: AcquireTokenSilentOptions,
  ): Promise<AcquireTokenResult>;
  /**
   * Get the application configuration values that the organization's IT
   * administrator has deployed for the given account via the MAM channel.
   *
   * For configuration deployed via the MDM channel (device enrollment), use
   * the Managed Configurations plugin instead.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getAppConfig(options: GetAppConfigOptions): Promise<GetAppConfigResult>;
  /**
   * Get the account that is currently enrolled in Mobile Application
   * Management (MAM).
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getEnrolledAccount(): Promise<GetEnrolledAccountResult>;
  /**
   * Get the app protection policy that is currently applied for the given
   * account.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getPolicy(options: GetPolicyOptions): Promise<GetPolicyResult>;
  /**
   * Get the versions of the Intune App SDK and the Microsoft Authentication
   * Library (MSAL) that the plugin was built with.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getSdkVersion(): Promise<GetSdkVersionResult>;
  /**
   * Sign in and enroll an account using the login UI provided by the Intune
   * App SDK.
   *
   * On Android, use `acquireToken(...)` followed by
   * `registerAndEnrollAccount(...)` instead.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  loginAndEnrollAccount(): Promise<void>;
  /**
   * Register an account for Mobile Application Management (MAM) and enroll
   * it in the Intune service.
   *
   * Call this after a successful `acquireToken(...)` call. The enrollment
   * itself is asynchronous; listen for the `enrollmentChange` event to get
   * the enrollment result.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  registerAndEnrollAccount(
    options: RegisterAndEnrollAccountOptions,
  ): Promise<void>;
  /**
   * Show the diagnostic console of the Intune App SDK.
   *
   * The console allows the user to inspect the SDK state and collect logs
   * for support requests.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  showDiagnosticConsole(): Promise<void>;
  /**
   * Unenroll an account from Mobile Application Management (MAM) and
   * unregister it from the Intune service.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  unenrollAccount(options: UnenrollAccountOptions): Promise<void>;
  /**
   * Called when the application configuration changes.
   *
   * Use `getAppConfig(...)` to read the new configuration values.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'appConfigChange',
    listenerFunc: (event: AppConfigChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the enrollment state of an account changes, for example
   * when an enrollment attempt succeeds or fails.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'enrollmentChange',
    listenerFunc: (event: EnrollmentChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the app protection policy changes.
   *
   * Use `getPolicy(...)` to read the new policy values.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'policyChange',
    listenerFunc: (event: PolicyChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the Intune service requests a selective wipe of the
   * account's data.
   *
   * The Intune App SDK wipes the data it manages, but it does **not** wipe
   * the web layer storage of your Capacitor app (e.g. IndexedDB, Local
   * Storage). Use this event to clean up any data your web code has
   * persisted.
   *
   * The event is delivered even if the wipe was requested while your app
   * was not running (see the documentation for details).
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'wipeRequested',
    listenerFunc: (event: WipeRequestedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.1.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface AcquireTokenOptions {
  /**
   * Whether or not to force the account selection prompt to be shown, even
   * if a user is already signed in.
   *
   * @since 0.1.0
   * @default false
   */
  forcePrompt?: boolean;
  /**
   * The username to pre-fill in the sign-in UI.
   *
   * @since 0.1.0
   * @example "jane.doe@contoso.com"
   */
  loginHint?: string;
  /**
   * The scopes to request the access token for.
   *
   * @since 0.1.0
   * @example ["https://graph.microsoft.com/.default"]
   */
  scopes: string[];
}

/**
 * @since 0.1.0
 */
export interface AcquireTokenResult {
  /**
   * The acquired access token.
   *
   * @since 0.1.0
   */
  accessToken: string;
  /**
   * The Microsoft Entra object ID (OID) of the signed-in account.
   *
   * Use this identifier for all other methods of this plugin.
   *
   * @since 0.1.0
   * @example "870ba1ef-6d94-4288-9f8e-000c04a92da2"
   */
  accountId: string;
  /**
   * The raw ID token of the signed-in account, if available.
   *
   * @since 0.1.0
   */
  idToken: string | null;
  /**
   * The Microsoft Entra tenant ID of the signed-in account, if available.
   *
   * @since 0.1.0
   * @example "b55f0d51-fe4d-4a04-a4a6-0b0a4d011c9d"
   */
  tenantId: string | null;
  /**
   * The username (usually the UPN) of the signed-in account, if available.
   *
   * @since 0.1.0
   * @example "jane.doe@contoso.com"
   */
  username: string | null;
}

/**
 * @since 0.1.0
 */
export interface AcquireTokenSilentOptions {
  /**
   * The Microsoft Entra object ID (OID) of the account to acquire the token
   * for.
   *
   * @since 0.1.0
   * @example "870ba1ef-6d94-4288-9f8e-000c04a92da2"
   */
  accountId: string;
  /**
   * Whether or not to ignore any cached token and force a token refresh.
   *
   * @since 0.1.0
   * @default false
   */
  forceRefresh?: boolean;
  /**
   * The scopes to request the access token for.
   *
   * @since 0.1.0
   * @example ["https://graph.microsoft.com/.default"]
   */
  scopes: string[];
}

/**
 * @since 0.1.0
 */
export interface AppConfigChangeEvent {
  /**
   * The Microsoft Entra object ID (OID) of the affected account, if
   * available.
   *
   * @since 0.1.0
   */
  accountId: string | null;
}

/**
 * @since 0.1.0
 */
export interface AppConfigConflict {
  /**
   * The configuration key for which conflicting values exist.
   *
   * @since 0.1.0
   */
  key: string;
  /**
   * All values that have been deployed for the configuration key.
   *
   * @since 0.1.0
   */
  values: string[];
}

/**
 * @since 0.1.0
 */
export interface EnrolledAccount {
  /**
   * The Microsoft Entra object ID (OID) of the enrolled account.
   *
   * @since 0.1.0
   * @example "870ba1ef-6d94-4288-9f8e-000c04a92da2"
   */
  accountId: string;
  /**
   * The username (usually the UPN) of the enrolled account, if available.
   *
   * @since 0.1.0
   * @example "jane.doe@contoso.com"
   */
  username: string | null;
}

/**
 * @since 0.1.0
 */
export interface EnrollmentChangeEvent {
  /**
   * The Microsoft Entra object ID (OID) of the affected account, if
   * available.
   *
   * @since 0.1.0
   */
  accountId: string | null;
  /**
   * The new enrollment status of the account.
   *
   * @since 0.1.0
   */
  status: EnrollmentStatus;
}

/**
 * @since 0.1.0
 */
export interface GetAppConfigOptions {
  /**
   * The Microsoft Entra object ID (OID) of the account to get the
   * application configuration for.
   *
   * @since 0.1.0
   * @example "870ba1ef-6d94-4288-9f8e-000c04a92da2"
   */
  accountId: string;
}

/**
 * @since 0.1.0
 */
export interface GetAppConfigResult {
  /**
   * The configuration keys for which multiple conflicting values have been
   * deployed.
   *
   * @since 0.1.0
   */
  conflicts: AppConfigConflict[];
  /**
   * The merged application configuration values.
   *
   * For keys with conflicting values, the value that the Intune App SDK
   * returns first is used.
   *
   * @since 0.1.0
   */
  values: Record<string, string>;
}

/**
 * @since 0.1.0
 */
export interface GetEnrolledAccountResult {
  /**
   * The enrolled account or `null` if no account is enrolled.
   *
   * @since 0.1.0
   */
  account: EnrolledAccount | null;
}

/**
 * @since 0.1.0
 */
export interface GetPolicyOptions {
  /**
   * The Microsoft Entra object ID (OID) of the account to get the app
   * protection policy for.
   *
   * @since 0.1.0
   * @example "870ba1ef-6d94-4288-9f8e-000c04a92da2"
   */
  accountId: string;
}

/**
 * @since 0.1.0
 */
export interface GetPolicyResult {
  /**
   * Whether or not the policy allows syncing contacts to the device.
   *
   * @since 0.1.0
   */
  contactSyncAllowed: boolean;
  /**
   * Whether or not the policy requires files to be encrypted.
   *
   * On Android, this reflects whether file encryption is currently in use.
   *
   * @since 0.1.0
   */
  fileEncryptionRequired: boolean;
  /**
   * Whether or not the policy requires links to be opened in a managed
   * browser (e.g. Microsoft Edge).
   *
   * @since 0.1.0
   */
  managedBrowserRequired: boolean;
  /**
   * Whether or not the policy requires a PIN to access the app.
   *
   * @since 0.1.0
   */
  pinRequired: boolean;
  /**
   * Whether or not the policy allows saving files to personal (local)
   * storage.
   *
   * @since 0.1.0
   */
  saveToPersonalStorageAllowed: boolean;
  /**
   * Whether or not the policy allows taking screenshots.
   *
   * @since 0.1.0
   */
  screenCaptureAllowed: boolean;
}

/**
 * @since 0.1.0
 */
export interface GetSdkVersionResult {
  /**
   * The version of the Intune App SDK.
   *
   * @since 0.1.0
   * @example "12.0.2"
   */
  intuneSdkVersion: string;
  /**
   * The version of the Microsoft Authentication Library (MSAL), if
   * available.
   *
   * @since 0.1.0
   * @example "5.1.0"
   */
  msalVersion: string | null;
}

/**
 * @since 0.1.0
 */
export interface PolicyChangeEvent {
  /**
   * The Microsoft Entra object ID (OID) of the affected account, if
   * available.
   *
   * @since 0.1.0
   */
  accountId: string | null;
}

/**
 * @since 0.1.0
 */
export interface RegisterAndEnrollAccountOptions {
  /**
   * The Microsoft Entra object ID (OID) of the account to register and
   * enroll, as returned by `acquireToken(...)`.
   *
   * @since 0.1.0
   * @example "870ba1ef-6d94-4288-9f8e-000c04a92da2"
   */
  accountId: string;
}

/**
 * @since 0.1.0
 */
export interface UnenrollAccountOptions {
  /**
   * The Microsoft Entra object ID (OID) of the account to unenroll.
   *
   * @since 0.1.0
   * @example "870ba1ef-6d94-4288-9f8e-000c04a92da2"
   */
  accountId: string;
  /**
   * Whether or not the account's data should be wiped as part of the
   * unenrollment.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   * @default false
   */
  wipe?: boolean;
}

/**
 * @since 0.1.0
 */
export interface WipeRequestedEvent {
  /**
   * The Microsoft Entra object ID (OID) of the affected account, if
   * available.
   *
   * @since 0.1.0
   */
  accountId: string | null;
}

/**
 * The enrollment status of an account.
 *
 * @since 0.1.0
 */
export type EnrollmentStatus = 'enrolled' | 'failed' | 'pending' | 'unenrolled';

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The enrollment of the account failed.
   *
   * @since 0.1.0
   */
  EnrollmentFailed = 'ENROLLMENT_FAILED',
  /**
   * The user canceled the sign-in interaction.
   *
   * @since 0.1.0
   */
  InteractionCanceled = 'INTERACTION_CANCELED',
  /**
   * No account with the given `accountId` is signed in or enrolled.
   *
   * @since 0.1.0
   */
  NotEnrolled = 'NOT_ENROLLED',
  /**
   * The token acquisition failed.
   *
   * @since 0.1.0
   */
  TokenAcquisitionFailed = 'TOKEN_ACQUISITION_FAILED',
  /**
   * The unenrollment of the account failed.
   *
   * @since 0.1.0
   */
  UnenrollFailed = 'UNENROLL_FAILED',
}
