export interface ActionSheetPlugin {
  /**
   * Show an action sheet with a list of buttons.
   *
   * If the user selects a button with the `ActionSheetButtonStyle.Cancel` style
   * or dismisses the action sheet, the promise is rejected with the `CANCELED`
   * error code.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  showActions(options: ShowActionsOptions): Promise<ShowActionsResult>;
}

/**
 * @since 0.1.0
 */
export interface ShowActionsOptions {
  /**
   * Whether the action sheet can be dismissed by tapping outside of it or
   * pressing the back button (Android).
   *
   * On iOS, the action sheet can always be dismissed by tapping outside of it
   * (a system behavior).
   *
   * @since 0.1.0
   * @default true
   * @example true
   */
  cancelable?: boolean;
  /**
   * The message to display below the title.
   *
   * @since 0.1.0
   * @example 'This action cannot be undone.'
   */
  message?: string;
  /**
   * The buttons to display in the action sheet.
   *
   * @since 0.1.0
   */
  options: ActionSheetButton[];
  /**
   * The title of the action sheet.
   *
   * @since 0.1.0
   * @example 'Photo Options'
   */
  title?: string;
}

/**
 * @since 0.1.0
 */
export interface ActionSheetButton {
  /**
   * The style of the button.
   *
   * @since 0.1.0
   * @default ActionSheetButtonStyle.Default
   * @example ActionSheetButtonStyle.Destructive
   */
  style?: ActionSheetButtonStyle;
  /**
   * The title of the button.
   *
   * @since 0.1.0
   * @example 'Delete'
   */
  title: string;
}

/**
 * @since 0.1.0
 */
export interface ShowActionsResult {
  /**
   * The index of the selected button in the `options` array (zero-based).
   *
   * @since 0.1.0
   * @example 0
   */
  index: number;
}

/**
 * The style of an action sheet button.
 *
 * @since 0.1.0
 */
export enum ActionSheetButtonStyle {
  /**
   * A button that cancels the action sheet.
   *
   * It should be the last button in the `options` array.
   *
   * @since 0.1.0
   */
  Cancel = 'CANCEL',
  /**
   * A button with the default style.
   *
   * @since 0.1.0
   */
  Default = 'DEFAULT',
  /**
   * A button that indicates a destructive action.
   *
   * @since 0.1.0
   */
  Destructive = 'DESTRUCTIVE',
}

/**
 * @since 0.2.0
 */
export enum ErrorCode {
  /**
   * The user canceled the action sheet by selecting a cancel button or by
   * dismissing the action sheet.
   *
   * @since 0.2.0
   */
  Canceled = 'CANCELED',
}
