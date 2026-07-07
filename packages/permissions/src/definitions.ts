/**
 * @since 0.1.0
 */
export interface PermissionsPlugin {
  /**
   * Check the current states of one or more permissions.
   *
   * This method never displays a permission prompt.
   *
   * @since 0.1.0
   */
  check(options: CheckOptions): Promise<CheckResult>;
  /**
   * Request one or more permissions from the user.
   *
   * Permissions that are already granted or that cannot be requested
   * on the current platform are not requested again. In this case,
   * the current state of the permission is returned.
   *
   * On Android, the corresponding permissions must be declared in the
   * `AndroidManifest.xml` file of your app. Otherwise, the call is rejected.
   *
   * On iOS, the corresponding usage descriptions must be provided in the
   * `Info.plist` file of your app. Otherwise, the call is rejected.
   *
   * On the web, only the `NOTIFICATIONS` permission can be requested.
   * For all other permissions, the current state is returned since
   * browsers display the permission prompt when the corresponding
   * web API is used for the first time.
   *
   * @since 0.1.0
   */
  request(options: RequestOptions): Promise<RequestResult>;
}

/**
 * @since 0.1.0
 */
export interface CheckOptions {
  /**
   * The permissions to check.
   *
   * @since 0.1.0
   * @example ["CAMERA", "MICROPHONE"]
   */
  permissions: Permission[];
}

/**
 * @since 0.1.0
 */
export interface CheckResult {
  /**
   * The states of the checked permissions in the same order
   * as the provided permissions.
   *
   * @since 0.1.0
   */
  statuses: PermissionStatus[];
}

/**
 * @since 0.1.0
 */
export interface PermissionStatus {
  /**
   * The permission that was checked or requested.
   *
   * @since 0.1.0
   */
  permission: Permission;
  /**
   * The state of the permission.
   *
   * @since 0.1.0
   */
  state: PermissionState;
}

/**
 * @since 0.1.0
 */
export interface RequestOptions {
  /**
   * The permissions to request.
   *
   * @since 0.1.0
   * @example ["CAMERA", "MICROPHONE"]
   */
  permissions: Permission[];
}

/**
 * @since 0.1.0
 */
export interface RequestResult {
  /**
   * The states of the requested permissions in the same order
   * as the provided permissions.
   *
   * @since 0.1.0
   */
  statuses: PermissionStatus[];
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The permission request failed.
   *
   * @since 0.1.0
   */
  RequestFailed = 'REQUEST_FAILED',
  /**
   * A required usage description is missing in the `Info.plist` file.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  UsageDescriptionMissing = 'USAGE_DESCRIPTION_MISSING',
}

/**
 * @since 0.1.0
 */
export enum Permission {
  /**
   * Permission to use Bluetooth.
   *
   * On Android 12 (API level 31) and later, this covers the
   * `BLUETOOTH_SCAN` and `BLUETOOTH_CONNECT` permissions.
   * On older Android versions, no runtime permission is required
   * and the state is always `granted`.
   *
   * On the web, this permission is not available.
   *
   * @since 0.1.0
   */
  Bluetooth = 'BLUETOOTH',
  /**
   * Permission to read and write calendar events.
   *
   * On iOS 17 and later, write-only access is reported as `limited`.
   *
   * On the web, this permission is not available.
   *
   * @since 0.1.0
   */
  Calendar = 'CALENDAR',
  /**
   * Permission to use the camera.
   *
   * @since 0.1.0
   */
  Camera = 'CAMERA',
  /**
   * Permission to read and write contacts.
   *
   * On iOS 18 and later, limited access is reported as `limited`.
   *
   * On the web, this permission is not available.
   *
   * @since 0.1.0
   */
  Contacts = 'CONTACTS',
  /**
   * Permission to access the location while the app is in use.
   *
   * On Android 12 (API level 31) and later, the user may grant only
   * approximate location access. In this case, the state is still
   * reported as `granted`.
   *
   * @since 0.1.0
   */
  Location = 'LOCATION',
  /**
   * Permission to access the location even while the app is in the
   * background.
   *
   * This permission should only be requested after the `LOCATION`
   * permission has been granted.
   *
   * On the web, this permission is not available.
   *
   * @since 0.1.0
   */
  LocationAlways = 'LOCATION_ALWAYS',
  /**
   * Permission to use the microphone.
   *
   * @since 0.1.0
   */
  Microphone = 'MICROPHONE',
  /**
   * Permission to access motion and fitness data.
   *
   * On Android 10 (API level 29) and later, this covers the
   * `ACTIVITY_RECOGNITION` permission. On older Android versions,
   * no runtime permission is required and the state is always `granted`.
   *
   * On the web, this permission is not available.
   *
   * @since 0.1.0
   */
  Motion = 'MOTION',
  /**
   * Permission to display notifications.
   *
   * On Android 13 (API level 33) and later, this covers the
   * `POST_NOTIFICATIONS` permission. On older Android versions,
   * no runtime permission is required and the state is always `granted`.
   *
   * @since 0.1.0
   */
  Notifications = 'NOTIFICATIONS',
  /**
   * Permission to access the photo library.
   *
   * On Android 14 (API level 34) and later and on iOS, partial access
   * to the photo library is reported as `limited`.
   *
   * On the web, this permission is not available.
   *
   * @since 0.1.0
   */
  Photos = 'PHOTOS',
  /**
   * Permission to read and write reminders.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  Reminders = 'REMINDERS',
}

/**
 * The state of a permission.
 *
 * - `granted`: The permission is granted.
 * - `denied`: The permission is denied. It can no longer be requested from
 *   within the app. The user must grant it manually in the system settings.
 * - `prompt`: The permission has not been requested yet.
 *   Requesting it will display a permission prompt.
 * - `prompt-with-rationale`: The permission was denied before but can be
 *   requested again. It's recommended to explain why the permission is
 *   needed before requesting it again. Only used on Android.
 * - `limited`: The permission is granted partially, for example limited
 *   photo library access or write-only calendar access.
 * - `unavailable`: The permission does not exist on the current platform.
 *
 * @since 0.1.0
 */
export type PermissionState =
  | 'granted'
  | 'denied'
  | 'prompt'
  | 'prompt-with-rationale'
  | 'limited'
  | 'unavailable';
