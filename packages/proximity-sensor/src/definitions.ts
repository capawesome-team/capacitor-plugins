import type { PluginListenerHandle } from '@capacitor/core';

export interface ProximitySensorPlugin {
  /**
   * Get the latest measurement.
   *
   * This method returns the most recent measurement from the proximity sensor.
   *
   * On iOS, reading the measurement enables proximity monitoring, which turns
   * off the screen while an object is close to the sensor.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getMeasurement(): Promise<GetMeasurementResult>;
  /**
   * Check if the proximity sensor is available on the device.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Start emitting `measurement` events.
   *
   * On iOS, this enables proximity monitoring, which turns off the screen
   * while an object is close to the sensor.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  startMeasurementUpdates(): Promise<void>;
  /**
   * Stop emitting `measurement` events.
   *
   * On iOS, this disables proximity monitoring.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  stopMeasurementUpdates(): Promise<void>;
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
   * Remove all listeners for this plugin.
   *
   * @since 0.1.0
   */
  removeAllListeners(): Promise<void>;
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
   * Whether the proximity sensor is available on the device.
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
   * The distance between the sensor and the nearby object in centimeters (cm).
   *
   * Most devices only report whether an object is near or far, so this value
   * is typically either `0` (near) or the sensor's maximum range (far).
   *
   * Only available on Android. Always `null` on iOS.
   *
   * @example 5
   * @since 0.1.0
   */
  distance: number | null;
  /**
   * Whether an object is close to the sensor.
   *
   * @example true
   * @since 0.1.0
   */
  near: boolean;
}
