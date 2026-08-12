/// <reference types="@capacitor/cli" />

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    NavigationBar?: {
      /**
       * The hexadecimal color to set as the background color of the navigation bar.
       *
       * Use `'transparent'` to make the navigation bar transparent.
       *
       * Only available on Android.
       *
       * @since 8.0.0
       * @example "#ffffff"
       */
      color?: string;
      /**
       * The hexadecimal color to set as the color of the navigation bar divider.
       *
       * Use `'transparent'` to hide the divider.
       *
       * Only available on Android (API level 28+).
       *
       * @since 8.0.0
       * @example "#d9d9d9"
       */
      dividerColor?: string;
      /**
       * The style of the navigation bar buttons.
       *
       * Only available on Android.
       *
       * @since 8.0.0
       * @example "LIGHT"
       */
      style?: 'DARK' | 'LIGHT' | 'DEFAULT';
    };
  }
}

/**
 * @since 8.0.0
 */
export interface NavigationBarPlugin {
  /**
   * Get the current background color of the navigation bar.
   *
   * Only available on Android.
   *
   * @since 8.0.0
   */
  getColor(): Promise<GetColorResult>;
  /**
   * Get the current style of the navigation bar buttons.
   *
   * Only available on Android.
   *
   * @since 8.0.0
   */
  getStyle(): Promise<GetStyleResult>;
  /**
   * Hide the navigation bar.
   *
   * Only available on Android.
   *
   * @since 8.0.0
   */
  hide(): Promise<void>;
  /**
   * Set the background color of the navigation bar.
   *
   * Only available on Android.
   *
   * @since 8.0.0
   */
  setColor(options: SetColorOptions): Promise<void>;
  /**
   * Set the style of the navigation bar buttons.
   *
   * Only available on Android.
   *
   * @since 8.0.0
   */
  setStyle(options: SetStyleOptions): Promise<void>;
  /**
   * Show the navigation bar.
   *
   * Only available on Android.
   *
   * @since 8.0.0
   */
  show(): Promise<void>;
}

/**
 * @since 8.0.0
 */
export interface GetColorResult {
  /**
   * The hexadecimal color of the navigation bar, or `'transparent'` if the navigation bar is fully transparent.
   *
   * @since 8.0.0
   * @example "#ffffff"
   * @example "transparent"
   */
  color: string;
}

/**
 * @since 8.0.0
 */
export interface GetStyleResult {
  /**
   * The style of the navigation bar buttons.
   *
   * @since 8.0.0
   */
  style: Style;
}

/**
 * @since 8.0.0
 */
export interface SetColorOptions {
  /**
   * The hexadecimal color to set as the background color of the navigation bar.
   *
   * Use `'transparent'` to make the navigation bar transparent.
   *
   * @since 8.0.0
   * @example "#ffffff"
   */
  color: string;
  /**
   * The hexadecimal color to set as the color of the navigation bar divider.
   *
   * Use `'transparent'` to hide the divider.
   *
   * Only available on Android (API level 28+).
   *
   * @since 8.0.0
   * @example "#d9d9d9"
   */
  dividerColor?: string;
}

/**
 * @since 8.0.0
 */
export interface SetStyleOptions {
  /**
   * The style of the navigation bar buttons.
   *
   * @since 8.0.0
   */
  style: Style;
}

/**
 * @since 8.0.0
 */
export enum Style {
  /**
   * Light icons on a dark background.
   *
   * @since 8.0.0
   */
  Dark = 'DARK',
  /**
   * Resolved from the current device appearance.
   *
   * @since 8.0.0
   */
  Default = 'DEFAULT',
  /**
   * Dark icons on a light background.
   *
   * @since 8.0.0
   */
  Light = 'LIGHT',
}
