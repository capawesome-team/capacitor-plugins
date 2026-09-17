export interface TiktokAppEventsPlugin {
  /**
   * Flush all queued events to TikTok immediately.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  flush(): Promise<void>;
  /**
   * Identify the current user to improve the attribution of events.
   *
   * Personal data is hashed (SHA-256) by the TikTok SDK on the device
   * before it is sent to TikTok.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  identify(options: IdentifyOptions): Promise<void>;
  /**
   * Initialize the TikTok App Events SDK.
   *
   * This method must be called before any other method.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  initialize(options: InitializeOptions): Promise<void>;
  /**
   * Log out the current user and clear the identification data.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  logout(): Promise<void>;
  /**
   * Track an event.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  trackEvent(options: TrackEventOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface IdentifyOptions {
  /**
   * The email address of the user.
   *
   * @since 0.1.0
   */
  email?: string;
  /**
   * The unique identifier of the user in your system.
   *
   * @since 0.1.0
   */
  externalId: string;
  /**
   * The user name of the user.
   *
   * @since 0.1.0
   */
  externalUserName?: string;
  /**
   * The phone number of the user.
   *
   * @since 0.1.0
   */
  phoneNumber?: string;
}

/**
 * @since 0.1.0
 */
export interface InitializeOptions {
  /**
   * The access token of your app.
   *
   * You can find it in the TikTok Events Manager.
   *
   * @since 0.1.0
   */
  accessToken: string;
  /**
   * Whether the SDK automatically tracks in-app purchases made through
   * Google Play Billing (Android) or StoreKit (iOS).
   *
   * @since 0.1.0
   * @default true
   */
  automaticPurchaseTracking?: boolean;
  /**
   * Whether the SDK automatically tracks app installs, app launches and
   * second-day retention.
   *
   * @since 0.1.0
   * @default true
   */
  automaticTracking?: boolean;
  /**
   * Whether debug mode is enabled.
   *
   * In debug mode, events are sent to the test events pipeline of the
   * TikTok Events Manager and are not used for reporting. Make sure to
   * disable debug mode before releasing your app.
   *
   * @since 0.1.0
   * @default false
   */
  debugMode?: boolean;
  /**
   * The App Store ID of your app.
   *
   * Required on iOS. On Android, the package name of your app is used instead.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   * @example '1234567890'
   */
  iosAppId?: string;
  /**
   * Whether the SDK updates the SKAdNetwork conversion value.
   *
   * Disable this if another SDK (e.g. a mobile measurement partner) already
   * updates the conversion value to avoid conflicts.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   * @default true
   */
  iosSkAdNetworkSupport?: boolean;
  /**
   * Whether Limited Data Use (LDU) mode is enabled.
   *
   * @since 0.1.0
   * @default false
   */
  limitedDataUse?: boolean;
  /**
   * The TikTok App ID of your app.
   *
   * You can find it in the TikTok Events Manager. Multiple IDs can be
   * provided as a comma-separated list.
   *
   * @since 0.1.0
   * @example '7123456789012345678'
   */
  tiktokAppId: string;
}

/**
 * @since 0.1.0
 */
export interface TrackEventOptions {
  /**
   * A unique identifier of the event.
   *
   * Used by TikTok to deduplicate events that are also reported
   * through the Events API.
   *
   * @since 0.1.0
   */
  id?: string;
  /**
   * The name of the event.
   *
   * Can be a standard event name (e.g. `Purchase`, `AddToCart`,
   * `Registration`) or a custom event name.
   *
   * @since 0.1.0
   * @example 'Purchase'
   */
  name: string;
  /**
   * The properties of the event.
   *
   * @since 0.1.0
   * @example { currency: 'USD', value: 9.99 }
   */
  properties?: Record<string, unknown>;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The initialization of the SDK failed.
   *
   * @since 0.1.0
   */
  InitializationFailed = 'INITIALIZATION_FAILED',
  /**
   * The plugin has not been initialized yet.
   *
   * Call `initialize(...)` before calling any other method.
   *
   * @since 0.1.0
   */
  NotInitialized = 'NOT_INITIALIZED',
}
