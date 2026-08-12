import type { PluginListenerHandle } from '@capacitor/core';

export interface BatteryPlugin {
  /**
   * Get the current battery level of the device.
   *
   * On the web, this is only supported in browsers that implement the
   * [Battery Status API](https://developer.mozilla.org/en-US/docs/Web/API/Battery_Status_API)
   * (Chromium-based browsers).
   *
   * Only available on Android, iOS and Web.
   *
   * @since 0.1.0
   */
  getBatteryLevel(): Promise<GetBatteryLevelResult>;
  /**
   * Get the current battery state of the device.
   *
   * On the web, this is only supported in browsers that implement the
   * [Battery Status API](https://developer.mozilla.org/en-US/docs/Web/API/Battery_Status_API)
   * (Chromium-based browsers).
   *
   * Only available on Android, iOS and Web.
   *
   * @since 0.1.0
   */
  getBatteryState(): Promise<GetBatteryStateResult>;
  /**
   * Get whether the low power mode is currently enabled.
   *
   * On Android, this refers to the power saver mode. On iOS, this refers to
   * the Low Power Mode.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  isLowPowerModeEnabled(): Promise<IsLowPowerModeEnabledResult>;
  /**
   * Listen for changes to the battery level of the device.
   *
   * The device is only observed while at least one listener is attached.
   *
   * On the web, this is only supported in browsers that implement the
   * [Battery Status API](https://developer.mozilla.org/en-US/docs/Web/API/Battery_Status_API)
   * (Chromium-based browsers).
   *
   * Only available on Android, iOS and Web.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'batteryLevelChange',
    listenerFunc: (event: BatteryLevelChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Listen for changes to the battery state of the device.
   *
   * The device is only observed while at least one listener is attached.
   *
   * On the web, this is only supported in browsers that implement the
   * [Battery Status API](https://developer.mozilla.org/en-US/docs/Web/API/Battery_Status_API)
   * (Chromium-based browsers).
   *
   * Only available on Android, iOS and Web.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'batteryStateChange',
    listenerFunc: (event: BatteryStateChangeEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Listen for changes to the low power mode of the device.
   *
   * The device is only observed while at least one listener is attached.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'lowPowerModeChange',
    listenerFunc: (event: LowPowerModeChangeEvent) => void,
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
export interface GetBatteryLevelResult {
  /**
   * The current battery level of the device as a value between `0.0` and `1.0`.
   *
   * @example 0.75
   * @since 0.1.0
   */
  level: number;
}

/**
 * @since 0.1.0
 */
export interface GetBatteryStateResult {
  /**
   * The current battery state of the device.
   *
   * @example 'charging'
   * @since 0.1.0
   */
  state: BatteryState;
}

/**
 * @since 0.1.0
 */
export interface IsLowPowerModeEnabledResult {
  /**
   * Whether the low power mode is currently enabled.
   *
   * @example true
   * @since 0.1.0
   */
  enabled: boolean;
}

/**
 * @since 0.1.0
 */
export interface BatteryLevelChangeEvent {
  /**
   * The current battery level of the device as a value between `0.0` and `1.0`.
   *
   * @example 0.75
   * @since 0.1.0
   */
  level: number;
}

/**
 * @since 0.1.0
 */
export interface BatteryStateChangeEvent {
  /**
   * The current battery state of the device.
   *
   * @example 'charging'
   * @since 0.1.0
   */
  state: BatteryState;
}

/**
 * @since 0.1.0
 */
export interface LowPowerModeChangeEvent {
  /**
   * Whether the low power mode is currently enabled.
   *
   * @example true
   * @since 0.1.0
   */
  enabled: boolean;
}

/**
 * The battery state of the device.
 *
 * - `charging`: The device is plugged into power and the battery is charging.
 * - `full`: The device is plugged into power and the battery is fully charged.
 * - `unplugged`: The device is not plugged into power and the battery is
 *   discharging.
 * - `unknown`: The battery state could not be determined.
 *
 * @since 0.1.0
 */
export type BatteryState = 'charging' | 'full' | 'unplugged' | 'unknown';
