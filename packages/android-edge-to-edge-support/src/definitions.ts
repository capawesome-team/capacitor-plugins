export interface EdgeToEdgePlugin {
  /**
   * Set the background color of the status bar and navigation bar.
   *
   * Only available on Android.
   *
   * @since 7.0.0
   */
  setBackgroundColor(options: SetBackgroundColorOptions): Promise<void>;
}

/**
 * @since 7.0.0
 */
export interface SetBackgroundColorOptions {
  /**
   * The hexadecimal color to set as the background color of the status bar and navigation bar.
   *
   * @since 7.0.0
   * @example "#ffffff"
   * @example "#000000"
   */
  color: string;
}
