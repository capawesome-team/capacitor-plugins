import type { PluginListenerHandle } from '@capacitor/core';

export interface ThermalStatePlugin {
  /**
   * Get the current thermal state of the device.
   *
   * Only available on Android (API level 29+) and iOS.
   *
   * @since 0.1.0
   */
  getThermalState(): Promise<GetThermalStateResult>;
  /**
   * Listen for changes to the thermal state of the device.
   *
   * The device is only observed while at least one listener is attached.
   *
   * Only available on Android (API level 29+) and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'thermalStateChange',
    listenerFunc: (event: ThermalStateChangeEvent) => void,
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
export interface GetThermalStateResult {
  /**
   * The current thermal state of the device.
   *
   * @example 'nominal'
   * @since 0.1.0
   */
  state: ThermalStateValue;
}

/**
 * @since 0.1.0
 */
export interface ThermalStateChangeEvent {
  /**
   * The current thermal state of the device.
   *
   * @example 'nominal'
   * @since 0.1.0
   */
  state: ThermalStateValue;
}

/**
 * The thermal state of the device.
 *
 * - `critical`: The thermal state is significantly impacting performance.
 *   Reduce the workload as much as possible.
 * - `fair`: The thermal state is slightly elevated. Consider reducing
 *   non-essential work.
 * - `nominal`: The thermal state is within normal limits. No action is needed.
 * - `serious`: The thermal state is high. Reduce the workload to help the
 *   device cool down.
 *
 * @since 0.1.0
 */
export type ThermalStateValue = 'critical' | 'fair' | 'nominal' | 'serious';
