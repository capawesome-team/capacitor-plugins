export interface TorchPlugin {
  /**
   * Enable the torch.
   *
   * Only available on Android (SDK 23+), iOS and Web.
   *
   * @since 6.0.0
   */
  enable(options?: EnableOptions): Promise<void>;
  /**
   * Disable the torch.
   *
   * Only available on Android (SDK 23+), iOS and Web.
   *
   * @since 6.0.0
   */
  disable(options?: DisableOptions): Promise<void>;
  /**
   * Check if the torch is available.
   *
   * Only available on Android, iOS and Web.
   *
   * @since 6.0.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Check if the torch is enabled.
   *
   * Only available on Android, iOS and Web.
   *
   * @since 6.0.0
   */
  isEnabled(options?: IsEnabledOptions): Promise<IsEnabledResult>;
  /**
   * Toggle the torch.
   *
   * Only available on Android (SDK 23+), iOS and Web.
   *
   * @since 6.0.0
   */
  toggle(options?: ToggleOptions): Promise<void>;
}

/**
 * @since 6.2.0
 */
export interface EnableOptions {
  /**
   * The stream of media to enable the torch on.
   *
   * **Attention**: The stream must have a video track.
   * The facing mode of the video track must be the one that corresponds to the torch.
   *
   * Only available on Web.
   *
   * @since 6.2.0
   */
  stream?: MediaStream;
}

/**
 * @since 6.2.0
 */
export interface DisableOptions {
  /**
   * The stream of media to disable the torch on.
   *
   * **Attention**: The stream must have a video track.
   * The facing mode of the video track must be the one that corresponds to the torch.
   *
   * Only available on Web.
   *
   * @since 6.2.0
   */
  stream?: MediaStream;
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

/**
 * @since 6.2.0
 */
export interface IsEnabledOptions {
  /**
   * The stream of media to check if the torch is enabled on.
   *
   * **Attention**: The stream must have a video track.
   * The facing mode of the video track must be the one that corresponds to the torch.
   *
   * Only available on Web.
   *
   * @since 6.2.0
   */
  stream?: MediaStream;
}

/**
 * @since 6.2.0
 */
export interface ToggleOptions {
  /**
   * The stream of media to toggle the torch on.
   *
   * **Attention**: The stream must have a video track.
   * The facing mode of the video track must be the one that corresponds to the torch.
   *
   * Only available on Web.
   *
   * @since 6.2.0
   */
  stream?: MediaStream;
}
