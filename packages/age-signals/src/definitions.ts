/**
 * @since 0.0.1
 */
export interface AgeSignalsPlugin {
  /**
   * Get the age range that the user has already shared with the app.
   *
   * This method never shows a system prompt. Call `requestAgeRange(...)` first
   * and only call this method if the returned status was `SHARED`.
   *
   * Use this method to poll for changes (for example a `significantChange`
   * status that moves from `PENDING` to `APPROVED`) without prompting the user
   * again.
   *
   * Only available on Android.
   *
   * @since 0.5.0
   */
  getAgeRange(): Promise<GetAgeRangeResult>;
  /**
   * Get the regulatory requirements that apply to the current user.
   *
   * Call this method before starting any age assurance flow to find out
   * whether the user is in a region and account state where age assurance
   * applies at all.
   *
   * Only available on iOS (26.2+).
   *
   * @since 0.5.0
   */
  getRegulatoryRequirements(): Promise<GetRegulatoryRequirementsResult>;
  /**
   * Check whether age signals are available on this device.
   *
   * On **Android**, this checks whether the Google Play Store is installed.
   * On **iOS**, this checks whether the device runs iOS 26.0 or later.
   *
   * A result of `true` does not guarantee that an age range can be retrieved.
   * Always handle the errors of the other methods as well.
   *
   * @since 0.5.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Ask the user to share their age range with the app.
   *
   * This method may show a system prompt. The system caches the decision of
   * the user, so subsequent calls may resolve without any user interaction.
   *
   * On **Android**, the prompt is only shown to unsupervised users who have
   * chosen to be asked before sharing. It is not shown in regions where age
   * verification is mandatory. In that case the status is
   * `VERIFICATION_REQUIRED` and the user must resolve their status in the
   * Google Play Store. Google Play also suppresses the prompt after the user
   * has dismissed or declined it a few times.
   * On **iOS**, the prompt is not shown in regions where age assurance is
   * mandatory. In that case the age range is shared without user interaction.
   *
   * @since 0.5.0
   */
  requestAgeRange(
    options?: RequestAgeRangeOptions,
  ): Promise<RequestAgeRangeResult>;
  /**
   * Set the next access result to be returned by the fake manager.
   *
   * Only available on Android.
   *
   * @since 0.5.0
   */
  setNextAgeSignalsAccessResult(
    options: SetNextAgeSignalsAccessResultOptions,
  ): Promise<void>;
  /**
   * Set the next error to be thrown by the fake manager when the age range is
   * read.
   *
   * Only available on Android.
   *
   * @since 0.3.1
   */
  setNextAgeSignalsException(
    options: SetNextAgeSignalsExceptionOptions,
  ): Promise<void>;
  /**
   * Set the next age range result to be returned by the fake manager.
   *
   * Only available on Android.
   *
   * @since 0.3.1
   */
  setNextAgeSignalsResult(
    options: SetNextAgeSignalsResultOptions,
  ): Promise<void>;
  /**
   * Set the next error to be thrown by the fake manager when access to the age
   * range is requested.
   *
   * Only available on Android.
   *
   * @since 0.5.0
   */
  setNextRequestAgeSignalsAccessException(
    options: SetNextRequestAgeSignalsAccessExceptionOptions,
  ): Promise<void>;
  /**
   * Enable or disable the fake manager for testing.
   *
   * The fake manager is only available in debuggable builds. In release builds
   * this method rejects with `FAKE_MANAGER_NOT_ALLOWED`, because it would
   * otherwise allow age signals to be forged from the web layer.
   *
   * Only available on Android.
   *
   * @since 0.3.1
   */
  setUseFakeManager(options: SetUseFakeManagerOptions): Promise<void>;
  /**
   * Show a system interface that lets the user acknowledge a significant
   * change to the app.
   *
   * Only call this method if `getRegulatoryRequirements()` returned
   * `SIGNIFICANT_APP_CHANGE_REQUIRES_ADULT_NOTIFICATION`.
   *
   * Only available on iOS (26.4+).
   *
   * @since 0.5.0
   */
  showSignificantUpdateAcknowledgment(
    options: ShowSignificantUpdateAcknowledgmentOptions,
  ): Promise<void>;
}

/**
 * @since 0.5.0
 */
