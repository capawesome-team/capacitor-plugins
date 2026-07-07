export interface AppLauncherPlugin {
  /**
   * Check if an app can be opened with the given URL.
   *
   * On **iOS**, every URL scheme you want to check must be declared in the
   * `LSApplicationQueriesSchemes` key of your app's `Info.plist`. Otherwise
   * this method always resolves with `value: false`.
   *
   * On **Android**, every package name or URL scheme you want to check must be
   * declared in the `<queries>` element of your app's `AndroidManifest.xml`.
   * Otherwise this method always resolves with `value: false`.
   *
   * @since 0.1.0
   */
  canOpenUrl(options: CanOpenUrlOptions): Promise<CanOpenUrlResult>;
  /**
   * Open an app with the given URL.
   *
   * @since 0.1.0
   */
  openUrl(options: OpenUrlOptions): Promise<OpenUrlResult>;
}

/**
 * @since 0.1.0
 */
export interface CanOpenUrlOptions {
  /**
   * The URL to check.
   *
   * On **iOS**, this must be a URL scheme (e.g. `mailto:`).
   *
   * On **Android**, this can be a URL scheme (e.g. `mailto:`) or a package name
   * (e.g. `com.google.android.gm`).
   *
   * @since 0.1.0
   * @example 'mailto:'
   */
  url: string;
}

/**
 * @since 0.1.0
 */
export interface CanOpenUrlResult {
  /**
   * Whether or not the app can be opened with the given URL.
   *
   * On the web, this is always `true` because the browser cannot determine
   * whether a URL can be opened.
   *
   * @since 0.1.0
   * @example true
   */
  value: boolean;
}

/**
 * @since 0.1.0
 */
export interface OpenUrlOptions {
  /**
   * The URL to open.
   *
   * On **iOS**, this must be a URL scheme (e.g. `mailto:`).
   *
   * On **Android**, this can be a URL scheme (e.g. `mailto:`) or a package name
   * (e.g. `com.google.android.gm`).
   *
   * @since 0.1.0
   * @example 'mailto:'
   */
  url: string;
}

/**
 * @since 0.1.0
 */
export interface OpenUrlResult {
  /**
   * Whether or not the app was opened successfully.
   *
   * @since 0.1.0
   * @example true
   */
  completed: boolean;
}
