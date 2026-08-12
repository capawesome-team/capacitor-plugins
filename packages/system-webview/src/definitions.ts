export interface SystemWebviewPlugin {
  /**
   * Get information about the active Android System WebView provider.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  getInfo(): Promise<GetInfoResult>;
  /**
   * Check whether the active Android System WebView is older than the minimum
   * Chromium major version required by your app.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  isUpdateRequired(
    options: IsUpdateRequiredOptions,
  ): Promise<IsUpdateRequiredResult>;
  /**
   * Open the Play Store entry of the active Android System WebView provider so
   * the user can update it.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  openAppStore(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface GetInfoResult {
  /**
   * The Chromium major version of the active WebView provider.
   *
   * This is the integer before the first dot of the `versionName`
   * (e.g. `126` for `126.0.6478.122`).
   *
   * `null` if the `versionName` is not a Chromium-style version, which can
   * happen with OEM WebView forks.
   *
   * @since 0.1.0
   * @example 126
   */
  majorVersion: number | null;
  /**
   * The package name of the active WebView provider.
   *
   * @since 0.1.0
   * @example "com.google.android.webview"
   */
  packageName: string;
  /**
   * The version name of the active WebView provider.
   *
   * @since 0.1.0
   * @example "126.0.6478.122"
   */
  versionName: string;
}

/**
 * @since 0.1.0
 */
export interface IsUpdateRequiredOptions {
  /**
   * The minimum Chromium major version your app requires.
   *
   * The active WebView is considered outdated if its `majorVersion` is lower
   * than this value.
   *
   * @since 0.1.0
   * @example 105
   */
  minMajorVersion: number;
}

/**
 * @since 0.1.0
 */
export interface IsUpdateRequiredResult {
  /**
   * `true` if the active WebView is older than the required minimum version,
   * otherwise `false`.
   *
   * @since 0.1.0
   */
  required: boolean;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The Play Store could not be opened.
   *
   * @since 0.1.0
   */
  OpenFailed = 'OPEN_FAILED',
  /**
   * The WebView version name is not a Chromium-style version and could not be
   * parsed into a major version.
   *
   * @since 0.1.0
   */
  VersionUnparseable = 'VERSION_UNPARSEABLE',
  /**
   * The active WebView package could not be determined, which can happen on
   * custom ROMs or when the System WebView is disabled.
   *
   * @since 0.1.0
   */
  WebViewPackageUnavailable = 'WEB_VIEW_PACKAGE_UNAVAILABLE',
}
