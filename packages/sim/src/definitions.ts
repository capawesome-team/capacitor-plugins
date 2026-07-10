import type { PermissionState } from '@capacitor/core';

export interface SimPlugin {
  /**
   * Check the permission to read the SIM cards.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Get information about the SIM cards installed on the device.
   *
   * On devices with multiple SIM slots, all active SIM cards are returned.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  getSimCards(): Promise<GetSimCardsResult>;
  /**
   * Request the permission to read the SIM cards.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  requestPermissions(): Promise<PermissionStatus>;
}

/**
 * @since 0.1.0
 */
export interface GetSimCardsResult {
  /**
   * The SIM cards installed on the device.
   *
   * @since 0.1.0
   */
  simCards: SimCard[];
}

/**
 * @since 0.1.0
 */
export interface SimCard {
  /**
   * The name of the carrier.
   *
   * Returns `null` if the carrier name is not available.
   *
   * @example 'T-Mobile'
   * @since 0.1.0
   */
  carrierName: string | null;
  /**
   * The user-editable display name of the SIM card.
   *
   * Returns `null` if the display name is not available.
   *
   * @example 'Personal'
   * @since 0.1.0
   */
  displayName: string | null;
  /**
   * Whether the SIM card is an embedded SIM (eSIM).
   *
   * Returns `null` if the information is not available.
   *
   * @example false
   * @since 0.1.0
   */
  isEmbedded: boolean | null;
  /**
   * The two-letter ISO 3166-1 country code of the carrier.
   *
   * Returns `null` if the country code is not available.
   *
   * @example 'us'
   * @since 0.1.0
   */
  isoCountryCode: string | null;
  /**
   * The Mobile Country Code (MCC) of the carrier.
   *
   * Returns `null` if the mobile country code is not available.
   *
   * @example '310'
   * @since 0.1.0
   */
  mobileCountryCode: string | null;
  /**
   * The Mobile Network Code (MNC) of the carrier.
   *
   * Returns `null` if the mobile network code is not available.
   *
   * @example '260'
   * @since 0.1.0
   */
  mobileNetworkCode: string | null;
  /**
   * The phone number associated with the SIM card.
   *
   * This value is often empty because carriers do not reliably store the
   * phone number on the SIM card. In that case, `null` is returned.
   *
   * @example '+12025550123'
   * @since 0.1.0
   */
  phoneNumber: string | null;
  /**
   * The index of the SIM slot on the device.
   *
   * @example 0
   * @since 0.1.0
   */
  slotIndex: number;
}

/**
 * @since 0.1.0
 */
export interface PermissionStatus {
  /**
   * The permission state of reading the SIM cards.
   *
   * @since 0.1.0
   */
  readSimCards: PermissionState;
}
