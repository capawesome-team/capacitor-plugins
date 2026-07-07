import type { PluginListenerHandle } from '@capacitor/core';

export interface PrivacyScreenPlugin {
  /**
   * Disable the privacy screen.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  disable(): Promise<void>;
  /**
   * Enable the privacy screen.
   *
   * On Android, this sets the `FLAG_SECURE` flag on the window, which hides the
   * app content in the app switcher and blocks screenshots and screen recordings.
   *
   * On iOS, this installs an overlay that hides the app content in the app
   * switcher. Screenshots are only blocked if the `ios.preventScreenshots`
   * option is enabled.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  enable(options?: EnableOptions): Promise<void>;
  /**
   * Check whether the privacy screen is currently enabled.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  isEnabled(): Promise<IsEnabledResult>;
  /**
   * Listen for when the user takes a screenshot of the app.
   *
   * The event can only be observed after the screenshot has been taken and
   * therefore cannot be prevented.
   *
   * On Android, this event is only emitted on Android 14 (API level 34) and
   * newer. On older versions, the listener is never called.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'screenshotTaken',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.1.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface EnableOptions {
  /**
   * Options that are only applied on iOS.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  ios?: EnableIosOptions;
}

/**
 * @since 0.1.0
 */
export interface EnableIosOptions {
  /**
   * Whether to block screenshots while the privacy screen is enabled.
   *
   * There is no official iOS API to prevent screenshots. This uses an
   * unofficial technique that may stop working in future iOS versions.
   *
   * Only available on iOS.
   *
   * @default false
   * @example true
   * @since 0.1.0
   */
  preventScreenshots?: boolean;
}

/**
 * @since 0.1.0
 */
export interface IsEnabledResult {
  /**
   * Whether the privacy screen is currently enabled.
   *
   * @example true
   * @since 0.1.0
   */
  enabled: boolean;
}