export interface GetAgeRangeResult {
  /**
   * The age range that the user has shared with the app.
   *
   * `undefined` if the user has not shared their age range.
   *
   * @since 0.5.0
   */
  ageRange?: AgeRange;
  /**
   * An ID assigned to supervised user installs by Google Play, used for the
   * purposes of notifying you of revoked app approval.
   *
   * Only available on Android.
   *
   * @since 0.5.0
   * @example "abc123xyz"
   */
  installId?: string;
  /**
   * The parental approval state for the significant changes of the app.
   *
   * Significant changes are declared in the Google Play Console. Google Play
   * then asks the parent or guardian of a supervised user to approve them.
   *
   * `undefined` if the user is not supervised, if no significant change has
   * been declared yet or if significant changes do not apply in the
   * jurisdiction of the user.
   *
   * Only available on Android.
   *
   * @since 0.5.0
   */
  significantChange?: SignificantChange;
}

/**
 * @since 0.5.0
 */
export interface GetRegulatoryRequirementsResult {
  /**
   * Whether the user must share their age range because of a law or
   * regulation that applies to their region and account.
   *
   * @since 0.5.0
   * @example true
   */
  ageAssuranceRequired: boolean;
  /**
   * The regulatory features that the app must support for this user.
   *
   * Empty on iOS versions below 26.4.
   *
   * @since 0.5.0
   */
  regulatoryFeatures: RegulatoryFeature[];
}

/**
 * @since 0.5.0
 */
export interface IsAvailableResult {
  /**
   * Whether age signals are available on this device.
   *
   * @since 0.5.0
   * @example true
   */
  available: boolean;
}

/**
 * @since 0.5.0
 */
export interface RequestAgeRangeOptions {
  /**
   * The age thresholds that are relevant for the app.
   *
   * The provided array must contain at least 1 and at most 3 ages. The ages
   * must be sorted in ascending order and must be at least two years apart
   * from each other.
   *
   * The system may ignore these thresholds and return a different age range if
   * a law or regulation requires it.
   *
   * Only available on iOS.
   *
   * @since 0.5.0
   * @default [13, 15, 18]
   * @example [13, 16, 18]
   */
  ageGates?: number[];
}

/**
 * @since 0.5.0
 */
export interface RequestAgeRangeResult {
  /**
   * The age range that the user has shared with the app.
   *
   * `undefined` if the status is not `SHARED`.
   *
   * @since 0.5.0
   */
  ageRange?: AgeRange;
  /**
   * An ID assigned to supervised user installs by Google Play, used for the
   * purposes of notifying you of revoked app approval.
   *
   * Only available on Android.
   *
   * @since 0.5.0
   * @example "abc123xyz"
   */
  installId?: string;
  /**
   * The parental approval state for the significant changes of the app.
   *
   * Significant changes are declared in the Google Play Console. Google Play
   * then asks the parent or guardian of a supervised user to approve them.
   *
   * `undefined` if the user is not supervised, if no significant change has
   * been declared yet or if significant changes do not apply in the
   * jurisdiction of the user.
   *
   * Only available on Android.
   *
   * @since 0.5.0
   */
  significantChange?: SignificantChange;
  /**
   * Whether the user has shared their age range with the app.
   *
   * @since 0.5.0
   */
  status: AgeRangeStatus;
}

/**
 * @since 0.5.0
 */
export interface SetNextAgeSignalsAccessResultOptions {
  /**
   * The status to be returned by the fake manager.
   *
   * @since 0.5.0
   */
  status: AgeRangeStatus;
}

/**
 * @since 0.3.1
 */
export interface SetNextAgeSignalsExceptionOptions {
  /**
   * The error code to be thrown by the fake manager.
   *
   * Only the error codes that are available on Android are supported.
   *
   * @since 0.3.1
   */
  errorCode: ErrorCode;
}

/**
 * @since 0.3.1
 */
