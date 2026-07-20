import type { PluginListenerHandle } from '@capacitor/core';

export interface VolumePlugin {
  /**
   * Get the current volume level.
   *
   * On iOS, this always returns the media volume.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getVolume(options?: GetVolumeOptions): Promise<GetVolumeResult>;
  /**
   * Check whether the hardware volume buttons are currently being watched.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  isWatching(): Promise<IsWatchingResult>;
  /**
   * Set the volume level.
   *
   * On iOS, this always sets the media volume.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  setVolume(options: SetVolumeOptions): Promise<void>;
  /**
   * Start watching the hardware volume buttons.
   *
   * The `volumeButtonPressed`, `volumeButtonReleased` and `volumeChange`
   * events are only emitted while watching.
   *
   * If the volume buttons are already being watched, this call has
   * no effect. Call `stopWatching()` first to change the options.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  startWatching(options?: StartWatchingOptions): Promise<void>;
  /**
   * Stop watching the hardware volume buttons.
   *
   * On iOS, this also restores the volume level that was set when
   * watching started if the `suppressVolumeChange` option was enabled.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  stopWatching(): Promise<void>;
  /**
   * Called when a hardware volume button is pressed while watching.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'volumeButtonPressed',
    listenerFunc: (event: VolumeButtonPressedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a hardware volume button is released while watching.
   *
   * Only available on Android.
   *
   * @since 0.2.0
   */
  addListener(
    eventName: 'volumeButtonReleased',
    listenerFunc: (event: VolumeButtonReleasedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the volume level changes while watching.
   *
   * On Android, this is called for changes to the music stream.
   * On iOS, this is called for changes to the media volume and is
   * not called while the `suppressVolumeChange` option is enabled.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'volumeChange',
    listenerFunc: (event: VolumeChangeEvent) => void,
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
export interface GetVolumeOptions {
  /**
   * The audio stream to get the volume for.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   * @default VolumeStream.Music
   * @example 'MUSIC'
   */
  stream?: VolumeStream;
}

/**
 * @since 0.1.0
 */
export interface GetVolumeResult {
  /**
   * The current volume level as a value between `0` and `1`.
   *
   * @since 0.1.0
   * @example 0.5
   */
  volume: number;
}

/**
 * @since 0.1.0
 */
export interface SetVolumeOptions {
  /**
   * The audio stream to set the volume for.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   * @default VolumeStream.Music
   * @example 'MUSIC'
   */
  stream?: VolumeStream;
  /**
   * The volume level to set as a value between `0` and `1`.
   *
   * @since 0.1.0
   * @example 0.5
   */
  volume: number;
}

/**
 * @since 0.1.0
 */
export interface StartWatchingOptions {
  /**
   * Whether to keep the volume level unchanged when a hardware volume
   * button is pressed while watching.
   *
   * On Android, the volume key events are consumed so that the system
   * does not apply the volume change or display the volume panel.
   * On iOS, the volume level is reset immediately after each button
   * press and the system volume indicator is hidden. If the volume
   * level is close to the minimum or maximum, it is nudged to a value
   * from which both buttons can still be detected. The original volume
   * level is restored when watching stops.
   *
   * @since 0.1.0
   * @default false
   * @example true
   */
  suppressVolumeChange?: boolean;
}

/**
 * @since 0.1.0
 */
export interface IsWatchingResult {
  /**
   * Whether the hardware volume buttons are currently being watched.
   *
   * @since 0.1.0
   * @example true
   */
  watching: boolean;
}

/**
 * @since 0.1.0
 */
export interface VolumeButtonPressedEvent {
  /**
   * The direction of the pressed hardware volume button.
   *
   * @since 0.1.0
   * @example 'up'
   */
  direction: Direction;
}

/**
 * @since 0.2.0
 */
export interface VolumeButtonReleasedEvent {
  /**
   * The direction of the released hardware volume button.
   *
   * @since 0.2.0
   * @example 'up'
   */
  direction: Direction;
}

/**
 * @since 0.1.0
 */
export interface VolumeChangeEvent {
  /**
   * The new volume level as a value between `0` and `1`.
   *
   * @since 0.1.0
   * @example 0.5
   */
  volume: number;
}

/**
 * The direction of a hardware volume button.
 *
 * - `up`: The volume up button.
 * - `down`: The volume down button.
 *
 * @since 0.1.0
 */
export type Direction = 'down' | 'up';

/**
 * The audio stream of the device.
 *
 * @since 0.1.0
 */
export enum VolumeStream {
  /**
   * The audio stream for alarms.
   *
   * @since 0.1.0
   */
  Alarm = 'ALARM',
  /**
   * The audio stream for music and media playback.
   *
   * @since 0.1.0
   */
  Music = 'MUSIC',
  /**
   * The audio stream for notifications.
   *
   * @since 0.1.0
   */
  Notification = 'NOTIFICATION',
  /**
   * The audio stream for the phone ring.
   *
   * @since 0.1.0
   */
  Ring = 'RING',
  /**
   * The audio stream for system sounds.
   *
   * @since 0.1.0
   */
  System = 'SYSTEM',
  /**
   * The audio stream for phone calls.
   *
   * @since 0.1.0
   */
  VoiceCall = 'VOICE_CALL',
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * Do Not Disturb access is required to change the volume of the
   * ring, notification or system stream while Do Not Disturb is active.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  DoNotDisturbAccessRequired = 'DO_NOT_DISTURB_ACCESS_REQUIRED',
}
