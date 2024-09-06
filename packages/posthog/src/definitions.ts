export interface PosthogPlugin {
  /**
   * Assign another distinct ID to the current user.
   *
   * @since 6.0.0
   */
  alias(options: AliasOptions): Promise<void>;
  /**
   * Capture an event.
   *
   * @since 6.0.0
   */
  capture(options: CaptureOptions): Promise<void>;
  /**
   * Flush all events in the queue.
   *
   * @since 6.0.0
   */
  flush(): Promise<void>;
  /**
   * Identify the current user.
   *
   * @since 6.0.0
   */
  identify(options: IdentifyOptions): Promise<void>;
  /**
   * Register a new super property. This property will be sent with every event.
   *
   * @since 6.0.0
   */
  register(options: RegisterOptions): Promise<void>;
  /**
   * Reset the current user's ID and anonymous ID.
   *
   * @since 6.0.0
   */
  reset(): Promise<void>;
  /**
   * Send a screen event.
   *
   * @since 6.0.0
   */
  screen(options: ScreenOptions): Promise<void>;
  /**
   * Setup the PostHog SDK with the provided options.
   *
   * **Attention**: This method should be called before any other method.
   *
   * @since 6.0.0
   */
  setup(options: SetupOptions): Promise<void>;
  /**
   * Remove a super property.
   *
   * @since 6.0.0
   */
  unregister(options: UnregisterOptions): Promise<void>;
}

/**
 * @since 6.0.0
 */
export interface AliasOptions {
  /**
   * The new distinct ID to assign to the current user.
   *
   * @since 6.0.0
   */
  alias: string;
}

/**
 * @since 6.0.0
 */
export interface CaptureOptions {
  /**
   * The name of the event to capture.
   *
   * @since 6.0.0
   */
  event: string;
  /**
   * The properties to send with the event.
   *
   * @since 6.0.0
   */
  properties?: Record<string, any>;
}

/**
 * @since 6.0.0
 */
export interface IdentifyOptions {
  /**
   * The distinct ID of the user.
   *
   * @since 6.0.0
   */
  distinctId: string;
  /**
   * The person properties to set.
   *
   * @since 6.0.0
   */
  userProperties?: Record<string, any>;
}

/**
 * @since 6.0.0
 */
export interface RegisterOptions {
  /**
   * The name of the super property.
   *
   * @since 6.0.0
   */
  key: string;
  /**
   * The value of the super property.
   *
   * @since 6.0.0
   */
  value: any;
}

/**
 * @since 6.0.0
 */
export interface ScreenOptions {
  /**
   * The name of the screen.
   *
   * @since 6.0.0
   */
  screenTitle: string;
  /**
   * The properties to send with the screen event.
   *
   * @since 6.0.0
   */
  properties?: Record<string, any>;
}

/**
 * @since 6.0.0
 */
export interface SetupOptions {
  /**
   * The API key of your PostHog project.
   *
   * @since 6.0.0
   * @example 'phc_g8wMenebiIQ1pYd5v9Vy7oakn6MczVKIsNG5ZHCspdy'
   */
  apiKey: string;
  /**
   * The host of your PostHog instance.
   *
   * @since 6.0.0
   * @example 'https://eu.i.posthog.com'
   * @default 'https://us.i.posthog.com'
   */
  host?: string;
}

/**
 * @since 6.0.0
 */
export interface UnregisterOptions {
  /**
   * The name of the super property to remove.
   *
   * @since 6.0.0
   */
  key: string;
}
