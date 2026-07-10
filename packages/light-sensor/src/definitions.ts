import type { PluginListenerHandle } from '@capacitor/core';

export interface LightSensorPlugin {
  /**
   * Get the latest measurement.
   *
   * This method returns the most recent measurement from the ambient light sensor.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  getMeasurement(): Promise<GetMeasurementResult>;
  /**
   * Check if the ambient light sensor is available on the device.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Start emitting `measurement` events.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  startMeasurementUpdates(): Promise<void>;
  /**
   * Stop emitting `measurement` events.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  stopMeasurementUpdates(): Promise<void>;
  /**
   * Called when a new measurement is available.
   *
   * Only available on Android.
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
   * Whether the ambient light sensor is available on the device.
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
   * The ambient light level in lux (lx).
   *
   * @example 320
   * @since 0.1.0
   */
  illuminance: number;
}
