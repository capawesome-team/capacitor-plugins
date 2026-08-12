export interface HomeIndicatorPlugin {
  /**
   * Hide the home indicator.
   *
   * The home indicator auto-hides while the screen is idle and reappears
   * when the user touches the screen. The system does not allow permanently
   * removing the home indicator.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  hide(): Promise<void>;
  /**
   * Check whether the home indicator is currently set to be hidden.
   *
   * This reflects the plugin-managed state, not the actual visibility of the
   * home indicator on screen (which the system controls). The state is kept
   * in memory only and resets when the app is restarted.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  isHidden(): Promise<IsHiddenResult>;
  /**
   * Show the home indicator.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  show(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface IsHiddenResult {
  /**
   * Whether the home indicator is currently set to be hidden.
   *
   * @example true
   * @since 0.1.0
   */
  hidden: boolean;
}
