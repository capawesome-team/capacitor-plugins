import type { PluginListenerHandle } from '@capacitor/core';

export interface SingularPlugin {
  /**
   * Remove all global properties.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  clearGlobalProperties(): Promise<void>;
  /**
   * Create a short link that attributes installs to a referring user.
   *
   * The short link expires after 30 days.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  createReferrerShortLink(
    options: CreateReferrerShortLinkOptions,
  ): Promise<CreateReferrerShortLinkResult>;
  /**
   * Get all global properties.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getGlobalProperties(): Promise<GetGlobalPropertiesResult>;
  /**
   * Get whether data sharing is limited.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getLimitDataSharing(): Promise<GetLimitDataSharingResult>;
  /**
   * Initialize the Singular SDK and start the first session.
   *
   * This method must be called before any other method.
   * Add your listeners before calling this method so that no
   * deferred deep link or attribution event is missed.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  initialize(options: InitializeOptions): Promise<void>;
  /**
   * Get whether all tracking has been stopped via `stopAllTracking()`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  isAllTrackingStopped(): Promise<IsAllTrackingStoppedResult>;
  /**
   * Resume all tracking after it was stopped via `stopAllTracking()`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  resumeAllTracking(): Promise<void>;
  /**
   * Set the custom user ID that is attached to all sessions and events.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  setCustomUserId(options: SetCustomUserIdOptions): Promise<void>;
  /**
   * Set the push notification device token used for uninstall tracking.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  setDeviceToken(options: SetDeviceTokenOptions): Promise<void>;
  /**
   * Set a global property that is attached to all events.
   *
   * At most 5 global properties can be set.
   * The call is rejected if the property could not be set.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  setGlobalProperty(options: SetGlobalPropertyOptions): Promise<void>;
  /**
   * Set whether the SDK is allowed to collect advertising identifiers
   * (e.g. the Google Advertising ID or the IDFA).
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  setLimitAdvertisingIdentifiers(
    options: SetLimitAdvertisingIdentifiersOptions,
  ): Promise<void>;
  /**
   * Set whether data sharing with third parties is limited
   * (e.g. after the user opted out under CCPA).
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  setLimitDataSharing(options: SetLimitDataSharingOptions): Promise<void>;
  /**
   * Get the current SKAdNetwork conversion value.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  skanGetConversionValue(): Promise<SkanGetConversionValueResult>;
  /**
   * Register the app for SKAdNetwork attribution.
   *
   * Only required if `iosManualSkanConversionManagement` is enabled.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  skanRegisterAppForAdNetworkAttribution(): Promise<void>;
  /**
   * Update the SKAdNetwork conversion value.
   *
   * Only required if `iosManualSkanConversionManagement` is enabled.
   * The call is rejected if the conversion value could not be updated.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  skanUpdateConversionValue(
    options: SkanUpdateConversionValueOptions,
  ): Promise<void>;
  /**
   * Stop all tracking.
   *
   * This setting persists across app restarts until `resumeAllTracking()` is called.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  stopAllTracking(): Promise<void>;
  /**
   * Track ad revenue.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  trackAdRevenue(options: TrackAdRevenueOptions): Promise<void>;
  /**
   * Track an event.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  trackEvent(options: TrackEventOptions): Promise<void>;
  /**
   * Track a revenue event.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  trackRevenue(options: TrackRevenueOptions): Promise<void>;
  /**
   * Notify the SDK that the user has opted in to tracking (e.g. under GDPR).
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  trackingOptIn(): Promise<void>;
  /**
   * Notify the SDK that the user is under 13 years old
   * so that the SDK does not collect advertising identifiers.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  trackingUnder13(): Promise<void>;
  /**
   * Remove the custom user ID.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  unsetCustomUserId(): Promise<void>;
  /**
   * Remove a global property.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  unsetGlobalProperty(options: UnsetGlobalPropertyOptions): Promise<void>;
  /**
   * Add a listener for when the device attribution information is received.
   *
   * The event is emitted once after the first session, and only if
   * device attribution is enabled for your Singular account.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'deviceAttributionInfoReceived',
    listenerFunc: (event: DeviceAttributionInfoReceivedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Add a listener for when the Singular Device ID (SDID) is received.
   *
   * The event is emitted after the first session and on every
   * subsequent launch with the stored SDID.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'sdidReceived',
    listenerFunc: (event: SdidReceivedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Add a listener for when a custom Singular Device ID (SDID) has been stored.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'sdidSet',
    listenerFunc: (event: SdidSetEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Add a listener for when a Singular Link is resolved.
   *
   * This includes deferred deep links after an install.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'singularLinkResolved',
    listenerFunc: (event: SingularLinkResolvedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Add a listener for when the SDK updates the SKAdNetwork conversion value.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'skanConversionValueUpdated',
    listenerFunc: (event: SkanConversionValueUpdatedEvent) => void,
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
export interface CreateReferrerShortLinkOptions {
  /**
   * The Singular Link to shorten.
   *
   * @since 0.1.0
   * @example 'https://myapp.sng.link/A1b2c/d3e4'
   */
  baseLink: string;
  /**
   * Additional parameters that are passed through to the installed app.
   *
   * @since 0.1.0
   */
  passthroughParameters?: Record<string, string>;
  /**
   * The unique identifier of the referring user.
   *
   * @since 0.1.0
   */
  referrerId: string;
  /**
   * The name of the referring user.
   *
   * @since 0.1.0
   */
  referrerName: string;
}

