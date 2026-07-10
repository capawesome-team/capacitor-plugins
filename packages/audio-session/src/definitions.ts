import type { PluginListenerHandle } from '@capacitor/core';

export interface AudioSessionPlugin {
  /**
   * Configure the audio session category, mode and options.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  configure(options: ConfigureOptions): Promise<void>;
  /**
   * Get the audio outputs of the current audio route.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  getCurrentOutputs(): Promise<GetCurrentOutputsResult>;
  /**
   * Override the audio output port that is used for playback.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  overrideOutput(options: OverrideOutputOptions): Promise<void>;
  /**
   * Activate or deactivate the audio session.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  setActive(options: SetActiveOptions): Promise<void>;
  /**
   * Called when the audio session is interrupted, e.g. by an incoming phone call.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'interruption',
    listenerFunc: (event: InterruptionEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the audio route changes, e.g. when headphones are plugged in or out.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'routeChange',
    listenerFunc: (event: RouteChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface ConfigureOptions {
  /**
   * The audio session category.
   *
   * @example 'playback'
   * @since 0.1.0
   */
  category: AudioSessionCategory;
  /**
   * The audio session mode.
   *
   * @default 'default'
   * @example 'moviePlayback'
   * @since 0.1.0
   */
  mode?: AudioSessionMode;
  /**
   * The audio session category options.
   *
   * @since 0.1.0
   */
  options?: AudioSessionCategoryOptions;
}

/**
 * @since 0.1.0
 */
export interface AudioSessionCategoryOptions {
  /**
   * Whether AirPlay devices can be used for output.
   *
   * @default false
   * @since 0.1.0
   */
  allowAirPlay?: boolean;
  /**
   * Whether Bluetooth hands-free devices can be used for input and output.
   *
   * @default false
   * @since 0.1.0
   */
  allowBluetooth?: boolean;
  /**
   * Whether Bluetooth A2DP devices can be used for output.
   *
   * @default false
   * @since 0.1.0
   */
  allowBluetoothA2DP?: boolean;
  /**
   * Whether audio is routed to the built-in speaker instead of the receiver
   * when no other audio route is connected.
   *
   * @default false
   * @since 0.1.0
   */
  defaultToSpeaker?: boolean;
  /**
   * Whether audio from other sessions is reduced in volume (ducked) while audio
   * from this session plays.
   *
   * @default false
   * @since 0.1.0
   */
  duckOthers?: boolean;
  /**
   * Whether audio from other sessions using the `spokenAudio` mode is interrupted
   * and audio from this session is mixed with the remaining audio.
   *
   * @default false
   * @since 0.1.0
   */
  interruptSpokenAudioAndMixWithOthers?: boolean;
  /**
   * Whether audio from this session mixes with audio from other active sessions
   * instead of interrupting them.
   *
   * @default false
   * @since 0.1.0
   */
  mixWithOthers?: boolean;
}

/**
 * @since 0.1.0
 */
export interface SetActiveOptions {
  /**
   * Whether the audio session should be activated (`true`) or deactivated (`false`).
   *
   * @since 0.1.0
   */
  active: boolean;
  /**
   * Whether other audio sessions are notified when this session is deactivated,
   * so they can resume playback.
   *
   * @default true
   * @since 0.1.0
   */
  notifyOthersOnDeactivation?: boolean;
}

/**
 * @since 0.1.0
 */
export interface GetCurrentOutputsResult {
  /**
   * The audio outputs of the current audio route.
   *
   * @since 0.1.0
   */
  outputs: AudioSessionOutput[];
}

/**
 * @since 0.1.0
 */
export interface OverrideOutputOptions {
  /**
   * The audio output port to route playback to.
   *
   * @example 'speaker'
   * @since 0.1.0
   */
  type: OverrideOutputType;
}

/**
 * @since 0.1.0
 */
export interface AudioSessionOutput {
  /**
   * The human-readable name of the audio output port.
   *
   * @example 'Speaker'
   * @since 0.1.0
   */
  portName: string;
  /**
   * The type of the audio output port.
   *
   * @example 'Speaker'
   * @since 0.1.0
   */
  portType: string;
}

/**
 * @since 0.1.0
 */
export interface InterruptionEvent {
  /**
   * Whether playback should resume after the interruption ended.
   *
   * Only `true` if `type` is `ended`.
   *
   * @since 0.1.0
   */
  shouldResume: boolean;
  /**
   * The type of the interruption.
   *
   * @since 0.1.0
   */
  type: InterruptionType;
}

/**
 * @since 0.1.0
 */
export interface RouteChangeEvent {
  /**
   * The audio outputs of the audio route after the change.
   *
   * @since 0.1.0
   */
  outputs: AudioSessionOutput[];
  /**
   * The reason why the audio route changed.
   *
   * @since 0.1.0
   */
  reason: RouteChangeReason;
}

/**
 * The audio session category.
 *
 * @since 0.1.0
 */
export type AudioSessionCategory =
  | 'ambient'
  | 'multiRoute'
  | 'playAndRecord'
  | 'playback'
  | 'record'
  | 'soloAmbient';

/**
 * The audio session mode.
 *
 * @since 0.1.0
 */
export type AudioSessionMode =
  | 'default'
  | 'gameChat'
  | 'measurement'
  | 'moviePlayback'
  | 'spokenAudio'
  | 'videoChat'
  | 'videoRecording'
  | 'voiceChat'
  | 'voicePrompt';

/**
 * The audio output port to route playback to.
 *
 * @since 0.1.0
 */
export type OverrideOutputType = 'default' | 'speaker';

/**
 * The type of an audio session interruption.
 *
 * @since 0.1.0
 */
export type InterruptionType = 'began' | 'ended';

/**
 * The reason why the audio route changed.
 *
 * @since 0.1.0
 */
export type RouteChangeReason =
  | 'categoryChange'
  | 'newDeviceAvailable'
  | 'noSuitableRouteForCategory'
  | 'oldDeviceUnavailable'
  | 'override'
  | 'routeConfigurationChange'
  | 'unknown'
  | 'wakeFromSleep';

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The audio session could not be activated or deactivated.
   *
   * @since 0.1.0
   */
  ActivationFailed = 'ACTIVATION_FAILED',
  /**
   * The audio session could not be configured.
   *
   * @since 0.1.0
   */
  ConfigurationFailed = 'CONFIGURATION_FAILED',
}
