export interface AppLanguagePlugin {
  /**
   * Get the app's current language override.
   *
   * The language override is independent of the device language and only
   * affects natively rendered strings (e.g. permission dialogs).
   *
   * @since 0.1.0
   */
  getLanguage(): Promise<GetLanguageResult>;
  /**
   * Open the app's settings page in the system settings.
   *
   * On iOS, this is where the user can change the app's language (the language
   * row is only shown if the app bundle provides more than one localization).
   *
   * @since 0.1.0
   */
  openSettings(): Promise<void>;
  /**
   * Reset the app's language override so the app follows the device language
   * again.
   *
   * Resetting the language recreates the current activity, which reloads the
   * web view.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  resetLanguage(): Promise<void>;
  /**
   * Set the app's language override.
   *
   * Setting the language recreates the current activity, which reloads the
   * web view.
   *
   * On iOS, the language can only be changed by the user in the system
   * settings (see the `openSettings(...)` method).
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  setLanguage(options: SetLanguageOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface GetLanguageResult {
  /**
   * The BCP 47 language tag of the app's language override.
   *
   * Returns `null` if no override is set and the app follows the device
   * language.
   *
   * @example 'de-DE'
   * @since 0.1.0
   */
  languageTag: string | null;
}

/**
 * @since 0.1.0
 */
export interface SetLanguageOptions {
  /**
   * The BCP 47 language tag to set as the app's language override.
   *
   * @example 'de-DE'
   * @since 0.1.0
   */
  languageTag: string;
}
