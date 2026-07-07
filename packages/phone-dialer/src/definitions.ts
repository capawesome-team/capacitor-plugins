export interface PhoneDialerPlugin {
  /**
   * Check whether the device is able to open the phone dialer.
   *
   * On devices without telephony capability (e.g. Wi-Fi-only tablets or
   * iPod touch), this resolves with `canDial` set to `false`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  canDial(): Promise<CanDialResult>;
  /**
   * Open the native phone dialer prefilled with the given number.
   *
   * The user reviews the number and decides whether to place the call. The
   * plugin never places the call itself.
   *
   * If the device is not able to open the phone dialer, the call rejects as
   * unavailable.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  dial(options: DialOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface CanDialResult {
  /**
   * Whether the device is able to open the phone dialer.
   *
   * @since 0.1.0
   */
  canDial: boolean;
}

/**
 * @since 0.1.0
 */
export interface DialOptions {
  /**
   * The phone number to prefill in the dialer.
   *
   * Only digits and the characters `+`, `*` and `#` are kept. All other
   * characters (e.g. spaces or dashes) are removed before dialing.
   *
   * @example '+41791234567'
   * @since 0.1.0
   */
  number: string;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The phone dialer failed to open.
   *
   * @since 0.1.0
   */
  DialFailed = 'DIAL_FAILED',
}
