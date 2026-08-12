export interface LocalizationPlugin {
  /**
   * Get the user's preferred locales, ordered by preference.
   *
   * The first entry is the most preferred locale.
   *
   * @since 0.1.0
   */
  getLocales(): Promise<GetLocalesResult>;
  /**
   * Get the user's regional formatting settings.
   *
   * @since 0.1.0
   */
  getSettings(): Promise<GetSettingsResult>;
}

/**
 * @since 0.1.0
 */
export interface GetLocalesResult {
  /**
   * The user's preferred locales, ordered by preference.
   *
   * The first entry is the most preferred locale.
   *
   * @since 0.1.0
   */
  locales: Locale[];
}

/**
 * @since 0.1.0
 */
export interface GetSettingsResult {
  /**
   * The first day of the week as configured by the user.
   *
   * The value follows the ISO 8601 convention where `1` is Monday and `7` is
   * Sunday.
   *
   * @example 1
   * @since 0.1.0
   */
  firstDayOfWeek: number;
  /**
   * The identifier of the user's current time zone.
   *
   * @example 'Europe/Berlin'
   * @since 0.1.0
   */
  timeZone: string;
  /**
   * Whether the user prefers a 24-hour clock over a 12-hour clock.
   *
   * @since 0.1.0
   */
  uses24HourClock: boolean;
}

/**
 * @since 0.1.0
 */
export interface Locale {
  /**
   * The ISO 4217 currency code of the locale.
   *
   * Returns `null` if the currency cannot be determined.
   *
   * @example 'EUR'
   * @since 0.1.0
   */
  currencyCode: string | null;
  /**
   * The currency symbol of the locale.
   *
   * Returns `null` if the currency symbol cannot be determined.
   *
   * @example '€'
   * @since 0.1.0
   */
  currencySymbol: string | null;
  /**
   * The character used to separate the integer part from the fractional part
   * of a number.
   *
   * Returns `null` if the separator cannot be determined.
   *
   * @example ','
   * @since 0.1.0
   */
  decimalSeparator: string | null;
  /**
   * The character used to group digits of the integer part of a number.
   *
   * Returns `null` if the separator cannot be determined.
   *
   * @example '.'
   * @since 0.1.0
   */
  groupingSeparator: string | null;
  /**
   * The ISO 639 language code of the locale.
   *
   * @example 'de'
   * @since 0.1.0
   */
  languageCode: string;
  /**
   * The BCP 47 language tag of the locale.
   *
   * @example 'de-DE'
   * @since 0.1.0
   */
  languageTag: string;
  /**
   * The measurement system used by the locale.
   *
   * Returns `null` if the measurement system cannot be determined.
   *
   * @example 'metric'
   * @since 0.1.0
   */
  measurementSystem: MeasurementSystem | null;
  /**
   * The ISO 3166 region code of the locale.
   *
   * Returns `null` if the region cannot be determined.
   *
   * @example 'DE'
   * @since 0.1.0
   */
  regionCode: string | null;
  /**
   * The writing direction of the locale's language.
   *
   * @example 'ltr'
   * @since 0.1.0
   */
  textDirection: TextDirection;
}

/**
 * The writing direction of a language.
 *
 * - `ltr`: Left-to-right (e.g. English, German).
 * - `rtl`: Right-to-left (e.g. Arabic, Hebrew).
 *
 * @since 0.1.0
 */
export type TextDirection = 'ltr' | 'rtl';

/**
 * The measurement system used by a locale.
 *
 * - `metric`: The metric system (e.g. meters, kilograms).
 * - `us`: The United States customary system (e.g. feet, pounds).
 * - `uk`: The United Kingdom imperial system.
 *
 * @since 0.1.0
 */
export type MeasurementSystem = 'metric' | 'us' | 'uk';
