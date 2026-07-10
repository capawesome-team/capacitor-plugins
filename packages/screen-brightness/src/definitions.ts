export interface ScreenBrightnessPlugin {
  /**
   * Get the current screen brightness.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getBrightness(): Promise<GetBrightnessResult>;
  /**
   * Reset the screen brightness to the system default.
   *
   * This hands the brightness control back to the operating system.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  resetBrightness(): Promise<void>;
  /**
   * Set the screen brightness.
   *
   * On Android, only the brightness of the current app window is changed.
   * The change is reverted automatically as soon as the app is closed.
   *
   * On iOS, this changes the **system** brightness and the change persists
   * after the app is closed.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  setBrightness(options: SetBrightnessOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface GetBrightnessResult {
  /**
   * The current screen brightness as a value between `0.0` (darkest) and
   * `1.0` (brightest).
   *
   * @example 0.5
   * @since 0.1.0
   */
  brightness: number;
}

/**
 * @since 0.1.0
 */
export interface SetBrightnessOptions {
  /**
   * The screen brightness to set as a value between `0.0` (darkest) and
   * `1.0` (brightest).
   *
   * @example 0.5
   * @since 0.1.0
   */
  brightness: number;
}
