/**
 * @since 0.1.0
 */
export interface GooglePlayServicesPlugin {
  /**
   * Get the status of Google Play Services on this device.
   *
   * Use this method to find out why Google Play Services is not available,
   * for example to distinguish devices without Google Play Services
   * from devices with an outdated version.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  getStatus(): Promise<GetStatusResult>;
  /**
   * Get the version code of the Google Play Services APK installed on this device.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  getVersion(): Promise<GetVersionResult>;
  /**
   * Check whether Google Play Services is available on this device.
   *
   * This resolves to `true` if Google Play Services is installed, enabled and up to date.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Make Google Play Services available on this device.
   *
   * If Google Play Services is missing, disabled, outdated or currently updating,
   * this shows the system dialog that prompts the user to install, enable or update it.
   * The promise resolves once Google Play Services is available.
   * If the user dismisses the dialog, the promise is rejected with the `CANCELED` error code.
   * If Google Play Services can not be made available, for example on devices
   * without the Google Play Store, the promise is rejected with the error message
   * from Google Play Services.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  makeAvailable(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface GetStatusResult {
  /**
   * The status of Google Play Services on this device.
   *
   * @since 0.1.0
   * @example "SUCCESS"
   */
  status: Status;
}

/**
 * @since 0.1.0
 */
export interface GetVersionResult {
  /**
   * The version code of the Google Play Services APK installed on this device.
   *
   * This is `0` if Google Play Services is not installed.
   *
   * @since 0.1.0
   * @example 254036000
   */
  version: number;
}

/**
 * @since 0.1.0
 */
export interface IsAvailableResult {
  /**
   * Whether or not Google Play Services is available on this device.
   *
   * @since 0.1.0
   * @example true
   */
  available: boolean;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The user canceled the operation, for example by dismissing the dialog.
   *
   * @since 0.1.0
   */
  Canceled = 'CANCELED',
}

/**
 * @since 0.1.0
 */
export enum Status {
  /**
   * Google Play Services is disabled on this device.
   *
   * @since 0.1.0
   */
  ServiceDisabled = 'SERVICE_DISABLED',
  /**
   * The installed version of Google Play Services is not authentic.
   *
   * @since 0.1.0
   */
  ServiceInvalid = 'SERVICE_INVALID',
  /**
   * Google Play Services is not installed on this device.
   *
   * @since 0.1.0
   */
  ServiceMissing = 'SERVICE_MISSING',
  /**
   * Google Play Services is currently being updated on this device.
   *
   * @since 0.1.0
   */
  ServiceUpdating = 'SERVICE_UPDATING',
  /**
   * The installed version of Google Play Services is out of date.
   *
   * @since 0.1.0
   */
  ServiceVersionUpdateRequired = 'SERVICE_VERSION_UPDATE_REQUIRED',
  /**
   * Google Play Services is available.
   *
   * @since 0.1.0
   */
  Success = 'SUCCESS',
}
