export interface OptionPickerPlugin {
  /**
   * Present a native picker that lets the user select one option from a list.
   *
   * The returned promise is rejected with the `ErrorCode.Canceled` error code
   * if the user cancels or dismisses the picker.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  present(options: PresentOptions): Promise<PresentResult>;
}

/**
 * @since 0.1.0
 */
export interface PresentOptions {
  /**
   * The text of the cancel button.
   *
   * @since 0.1.0
   * @default 'Cancel'
   * @example 'Cancel'
   */
  cancelButtonText?: string;
  /**
   * The text of the done button.
   *
   * @since 0.1.0
   * @default 'Ok'
   * @example 'Done'
   */
  doneButtonText?: string;
  /**
   * The options the user can choose from.
   *
   * Must contain at least one option.
   *
   * @since 0.1.0
   */
  options: PickerOption[];
  /**
   * The theme of the picker.
   *
   * `auto` follows the system appearance.
   *
   * @since 0.1.0
   * @default 'auto'
   * @example 'dark'
   */
  theme?: 'auto' | 'light' | 'dark';
  /**
   * The title of the picker.
   *
   * @since 0.1.0
   * @example 'Select a country'
   */
  title?: string;
  /**
   * The value of the option that is selected when the picker opens.
   *
   * If no option has this value, the first option is selected.
   *
   * @since 0.1.0
   * @example 'de'
   */
  value?: string;
}

/**
 * @since 0.1.0
 */
export interface PickerOption {
  /**
   * The text displayed for the option.
   *
   * @since 0.1.0
   * @example 'Germany'
   */
  label: string;
  /**
   * The value returned when the option is selected.
   *
   * @since 0.1.0
   * @example 'de'
   */
  value: string;
}

/**
 * @since 0.1.0
 */
export interface PresentResult {
  /**
   * The value of the selected option.
   *
   * @since 0.1.0
   * @example 'de'
   */
  value: string;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The user canceled or dismissed the picker.
   *
   * @since 0.1.0
   */
  Canceled = 'CANCELED',
}