/**
 * @since 0.1.0
 */
export interface CreateReferrerShortLinkResult {
  /**
   * The generated short link.
   *
   * @since 0.1.0
   */
  link: string;
}

/**
 * @since 0.1.0
 */
export interface DeviceAttributionInfoReceivedEvent {
  /**
   * The identifier of the campaign.
   *
   * @since 0.1.0
   */
  campaignId?: string;
  /**
   * The name of the campaign.
   *
   * @since 0.1.0
   */
  campaignName?: string;
  /**
   * The timestamp of the attributed click in milliseconds since the Unix epoch.
   *
   * @since 0.1.0
   */
  clickTimestamp?: number;
  /**
   * The identifier of the creative.
   *
   * @since 0.1.0
   */
  creativeId?: string;
  /**
   * The name of the creative.
   *
   * @since 0.1.0
   */
  creativeName?: string;
  /**
   * The type of the attribution match.
   *
   * @since 0.1.0
   */
  matchType?: string;
  /**
   * The name of the attributed network.
   *
   * @since 0.1.0
   * @example 'Organic'
   */
  network: string;
  /**
   * The passthrough parameters of the attributed link.
   *
   * @since 0.1.0
   */
  passthrough?: string;
  /**
   * The identifier of the sub campaign.
   *
   * @since 0.1.0
   */
  subcampaignId?: string;
  /**
   * The name of the sub campaign.
   *
   * @since 0.1.0
   */
  subcampaignName?: string;
}

/**
 * @since 0.1.0
 */
export interface GetGlobalPropertiesResult {
  /**
   * The global properties.
   *
   * @since 0.1.0
   */
  properties: Record<string, string>;
}

/**
 * @since 0.1.0
 */
export interface GetLimitDataSharingResult {
  /**
   * Whether data sharing is limited.
   *
   * @since 0.1.0
   */
  limit: boolean;
}

/**
 * @since 0.1.0
 */
export interface InitializeOptions {
  /**
   * The Facebook App ID used for Meta Install Referrer attribution.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  androidFacebookAppId?: string;
  /**
   * The SDK key of your Singular account.
   *
   * @since 0.1.0
   */
  apiKey: string;
  /**
   * The branded domains that should be resolved as Singular Links.
   *
   * @since 0.1.0
   * @example ['links.myapp.com']
   */
  brandedDomains?: string[];
  /**
   * A custom Singular Device ID (SDID) that is used instead of the one
   * generated by Singular.
   *
   * @since 0.1.0
   */
  customSdid?: string;
  /**
   * The custom user ID that is attached to all sessions and events.
   *
   * @since 0.1.0
   */
  customUserId?: string;
  /**
   * The email service provider domains that should be resolved as Singular Links.
   *
   * @since 0.1.0
   * @example ['click.myapp.com']
   */
  espDomains?: string[];
  /**
   * Global properties that are attached to all events.
   *
   * Existing global properties with the same key are overridden.
   * At most 5 global properties can be set.
   *
   * @since 0.1.0
   */
  globalProperties?: Record<string, string>;
  /**
   * Whether the SKAdNetwork conversion value is managed by your app
   * instead of by the SDK.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   * @default false
   */
  iosManualSkanConversionManagement?: boolean;
  /**
   * Whether SKAdNetwork support is enabled.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   * @default true
   */
  iosSkAdNetworkEnabled?: boolean;
  /**
   * The number of seconds the SDK waits for the App Tracking Transparency
   * authorization before sending the first session.
   *
   * Set this to a value greater than `0` if you request the tracking
   * authorization right after the app launch.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   * @default 0
   */
  iosWaitForTrackingAuthorizationTimeout?: number;
  /**
   * Whether the SDK is not allowed to collect advertising identifiers
   * (e.g. the Google Advertising ID or the IDFA).
   *
   * @since 0.1.0
   * @default false
   */
  limitAdvertisingIdentifiers?: boolean;
  /**
   * Whether data sharing with third parties is limited.
   *
   * @since 0.1.0
   * @default false
   */
  limitDataSharing?: boolean;
  /**
   * Whether debug logging is enabled.
   *
   * @since 0.1.0
   * @default false
   */
  loggingEnabled?: boolean;
  /**
   * The SDK secret of your Singular account.
   *
   * @since 0.1.0
   */
  secret: string;
  /**
   * The number of seconds the app can stay in the background
   * before a new session is started.
   *
   * @since 0.1.0
   * @default 60
   */
  sessionTimeout?: number;
  /**
   * The number of seconds the SDK waits for a short link to be resolved.
   *
   * @since 0.1.0
   * @default 10
   */
  shortLinkResolveTimeout?: number;
}

