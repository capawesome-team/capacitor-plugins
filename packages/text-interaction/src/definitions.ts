export interface TextInteractionPlugin {
  /**
   * Disable text interaction in the app's WebView.
   *
   * This turns off system text-interaction gestures such as text selection,
   * the selection magnifier and the callout (copy/paste) menu.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  disable(): Promise<void>;
  /**
   * Enable text interaction in the app's WebView.
   *
   * Text interaction is enabled by default.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  enable(): Promise<void>;
  /**
   * Check whether text interaction is currently enabled in the app's WebView.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  isEnabled(): Promise<IsEnabledResult>;
}

/**
 * @since 0.1.0
 */
export interface IsEnabledResult {
  /**
   * Whether text interaction is currently enabled.
   *
   * @example true
   * @since 0.1.0
   */
  enabled: boolean;
}