export interface SetNextAgeSignalsResultOptions {
  /**
   * The (inclusive) lower bound of the age range.
   *
   * @since 0.3.1
   * @example 13
   */
  ageLower?: number;
  /**
   * How the age range was established.
   *
   * @since 0.5.0
   */
  ageRangeSource?: AgeRangeSource;
  /**
   * The (inclusive) upper bound of the age range.
   *
   * @since 0.3.1
   * @example 15
   */
  ageUpper?: number;
  /**
   * An ID assigned to supervised user installs by Google Play.
   *
   * @since 0.3.1
   * @example "fake_install_id"
   */
  installId?: string;
  /**
   * The effective date of the most recently approved significant change, in
   * ISO 8601 format.
   *
   * @since 0.5.0
   * @example "2026-01-03T12:00:00.000Z"
   */
  significantChangeApprovalDate?: string;
  /**
   * The parental approval state for the most recent significant change.
   *
   * @since 0.5.0
   */
  significantChangeStatus?: SignificantChangeStatus;
}

/**
 * @since 0.5.0
 */
export interface SetNextRequestAgeSignalsAccessExceptionOptions {
  /**
   * The error code to be thrown by the fake manager.
   *
   * Only the error codes that are available on Android are supported.
   *
   * @since 0.5.0
   */
  errorCode: ErrorCode;
}

/**
 * @since 0.3.1
 */
export interface SetUseFakeManagerOptions {
  /**
   * Whether to use the fake manager for testing.
   *
   * @since 0.3.1
   * @default false
   * @example true
   */
  useFake: boolean;
}

/**
 * @since 0.5.0
 */
export interface ShowSignificantUpdateAcknowledgmentOptions {
  /**
   * A short description of what has changed in the app and why the user must
   * acknowledge it.
   *
   * @since 0.5.0
   * @example "This app now supports direct messages between users."
   */
  updateDescription: string;
}

/**
 * @since 0.5.0
 */
export interface AgeRange {
  /**
   * The parental controls that are active for the user.
   *
   * Only available on iOS.
   *
   * @since 0.5.0
   */
  activeParentalControls?: ParentalControl[];
  /**
   * How the age range was declared.
   *
   * The more granular values are only returned in some regions.
   *
   * `undefined` if the system does not provide this information.
   *
   * Only available on iOS.
   *
   * @since 0.5.0
   */
  ageRangeDeclaration?: AgeRangeDeclaration;
  /**
   * How the age range was established.
   *
   * `undefined` if the system does not provide this information.
   *
   * Only available on Android.
   *
   * @since 0.5.0
   */
  ageRangeSource?: AgeRangeSource;
  /**
   * The (inclusive) lower bound of the age range.
   *
   * `undefined` if the user is below the lowest requested age gate.
   *
   * @since 0.5.0
   * @example 13
   */
  lowerBound?: number;
  /**
   * The (inclusive) upper bound of the age range.
   *
   * `undefined` if the user meets or exceeds the highest requested age gate.
   *
   * @since 0.5.0
   * @example 15
   */
  upperBound?: number;
}

/**
 * @since 0.5.0
 */
export interface SignificantChange {
  /**
   * The effective date of the most recently approved significant change, in
   * ISO 8601 format.
   *
   * All significant changes with an earlier effective date are approved as
   * well.
   *
   * `undefined` if no significant change has been approved yet.
   *
   * @since 0.5.0
   * @example "2026-01-03T12:00:00.000Z"
   */
  approvalDate?: string;
  /**
   * The parental approval state for the most recent significant change.
   *
   * @since 0.5.0
   */
  status: SignificantChangeStatus;
}

/**
 * How the age range was declared.
 *
 * Only available on iOS.
 *
 * @since 0.4.0
 */
