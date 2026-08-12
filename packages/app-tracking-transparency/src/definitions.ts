export interface AppTrackingTransparencyPlugin {
  /**
   * Get the advertising identifier (IDFA) of the device.
   *
   * The advertising identifier is only available if the tracking authorization
   * status is `authorized`. Otherwise, `null` is returned.
   *
   * **Note**: The iOS Simulator always returns `null`, even if the tracking
   * authorization status is `authorized`. Use a real device to test this method.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  getAdvertisingIdentifier(): Promise<GetAdvertisingIdentifierResult>;
  /**
   * Get the current tracking authorization status.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  getStatus(): Promise<GetStatusResult>;
  /**
   * Request permission to track the user.
   *
   * This will present the system tracking authorization prompt if the status
   * has not been determined yet. The prompt is only shown once per install.
   *
   * The `NSUserTrackingUsageDescription` key must be added to the `Info.plist`
   * file of your app.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  requestPermission(): Promise<RequestPermissionResult>;
}

/**
 * @since 0.1.0
 */
export interface GetStatusResult {
  /**
   * The current tracking authorization status.
   *
   * @since 0.1.0
   */
  status: TrackingStatus;
}

/**
 * @since 0.1.0
 */
export interface RequestPermissionResult {
  /**
   * The tracking authorization status after the request.
   *
   * @since 0.1.0
   */
  status: TrackingStatus;
}

/**
 * @since 0.1.0
 */
export interface GetAdvertisingIdentifierResult {
  /**
   * The advertising identifier (IDFA) of the device.
   *
   * Returns `null` if the tracking authorization status is not `authorized`
   * or if no advertising identifier is available, which is always the case
   * on the iOS Simulator.
   *
   * @example '6D92078A-8246-4BA4-AE5B-76104861E7DC'
   * @since 0.1.0
   */
  advertisingIdentifier: string | null;
}

/**
 * The tracking authorization status.
 *
 * - `authorized`: The user authorized access to app-related data for tracking.
 * - `denied`: The user denied access to app-related data for tracking.
 * - `notDetermined`: The user has not yet received a tracking authorization request.
 * - `restricted`: Tracking authorization is restricted and cannot be changed by the user.
 *
 * @since 0.1.0
 */
export type TrackingStatus =
  | 'authorized'
  | 'denied'
  | 'notDetermined'
  | 'restricted';
