import type { PluginListenerHandle } from '@capacitor/core';

export interface ScreenReaderPlugin {
  /**
   * Post an accessibility announcement to the active screen reader.
   *
   * This does **not** perform text-to-speech. It posts an announcement that is
   * read out by the screen reader (VoiceOver/TalkBack) if one is active. For
   * real text-to-speech, use the [Speech Synthesis](https://capawesome.io/docs/sdks/capacitor/speech-synthesis/)
   * plugin instead.
   *
   * On the web, the announcement is made through a visually hidden
   * `aria-live` region, so it is only read out if the user has a screen reader
   * running.
   *
   * @since 0.1.0
   */
  announce(options: AnnounceOptions): Promise<void>;
  /**
   * Check whether a screen reader is currently enabled.
   *
   * On Android, this refers to whether touch exploration (TalkBack) is enabled.
   * On iOS, this refers to whether VoiceOver is running.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  isEnabled(): Promise<IsEnabledResult>;
  /**
   * Listen for changes to the enabled state of the screen reader.
   *
   * The device is only observed while at least one listener is attached.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'stateChange',
    listenerFunc: (event: StateChangeEvent) => void,
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
export interface AnnounceOptions {
  /**
   * The message to announce.
   *
   * @example 'The item was added to your cart.'
   * @since 0.1.0
   */
  value: string;
  /**
   * The language of the message as a BCP 47 language tag.
   *
   * This helps the screen reader pronounce the message correctly.
   *
   * Only available on Android.
   *
   * @example 'en'
   * @since 0.1.0
   */
  language?: string;
}

/**
 * @since 0.1.0
 */
export interface IsEnabledResult {
  /**
   * Whether a screen reader is currently enabled.
   *
   * @example true
   * @since 0.1.0
   */
  enabled: boolean;
}

/**
 * @since 0.1.0
 */
export interface StateChangeEvent {
  /**
   * Whether a screen reader is currently enabled.
   *
   * @example true
   * @since 0.1.0
   */
  enabled: boolean;
}
