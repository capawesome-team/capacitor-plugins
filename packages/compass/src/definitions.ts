import type { PluginListenerHandle } from '@capacitor/core';

export interface CompassPlugin {
  /**
   * Add a listener for heading changes.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'headingChange',
    listenerFunc: (event: HeadingChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Get the current device heading.
   *
   * This method returns the most recent heading reading from the device's
   * compass sensor.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getHeading(): Promise<GetHeadingResult>;
  /**
   * Check if the compass sensor is available on the device.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Remove all listeners for this plugin.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  removeAllListeners(): Promise<void>;
  /**
   * Start emitting `headingChange` events.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  startHeadingUpdates(): Promise<void>;
  /**
   * Stop emitting `headingChange` events.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  stopHeadingUpdates(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export type GetHeadingResult = Heading;

/**
 * @since 0.1.0
 */
export type HeadingChangeEvent = Heading;

/**
 * @since 0.1.0
 */
export interface IsAvailableResult {
  /**
   * Whether the compass sensor is available on the device.
   *
   * @since 0.1.0
   */
  available: boolean;
}

/**
 * @since 0.1.0
 */
export interface Heading {
  /**
   * The maximum deviation between the reported heading and the true heading in degrees.
   *
   * A negative value or `null` indicates that the accuracy is invalid or unknown.
   *
   * @example 15
   * @since 0.1.0
   */
  accuracy: number | null;
  /**
   * The heading relative to magnetic north in degrees.
   *
   * The value ranges from `0` to `360`, where `0` means the device is
   * pointing towards magnetic north.
   *
   * @example 149.6
   * @since 0.1.0
   */
  magneticHeading: number;
  /**
   * The heading relative to true (geographic) north in degrees.
   *
   * The value ranges from `0` to `360`, where `0` means the device is
   * pointing towards true north. Returns `null` if the true heading cannot be
   * determined.
   *
   * On Android, this value is always `null`.
   *
   * On iOS, this value requires location services to be enabled and the
   * `NSLocationWhenInUseUsageDescription` key to be set. Otherwise, it is `null`.
   *
   * @example 152.1
   * @since 0.1.0
   */
  trueHeading: number | null;
}
