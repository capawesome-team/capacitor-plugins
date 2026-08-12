export interface DeviceInfoPlugin {
  /**
   * Get a unique identifier for the device.
   *
   * On **Android**, the identifier is the `ANDROID_ID` value, which is unique
   * per app-signing key, user, and device. It is reset when the app is
   * reinstalled after the signing key changes or the device is factory reset.
   *
   * On **iOS**, the identifier is the `identifierForVendor` value, which is
   * unique per vendor and device. It is reset when all apps from the vendor are
   * uninstalled.
   *
   * On **Web**, the identifier is a random UUID that is persisted in the
   * browser's `localStorage`. It is reset when the browser storage is cleared.
   *
   * @since 0.1.0
   */
  getId(): Promise<GetIdResult>;
  /**
   * Get information about the device.
   *
   * @since 0.1.0
   */
  getInfo(): Promise<GetInfoResult>;
  /**
   * Get the time the device has been running since its last boot, in
   * milliseconds.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getUptime(): Promise<GetUptimeResult>;
}

/**
 * @since 0.1.0
 */
export interface GetIdResult {
  /**
   * The unique identifier of the device.
   *
   * @example '2f60cd8d-3b6a-4b3c-8f2e-1a2b3c4d5e6f'
   * @since 0.1.0
   */
  identifier: string;
}

/**
 * @since 0.1.0
 */
export interface GetInfoResult {
  /**
   * The Android SDK version number (API level).
   *
   * Returns `null` on platforms other than Android.
   *
   * Only available on Android.
   *
   * @example 34
   * @since 0.1.0
   */
  androidSdkVersion: number | null;
  /**
   * The type of the device.
   *
   * @since 0.1.0
   */
  deviceType: DeviceType;
  /**
   * The major version number of iOS.
   *
   * Returns `null` on platforms other than iOS.
   *
   * Only available on iOS.
   *
   * @example 17
   * @since 0.1.0
   */
  iosVersion: number | null;
  /**
   * Whether the app is running on a virtual device (simulator or emulator).
   *
   * @since 0.1.0
   */
  isVirtual: boolean;
  /**
   * The manufacturer of the device.
   *
   * @example 'Apple'
   * @since 0.1.0
   */
  manufacturer: string;
  /**
   * The model identifier of the device.
   *
   * On **iOS**, this is the internal model identifier (e.g. `iPhone13,4`), not
   * the marketing name.
   *
   * @example 'iPhone13,4'
   * @since 0.1.0
   */
  model: string;
  /**
   * The name of the device.
   *
   * On **iOS 16 and newer**, a generic device name (e.g. `iPhone`) is returned
   * unless the app has the required entitlement.
   *
   * Returns `null` if the name cannot be determined.
   *
   * @example 'iPhone'
   * @since 0.1.0
   */
  name: string | null;
  /**
   * The operating system of the device.
   *
   * @since 0.1.0
   */
  operatingSystem: OperatingSystem;
  /**
   * The version of the operating system.
   *
   * @example '17.5'
   * @since 0.1.0
   */
  osVersion: string;
  /**
   * The platform the app is running on.
   *
   * @since 0.1.0
   */
  platform: Platform;
  /**
   * The total amount of memory of the device, in bytes.
   *
   * Returns `null` if the total memory cannot be determined.
   *
   * Only available on Android and iOS.
   *
   * @example 4294967296
   * @since 0.1.0
   */
  totalMemory: number | null;
  /**
   * The amount of memory used by the app, in bytes.
   *
   * Returns `null` if the used memory cannot be determined.
   *
   * Only available on Android and iOS.
   *
   * @example 134217728
   * @since 0.1.0
   */
  usedMemory: number | null;
  /**
   * The version of the WebView that renders the app.
   *
   * On **iOS**, the version of the operating system is returned, because the
   * WebKit version is tied to it.
   *
   * Returns `null` if the WebView version cannot be determined.
   *
   * @example '126.0.6478.40'
   * @since 0.1.0
   */
  webViewVersion: string | null;
}

/**
 * @since 0.1.0
 */
export interface GetUptimeResult {
  /**
   * The time the device has been running since its last boot, in milliseconds.
   *
   * @example 123456789
   * @since 0.1.0
   */
  uptime: number;
}

/**
 * The type of a device.
 *
 * - `phone`: A handheld phone-sized device.
 * - `tablet`: A tablet-sized device.
 * - `desktop`: A desktop or laptop computer.
 * - `tv`: A television or set-top box.
 * - `unknown`: The type could not be determined.
 *
 * @since 0.1.0
 */
export type DeviceType = 'phone' | 'tablet' | 'desktop' | 'tv' | 'unknown';

/**
 * The operating system of a device.
 *
 * @since 0.1.0
 */
export type OperatingSystem = 'android' | 'ios' | 'windows' | 'mac' | 'unknown';

/**
 * The platform an app is running on.
 *
 * @since 0.1.0
 */
export type Platform = 'android' | 'ios' | 'web';
