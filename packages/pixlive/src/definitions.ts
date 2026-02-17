import type { PluginListenerHandle } from '@capacitor/core';

/**
 * @since 8.0.0
 */
export interface PixlivePlugin {
  /**
   * Sync content from PixLive Maker filtered by tags.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  synchronize(options: SynchronizeOptions): Promise<void>;
  /**
   * Sync content filtered by tags, tour IDs, and context IDs.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  synchronizeWithToursAndContexts(
    options: SynchronizeWithToursAndContextsOptions,
  ): Promise<void>;
  /**
   * Update tag-to-context mappings for language filtering.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  updateTagMapping(options: UpdateTagMappingOptions): Promise<void>;
  /**
   * Enable only contexts matching specific tags.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  enableContextsWithTags(options: EnableContextsWithTagsOptions): Promise<void>;
  /**
   * Get all synchronized contexts.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  getContexts(): Promise<GetContextsResult>;
  /**
   * Get a single context by ID.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  getContext(options: GetContextOptions): Promise<GetContextResult>;
  /**
   * Programmatically trigger/activate a context.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  activateContext(options: ActivateContextOptions): Promise<void>;
  /**
   * Stop the currently playing/active context.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  stopContext(): Promise<void>;
  /**
   * Get GPS points near a given location, sorted by distance.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  getNearbyGPSPoints(
    options: GetNearbyGPSPointsOptions,
  ): Promise<GetNearbyGPSPointsResult>;
  /**
   * Get all GPS points within a geographic bounding box.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  getGPSPointsInBoundingBox(
    options: GetGPSPointsInBoundingBoxOptions,
  ): Promise<GetGPSPointsInBoundingBoxResult>;
  /**
   * Get contexts associated with nearby detected beacons.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  getNearbyBeacons(): Promise<GetNearbyBeaconsResult>;
  /**
   * Start background GPS proximity detection.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  startNearbyGPSDetection(): Promise<void>;
  /**
   * Stop background GPS proximity detection.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  stopNearbyGPSDetection(): Promise<void>;
  /**
   * Enable GPS-triggered local notifications.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  startGPSNotifications(): Promise<void>;
  /**
   * Disable GPS-triggered local notifications.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  stopGPSNotifications(): Promise<void>;
  /**
   * Enable or disable notification support.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  setNotificationsSupport(
    options: SetNotificationsSupportOptions,
  ): Promise<void>;
  /**
   * Set the language for SDK UI elements.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  setInterfaceLanguage(options: SetInterfaceLanguageOptions): Promise<void>;
  /**
   * Create the native AR camera view at specified screen coordinates.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  createARView(options: CreateARViewOptions): Promise<void>;
  /**
   * Destroy the AR camera view.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  destroyARView(): Promise<void>;
  /**
   * Resize the AR view.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  resizeARView(options: ResizeARViewOptions): Promise<void>;
  /**
   * Enable or disable touch event interception on the AR view.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  setARViewTouchEnabled(options: SetARViewTouchEnabledOptions): Promise<void>;
  /**
   * Define a rectangular region where touches pass through the AR view to the web layer.
   *
   * Only available on Android and iOS.
   *
   * @since 8.0.0
   */
  setARViewTouchHole(options: SetARViewTouchHoleOptions): Promise<void>;
  /**
   * Called when a QR code or barcode is scanned by the AR camera.
   *
   * @since 8.0.0
   */
  addListener(
    eventName: 'codeRecognize',
    listenerFunc: (event: CodeRecognizeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when an AR context is detected/entered.
   *
   * @since 8.0.0
   */
  addListener(
    eventName: 'enterContext',
    listenerFunc: (event: EnterContextEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when an AR context is lost/exited.
   *
   * @since 8.0.0
   */
  addListener(
    eventName: 'exitContext',
    listenerFunc: (event: ExitContextEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when AR content/annotations become visible on screen.
   *
   * @since 8.0.0
   */
  addListener(
    eventName: 'presentAnnotations',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when AR content/annotations are hidden.
   *
   * @since 8.0.0
   */
  addListener(
    eventName: 'hideAnnotations',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when AR content dispatches a custom event.
   *
   * @since 8.0.0
   */
  addListener(
    eventName: 'eventFromContent',
    listenerFunc: (event: EventFromContentEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called during synchronization with progress updates.
   *
   * @since 8.0.0
   */
  addListener(
    eventName: 'syncProgress',
    listenerFunc: (event: SyncProgressEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the SDK requires synchronization with specific tags.
   *
   * @since 8.0.0
   */
  addListener(
    eventName: 'requireSync',
    listenerFunc: (event: RequireSyncEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 8.0.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 8.0.0
 */
export interface SynchronizeOptions {
  /**
   * The tags to filter content by.
   *
   * @since 8.0.0
   */
  tags: string[][];
}

/**
 * @since 8.0.0
 */
export interface SynchronizeWithToursAndContextsOptions {
  /**
   * The tags to filter content by.
   *
   * @since 8.0.0
   */
  tags: string[][];
  /**
   * The tour IDs to sync.
   *
   * @since 8.0.0
   */
  tourIds: number[];
  /**
   * The context IDs to sync.
   *
   * @since 8.0.0
   */
  contextIds: string[];
}

/**
 * @since 8.0.0
 */
export interface UpdateTagMappingOptions {
  /**
   * The tags to map.
   *
   * @since 8.0.0
   */
  tags: string[];
}

/**
 * @since 8.0.0
 */
export interface EnableContextsWithTagsOptions {
  /**
   * The tags to enable contexts for.
   *
   * @since 8.0.0
   */
  tags: string[];
}

/**
 * @since 8.0.0
 */
export interface GetContextOptions {
  /**
   * The ID of the context to retrieve.
   *
   * @since 8.0.0
   * @example "12345"
   */
  contextId: string;
}

/**
 * @since 8.0.0
 */
export interface ActivateContextOptions {
  /**
   * The ID of the context to activate.
   *
   * @since 8.0.0
   * @example "12345"
   */
  contextId: string;
}

/**
 * @since 8.0.0
 */
export interface GetNearbyGPSPointsOptions {
  /**
   * The latitude of the reference location.
   *
   * @since 8.0.0
   */
  latitude: number;
  /**
   * The longitude of the reference location.
   *
   * @since 8.0.0
   */
  longitude: number;
}

/**
 * @since 8.0.0
 */
export interface GetGPSPointsInBoundingBoxOptions {
  /**
   * The minimum latitude of the bounding box.
   *
   * @since 8.0.0
   */
  minLatitude: number;
  /**
   * The minimum longitude of the bounding box.
   *
   * @since 8.0.0
   */
  minLongitude: number;
  /**
   * The maximum latitude of the bounding box.
   *
   * @since 8.0.0
   */
  maxLatitude: number;
  /**
   * The maximum longitude of the bounding box.
   *
   * @since 8.0.0
   */
  maxLongitude: number;
}

/**
 * @since 8.0.0
 */
export interface SetNotificationsSupportOptions {
  /**
   * Whether notifications support should be enabled.
   *
   * @since 8.0.0
   */
  enabled: boolean;
}

/**
 * @since 8.0.0
 */
export interface SetInterfaceLanguageOptions {
  /**
   * The language code to set.
   *
   * @since 8.0.0
   * @example "en"
   */
  language: string;
}

/**
 * @since 8.0.0
 */
export interface CreateARViewOptions {
  /**
   * The x position of the AR view.
   *
   * @since 8.0.0
   */
  x: number;
  /**
   * The y position of the AR view.
   *
   * @since 8.0.0
   */
  y: number;
  /**
   * The width of the AR view.
   *
   * @since 8.0.0
   */
  width: number;
  /**
   * The height of the AR view.
   *
   * @since 8.0.0
   */
  height: number;
}

/**
 * @since 8.0.0
 */
export interface ResizeARViewOptions {
  /**
   * The new x position of the AR view.
   *
   * @since 8.0.0
   */
  x: number;
  /**
   * The new y position of the AR view.
   *
   * @since 8.0.0
   */
  y: number;
  /**
   * The new width of the AR view.
   *
   * @since 8.0.0
   */
  width: number;
  /**
   * The new height of the AR view.
   *
   * @since 8.0.0
   */
  height: number;
}

/**
 * @since 8.0.0
 */
export interface SetARViewTouchEnabledOptions {
  /**
   * Whether touch events should be intercepted by the AR view.
   *
   * @since 8.0.0
   */
  enabled: boolean;
}

/**
 * @since 8.0.0
 */
export interface SetARViewTouchHoleOptions {
  /**
   * The top coordinate of the touch hole region.
   *
   * @since 8.0.0
   */
  top: number;
  /**
   * The bottom coordinate of the touch hole region.
   *
   * @since 8.0.0
   */
  bottom: number;
  /**
   * The left coordinate of the touch hole region.
   *
   * @since 8.0.0
   */
  left: number;
  /**
   * The right coordinate of the touch hole region.
   *
   * @since 8.0.0
   */
  right: number;
}

/**
 * @since 8.0.0
 */
export interface GetContextsResult {
  /**
   * The list of synchronized contexts.
   *
   * @since 8.0.0
   */
  contexts: Context[];
}

/**
 * @since 8.0.0
 */
export interface GetContextResult {
  /**
   * The context.
   *
   * @since 8.0.0
   */
  context: Context;
}

/**
 * @since 8.0.0
 */
export interface GetNearbyBeaconsResult {
  /**
   * The contexts associated with nearby beacons.
   *
   * @since 8.0.0
   */
  contexts: Context[];
}

/**
 * @since 8.0.0
 */
export interface GetNearbyGPSPointsResult {
  /**
   * The nearby GPS points sorted by distance.
   *
   * @since 8.0.0
   */
  points: GPSPoint[];
}

/**
 * @since 8.0.0
 */
export interface GetGPSPointsInBoundingBoxResult {
  /**
   * The GPS points within the bounding box.
   *
   * @since 8.0.0
   */
  points: GPSPoint[];
}

/**
 * @since 8.0.0
 */
export interface Context {
  /**
   * The unique identifier of the context.
   *
   * @since 8.0.0
   */
  contextId: string;
  /**
   * The name of the context.
   *
   * @since 8.0.0
   */
  name: string;
  /**
   * The description of the context.
   *
   * @since 8.0.0
   */
  description: string | null;
  /**
   * The last update timestamp.
   *
   * @since 8.0.0
   */
  lastUpdate: string;
  /**
   * The URL of the thumbnail image.
   *
   * @since 8.0.0
   */
  imageThumbnailURL: string | null;
  /**
   * The URL of the high-resolution image.
   *
   * @since 8.0.0
   */
  imageHiResURL: string | null;
  /**
   * The notification title.
   *
   * @since 8.0.0
   */
  notificationTitle: string | null;
  /**
   * The notification message.
   *
   * @since 8.0.0
   */
  notificationMessage: string | null;
  /**
   * The tags associated with the context.
   *
   * @since 8.0.0
   */
  tags: string[];
}

/**
 * @since 8.0.0
 */
export interface GPSPoint {
  /**
   * The ID of the associated context.
   *
   * @since 8.0.0
   */
  contextId: string;
  /**
   * The latitude of the GPS point.
   *
   * @since 8.0.0
   */
  latitude: number;
  /**
   * The longitude of the GPS point.
   *
   * @since 8.0.0
   */
  longitude: number;
  /**
   * The detection radius in meters.
   *
   * @since 8.0.0
   */
  detectionRadius: number | null;
  /**
   * The distance from the current position in meters.
   *
   * @since 8.0.0
   */
  distanceFromCurrentPosition: number;
}

/**
 * @since 8.0.0
 */
export interface CodeRecognizeEvent {
  /**
   * The scanned code value.
   *
   * @since 8.0.0
   */
  code: string;
  /**
   * The type of the scanned code.
   *
   * @since 8.0.0
   */
  type: string;
}

/**
 * @since 8.0.0
 */
export interface EnterContextEvent {
  /**
   * The ID of the entered context.
   *
   * @since 8.0.0
   */
  contextId: string;
}

/**
 * @since 8.0.0
 */
export interface ExitContextEvent {
  /**
   * The ID of the exited context.
   *
   * @since 8.0.0
   */
  contextId: string;
}

/**
 * @since 8.0.0
 */
export interface EventFromContentEvent {
  /**
   * The name of the custom event.
   *
   * @since 8.0.0
   */
  name: string;
  /**
   * The parameters of the custom event.
   *
   * @since 8.0.0
   */
  params: string;
}

/**
 * @since 8.0.0
 */
export interface SyncProgressEvent {
  /**
   * The sync progress value between 0.0 and 1.0.
   *
   * @since 8.0.0
   */
  progress: number;
}

/**
 * @since 8.0.0
 */
export interface RequireSyncEvent {
  /**
   * The tags that require synchronization.
   *
   * @since 8.0.0
   */
  tags: string[];
}
