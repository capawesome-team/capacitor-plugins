import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

/**
 * @since 0.0.1
 */
export type PixlivePermissionType =
  | 'bluetooth'
  | 'bluetoothConnect'
  | 'bluetoothScan'
  | 'camera'
  | 'location'
  | 'notifications';

/**
 * @since 0.0.1
 */
export interface PixlivePlugin {
  /**
   * Initialize the PixLive SDK.
   *
   * This must be called before any other method.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  initialize(): Promise<void>;
  /**
   * Check the status of permissions.
   *
   * @since 0.0.1
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Request permissions.
   *
   * @since 0.0.1
   */
  requestPermissions(
    options?: PixlivePluginPermission,
  ): Promise<PermissionStatus>;
  /**
   * Sync content from PixLive Maker filtered by tags.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  synchronize(options: SynchronizeOptions): Promise<void>;
  /**
   * Sync content filtered by tags, tour IDs, and context IDs.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  synchronizeWithToursAndContexts(
    options: SynchronizeWithToursAndContextsOptions,
  ): Promise<void>;
  /**
   * Update tag-to-context mappings for language filtering.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  updateTagMapping(options: UpdateTagMappingOptions): Promise<void>;
  /**
   * Enable only contexts matching specific tags.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  enableContextsWithTags(options: EnableContextsWithTagsOptions): Promise<void>;
  /**
   * Get all synchronized contexts.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getContexts(): Promise<GetContextsResult>;
  /**
   * Get a single context by ID.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getContext(options: GetContextOptions): Promise<GetContextResult>;
  /**
   * Programmatically trigger/activate a context.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  activateContext(options: ActivateContextOptions): Promise<void>;
  /**
   * Stop the currently playing/active context.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  stopContext(): Promise<void>;
  /**
   * Get GPS points near a given location, sorted by distance.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getNearbyGPSPoints(
    options: GetNearbyGPSPointsOptions,
  ): Promise<GetNearbyGPSPointsResult>;
  /**
   * Get all GPS points within a geographic bounding box.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getGPSPointsInBoundingBox(
    options: GetGPSPointsInBoundingBoxOptions,
  ): Promise<GetGPSPointsInBoundingBoxResult>;
  /**
   * Get contexts associated with nearby detected beacons.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  getNearbyBeacons(): Promise<GetNearbyBeaconsResult>;
  /**
   * Start background GPS proximity detection.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  startNearbyGPSDetection(): Promise<void>;
  /**
   * Stop background GPS proximity detection.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  stopNearbyGPSDetection(): Promise<void>;
  /**
   * Enable GPS-triggered local notifications.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  startGPSNotifications(): Promise<void>;
  /**
   * Disable GPS-triggered local notifications.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  stopGPSNotifications(): Promise<void>;
  /**
   * Enable or disable notification support.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  setNotificationsSupport(
    options: SetNotificationsSupportOptions,
  ): Promise<void>;
  /**
   * Set the language for SDK UI elements.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  setInterfaceLanguage(options: SetInterfaceLanguageOptions): Promise<void>;
  /**
   * Create the native AR camera view at specified screen coordinates.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  createARView(options: CreateARViewOptions): Promise<void>;
  /**
   * Destroy the AR camera view.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  destroyARView(): Promise<void>;
  /**
   * Resize the AR view.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  resizeARView(options: ResizeARViewOptions): Promise<void>;
  /**
   * Enable or disable touch event interception on the AR view.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  setARViewTouchEnabled(options: SetARViewTouchEnabledOptions): Promise<void>;
  /**
   * Define a rectangular region where touches pass through the AR view to the web layer.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  setARViewTouchHole(options: SetARViewTouchHoleOptions): Promise<void>;
  /**
   * Get the version of the sdk.
   *
   * @since 0.0.1
   */
  getVersion(): Promise<GetVersionResult>;
  /**
   * Called when a QR code or barcode is scanned by the AR camera.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'codeRecognize',
    listenerFunc: (event: CodeRecognizeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when an AR context is detected/entered.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'enterContext',
    listenerFunc: (event: EnterContextEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when an AR context is lost/exited.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'exitContext',
    listenerFunc: (event: ExitContextEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when AR content/annotations become visible on screen.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'presentAnnotations',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when AR content/annotations are hidden.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'hideAnnotations',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when AR content dispatches a custom event.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'eventFromContent',
    listenerFunc: (event: EventFromContentEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called during synchronization with progress updates.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'syncProgress',
    listenerFunc: (event: SyncProgressEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the SDK requires synchronization with specific tags.
   *
   * @since 0.0.1
   */
  addListener(
    eventName: 'requireSync',
    listenerFunc: (event: RequireSyncEvent) => void,
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
export interface SynchronizeOptions {
  /**
   * The tags to filter content by.
   *
   * @since 0.0.1
   */
  tags: string[][];
}

/**
 * @since 0.0.1
 */
export interface SynchronizeWithToursAndContextsOptions {
  /**
   * The tags to filter content by.
   *
   * @since 0.0.1
   */
  tags: string[][];
  /**
   * The tour IDs to sync.
   *
   * @since 0.0.1
   */
  tourIds: number[];
  /**
   * The context IDs to sync.
   *
   * @since 0.0.1
   */
  contextIds: string[];
}

/**
 * @since 0.0.1
 */
export interface UpdateTagMappingOptions {
  /**
   * The tags to map.
   *
   * @since 0.0.1
   */
  tags: string[];
}

/**
 * @since 0.0.1
 */
export interface EnableContextsWithTagsOptions {
  /**
   * The tags to enable contexts for.
   *
   * @since 0.0.1
   */
  tags: string[];
}

/**
 * @since 0.0.1
 */
export interface GetContextOptions {
  /**
   * The ID of the context to retrieve.
   *
   * @since 0.0.1
   * @example "12345"
   */
  contextId: string;
}

/**
 * @since 0.0.1
 */
export interface ActivateContextOptions {
  /**
   * The ID of the context to activate.
   *
   * @since 0.0.1
   * @example "12345"
   */
  contextId: string;
}

/**
 * @since 0.0.1
 */
export interface GetNearbyGPSPointsOptions {
  /**
   * The latitude of the reference location.
   *
   * @since 0.0.1
   */
  latitude: number;
  /**
   * The longitude of the reference location.
   *
   * @since 0.0.1
   */
  longitude: number;
}

/**
 * @since 0.0.1
 */
export interface GetGPSPointsInBoundingBoxOptions {
  /**
   * The minimum latitude of the bounding box.
   *
   * @since 0.0.1
   */
  minLatitude: number;
  /**
   * The minimum longitude of the bounding box.
   *
   * @since 0.0.1
   */
  minLongitude: number;
  /**
   * The maximum latitude of the bounding box.
   *
   * @since 0.0.1
   */
  maxLatitude: number;
  /**
   * The maximum longitude of the bounding box.
   *
   * @since 0.0.1
   */
  maxLongitude: number;
}

/**
 * @since 0.0.1
 */
export interface SetNotificationsSupportOptions {
  /**
   * Whether notifications support should be enabled.
   *
   * @since 0.0.1
   */
  enabled: boolean;
}

/**
 * @since 0.0.1
 */
export interface SetInterfaceLanguageOptions {
  /**
   * The language code to set.
   *
   * @since 0.0.1
   * @example "en"
   */
  language: string;
}

/**
 * @since 0.0.1
 */
export interface CreateARViewOptions {
  /**
   * The x position of the AR view.
   *
   * @since 0.0.1
   */
  x: number;
  /**
   * The y position of the AR view.
   *
   * @since 0.0.1
   */
  y: number;
  /**
   * The width of the AR view.
   *
   * @since 0.0.1
   */
  width: number;
  /**
   * The height of the AR view.
   *
   * @since 0.0.1
   */
  height: number;
}

/**
 * @since 0.0.1
 */
export interface ResizeARViewOptions {
  /**
   * The new x position of the AR view.
   *
   * @since 0.0.1
   */
  x: number;
  /**
   * The new y position of the AR view.
   *
   * @since 0.0.1
   */
  y: number;
  /**
   * The new width of the AR view.
   *
   * @since 0.0.1
   */
  width: number;
  /**
   * The new height of the AR view.
   *
   * @since 0.0.1
   */
  height: number;
}

/**
 * @since 0.0.1
 */
export interface SetARViewTouchEnabledOptions {
  /**
   * Whether touch events should be intercepted by the AR view.
   *
   * @since 0.0.1
   */
  enabled: boolean;
}

/**
 * @since 0.0.1
 */
export interface SetARViewTouchHoleOptions {
  /**
   * The top coordinate of the touch hole region.
   *
   * @since 0.0.1
   */
  top: number;
  /**
   * The bottom coordinate of the touch hole region.
   *
   * @since 0.0.1
   */
  bottom: number;
  /**
   * The left coordinate of the touch hole region.
   *
   * @since 0.0.1
   */
  left: number;
  /**
   * The right coordinate of the touch hole region.
   *
   * @since 0.0.1
   */
  right: number;
}

/**
 * @since 0.0.1
 */
export interface PixlivePluginPermission {
  /**
   * The permissions to request.
   *
   * @since 0.0.1
   */
  permissions: PixlivePermissionType[];
}

/**
 * @since 0.0.1
 */
export interface PermissionStatus {
  /**
   * Permission state of the Bluetooth permission.
   *
   * Only available on iOS.
   *
   * @since 0.0.1
   */
  bluetooth?: PermissionState;
  /**
   * Permission state of the Bluetooth Connect permission.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  bluetoothConnect?: PermissionState;
  /**
   * Permission state of the Bluetooth Scan permission.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  bluetoothScan?: PermissionState;
  /**
   * Permission state of the Camera permission.
   *
   * @since 0.0.1
   */
  camera: PermissionState;
  /**
   * Permission state of the Location permission.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  location?: PermissionState;
  /**
   * Permission state of the Notifications permission.
   *
   * @since 0.0.1
   */
  notifications?: PermissionState;
}

/**
 * @since 0.0.1
 */
export interface GetContextsResult {
  /**
   * The list of synchronized contexts.
   *
   * @since 0.0.1
   */
  contexts: Context[];
}

/**
 * @since 0.0.1
 */
export interface GetContextResult {
  /**
   * The context.
   *
   * @since 0.0.1
   */
  context: Context;
}

/**
 * @since 0.0.1
 */
export interface GetNearbyBeaconsResult {
  /**
   * The contexts associated with nearby beacons.
   *
   * @since 0.0.1
   */
  contexts: Context[];
}

/**
 * @since 0.0.1
 */
export interface GetNearbyGPSPointsResult {
  /**
   * The nearby GPS points sorted by distance.
   *
   * @since 0.0.1
   */
  points: GPSPoint[];
}

/**
 * @since 0.0.1
 */
export interface GetGPSPointsInBoundingBoxResult {
  /**
   * The GPS points within the bounding box.
   *
   * @since 0.0.1
   */
  points: GPSPoint[];
}

/**
 * @since 0.0.1
 */
export interface Context {
  /**
   * The unique identifier of the context.
   *
   * @since 0.0.1
   */
  contextId: string;
  /**
   * The name of the context.
   *
   * @since 0.0.1
   */
  name: string;
  /**
   * The description of the context.
   *
   * @since 0.0.1
   */
  description: string | null;
  /**
   * The last update timestamp.
   *
   * @since 0.0.1
   */
  lastUpdate: string;
  /**
   * The URL of the thumbnail image.
   *
   * @since 0.0.1
   */
  imageThumbnailURL: string | null;
  /**
   * The URL of the high-resolution image.
   *
   * @since 0.0.1
   */
  imageHiResURL: string | null;
  /**
   * The notification title.
   *
   * @since 0.0.1
   */
  notificationTitle: string | null;
  /**
   * The notification message.
   *
   * @since 0.0.1
   */
  notificationMessage: string | null;
  /**
   * The tags associated with the context.
   *
   * @since 0.0.1
   */
  tags: string[];
  /**
   * The languages provided for this context.
   *
   * @since 0.1.3
   */
  languages: string[];
}

/**
 * @since 0.0.1
 */
export interface GPSPoint {
  /**
   * The ID of the associated context.
   *
   * @since 0.0.1
   */
  contextId: string;
  /**
   * The category of the GPS point.
   *
   * @since 0.0.1
   */
  category: string;
  /**
   * The label of the GPS point.
   *
   * @since 0.0.1
   */
  label: string;
  /**
   * The latitude of the GPS point.
   *
   * @since 0.0.1
   */
  latitude: number;
  /**
   * The longitude of the GPS point.
   *
   * @since 0.0.1
   */
  longitude: number;
  /**
   * The detection radius in meters.
   *
   * @since 0.0.1
   */
  detectionRadius: number | null;
  /**
   * The distance from the current position in meters.
   *
   * @since 0.0.1
   */
  distanceFromCurrentPosition: number;
}

/**
 * @since 0.0.1
 */
export interface CodeRecognizeEvent {
  /**
   * The scanned code value.
   *
   * @since 0.0.1
   */
  code: string;
  /**
   * The type of the scanned code.
   *
   * @since 0.0.1
   */
  type: string;
}

/**
 * @since 0.0.1
 */
export interface EnterContextEvent {
  /**
   * The ID of the entered context.
   *
   * @since 0.0.1
   */
  contextId: string;
}

/**
 * @since 0.0.1
 */
export interface ExitContextEvent {
  /**
   * The ID of the exited context.
   *
   * @since 0.0.1
   */
  contextId: string;
}

/**
 * @since 0.0.1
 */
export interface EventFromContentEvent {
  /**
   * The name of the custom event.
   *
   * @since 0.0.1
   */
  name: string;
  /**
   * The parameters of the custom event.
   *
   * @since 0.0.1
   */
  params: string;
}

/**
 * @since 0.0.1
 */
export interface SyncProgressEvent {
  /**
   * The sync progress value between 0.0 and 1.0.
   *
   * @since 0.0.1
   */
  progress: number;
}

/**
 * @since 0.0.1
 */
export interface RequireSyncEvent {
  /**
   * The tags that require synchronization.
   *
   * @since 0.0.1
   */
  tags: string[];
}

/**
 * @since 0.0.1
 */
export interface GetVersionResult {
  /**
   * The version of the sdk.
   *
   * @since 0.0.1
   */
  version: string;
}
