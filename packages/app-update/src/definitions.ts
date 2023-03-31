import type { PluginListenerHandle } from '@capacitor/core';

export interface AppUpdatePlugin {
  /**
   * Returns app update informations.
   *
   * Only available for Android and iOS.
   */
  getAppUpdateInfo(options?: GetAppUpdateInfoOptions): Promise<AppUpdateInfo>;
  /**
   * Opens the app store entry of the app in the Play Store (Android) or App Store (iOS).
   *
   * Only available for Android and iOS.
   */
  openAppStore(options?: OpenAppStoreOptions): Promise<void>;
  /**
   * Performs an immediate in-app update.
   *
   * Only available for Android.
   */
  performImmediateUpdate(): Promise<AppUpdateResult>;
  /**
   * Starts a flexible in-app update.
   *
   * Only available for Android.
   */
  startFlexibleUpdate(): Promise<AppUpdateResult>;
  /**
   * Completes a flexible in-app update by restarting the app.
   *
   * Only available for Android.
   */
  completeFlexibleUpdate(): Promise<void>;
  /**
   * Adds a flexbile in-app update state change listener.
   *
   * Only available for Android.
   */
  addListener(
    eventName: 'onFlexibleUpdateStateChange',
    listenerFunc: (state: FlexibleUpdateState) => void,
  ): PluginListenerHandle;
  /**
   * Remove all listeners for this plugin.
   */
  removeAllListeners(): Promise<void>;
}

export interface GetAppUpdateInfoOptions {
  /**
   *  The two-letter country code for the store you want to search.
   *  See http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2 for a list of ISO Country Codes.
   *
   *  Only available for iOS.
   */
  country?: string;
}

export interface AppUpdateInfo {
  /**
   * Version code (Android) or CFBundleShortVersionString (iOS) of the currently installed app version.
   *
   * Only available for Android and iOS.
   */
  currentVersion: string;
  /**
   * Version code (Android) or CFBundleShortVersionString (iOS) of the update.
   *
   * Only available for Android and iOS.
   */
  availableVersion: string;
  /**
   * Release date of the update in ISO 8601 (UTC) format.
   *
   * Only available for iOS.
   */
  availableVersionReleaseDate?: string;
  /**
   * The app update availability.
   *
   * Only available for Android and iOS.
   */
  updateAvailability: AppUpdateAvailability;
  /**
   * In-app update priority for this update, as defined by the developer in the Google Play Developer API.
   *
   * Only available for Android.
   */
  updatePriority?: number;
  /**
   * `true` if an immediate update is allowed, otherwise `false`.
   *
   * Only available for Android.
   */
  immediateUpdateAllowed?: boolean;
  /**
   * `true` if a flexible update is allowed, otherwise `false`.
   *
   * Only available for Android.
   */
  flexibleUpdateAllowed?: boolean;
  /**
   * Number of days since the Google Play Store app on the user's device has learnt about an available update if an update is available or in progress.
   *
   * Only available for Android.
   */
  clientVersionStalenessDays?: number;
  /**
   * Flexible in-app update install status.
   *
   * Only available for Android.
   */
  installStatus?: FlexibleUpdateInstallStatus;
  /**
   * The minimum version of the operating system required for the app to run in iOS.
   *
   * Only available for iOS.
   */
  minimumOsVersion?: string;
}

export enum AppUpdateAvailability {
  UNKNOWN = 0,
  UPDATE_NOT_AVAILABLE = 1,
  UPDATE_AVAILABLE = 2,
  UPDATE_IN_PROGRESS = 3,
}

export interface OpenAppStoreOptions {
  /**
   *  The two-letter country code for the store you want to search.
   *  See http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2 for a list of ISO Country Codes.
   *
   *  Only available for iOS.
   */
  country?: string;
}

export interface FlexibleUpdateState {
  /**
   * Flexible in-app update install status.
   */
  installStatus: FlexibleUpdateInstallStatus;
  /**
   * Returns the number of bytes downloaded so far.
   * `undefined` if the install status is other than `DOWNLOADING`.
   */
  bytesDownloaded: number | undefined;
  /**
   * Returns the total number of bytes to be downloaded for this update.
   * `undefined` if the install status is other than `DOWNLOADING`.
   */
  totalBytesToDownload: number | undefined;
}

export enum FlexibleUpdateInstallStatus {
  UNKNOWN = 0,
  PENDING = 1,
  DOWNLOADING = 2,
  INSTALLING = 3,
  INSTALLED = 4,
  FAILED = 5,
  CANCELED = 6,
  DOWNLOADED = 11,
}

export interface AppUpdateResult {
  code: AppUpdateResultCode;
}

export enum AppUpdateResultCode {
  /**
   * The user has accepted the update.
   */
  OK = 0,
  /**
   * The user has denied or cancelled the update.
   */
  CANCELED = 1,
  /**
   * Some other error prevented either the user from providing consent or the update to proceed.
   */
  FAILED = 2,
  /**
   * No update available.
   */
  NOT_AVAILABLE = 3,
  /**
   * Update type not allowed.
   */
  NOT_ALLOWED = 4,
  /**
   * App update info missing.
   * You must call `getAppUpdateInfo()` before requesting an update.
   */
  INFO_MISSING = 5,
}
