/// <reference types="@capacitor/cli" />

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    EdgeToEdge?: {
      /**
       * The hexadecimal color to set as the background color of the status bar and navigation bar.
       *
       * @since 7.1.0
       * @deprecated Use `statusBarColor` and `navigationBarColor` instead.
       * @example "#ffffff"
       */
      backgroundColor?: string;
      /**
       * The hexadecimal color to set as the background color of the status bar.
       *
       * @since 7.3.0
       * @example "#ffffff"
       */
      statusBarColor?: string;
      /**
       * The hexadecimal color to set as the background color of the navigation bar.
       *
       * @since 7.3.0
       * @example "#000000"
       */
      navigationBarColor?: string;
    };
  }
}

export interface EdgeToEdgePlugin {
  /**
   * Enable the edge-to-edge mode.
   *
   * Only available on Android.
   *
   * @since 7.2.0
   */
  enable(): Promise<void>;
  /**
   * Disable the edge-to-edge mode.
   *
   * Only available on Android.
   *
   * @since 7.2.0
   */
  disable(): Promise<void>;
  /**
   * Return the insets that are currently applied to the webview.
   *
   * Only available on Android.
   *
   * @since 7.2.0
   */
  getInsets(): Promise<GetInsetsResult>;
  /**
   * Set the background color of the status bar and navigation bar.
   *
   * Only available on Android.
   *
   * @since 7.0.0
   * @deprecated Use `setStatusBarColor` and `setNavigationBarColor` instead.
   */
  setBackgroundColor(options: SetBackgroundColorOptions): Promise<void>;
  /**
   * Set the background color of the status bar.
   *
   * Only available on Android.
   *
   * @since 7.3.0
   */
  setStatusBarColor(options: SetStatusBarColorOptions): Promise<void>;
  /**
   * Set the background color of the navigation bar.
   *
   * Only available on Android.
   *
   * @since 7.3.0
   */
  setNavigationBarColor(options: SetNavigationBarColorOptions): Promise<void>;
}

/**
 * @since 7.2.0
 */
export interface GetInsetsResult {
  /**
   * The bottom inset that was applied to the webview.
   *
   * Only available on Android.
   *
   * @since 7.2.0
   */
  bottom: number;
  /**
   * The left inset that was applied to the webview.
   *
   * Only available on Android.
   *
   * @since 7.2.0
   */
  left: number;
  /**
   * The right inset that was applied to the webview.
   *
   * Only available on Android.
   *
   * @since 7.2.0
   */
  right: number;
  /**
   * The top inset that was applied to the webview.
   *
   * Only available on Android.
   *
   * @since 7.2.0
   */
  top: number;
}

/**
 * @since 7.0.0
 */
export interface SetBackgroundColorOptions {
  /**
   * The hexadecimal color to set as the background color of the status bar and navigation bar.
   *
   * @since 7.0.0
   * @example "#ffffff"
   * @example "#000000"
   */
  color: string;
}

/**
 * @since 7.3.0
 */
export interface SetStatusBarColorOptions {
  /**
   * The hexadecimal color to set as the background color of the status bar.
   *
   * @since 7.3.0
   * @example "#ffffff"
   * @example "#000000"
   */
  color: string;
}

/**
 * @since 7.3.0
 */
export interface SetNavigationBarColorOptions {
  /**
   * The hexadecimal color to set as the background color of the navigation bar.
   *
   * @since 7.3.0
   * @example "#ffffff"
   * @example "#000000"
   */
  color: string;
}
