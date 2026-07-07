export interface ToastPlugin {
  /**
   * Show a toast with a short text message.
   *
   * @since 0.1.0
   */
  show(options: ShowOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface ShowOptions {
  /**
   * The duration of the toast.
   *
   * @default ToastDuration.Short
   * @example ToastDuration.Long
   * @since 0.1.0
   */
  duration?: ToastDuration;
  /**
   * The position of the toast on the screen.
   *
   * On Android 12 and newer, the position is ignored and the toast is always
   * shown at the bottom of the screen (a system restriction).
   *
   * @default ToastPosition.Bottom
   * @example ToastPosition.Top
   * @since 0.1.0
   */
  position?: ToastPosition;
  /**
   * The text to show in the toast.
   *
   * @example 'Hello World!'
   * @since 0.1.0
   */
  text: string;
}

/**
 * The duration of a toast.
 *
 * @since 0.1.0
 */
export enum ToastDuration {
  /**
   * Show the toast for a longer period of time (about 3500 ms).
   *
   * @since 0.1.0
   */
  Long = 'LONG',
  /**
   * Show the toast for a shorter period of time (about 2000 ms).
   *
   * @since 0.1.0
   */
  Short = 'SHORT',
}

/**
 * The position of a toast on the screen.
 *
 * @since 0.1.0
 */
export enum ToastPosition {
  /**
   * Show the toast at the bottom of the screen.
   *
   * @since 0.1.0
   */
  Bottom = 'BOTTOM',
  /**
   * Show the toast in the center of the screen.
   *
   * @since 0.1.0
   */
  Center = 'CENTER',
  /**
   * Show the toast at the top of the screen.
   *
   * @since 0.1.0
   */
  Top = 'TOP',
}