export enum AgeRangeDeclaration {
  /**
   * The user set their own age range using an unspecified method.
   *
   * Only available on iOS (26.2+).
   *
   * @deprecated Deprecated by Apple in favor of `Confirmed`, but still returned by devices running iOS 26.2 to 26.4.
   * @since 0.5.0
   */
  CheckedByOtherMethod = 'CHECKED_BY_OTHER_METHOD',
  /**
   * The age range was set using a scrutinized method, like a credit card or
   * government ID.
   *
   * Only available on iOS (26.5+).
   *
   * @since 0.4.0
   */
  Confirmed = 'CONFIRMED',
  /**
   * The user set their own age range using a government ID.
   *
   * Only available on iOS (26.2+).
   *
   * @deprecated Deprecated by Apple in favor of `Confirmed`, but still returned by devices running iOS 26.2 to 26.4.
   * @since 0.5.0
   */
  GovernmentIdChecked = 'GOVERNMENT_ID_CHECKED',
  /**
   * A parent or guardian set the age range using an unspecified method.
   *
   * Only available on iOS (26.2+).
   *
   * @deprecated Deprecated by Apple in favor of `Confirmed`, but still returned by devices running iOS 26.2 to 26.4.
   * @since 0.5.0
   */
  GuardianCheckedByOtherMethod = 'GUARDIAN_CHECKED_BY_OTHER_METHOD',
  /**
   * A parent or guardian declared the age range without external verification.
   *
   * @since 0.4.0
   */
  GuardianDeclared = 'GUARDIAN_DECLARED',
  /**
   * A parent or guardian set the age range using a government ID.
   *
   * Only available on iOS (26.2+).
   *
   * @deprecated Deprecated by Apple in favor of `Confirmed`, but still returned by devices running iOS 26.2 to 26.4.
   * @since 0.5.0
   */
  GuardianGovernmentIdChecked = 'GUARDIAN_GOVERNMENT_ID_CHECKED',
  /**
   * A parent or guardian set the age range using a payment method, like a
   * credit card.
   *
   * Only available on iOS (26.2+).
   *
   * @deprecated Deprecated by Apple in favor of `Confirmed`, but still returned by devices running iOS 26.2 to 26.4.
   * @since 0.5.0
   */
  GuardianPaymentChecked = 'GUARDIAN_PAYMENT_CHECKED',
  /**
   * The user set their own age range using a payment method, like a credit
   * card.
   *
   * Only available on iOS (26.2+).
   *
   * @deprecated Deprecated by Apple in favor of `Confirmed`, but still returned by devices running iOS 26.2 to 26.4.
   * @since 0.5.0
   */
  PaymentChecked = 'PAYMENT_CHECKED',
  /**
   * The user declared their own age range without external verification.
   *
   * @since 0.4.0
   */
  SelfDeclared = 'SELF_DECLARED',
}

/**
 * How the age range was established.
 *
 * The tiers are ordered by increasing assurance from `TierA` to `TierD`.
 *
 * Only available on Android.
 *
 * @since 0.5.0
 */
export enum AgeRangeSource {
  /**
   * The user has self-declared their age.
   *
   * @since 0.5.0
   */
  TierA = 'TIER_A',
  /**
   * The age of the user is managed by a parent or guardian.
   *
   * @since 0.5.0
   */
  TierB = 'TIER_B',
  /**
   * The age of the user was assessed using a credit card, an email address, a
   * selfie assessment, a government ID or a tax ID.
   *
   * @since 0.5.0
   */
  TierC = 'TIER_C',
  /**
   * The age of the user was checked using a combination of a government ID and
   * a selfie assessment, or using a digital ID.
   *
   * @since 0.5.0
   */
  TierD = 'TIER_D',
}

/**
 * Whether the user has shared their age range with the app.
 *
 * @since 0.5.0
 */
export enum AgeRangeStatus {
  /**
   * The user has not shared their age range with the app.
   *
   * @since 0.5.0
   */
  NotShared = 'NOT_SHARED',
  /**
   * The user has shared their age range with the app.
   *
   * @since 0.5.0
   */
  Shared = 'SHARED',
  /**
   * The system did not specify whether the user has shared their age range.
   *
   * Treat this value like `NOT_SHARED`. Google Play does not document when it
   * is returned.
   *
   * Only available on Android.
   *
   * @since 0.5.0
   */
  Unspecified = 'UNSPECIFIED',
  /**
   * The user must verify their age in the Google Play Store before the age
   * range can be shared.
   *
   * Only available on Android.
   *
   * @since 0.5.0
   */
  VerificationRequired = 'VERIFICATION_REQUIRED',
}

/**
 * A parental control that is active for the user.
 *
 * Only available on iOS.
 *
 * @since 0.5.0
 */
export enum ParentalControl {
  /**
   * The system limits the communication features for the user.
   *
   * @since 0.5.0
   */
  CommunicationLimits = 'COMMUNICATION_LIMITS',
}

/**
 * A regulatory feature that the app must support for the current user.
 *
 * Only available on iOS.
 *
 * @since 0.5.0
 */
