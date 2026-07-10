import type { PluginListenerHandle } from '@capacitor/core';

/**
 * @since 0.1.0
 */
export interface ShakePlugin {
  /**
   * Start watching for shake gestures.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  startWatching(options?: StartWatchingOptions): Promise<void>;
  /**
   * Stop watching for shake gestures.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  stopWatching(): Promise<void>;
  /**
   * Called when a shake gesture is detected.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'shake',
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
export interface StartWatchingOptions {
  /**
   * The sensitivity of the shake detection.
   *
   * Use `'light'` to detect gentle shakes and `'hard'` to only detect strong shakes.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   * @default 'medium'
   * @example 'medium'
   */
  sensitivity?: Sensitivity;
}

/**
 * @since 0.1.0
 */
export type Sensitivity = 'hard' | 'light' | 'medium';
