import type { PluginListenerHandle } from '@capacitor/core';

export interface SuperwallPlugin {
  /**
   * Configure the Superwall SDK.
   *
   * This method must be called once before all other methods.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  configure(options: ConfigureOptions): Promise<void>;
  /**
   * Register a placement and present a paywall if the user doesn't have an active subscription.
   *
   * This is the primary method for feature gating and paywall presentation.
   * The feature closure will execute based on the gating mode:
   * - Non-gated: Executes immediately
   * - Gated: Executes after subscription or if already subscribed
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  register(options: RegisterOptions): Promise<RegisterResult>;
  /**
   * Check if a paywall would be presented for a placement without actually presenting it.
   *
   * Useful for determining whether to show a feature or paywall before the user interacts.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getPresentationResult(
    options: GetPresentationResultOptions,
  ): Promise<GetPresentationResultResult>;
  /**
   * Identify the current user with a unique ID.
   *
   * This links the user ID to their anonymous alias for analytics and paywall assignments.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  identify(options: IdentifyOptions): Promise<void>;
  /**
   * Reset the user identity.
   *
   * This rotates the anonymous user ID, clears local paywall assignments,
   * and requires the SDK to re-download configuration.
   * Should only be called on explicit logout.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  reset(): Promise<void>;
  /**
   * Get the current user ID.
   *
   * Returns the identified user ID if set, otherwise returns the anonymous ID.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getUserId(): Promise<GetUserIdResult>;
  /**
   * Check if the user is logged in (identified).
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getIsLoggedIn(): Promise<GetIsLoggedInResult>;
  /**
   * Set custom user attributes for personalization and audience filtering.
   *
   * Attributes can be used in audience filters on the Superwall dashboard.
   * Keys starting with `$` are reserved for Superwall use.
   * Arrays and nested structures are not supported.
   * Set values to null to remove attributes.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  setUserAttributes(options: SetUserAttributesOptions): Promise<void>;
  /**
   * Handle a deep link URL for paywall campaigns.
   *
   * This processes deep links associated with Superwall campaigns
   * configured on the dashboard.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  handleDeepLink(options: HandleDeepLinkOptions): Promise<void>;
  /**
   * Get the current subscription status.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getSubscriptionStatus(): Promise<GetSubscriptionStatusResult>;
  /**
   * Add a listener for Superwall analytics events.
   *
   * These events can be forwarded to your analytics platform.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'superwallEvent',
    listenerFunc: (event: SuperwallEventInfo) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Add a listener for subscription status changes.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'subscriptionStatusDidChange',
    listenerFunc: (event: SubscriptionStatusDidChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Add a listener for when a paywall is presented.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'paywallPresented',
    listenerFunc: (event: PaywallPresentedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Add a listener for when a paywall will dismiss.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'paywallWillDismiss',
    listenerFunc: (event: PaywallWillDismissEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Add a listener for when a paywall is dismissed.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'paywallDismissed',
    listenerFunc: (event: PaywallDismissedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Add a listener for custom paywall actions.
   *
   * Triggered when a user taps an element with the `data-pw-custom` attribute.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'customPaywallAction',
    listenerFunc: (event: CustomPaywallActionEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.0.1
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.0.1
 */
export interface ConfigureOptions {
  /**
   * The Superwall API key from your dashboard.
   *
   * @since 0.0.1
   */
  apiKey: string;
  /**
   * Optional configuration options for the SDK.
   *
   * @since 0.0.1
   */
  options?: SuperwallOptions;
}

/**
 * @since 0.0.1
 */
export interface SuperwallOptions {
  /**
   * Paywall presentation and behavior options.
   *
   * @since 0.0.1
   */
  paywalls?: PaywallOptions;
  /**
   * Logging configuration.
   *
   * @since 0.0.1
   */
  logging?: LoggingOptions;
  /**
   * Network environment for API requests.
   *
   * @since 0.0.1
   * @default NetworkEnvironment.Release
   */
  networkEnvironment?: NetworkEnvironment;
  /**
   * Override the locale identifier for localization.
   *
   * @since 0.0.1
   * @example 'en_US'
   */
  localeIdentifier?: string;
  /**
   * Observe external Google Play purchases.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   * @default true
   */
  shouldObservePurchases?: boolean;
  /**
   * Enable or disable external data collection for analytics.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   * @default true
   */
  isExternalDataCollectionEnabled?: boolean;
}

/**
 * @since 0.0.1
 */
export interface PaywallOptions {
  /**
   * Enable haptic feedback on paywall interactions.
   *
   * @since 0.0.1
   * @default true
   */
  isHapticFeedbackEnabled?: boolean;
  /**
   * Show an alert when purchase restoration fails.
   *
   * @since 0.0.1
   * @default true
   */
  shouldShowPurchaseFailureAlert?: boolean;
  /**
   * Preload paywalls during SDK initialization.
   *
   * @since 0.0.1
   * @default true
   */
  shouldPreload?: boolean;
  /**
   * Automatically dismiss paywall on purchase or restore.
   *
   * @since 0.0.1
   * @default true
   */
  automaticallyDismiss?: boolean;
}

/**
 * @since 0.0.1
 */
export interface LoggingOptions {
  /**
   * The log level for SDK logs.
   *
   * @since 0.0.1
   * @default LogLevel.Warn
   */
  level?: LogLevel;
  /**
   * The log scopes to enable.
   *
   * @since 0.0.1
   * @default [LogScope.All]
   */
  scopes?: LogScope[];
}

/**
 * @since 0.0.1
 */
export interface RegisterOptions {
  /**
   * The placement identifier configured in the Superwall dashboard.
   *
   * @since 0.0.1
   * @example 'campaign_trigger'
   */
  placement: string;
  /**
   * Optional parameters for audience filtering.
   *
   * Keys starting with `$` are reserved for Superwall use.
   *
   * @since 0.0.1
   * @example { 'user_level': 5, 'gems': 100 }
   */
  params?: Record<string, any>;
}

/**
 * @since 0.0.1
 */
export interface RegisterResult {
  /**
   * The result of the paywall presentation.
   *
   * @since 0.0.1
   */
  result: PaywallResult;
}

/**
 * @since 0.0.1
 */
export interface GetPresentationResultOptions {
  /**
   * The placement identifier to check.
   *
   * @since 0.0.1
   * @example 'campaign_trigger'
   */
  placement: string;
  /**
   * Optional parameters for audience filtering.
   *
   * @since 0.0.1
   */
  params?: Record<string, any>;
}

/**
 * @since 0.0.1
 */
export interface GetPresentationResultResult {
  /**
   * The type of presentation result.
   *
   * @since 0.0.1
   */
  type: PresentationResultType;
  /**
   * Experiment information if the result is a paywall or holdout.
   *
   * @since 0.0.1
   */
  experiment?: Experiment;
}

/**
 * @since 0.0.1
 */
export interface IdentifyOptions {
  /**
   * The unique user ID to identify the user.
   *
   * @since 0.0.1
   */
  userId: string;
  /**
   * Additional options for identification.
   *
   * @since 0.0.1
   */
  options?: IdentifyOptionsConfig;
}

/**
 * @since 0.0.1
 */
export interface IdentifyOptionsConfig {
  /**
   * Restore paywall assignments from the server.
   *
   * Set to true when switching accounts to restore assignments.
   *
   * @since 0.0.1
   * @default false
   */
  restorePaywallAssignments?: boolean;
}

/**
 * @since 0.0.1
 */
export interface GetUserIdResult {
  /**
   * The current user ID (identified or anonymous).
   *
   * @since 0.0.1
   */
  userId: string;
}

/**
 * @since 0.0.1
 */
export interface GetIsLoggedInResult {
  /**
   * Whether the user is logged in (identified).
   *
   * @since 0.0.1
   */
  isLoggedIn: boolean;
}

/**
 * @since 0.0.1
 */
export interface SetUserAttributesOptions {
  /**
   * User attributes as key-value pairs.
   *
   * Keys starting with `$` are reserved.
   * Set values to null to remove attributes.
   *
   * @since 0.0.1
   * @example { 'username': 'john_doe', 'age': 25, 'premium': true }
   */
  attributes: Record<string, any>;
}

/**
 * @since 0.0.1
 */
export interface HandleDeepLinkOptions {
  /**
   * The deep link URL to handle.
   *
   * @since 0.0.1
   * @example 'myapp://campaign/special-offer'
   */
  url: string;
}

/**
 * @since 0.0.1
 */
export interface GetSubscriptionStatusResult {
  /**
   * The current subscription status.
   *
   * @since 0.0.1
   */
  status: SubscriptionStatus;
}

/**
 * @since 0.0.1
 */
export interface SuperwallEventInfo {
  /**
   * The type of Superwall event.
   *
   * @since 0.0.1
   */
  type: SuperwallEventType;
  /**
   * Additional event data as key-value pairs.
   *
   * @since 0.0.1
   */
  data: Record<string, any>;
}

/**
 * @since 0.0.1
 */
export interface SubscriptionStatusDidChangeEvent {
  /**
   * The new subscription status.
   *
   * @since 0.0.1
   */
  status: SubscriptionStatus;
}

/**
 * @since 0.0.1
 */
export interface PaywallPresentedEvent {
  /**
   * Information about the presented paywall.
   *
   * @since 0.0.1
   */
  paywallInfo: PaywallInfo;
}

/**
 * @since 0.0.1
 */
export interface PaywallWillDismissEvent {
  /**
   * Information about the paywall being dismissed.
   *
   * @since 0.0.1
   */
  paywallInfo: PaywallInfo;
}

/**
 * @since 0.0.1
 */
export interface PaywallDismissedEvent {
  /**
   * Information about the dismissed paywall.
   *
   * @since 0.0.1
   */
  paywallInfo: PaywallInfo;
}

/**
 * @since 0.0.1
 */
export interface CustomPaywallActionEvent {
  /**
   * The name of the custom action.
   *
   * @since 0.0.1
   */
  name: string;
}

/**
 * @since 0.0.1
 */
export interface PaywallInfo {
  /**
   * The unique identifier for the paywall.
   *
   * @since 0.0.1
   */
  id: string;
  /**
   * The placement identifier.
   *
   * @since 0.0.1
   */
  placement: string;
  /**
   * Experiment information if the paywall is part of an A/B test.
   *
   * @since 0.0.1
   */
  experiment?: Experiment;
  /**
   * Additional paywall metadata.
   *
   * @since 0.0.1
   */
  data: Record<string, any>;
}

/**
 * @since 0.0.1
 */
export interface Experiment {
  /**
   * The unique identifier for the experiment.
   *
   * @since 0.0.1
   */
  id: string;
  /**
   * The variant assigned to the user.
   *
   * @since 0.0.1
   */
  variant: string;
}

/**
 * Network environment for Superwall API requests.
 *
 * @since 0.0.1
 */
export enum NetworkEnvironment {
  /**
   * Production environment.
   *
   * @since 0.0.1
   */
  Release = 'RELEASE',
  /**
   * Development environment for testing.
   *
   * @since 0.0.1
   */
  Developer = 'DEVELOPER',
}

/**
 * Log level for SDK logging.
 *
 * @since 0.0.1
 */
export enum LogLevel {
  /**
   * Debug level logging.
   *
   * @since 0.0.1
   */
  Debug = 'DEBUG',
  /**
   * Info level logging.
   *
   * @since 0.0.1
   */
  Info = 'INFO',
  /**
   * Warning level logging.
   *
   * @since 0.0.1
   */
  Warn = 'WARN',
  /**
   * Error level logging.
   *
   * @since 0.0.1
   */
  Error = 'ERROR',
}

/**
 * Log scope for SDK logging.
 *
 * @since 0.0.1
 */
export enum LogScope {
  /**
   * All log scopes.
   *
   * @since 0.0.1
   */
  All = 'ALL',
  /**
   * Analytics related logs.
   *
   * @since 0.0.1
   */
  Analytics = 'ANALYTICS',
  /**
   * Configuration related logs.
   *
   * @since 0.0.1
   */
  Config = 'CONFIG',
  /**
   * Event tracking logs.
   *
   * @since 0.0.1
   */
  Events = 'EVENTS',
  /**
   * Paywall related logs.
   *
   * @since 0.0.1
   */
  Paywalls = 'PAYWALLS',
  /**
   * Purchase related logs.
   *
   * @since 0.0.1
   */
  Purchases = 'PURCHASES',
}

/**
 * Subscription status.
 *
 * @since 0.0.1
 */
export enum SubscriptionStatus {
  /**
   * Subscription status is unknown.
   *
   * @since 0.0.1
   */
  Unknown = 'UNKNOWN',
  /**
   * User has an active subscription.
   *
   * @since 0.0.1
   */
  Active = 'ACTIVE',
  /**
   * User does not have an active subscription.
   *
   * @since 0.0.1
   */
  Inactive = 'INACTIVE',
}

/**
 * Paywall presentation result.
 *
 * @since 0.0.1
 */
export enum PaywallResult {
  /**
   * User completed a purchase.
   *
   * @since 0.0.1
   */
  Purchased = 'PURCHASED',
  /**
   * User cancelled the paywall.
   *
   * @since 0.0.1
   */
  Cancelled = 'CANCELLED',
  /**
   * User restored purchases.
   *
   * @since 0.0.1
   */
  Restored = 'RESTORED',
}

/**
 * Presentation result type when checking if a paywall would show.
 *
 * @since 0.0.1
 */
export enum PresentationResultType {
  /**
   * The placement was not found.
   *
   * @since 0.0.1
   */
  PlacementNotFound = 'PLACEMENT_NOT_FOUND',
  /**
   * No audience matched for this placement.
   *
   * @since 0.0.1
   */
  NoAudienceMatch = 'NO_AUDIENCE_MATCH',
  /**
   * A paywall would be presented.
   *
   * @since 0.0.1
   */
  Paywall = 'PAYWALL',
  /**
   * User is in a holdout group.
   *
   * @since 0.0.1
   */
  Holdout = 'HOLDOUT',
  /**
   * Paywall is not available.
   *
   * @since 0.0.1
   */
  PaywallNotAvailable = 'PAYWALL_NOT_AVAILABLE',
}

/**
 * Superwall analytics event types.
 *
 * @since 0.0.1
 */
export enum SuperwallEventType {
  /**
   * First time user is seen.
   *
   * @since 0.0.1
   */
  FirstSeen = 'FIRST_SEEN',
  /**
   * App was opened.
   *
   * @since 0.0.1
   */
  AppOpen = 'APP_OPEN',
  /**
   * App was launched.
   *
   * @since 0.0.1
   */
  AppLaunch = 'APP_LAUNCH',
  /**
   * App was closed.
   *
   * @since 0.0.1
   */
  AppClose = 'APP_CLOSE',
  /**
   * Session started.
   *
   * @since 0.0.1
   */
  SessionStart = 'SESSION_START',
  /**
   * Deep link was opened.
   *
   * @since 0.0.1
   */
  DeepLink = 'DEEP_LINK',
  /**
   * Trigger was fired.
   *
   * @since 0.0.1
   */
  TriggerFire = 'TRIGGER_FIRE',
  /**
   * Paywall was opened.
   *
   * @since 0.0.1
   */
  PaywallOpen = 'PAYWALL_OPEN',
  /**
   * Paywall was closed.
   *
   * @since 0.0.1
   */
  PaywallClose = 'PAYWALL_CLOSE',
  /**
   * Paywall was declined.
   *
   * @since 0.0.1
   */
  PaywallDecline = 'PAYWALL_DECLINE',
  /**
   * Transaction started.
   *
   * @since 0.0.1
   */
  TransactionStart = 'TRANSACTION_START',
  /**
   * Transaction completed.
   *
   * @since 0.0.1
   */
  TransactionComplete = 'TRANSACTION_COMPLETE',
  /**
   * Transaction failed.
   *
   * @since 0.0.1
   */
  TransactionFail = 'TRANSACTION_FAIL',
  /**
   * Transaction was abandoned.
   *
   * @since 0.0.1
   */
  TransactionAbandon = 'TRANSACTION_ABANDON',
  /**
   * Transaction restore.
   *
   * @since 0.0.1
   */
  TransactionRestore = 'TRANSACTION_RESTORE',
  /**
   * Transaction timeout.
   *
   * @since 0.0.1
   */
  TransactionTimeout = 'TRANSACTION_TIMEOUT',
  /**
   * Subscription started.
   *
   * @since 0.0.1
   */
  SubscriptionStart = 'SUBSCRIPTION_START',
  /**
   * Free trial started.
   *
   * @since 0.0.1
   */
  FreeTrialStart = 'FREE_TRIAL_START',
  /**
   * Subscription status changed.
   *
   * @since 0.0.1
   */
  SubscriptionStatusDidChange = 'SUBSCRIPTION_STATUS_DID_CHANGE',
}
