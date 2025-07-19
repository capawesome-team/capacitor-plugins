export interface RealtimeKitPlugin {
  /**
   * Initialize the RealtimeKit plugin.
   *
   * This method must be called before using any other methods in the plugin.
   *
   * @since 0.0.0
   */
  initialize(): Promise<void>;
  /**
   * Start a meeting using the built-in UI.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.0
   */
  startMeeting(options: StartMeetingOptions): Promise<void>;
}

/**
 * @since 0.0.0
 */
export interface StartMeetingOptions {
  /**
   * The authentication token for the participant.
   *
   * @since 0.0.0
   */
  authToken: string;
  /**
   * Whether to join the meeting with audio enabled.
   *
   * @since 0.0.0
   * @default true
   * @example false
   */
  enableAudio?: boolean;
  /**
   * Whether to join the meeting with video enabled.
   *
   * @since 0.0.0
   * @default true
   * @example false
   */
  enableVideo?: boolean;
}
