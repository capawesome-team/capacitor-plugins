import type { PermissionState, PluginListenerHandle } from '@capacitor/core';

export type GyroscopePermissionState = PermissionState | 'limited';

export interface GyroscopePlugin {
  /**
   * Called when a new measurement is available.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'measurement',
    listenerFunc: (event: MeasurementEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Check if the app has permission to access the gyroscope sensor.
   *
   * @since 0.1.0
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Get the latest measurement.
   *
   * This method returns the most recent measurement from the gyroscope sensor.
   *
   * @since 0.1.0
   */
  getMeasurement(): Promise<GetMeasurementResult>;
  /**
   * Check if the gyroscope sensor is available on the device.
   *
   * @since 0.1.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.1.0
   */
  removeAllListeners(): Promise<void>;
  /**
   * Request permission to access the gyroscope sensor.
   *
   * @since 0.1.0
   */
  requestPermissions(): Promise<PermissionStatus>;
  /**
   * Start emitting `measurement` events.
   *
   * @since 0.1.0
   */
  startMeasurementUpdates(): Promise<void>;
  /**
   * Stop emitting `measurement` events.
   *
   * @since 0.1.0
   */
  stopMeasurementUpdates(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export type GetMeasurementResult = Measurement;

/**
 * @since 0.1.0
 */
export interface IsAvailableResult {
  /**
   * Whether the gyroscope sensor is available on the device.
   *
   * @since 0.1.0
   */
  available: boolean;
}

/**
 * @since 0.1.0
 */
export type MeasurementEvent = Measurement;

/**
 * @since 0.1.0
 */
export interface Measurement {
  /**
   * The rotation rate around the x-axis in radians per second (rad/s).
   *
   * @example 0.12
   * @since 0.1.0
   */
  x: number;
  /**
   * The rotation rate around the y-axis in radians per second (rad/s).
   *
   * @example 0.12
   * @since 0.1.0
   */
  y: number;
  /**
   * The rotation rate around the z-axis in radians per second (rad/s).
   *
   * @example 0.12
   * @since 0.1.0
   */
  z: number;
}

/**
 * @since 0.1.0
 */
export interface PermissionStatus {
  /**
   * The permission status of the gyroscope sensor.
   *
   * @since 0.1.0
   */
  gyroscope: GyroscopePermissionState;
}
