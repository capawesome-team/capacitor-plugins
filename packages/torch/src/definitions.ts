export interface TorchPlugin {
  /**
   * Enable the torch.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  enable(): Promise<void>;
  /**
   * Disable the torch.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  disable(): Promise<void>;
  /**
   * Check if the torch is available.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Check if the torch is enabled.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  isEnabled(): Promise<IsEnabledResult>;
  /**
   * Toggle the torch.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  toggle(): Promise<void>;
}

/**
 * @since 6.0.0
 */
export interface IsAvailableResult {
  /**
   * Whether the torch is available or not.
   *
   * @since 6.0.0
   */
  available: boolean;
}

/**
 * @since 6.0.0
 */
export interface IsEnabledResult {
  /**
   * Whether the torch is enabled or not.
   *
   * @since 6.0.0
   */
  enabled: boolean;
}
