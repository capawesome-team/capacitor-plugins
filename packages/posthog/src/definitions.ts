/// <reference types="@capacitor/cli" />

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    Posthog?: {
      /**
       * The API key of your PostHog project.
       *
       * @since 7.1.0
       * @example 'phc_g8wMenebiIQ1pYd5v9Vy7oakn6MczVKIsNG5ZHCspdy'
       */
      apiKey?: string;
      /**
       * The host of your PostHog instance.
       *
       * @since 7.1.0
       * @default 'https://us.i.posthog.com'
       * @example 'https://eu.i.posthog.com'
       */
      host?: string;
    };
  }
}

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
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  flush(): Promise<void>;
  /**
   * Get the value of a feature flag.
   *
   * @since 7.0.0
   */
  getFeatureFlag(options: GetFeatureFlagOptions): Promise<GetFeatureFlagResult>;
  /**
   * Get the payload of a feature flag.
   *
   * @since 7.1.0
   */
  getFeatureFlagPayload(
    options: GetFeatureFlagPayloadOptions,
  ): Promise<GetFeatureFlagPayloadResult>;
  /**
   * Associate the events for that user with a group.
   *
   * @since 6.0.0
   */
  group(options: GroupOptions): Promise<void>;
  /**
   * Identify the current user.
   *
   * @since 6.0.0
   */
  identify(options: IdentifyOptions): Promise<void>;
  /**
   * Check if a feature flag is enabled.
   *
   * @since 7.0.0
   */
  isFeatureEnabled(
    options: IsFeatureEnabledOptions,
  ): Promise<IsFeatureEnabledResult>;
  /**
   * Register a new super property. This property will be sent with every event.
   *
   * @since 6.0.0
   */
  register(options: RegisterOptions): Promise<void>;
  /**
   * Reload the feature flags.
   *
   * @since 7.0.0
   */
  reloadFeatureFlags(): Promise<void>;
  /**
   * Reset the current user's ID and anonymous ID.
   *
   * @since 6.0.0
   */
  reset(): Promise<void>;
  /**
   * Send a screen event.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  screen(options: ScreenOptions): Promise<void>;
  /**
   * Setup the PostHog SDK with the provided options.
   *
   * **Attention**: This method should be called before any other method.
   * Alternatively, on Android and iOS, you can configure this plugin in
   * your Capacitor Configuration file. In this case, you must not call this method.
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
 * @since 7.0.0
 */
export interface GetFeatureFlagOptions {
  /**
   * The key of the feature flag.
   *
   * @since 7.0.0
   */
  key: string;
}

/**
 * @since 7.1.0
 */
export interface GetFeatureFlagPayloadOptions {
  /**
   * The key of the feature flag.
   *
   * @since 7.1.0
   */
  key: string;
}

export interface GetFeatureFlagResult {
  /**
   * The value of the feature flag.
   *
   * If the feature flag does not exist, the value will be `null`.
   *
   * @since 7.0.0
   */
  value: string | boolean | null;
}

export interface GetFeatureFlagPayloadResult {
  /**
   * The value of the feature flag payload.
   *
   * @since 7.1.0
   */
  value: JsonType;
}

/**
 * @since 6.0.0
 */
export interface GroupOptions {
  /**
   * The group type.
   *
   * @since 6.0.0
   * @example 'company'
   */
  type: string;
  /**
   * The group key.
   *
   * @since 6.0.0
   * @example 'company_id_in_your_db'
   */
  key: string;
  /**
   * The properties to send with the group event.
   *
   * @since 6.0.0
   */
  groupProperties?: Record<string, any>;
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
 * @since 7.0.0
 */
export interface IsFeatureEnabledOptions {
  /**
   * The key of the feature flag.
   *
   * @since 7.0.0
   */
  key: string;
}

/**
 * @since 7.0.0
 */
export interface IsFeatureEnabledResult {
  /**
   * Whether the feature flag is enabled.
   *
   * If the feature flag does not exist, the value will be `false`.
   *
   * @since 7.0.0
   */
  enabled: boolean;
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
   * @default 'https://us.i.posthog.com'
   * @example 'https://eu.i.posthog.com'
   */
  host?: string;
  /**
   * Additional configuration options to pass to PostHog initialization.
   * These will be merged with the default configuration.
   *
   * @since 8.0.0
   * @example { 'autocapture': false, 'enable_recording_console_log': true }
   */
  config?: Record<string, any>;
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

/**
 * @since 7.1.0
 */
export type JsonType =
  | string
  | number
  | boolean
  | null
  | {
      [key: string]: JsonType;
    }
  | JsonType[];