/**
 * @since 0.1.0
 */
export interface IsAllTrackingStoppedResult {
  /**
   * Whether all tracking has been stopped.
   *
   * @since 0.1.0
   */
  stopped: boolean;
}

/**
 * @since 0.1.0
 */
export interface SdidReceivedEvent {
  /**
   * The Singular Device ID.
   *
   * @since 0.1.0
   */
  sdid: string;
}

/**
 * @since 0.1.0
 */
export interface SdidSetEvent {
  /**
   * The Singular Device ID.
   *
   * @since 0.1.0
   */
  sdid: string;
}

/**
 * @since 0.1.0
 */
export interface SetCustomUserIdOptions {
  /**
   * The custom user ID.
   *
   * @since 0.1.0
   */
  customUserId: string;
}

/**
 * @since 0.1.0
 */
export interface SetDeviceTokenOptions {
  /**
   * The push notification device token.
   *
   * On Android, this is the FCM registration token.
   * On iOS, this is the hex-encoded APNs device token.
   *
   * @since 0.1.0
   */
  token: string;
}

/**
 * @since 0.1.0
 */
export interface SetGlobalPropertyOptions {
  /**
   * The key of the global property.
   *
   * @since 0.1.0
   */
  key: string;
  /**
   * Whether an existing global property with the same key is overridden.
   *
   * @since 0.1.0
   * @default true
   */
  overrideExisting?: boolean;
  /**
   * The value of the global property.
   *
   * @since 0.1.0
   */
  value: string;
}

/**
 * @since 0.1.0
 */
export interface SetLimitAdvertisingIdentifiersOptions {
  /**
   * Whether the SDK is not allowed to collect advertising identifiers.
   *
   * @since 0.1.0
   */
  limit: boolean;
}

/**
 * @since 0.1.0
 */
export interface SetLimitDataSharingOptions {
  /**
   * Whether data sharing with third parties is limited.
   *
   * @since 0.1.0
   */
  limit: boolean;
}

/**
 * @since 0.1.0
 */
export interface SingularLinkResolvedEvent {
  /**
   * The deep link value of the Singular Link.
   *
   * @since 0.1.0
   */
  deepLink: string | null;
  /**
   * Whether the link is a deferred deep link (i.e. resolved after an install).
   *
   * @since 0.1.0
   */
  isDeferred: boolean;
  /**
   * The passthrough value of the Singular Link.
   *
   * @since 0.1.0
   */
  passthrough: string | null;
  /**
   * The query parameters of the Singular Link.
   *
   * @since 0.1.0
   */
  urlParameters: Record<string, string>;
}

/**
 * @since 0.1.0
 */
export interface SkanConversionValueUpdatedEvent {
  /**
   * The coarse conversion value.
   *
   * Only available on iOS 16.1+.
   *
   * @since 0.1.0
   */
  coarseValue: SkanCoarseConversionValue | null;
  /**
   * Whether the conversion window is locked.
   *
   * Only available on iOS 16.1+.
   *
   * @since 0.1.0
   */
  lockWindow: boolean;
  /**
   * The fine-grained conversion value (`0` - `63`).
   *
   * @since 0.1.0
   */
  value: number | null;
}

/**
 * @since 0.1.0
 */
