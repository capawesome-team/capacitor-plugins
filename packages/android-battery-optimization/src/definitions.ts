export interface BatteryOptimizationPlugin {
  /**
   * Returns whether or not battery optimization is enabled.
   *
   * Only available for Android.
   *
   * @since 0.0.1
   */
  isBatteryOptimizationEnabled(): Promise<IsBatteryOptimizationEnabledResult>;
  /**
   * Opens the battery optimization settings page.
   *
   * Only available for Android.
   *
   * @since 0.0.1
   */
  openBatteryOptimizationSettings(): Promise<void>;
  /**
   * Requests the battery optimization ignore permission.
   * This method needs the `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS` manifest permission.
   * Use this method only if your app meets an acceptable use case (see Google Play Policy).
   *
   * Only available for Android.
   *
   * @since 0.0.1
   */
  requestIgnoreBatteryOptimization(): Promise<void>;
}

/**
 * @since 0.0.1
 */
export interface IsBatteryOptimizationEnabledResult {
  /**
   * Whether or not battery optimization is enabled.
   *
   * @since 0.0.1
   */
  enabled: boolean;
}

export enum ErrorCode {
  /**
   * The picker was canceled by the user.
   *
   * @since 0.0.1
   */
  unavailable = 'unavailable',
}
