export interface MapsLauncherPlugin {
  /**
   * Get the navigation apps that are installed and can be launched.
   *
   * On iOS, Apple Maps is always included. Google Maps and Waze are only
   * included if the corresponding `LSApplicationQueriesSchemes` entries are
   * added to the `Info.plist` file of your app.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getAvailableApps(): Promise<GetAvailableAppsResult>;
  /**
   * Get the navigation app that is configured as the default handler for
   * navigation intents.
   *
   * Returns `null` if the default app is not part of the curated list of
   * supported apps or if no default app is set (i.e. the system shows a
   * chooser).
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  getDefaultApp(): Promise<GetDefaultAppResult>;
  /**
   * Launch a navigation app with turn-by-turn directions to a destination.
   *
   * If no `app` is provided, the system default behavior is used (a chooser on
   * Android, Apple Maps on iOS).
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  navigate(options: NavigateOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface GetAvailableAppsResult {
  /**
   * The navigation apps that are installed and can be launched.
   *
   * @since 0.1.0
   */
  apps: NavigationApp[];
}

/**
 * @since 0.1.0
 */
export interface GetDefaultAppResult {
  /**
   * The navigation app that is configured as the default handler.
   *
   * Returns `null` if the default app is not part of the curated list of
   * supported apps or if no default app is set.
   *
   * @since 0.1.0
   */
  app: NavigationApp | null;
}

/**
 * @since 0.1.0
 */
export interface NavigateOptions {
  /**
   * The navigation app to launch.
   *
   * If not provided, the system default behavior is used (a chooser on
   * Android, Apple Maps on iOS).
   *
   * @since 0.1.0
   */
  app?: NavigationApp;
  /**
   * The destination to navigate to.
   *
   * @since 0.1.0
   */
  destination: Destination;
  /**
   * The start location of the route.
   *
   * If not provided, the current location of the device is used.
   *
   * **Note**: The support depends on the selected app. Apple Maps supports it
   * fully, Google Maps opens the directions preview instead of starting
   * turn-by-turn navigation, and Waze ignores it.
   *
   * @since 0.1.0
   */
  start?: Destination;
  /**
   * The travel mode to use for the directions.
   *
   * **Note**: The support depends on the selected app and is best-effort.
   * Waze only supports driving and ignores this option.
   *
   * @default 'driving'
   * @since 0.1.0
   */
  travelMode?: TravelMode;
}

/**
 * A destination is either defined by its coordinates or by its address, but
 * not both.
 *
 * @since 0.1.0
 */
export interface Destination {
  /**
   * The address of the destination.
   *
   * Must be provided without `latitude` and `longitude`.
   *
   * @example 'Apple Park, Cupertino, CA'
   * @since 0.1.0
   */
  address?: string;
  /**
   * The latitude of the destination.
   *
   * Must be provided together with `longitude` and without `address`.
   *
   * @example 37.3349
   * @since 0.1.0
   */
  latitude?: number;
  /**
   * The longitude of the destination.
   *
   * Must be provided together with `latitude` and without `address`.
   *
   * @example -122.009
   * @since 0.1.0
   */
  longitude?: number;
}

/**
 * A navigation app that can be launched.
 *
 * @since 0.1.0
 */
export enum NavigationApp {
  /**
   * Apple Maps.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  AppleMaps = 'APPLE_MAPS',
  /**
   * Google Maps.
   *
   * @since 0.1.0
   */
  GoogleMaps = 'GOOGLE_MAPS',
  /**
   * Waze.
   *
   * @since 0.1.0
   */
  Waze = 'WAZE',
}

/**
 * The travel mode to use for the directions.
 *
 * - `driving`: Driving directions.
 * - `walking`: Walking directions.
 * - `bicycling`: Bicycling directions.
 * - `transit`: Public transit directions.
 *
 * @since 0.1.0
 */
export type TravelMode = 'driving' | 'walking' | 'bicycling' | 'transit';

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The requested navigation app is not installed or cannot be launched.
   *
   * @since 0.1.0
   */
  AppNotAvailable = 'APP_NOT_AVAILABLE',
  /**
   * The navigation app could not be launched.
   *
   * @since 0.1.0
   */
  LaunchFailed = 'LAUNCH_FAILED',
}