export interface SkanGetConversionValueResult {
  /**
   * The fine-grained conversion value (`0` - `63`)
   * or `null` if no value has been set yet.
   *
   * @since 0.1.0
   */
  value: number | null;
}

/**
 * @since 0.1.0
 */
export interface SkanUpdateConversionValueOptions {
  /**
   * The coarse conversion value.
   *
   * Only available on iOS 16.1+.
   *
   * @since 0.1.0
   */
  coarseValue?: SkanCoarseConversionValue;
  /**
   * Whether the conversion window should be locked.
   *
   * Only available on iOS 16.1+.
   *
   * @since 0.1.0
   * @default false
   */
  lockWindow?: boolean;
  /**
   * The fine-grained conversion value (`0` - `63`).
   *
   * @since 0.1.0
   */
  value: number;
}

/**
 * @since 0.1.0
 */
export interface TrackAdRevenueOptions {
  /**
   * The identifier of the ad group.
   *
   * @since 0.1.0
   */
  adGroupId?: string;
  /**
   * The name of the ad group.
   *
   * @since 0.1.0
   */
  adGroupName?: string;
  /**
   * The priority of the ad group.
   *
   * @since 0.1.0
   */
  adGroupPriority?: string;
  /**
   * The type of the ad group.
   *
   * @since 0.1.0
   */
  adGroupType?: string;
  /**
   * The name of the ad placement.
   *
   * @since 0.1.0
   */
  adPlacementName?: string;
  /**
   * The ad mediation platform that reported the revenue.
   *
   * @since 0.1.0
   * @example 'AdMob'
   */
  adPlatform: string;
  /**
   * The type of the ad.
   *
   * @since 0.1.0
   * @example 'Rewarded'
   */
  adType?: string;
  /**
   * The identifier of the ad unit.
   *
   * @since 0.1.0
   */
  adUnitId?: string;
  /**
   * The name of the ad unit.
   *
   * @since 0.1.0
   */
  adUnitName?: string;
  /**
   * The currency of the revenue as ISO 4217 code.
   *
   * @since 0.1.0
   * @example 'USD'
   */
  currency: string;
  /**
   * The identifier of the ad impression.
   *
   * @since 0.1.0
   */
  impressionId?: string;
  /**
   * The name of the ad network that served the ad.
   *
   * @since 0.1.0
   */
  networkName?: string;
  /**
   * The identifier of the placement.
   *
   * @since 0.1.0
   */
  placementId?: string;
  /**
   * The precision of the reported revenue.
   *
   * @since 0.1.0
   * @example 'estimated'
   */
  precision?: string;
  /**
   * The revenue amount.
   *
   * @since 0.1.0
   * @example 0.05
   */
  revenue: number;
}

/**
 * @since 0.1.0
 */
export interface TrackEventOptions {
  /**
   * The attributes of the event.
   *
   * Attribute keys and values are limited to 500 characters.
   *
   * @since 0.1.0
   * @example { level: 3, character: 'warrior' }
   */
  attributes?: Record<string, string | number | boolean>;
  /**
   * The name of the event.
   *
   * Can be a standard event name (e.g. `sng_login`, `sng_tutorial_complete`)
   * or a custom event name. Limited to 32 characters.
   *
   * @since 0.1.0
   * @example 'sng_login'
   */
  name: string;
}

/**
 * @since 0.1.0
 */
export interface TrackRevenueOptions {
  /**
   * The revenue amount.
   *
   * @since 0.1.0
   * @example 9.99
   */
  amount: number;
  /**
   * The attributes of the event.
   *
   * @since 0.1.0
   */
  attributes?: Record<string, string | number | boolean>;
  /**
   * The currency of the revenue as ISO 4217 code.
   *
   * @since 0.1.0
   * @example 'USD'
   */
  currency: string;
  /**
   * The name of the event.
   *
   * If provided, a custom revenue event with this name is tracked
   * instead of the default revenue event.
   *
   * @since 0.1.0
   * @example 'subscription_purchase'
   */
  eventName?: string;
}

/**
 * @since 0.1.0
 */
export interface UnsetGlobalPropertyOptions {
  /**
   * The key of the global property.
   *
   * @since 0.1.0
   */
  key: string;
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

/**
 * @since 0.1.0
 */
export enum SkanCoarseConversionValue {
  /**
   * @since 0.1.0
   */
  High = 'HIGH',
  /**
   * @since 0.1.0
   */
  Low = 'LOW',
  /**
   * @since 0.1.0
   */
  Medium = 'MEDIUM',
}
