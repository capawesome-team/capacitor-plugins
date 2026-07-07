/**
 * @since 0.1.0
 */
export interface RootDetectionPlugin {
  /**
   * Check whether developer mode is enabled on the device.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  isDeveloperModeEnabled(): Promise<IsDeveloperModeEnabledResult>;
  /**
   * Check whether the app is running on an emulator (Android) or simulator (iOS).
   *
   * @since 0.1.0
   */
  isEmulator(): Promise<IsEmulatorResult>;
  /**
   * Check whether the device is rooted (Android) or jailbroken (iOS).
   *
   * **Attention**: This is a best-effort, client-side check that can be
   * bypassed by a determined attacker. Do not rely on it as the sole security
   * measure. Use the App Integrity plugin when server-verifiable trust is
   * required.
   *
   * @since 0.1.0
   */
  isRooted(): Promise<IsRootedResult>;
}

/**
 * @since 0.1.0
 */
export interface IsDeveloperModeEnabledResult {
  /**
   * Whether developer mode is enabled on the device.
   *
   * @example false
   * @since 0.1.0
   */
  enabled: boolean;
}

/**
 * @since 0.1.0
 */
export interface IsEmulatorResult {
  /**
   * Whether the app is running on an emulator (Android) or simulator (iOS).
   *
   * @example false
   * @since 0.1.0
   */
  emulator: boolean;
}

/**
 * @since 0.1.0
 */
export interface IsRootedResult {
  /**
   * Whether the device is rooted (Android) or jailbroken (iOS).
   *
   * @example false
   * @since 0.1.0
   */
  rooted: boolean;
}
