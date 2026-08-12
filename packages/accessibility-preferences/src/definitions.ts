export interface AccessibilityPreferencesPlugin {
  /**
   * Get the user's current system accessibility preferences.
   *
   * Fields that the current platform cannot provide are set to `null`.
   *
   * @since 0.1.0
   */
  getPreferences(): Promise<GetPreferencesResult>;
}

/**
 * @since 0.1.0
 */
export interface GetPreferencesResult {
  /**
   * The system font scale factor.
   *
   * A value of `1.0` represents the default font size. Values greater than
   * `1.0` indicate that the user prefers larger text, values less than `1.0`
   * smaller text.
   *
   * On iOS, the value is derived from the preferred content size category.
   *
   * On Web, this is always `1.0` because the font scale is not exposed to web
   * content.
   *
   * @example 1.0
   * @since 0.1.0
   */
  fontScale: number;
  /**
   * Whether the user prefers bold text.
   *
   * Returns `null` on platforms where the value cannot be read (Web and
   * Android versions below 12).
   *
   * @example false
   * @since 0.1.0
   */
  isBoldTextEnabled: boolean | null;
  /**
   * Whether the user prefers increased contrast.
   *
   * On iOS, this reflects the "Increase Contrast" setting (darker system
   * colors). On Android, this reflects the high text contrast setting.
   *
   * Returns `null` on platforms where the value cannot be read.
   *
   * @example false
   * @since 0.1.0
   */
  isHighContrastEnabled: boolean | null;
  /**
   * Whether the user has enabled inverted colors.
   *
   * Returns `null` on Web.
   *
   * @example false
   * @since 0.1.0
   */
  isInvertColorsEnabled: boolean | null;
  /**
   * Whether the user prefers reduced motion (fewer animations).
   *
   * @example false
   * @since 0.1.0
   */
  isReduceMotionEnabled: boolean;
  /**
   * Whether the user prefers reduced transparency.
   *
   * Only available on iOS. Returns `null` on Android and Web.
   *
   * @example false
   * @since 0.1.0
   */
  isReduceTransparencyEnabled: boolean | null;
}
