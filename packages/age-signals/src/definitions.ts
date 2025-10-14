/**
 * @since 0.0.1
 */
export interface AgeSignalsPlugin {
  /**
   * Request the user's age signals from Google Play.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  checkAgeSignals(): Promise<CheckAgeSignalsResult>;
}

/**
 * @since 0.0.1
 */
export interface CheckAgeSignalsResult {
  /**
   * The user's verification status.
   *
   * @since 0.0.1
   */
  userStatus: UserStatus;
  /**
   * The (inclusive) lower bound of a supervised user's age range.
   *
   * Only available when `userStatus` is `SUPERVISED`, `SUPERVISED_APPROVAL_PENDING`, or `SUPERVISED_APPROVAL_DENIED`.
   *
   * @since 0.0.1
   * @example 13
   */
  ageLower?: number;
  /**
   * The (inclusive) upper bound of a supervised user's age range.
   *
   * Only available when `userStatus` is `SUPERVISED`, `SUPERVISED_APPROVAL_PENDING`, or `SUPERVISED_APPROVAL_DENIED` and the user's age is under 18.
   *
   * @since 0.0.1
   * @example 15
   */
  ageUpper?: number;
  /**
   * The effective from date of the most recent significant change that was approved.
   * When an app is installed, the date of the most recent significant change prior to install is used.
   *
   * Only available when `userStatus` is `SUPERVISED_APPROVAL_PENDING` or `SUPERVISED_APPROVAL_DENIED`.
   *
   * @since 0.0.1
   * @example "2024-01-15"
   */
  mostRecentApprovalDate?: string;
  /**
   * An ID assigned to supervised user installs by Google Play, used for the purposes of notifying you of revoked app approval.
   *
   * Only available when `userStatus` is `SUPERVISED`, `SUPERVISED_APPROVAL_PENDING`, or `SUPERVISED_APPROVAL_DENIED`.
   *
   * @since 0.0.1
   * @example "abc123xyz"
   */
  installId?: string;
}

/**
 * @since 0.0.1
 */
export enum UserStatus {
  /**
   * The user is over 18. Google verified the user's age using a commercially reasonable method such as a government-issued ID, credit card, or facial age estimation.
   *
   * @since 0.0.1
   */
  Verified = 'VERIFIED',
  /**
   * The user has a supervised Google Account managed by a parent who sets their age.
   * Use `ageLower` and `ageUpper` to determine the user's age range.
   *
   * @since 0.0.1
   */
  Supervised = 'SUPERVISED',
  /**
   * The user has a supervised Google Account, and their supervising parent has not yet approved one or more pending significant changes.
   * Use `ageLower` and `ageUpper` to determine the user's age range.
   * Use `mostRecentApprovalDate` to determine the last significant change that was approved.
   *
   * @since 0.0.1
   */
  SupervisedApprovalPending = 'SUPERVISED_APPROVAL_PENDING',
  /**
   * The user has a supervised Google Account, and their supervising parent denied approval for one or more significant changes.
   * Use `ageLower` and `ageUpper` to determine the user's age range.
   * Use `mostRecentApprovalDate` to determine the last significant change that was approved.
   *
   * @since 0.0.1
   */
  SupervisedApprovalDenied = 'SUPERVISED_APPROVAL_DENIED',
  /**
   * The user is not verified or supervised in applicable jurisdictions and regions. These users could be over or under 18.
   * To obtain an age signal from Google Play, ask the user to visit the Play Store to resolve their status.
   *
   * @since 0.0.1
   */
  Unknown = 'UNKNOWN',
  /**
   * All other users return this value.
   *
   * @since 0.0.1
   */
  Empty = 'EMPTY',
}

/**
 * @since 0.0.1
 */
export enum ErrorCode {
  /**
   * The Play Age Signals API is not available. The Play Store app version installed on the device might be old.
   *
   * @since 0.0.1
   */
  ApiNotAvailable = 'API_NOT_AVAILABLE',
  /**
   * No Play Store app is found on the device.
   *
   * @since 0.0.1
   */
  PlayStoreNotFound = 'PLAY_STORE_NOT_FOUND',
  /**
   * No available network is found.
   *
   * @since 0.0.1
   */
  NetworkError = 'NETWORK_ERROR',
  /**
   * Play Services is not available or its version is too old.
   *
   * @since 0.0.1
   */
  PlayServicesNotFound = 'PLAY_SERVICES_NOT_FOUND',
  /**
   * Binding to the service in the Play Store has failed. This can be due to having an old Play Store version installed on the device or device memory is overloaded.
   *
   * @since 0.0.1
   */
  CannotBindToService = 'CANNOT_BIND_TO_SERVICE',
  /**
   * The Play Store app needs to be updated.
   *
   * @since 0.0.1
   */
  PlayStoreVersionOutdated = 'PLAY_STORE_VERSION_OUTDATED',
  /**
   * Play Services needs to be updated.
   *
   * @since 0.0.1
   */
  PlayServicesVersionOutdated = 'PLAY_SERVICES_VERSION_OUTDATED',
  /**
   * There was a transient error in the client device.
   *
   * @since 0.0.1
   */
  ClientTransientError = 'CLIENT_TRANSIENT_ERROR',
  /**
   * The app was not installed by Google Play.
   *
   * @since 0.0.1
   */
  AppNotOwned = 'APP_NOT_OWNED',
  /**
   * Unknown internal error.
   *
   * @since 0.0.1
   */
  InternalError = 'INTERNAL_ERROR',
}
