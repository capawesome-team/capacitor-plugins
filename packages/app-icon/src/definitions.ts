export interface AppIconPlugin {
  /**
   * Get the name of the icon that is currently in use.
   *
   * Returns `null` if the default icon is in use.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getCurrentIcon(): Promise<GetCurrentIconResult>;
  /**
   * Check if changing the app icon is supported on the current device.
   *
   * On Android, this always resolves to `true`.
   * On iOS, this resolves to the value of `supportsAlternateIcons`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Restore the default app icon.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  resetIcon(): Promise<void>;
  /**
   * Change the app icon to the alternate icon with the given name.
   *
   * The icon must be declared by the app beforehand. See the setup instructions
   * for [Android](https://capawesome.io/docs/sdks/capacitor/app-icon/#android) and
   * [iOS](https://capawesome.io/docs/sdks/capacitor/app-icon/#ios) for more information.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  setIcon(options: SetIconOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface GetCurrentIconResult {
  /**
   * The name of the icon that is currently in use.
   *
   * Returns `null` if the default icon is in use.
   *
   * @example 'Christmas'
   * @since 0.1.0
   */
  icon: string | null;
}

/**
 * @since 0.1.0
 */
export interface IsAvailableResult {
  /**
   * Whether or not changing the app icon is supported on the current device.
   *
   * @since 0.1.0
   */
  available: boolean;
}

/**
 * @since 0.1.0
 */
export interface SetIconOptions {
  /**
   * The name of the alternate icon to use.
   *
   * On Android, this is the name of the `<activity-alias>` (without the leading dot).
   * On iOS, this is the name of the alternate app icon set in the asset catalog.
   *
   * @example 'Christmas'
   * @since 0.1.0
   */
  icon: string;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The app icon could not be changed.
   *
   * @since 0.1.0
   */
  ChangeFailed = 'CHANGE_FAILED',
  /**
   * The alternate icon with the given name could not be found.
   *
   * @since 0.1.0
   */
  IconNotFound = 'ICON_NOT_FOUND',
}
