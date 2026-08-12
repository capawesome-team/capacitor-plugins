export interface TextZoomPlugin {
  /**
   * Get the preferred text zoom of the device based on the operating system's
   * font size settings.
   *
   * This value can be used as an input for `setZoom(...)` to respect the
   * user's system font size preference.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getPreferredZoom(): Promise<GetPreferredZoomResult>;
  /**
   * Get the current text zoom of the WebView.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getZoom(): Promise<GetZoomResult>;
  /**
   * Set the text zoom of the WebView.
   *
   * The zoom is not persisted across app restarts. Persist the value with a
   * preferences plugin and re-apply it on app launch if needed.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  setZoom(options: SetZoomOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface GetPreferredZoomResult {
  /**
   * The preferred text zoom where `1.0` equals `100%` (no zoom).
   *
   * @example 1.0
   * @since 0.1.0
   */
  zoom: number;
}

/**
 * @since 0.1.0
 */
export interface GetZoomResult {
  /**
   * The current text zoom where `1.0` equals `100%` (no zoom).
   *
   * @example 1.0
   * @since 0.1.0
   */
  zoom: number;
}

/**
 * @since 0.1.0
 */
export interface SetZoomOptions {
  /**
   * The text zoom to set where `1.0` equals `100%` (no zoom).
   *
   * Must be greater than `0`.
   *
   * @example 1.5
   * @since 0.1.0
   */
  zoom: number;
}
