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
  /**
   * Whether the active network connection is subject to data saving
   * restrictions, such as Data Saver on Android or Low Data Mode on iOS.
   *
   * This is `false` if the device is not connected to a network and `null`
   * on browsers that do not expose this information.
   *
   * @example false
   * @since 0.1.2
   */
  constrained: boolean | null;
  /**
   * Whether the active network connection is considered expensive,
   * for example a metered Wi-Fi or cellular network.
   *
   * This is `false` if the device is not connected to a network and `null`
   * on platforms that cannot determine the cost of the connection (Web).
   *
   * @example false
   * @since 0.1.2
   */
  expensive: boolean | null;
  /**
   * Whether the active network connection is ultra-constrained, such as a
   * carrier-provided satellite network.
   *
   * This is `false` if the device is not connected to a network and `null`
   * on platforms that cannot determine this (Android, Web and iOS below 26).
   *
   * Only available on iOS 26+.
   *
   * @example false
   * @since 0.1.2
   */
  ultraConstrained: boolean | null;
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
