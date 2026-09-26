import type { PluginListenerHandle } from '@capacitor/core';

export interface IntunePlugin {
  /**
   * Acquire an access token interactively via the Microsoft Authentication
   * Library (MSAL).
   *
   * This presents the Microsoft sign-in UI if necessary. Use the returned
   * `accountId` to enroll the account via `registerAndEnrollAccount(...)`.
   *
   * If the tenant requires an app protection policy, the call is rejected
   * with the `PROTECTION_POLICY_REQUIRED` error code. Use
   * `remediateCompliance(...)` in this case.
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
   * If the tenant requires an app protection policy, the call is rejected
   * with the `PROTECTION_POLICY_REQUIRED` error code. Use
   * `remediateCompliance(...)` in this case.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  acquireTokenSilent(
    options: AcquireTokenSilentOptions,
  ): Promise<AcquireTokenResult>;
  /**
   * Decrypt a file that was encrypted by the Intune App SDK.
   *
   * On iOS, encrypted files can only be read through the Intune App SDK.
   * Call this method before handing a file to other consumers such as the
   * Filesystem plugin, a media player or an uploader.
   *
   * On Android, the app reads encrypted files transparently, so this method
   * is rarely needed. The file is tagged with the unmanaged identity, which
   * removes the encryption and takes the file out of the scope of a
   * selective wipe.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.1
   */
  decryptFile(options: DecryptFileOptions): Promise<void>;
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
   * Check whether a file is encrypted by the Intune App SDK.
   *
   * On Android, this reflects whether the file is tagged with a managed
   * identity whose app protection policy uses file encryption, since the
   * Intune App SDK for Android does not expose the encryption state of a
   * single file.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.1
   */
  isFileEncrypted(
    options: IsFileEncryptedOptions,
  ): Promise<IsFileEncryptedResult>;
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
   * Protect a file or directory for the given account.
   *
   * On iOS, the Intune App SDK does not encrypt files on its own. Call this
   * method for every file that contains organization data if the app
   * protection policy requires file encryption (see
   * `GetPolicyResult.fileEncryptionRequired`). The file is encrypted in
   * place if the policy requires it. For a directory, all files it currently
   * contains are protected; files added later must be protected separately.
   * Encrypted files can only be read through the Intune App SDK, so use
   * `decryptFile(...)` before reading them with other plugins.
   *
   * On Android, the Intune App SDK encrypts files automatically and the app
   * reads them transparently. This method tags the file or directory with
   * the account so that it is in the scope of a selective wipe. Files added
   * to a protected directory later inherit the protection.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.1
   */
  protectFile(options: ProtectFileOptions): Promise<void>;
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
   * Bring the app into compliance with the app protection policy of an
   * account so that Microsoft Entra ID grants tokens for it.
   *
   * Call this when `acquireToken(...)` or `acquireTokenSilent(...)` is
   * rejected with the `PROTECTION_POLICY_REQUIRED` error code, passing the
   * `data` of the error as options. The Intune App SDK registers and
   * enrolls the account as needed. If the returned status is `compliant`,
   * retry the token acquisition.
   *
   * On iOS, the Intune App SDK may restart the app during the remediation if
   * the account was not enrolled before. In that case, the promise is never
   * settled and the app should retry the sign-in after the restart.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.2
   */
  remediateCompliance(
    options: RemediateComplianceOptions,
  ): Promise<RemediateComplianceResult>;
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
 * @since 0.1.1
 */
export interface DecryptFileOptions {
  /**
   * The absolute path or `file://` URI to write the decrypted copy to.
   *
   * If not provided, the file is decrypted in place. An existing file at
   * the destination is overwritten.
   *
   * @since 0.1.1
   * @example "file:///data/user/0/com.example.app/files/recording-plain.m4a"
   */
  destination?: string;
  /**
   * The absolute path or `file://` URI of the encrypted file.
   *
   * @since 0.1.1
   * @example "file:///data/user/0/com.example.app/files/recording.m4a"
   */
  path: string;
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
 * @since 0.1.1
 */
export interface IsFileEncryptedOptions {
  /**
   * The absolute path or `file://` URI of the file to check.
   *
   * @since 0.1.1
   * @example "file:///data/user/0/com.example.app/files/recording.m4a"
   */
  path: string;
}

/**
 * @since 0.1.1
 */
export interface IsFileEncryptedResult {
  /**
   * Whether or not the file is encrypted by the Intune App SDK.
   *
   * @since 0.1.1
   */
  encrypted: boolean;
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
 * @since 0.1.1
 */
export interface ProtectFileOptions {
  /**
   * The Microsoft Entra object ID (OID) of the account that owns the file.
   *
   * @since 0.1.1
   * @example "870ba1ef-6d94-4288-9f8e-000c04a92da2"
   */
  accountId: string;
  /**
   * The absolute path or `file://` URI of the file or directory to protect.
   *
   * @since 0.1.1
   * @example "file:///data/user/0/com.example.app/files/recording.m4a"
   */
  path: string;
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
 * @since 0.1.2
 */
export interface RemediateComplianceOptions {
  /**
   * The Microsoft Entra object ID (OID) of the account to remediate.
   *
   * @since 0.1.2
   * @example "870ba1ef-6d94-4288-9f8e-000c04a92da2"
   */
  accountId: string;
  /**
   * The authority URL of the account.
   *
   * Only available on Android. Required on Android.
   *
   * @since 0.1.2
   * @example "https://login.microsoftonline.com/b55f0d51-fe4d-4a04-a4a6-0b0a4d011c9d"
   */
  authority?: string;
  /**
   * Whether or not to remediate without showing any UI of the Intune App
   * SDK.
   *
   * On iOS, the status `interactionRequired` is returned if the remediation
   * cannot be completed without user interaction.
   *
   * @since 0.1.2
   * @default false
   */
  silent?: boolean;
  /**
   * The Microsoft Entra tenant ID of the account.
   *
   * Only available on Android. Required on Android.
   *
   * @since 0.1.2
   * @example "b55f0d51-fe4d-4a04-a4a6-0b0a4d011c9d"
   */
  tenantId?: string;
  /**
   * The username (usually the UPN) of the account.
   *
   * Only available on Android. Required on Android.
   *
   * @since 0.1.2
   * @example "jane.doe@contoso.com"
   */
  username?: string;
}

/**
 * @since 0.1.2
 */
export interface RemediateComplianceResult {
  /**
   * A localized error message that can be displayed to the user if the
   * account is not compliant, if available.
   *
   * @since 0.1.2
   */
  errorMessage: string | null;
  /**
   * A localized error title that can be displayed to the user if the
   * account is not compliant, if available.
   *
   * @since 0.1.2
   */
  errorTitle: string | null;
  /**
   * The compliance status of the account after the remediation.
   *
   * @since 0.1.2
   */
  status: ComplianceStatus;
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
 * The compliance status of an account.
 *
 * - `canceled`: The user canceled the remediation. Only available on iOS.
 * - `clientError`: The remediation failed due to a client issue, such as a
 *   missing or invalid token. Only available on Android.
 * - `companyPortalRequired`: The Company Portal app must be installed. If it
 *   is already installed, the app must be restarted. Only available on
 *   Android.
 * - `compliant`: The account is compliant. Retry the token acquisition.
 * - `interactionRequired`: The remediation requires user interaction. Call
 *   `remediateCompliance(...)` again with `silent` set to `false`. Only
 *   available on iOS.
 * - `networkFailure`: The Intune service could not be reached. Retry when
 *   the network connection is restored.
 * - `notCompliant`: The account is not compliant.
 * - `pending`: The Intune service did not respond in time. Retry later.
 *   Only available on Android.
 * - `serviceFailure`: The compliance data could not be retrieved from the
 *   Intune service. Retry later.
 * - `unknown`: The status is unknown. Only available on Android.
 *
 * @since 0.1.2
 */
export type ComplianceStatus =
  | 'canceled'
  | 'clientError'
  | 'companyPortalRequired'
  | 'compliant'
  | 'interactionRequired'
  | 'networkFailure'
  | 'notCompliant'
  | 'pending'
  | 'serviceFailure'
  | 'unknown';

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
   * The account must be managed by an Intune app protection policy before a
   * token can be acquired (App Protection Conditional Access).
   *
   * The `data` of the error contains the `accountId`, `tenantId` and
   * `username` of the account and, on Android, the `authority`. Pass it to
   * `remediateCompliance(...)` and retry the token acquisition afterwards.
   *
   * @since 0.1.2
   */
  ProtectionPolicyRequired = 'PROTECTION_POLICY_REQUIRED',
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
