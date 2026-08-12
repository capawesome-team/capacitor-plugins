import type { PluginListenerHandle } from '@capacitor/core';

export interface SilentModePlugin {
  /**
   * Get the current ringer mode of the device.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  getRingerMode(): Promise<GetRingerModeResult>;
  /**
   * Get the current silent mode state of the device.
   *
   * On Android, the device is considered silent if the ringer mode is not set
   * to normal (that is, either vibrate or silent).
   *
   * On iOS, there is no public API to read the ring/silent switch. The state is
   * therefore determined using a heuristic that plays a short muted system sound
   * and measures how long it takes to complete. The result may be inaccurate
   * while other audio is playing.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  isSilent(): Promise<IsSilentResult>;
  /**
   * Listen for changes to the silent mode state of the device.
   *
   * The device is only observed while at least one listener is attached.
   *
   * On iOS, the state is polled on a timer while the app is in the foreground
   * and the listener is not called while the app is in the background.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'silentModeChange',
    listenerFunc: (event: SilentModeChangeEvent) => void,
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
export interface IsSilentResult {
  /**
   * Whether the device is currently in silent mode.
   *
   * @example true
   * @since 0.1.0
   */
  silent: boolean;
}

/**
 * @since 0.1.0
 */
export interface GetRingerModeResult {
  /**
   * The current ringer mode of the device.
   *
   * @example 'normal'
   * @since 0.1.0
   */
  mode: RingerMode;
}

/**
 * @since 0.1.0
 */
export interface SilentModeChangeEvent {
  /**
   * Whether the device is currently in silent mode.
   *
   * @example true
   * @since 0.1.0
   */
  silent: boolean;
}

/**
 * The ringer mode of the device.
 *
 * - `normal`: The device plays sounds and rings for incoming calls.
 * - `silent`: The device is silent and does not vibrate.
 * - `vibrate`: The device is silent but vibrates.
 *
 * @since 0.1.0
 */
export type RingerMode = 'normal' | 'silent' | 'vibrate';
