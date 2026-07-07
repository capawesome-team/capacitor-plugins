import type { PluginListenerHandle } from '@capacitor/core';

export interface NetworkPlugin {
  /**
   * Get the current network status of the device.
   *
   * Only available on Android, iOS and Web.
   *
   * @since 0.1.0
   */
  getStatus(): Promise<GetStatusResult>;
  /**
   * Get whether the airplane mode is currently enabled.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  isAirplaneModeEnabled(): Promise<IsAirplaneModeEnabledResult>;
  /**
   * Listen for changes to the network status of the device.
   *
   * The device is only observed while at least one listener is attached.
   *
   * Only available on Android, iOS and Web.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'networkStatusChange',
    listenerFunc: (event: GetStatusResult) => void,
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
export interface GetStatusResult {
  /**
   * Whether the device is currently connected to a network.
   *
   * @example true
   * @since 0.1.0
   */
  connected: boolean;
  /**
   * The type of the currently active network connection.
   *
   * @example 'WIFI'
   * @since 0.1.0
   */
  connectionType: ConnectionType;
  /**
   * Whether the active network connection has verified access to the internet.
   *
   * This is `null` on platforms that cannot validate internet access
   * (iOS and Web), where connectivity does not guarantee reachability.
   *
   * Only available on Android.
   *
   * @example true
   * @since 0.1.0
   */
  internetReachable: boolean | null;
}

/**
 * @since 0.1.0
 */
export interface IsAirplaneModeEnabledResult {
  /**
   * Whether the airplane mode is currently enabled.
   *
   * @example true
   * @since 0.1.0
   */
  enabled: boolean;
}

/**
 * The type of a network connection.
 *
 * @since 0.1.0
 */
export enum ConnectionType {
  /**
   * The device is connected via a cellular network.
   *
   * @since 0.1.0
   */
  Cellular = 'CELLULAR',
  /**
   * The device is connected via a wired ethernet network.
   *
   * @since 0.1.0
   */
  Ethernet = 'ETHERNET',
  /**
   * The device is not connected to any network.
   *
   * @since 0.1.0
   */
  None = 'NONE',
  /**
   * The type of the network connection could not be determined.
   *
   * @since 0.1.0
   */
  Unknown = 'UNKNOWN',
  /**
   * The device is connected via a virtual private network (VPN).
   *
   * @since 0.1.0
   */
  Vpn = 'VPN',
  /**
   * The device is connected via a Wi-Fi network.
   *
   * @since 0.1.0
   */
  Wifi = 'WIFI',
}