export enum RegulatoryFeature {
  /**
   * The user must share their age range with the app.
   *
   * @since 0.5.0
   */
  DeclaredAgeRangeRequired = 'DECLARED_AGE_RANGE_REQUIRED',
  /**
   * An adult user must acknowledge a significant change of the app.
   *
   * Use `showSignificantUpdateAcknowledgment(...)` to show the system
   * interface for the acknowledgment.
   *
   * @since 0.5.0
   */
  SignificantAppChangeRequiresAdultNotification = 'SIGNIFICANT_APP_CHANGE_REQUIRES_ADULT_NOTIFICATION',
  /**
   * A parent or guardian must consent to a significant change of the app.
   *
   * This plugin does not implement the consent flow. Use Apple's PermissionKit
   * framework to request the consent.
   *
   * @since 0.5.0
   */
  SignificantAppChangeRequiresParentalConsent = 'SIGNIFICANT_APP_CHANGE_REQUIRES_PARENTAL_CONSENT',
}

/**
 * The parental approval state for a significant change of the app.
 *
 * Only available on Android.
 *
 * @since 0.5.0
 */
export enum SignificantChangeStatus {
  /**
   * The parent or guardian has approved the most recent significant change and
   * all prior significant changes.
   *
   * @since 0.5.0
   */
  Approved = 'APPROVED',
  /**
   * The parent or guardian has declined one or more significant changes.
   *
   * @since 0.5.0
   */
  Declined = 'DECLINED',
  /**
   * The parent or guardian has not yet approved one or more significant
   * changes.
   *
   * @since 0.5.0
   */
  Pending = 'PENDING',
}

/**
 * @since 0.0.1
 */
export enum ErrorCode {
  /**
   * The age signals API is not available.
   *
   * On **Android**, the Play Store app version installed on the device might be
   * old. On **iOS**, the system was unable to share the age range.
   *
   * @since 0.0.1
   */
  ApiNotAvailable = 'API_NOT_AVAILABLE',
  /**
   * The app was not installed by Google Play.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  AppNotOwned = 'APP_NOT_OWNED',
  /**
   * Binding to the service in the Play Store has failed. This can be due to
   * having an old Play Store version installed on the device or device memory
   * is overloaded.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  CannotBindToService = 'CANNOT_BIND_TO_SERVICE',
  /**
   * There was a transient error in the client device.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  ClientTransientError = 'CLIENT_TRANSIENT_ERROR',
  /**
   * The fake manager is not available, because the app is not debuggable.
   *
   * Only available on Android.
   *
   * @since 0.5.0
   */
  FakeManagerNotAllowed = 'FAKE_MANAGER_NOT_ALLOWED',
  /**
   * The fake manager is not enabled.
   *
   * Only available on Android.
   *
   * @since 0.5.0
   */
  FakeManagerNotEnabled = 'FAKE_MANAGER_NOT_ENABLED',
  /**
   * Unknown internal error.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  InternalError = 'INTERNAL_ERROR',
  /**
   * The request contains invalid parameters.
   *
   * On **iOS**, this is also returned if the requested age gates are rejected
   * by the system.
   *
   * @since 0.5.0
   */
  InvalidRequest = 'INVALID_REQUEST',
  /**
   * No available network is found.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  NetworkError = 'NETWORK_ERROR',
  /**
   * Age signals are not supported on this device.
   *
   * @since 0.5.0
   */
  NotSupported = 'NOT_SUPPORTED',
  /**
   * Play Services is not available or its version is too old.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  PlayServicesNotFound = 'PLAY_SERVICES_NOT_FOUND',
  /**
   * Play Services needs to be updated.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  PlayServicesVersionOutdated = 'PLAY_SERVICES_VERSION_OUTDATED',
  /**
   * No Play Store app is found on the device.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  PlayStoreNotFound = 'PLAY_STORE_NOT_FOUND',
  /**
   * The Play Store app needs to be updated.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  PlayStoreVersionOutdated = 'PLAY_STORE_VERSION_OUTDATED',
  /**
   * No view controller was found to present the system interface.
   *
   * Only available on iOS.
   *
   * @since 0.5.0
   */
  PresentationContextUnavailable = 'PRESENTATION_CONTEXT_UNAVAILABLE',
  /**
   * The Age Signals SDK version is outdated.
   *
   * Only available on Android.
   *
   * @since 0.4.0
   */
  SdkVersionOutdated = 'SDK_VERSION_OUTDATED',
}
