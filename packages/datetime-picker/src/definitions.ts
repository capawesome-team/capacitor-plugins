/// <reference types="@capacitor/cli" />

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    /**
     * These configuration values are available:
     */
    DatetimePicker?: {
      /**
       * Choose the theme that the datetime picker should have.
       * With `auto` the system theme is used.
       *
       * Only available on Android and iOS.
       *
       * @since 0.0.1
       * @default 'auto'
       */
      theme?: 'auto' | 'light' | 'dark';
      /**
       * Whether to use the spinner or clock mode for the time picker on Android.
       *
       * Only available on Android.
       *
       * @since 5.1.0
       * @default 'clock'
       */
      androidTimePickerMode?: 'clock' | 'spinner';
      /**
       * Whether to use the calendar or spinner mode for the date picker on Android.
       *
       * Only available on Android.
       *
       * @since 5.1.0
       * @default 'calendar'
       */
      androidDatePickerMode?: 'calendar' | 'spinner';
    };
  }
}

export interface DatetimePickerPlugin {
  /**
   * Open the datetime picker.
   *
   * An error is thrown if the input is canceled or dismissed by the user.
   *
   * Only available on Android and iOS.
   *
   * @since 0.0.1
   */
  present(options?: PresentOptions): Promise<PresentResult>;
}

export interface PresentOptions {
  /**
   * The cancel button text.
   *
   * @since 0.0.1
   * @default 'Cancel'
   */
  cancelButtonText?: string;
  /**
   * The done button text.
   *
   * @since 0.0.1
   * @default 'Ok'
   */
  doneButtonText?: string;
  /**
   * The format in which values are received and returned.
   *
   * @since 0.0.1
   * @default 'yyyy-MM-dd'T'HH:mm:ss.sss'Z''
   */
  format?: string;
  /**
   * BCP 47 language tag to define the language of the UI.
   *
   * @since 0.0.2
   * @example 'en-US'
   */
  locale?: string;
  /**
   * The latest date and time to accept.
   * The format of this value must match the value of the `format` parameter.
   * This value must specify a date string later than or equal to the one specified by the `min` attribute.
   *
   * @since 0.0.1
   */
  max?: string;
  /**
   * The earliest date and time to accept.
   * The format of this value must match the value of the `format` parameter.
   * This value must specify a date string earlier than or equal to the one specified by the `max` attribute.
   *
   * @since 0.0.1
   */
  min?: string;
  /**
   * Whether you want a date or time or datetime picker.
   *
   * @since 0.0.1
   * @default 'datetime'
   */
  mode?: 'date' | 'time' | 'datetime';
  /**
   * Choose the theme that the datetime picker should have.
   * With `auto` the system theme is used.
   * This value overwrites the `theme` configuration value.
   *
   * Only available on Android and iOS.
   * Spinner options only available on Android
   *
   * @since 0.0.1
   */
  theme?: 'auto' | 'light' | 'dark';
  /**
   * The predefined value when opening the picker.
   * The format of this value must match the value of the `format` parameter.
   *
   * @since 0.0.1
   */
  value?: string;
  /**
   * Whether to use the spinner or clock mode for the time picker on Android.
   * This value overwrites the `androidTimePickerMode` configuration value.
   *
   * Only available on Android.
   *
   * @since 5.1.0
   */
  androidTimePickerMode?: 'clock' | 'spinner';
  /**
   * Whether to use the calendar or spinner mode for the date picker on Android.
   * This value overwrites the `androidDatePickerMode` configuration value.
   *
   * Only available on Android.
   *
   * @since 5.1.0
   */
  androidDatePickerMode?: 'calendar' | 'spinner';
  /**
   * The minute interval of the time picker.
   * This controls the granularity of the minute selector (e.g., 15 for 0, 15, 30, 45).
   * The value must be evenly divisible into 60.
   *
   * Only available on iOS when using time or datetime modes.
   * On Android, this parameter is ignored.
   *
   * @since 7.1.0
   * @default 1
   */
  minuteInterval?: number;
}

export interface PresentResult {
  /**
   * The value entered by the user.
   * The format of this value matches the value of the `format` parameter.
   *
   * @since 0.0.1
   */
  value: string;
}

export enum ErrorCode {
  /**
   * The picker was canceled by the user.
   *
   * @since 0.2.0
   */
  canceled = 'canceled',
  /**
   * The picker was dismissed by the user.
   *
   * @since 0.2.0
   */
  dismissed = 'dismissed',
}
