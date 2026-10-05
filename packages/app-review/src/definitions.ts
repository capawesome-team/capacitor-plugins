export interface AppReviewPlugin {
  /**
   * Open the App Store page for the current app and, if possible, open the dialog to leave a review.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  openAppStore(options?: OpenAppStoreOptions): Promise<void>;
  /**
   * Request an in-app review.
   *
   * **Attention**: On iOS, review requests are limited to 3 requests per year.
   *
   * Only available on Android and iOS (14+).
   *
   * @since 6.0.0
   */
  requestReview(): Promise<void>;
}

/**
 * @since 6.0.1
 */
export interface OpenAppStoreOptions {
  /**
   * The package name of the store app that should open the app store entry
   * (e.g. `com.android.vending` for the Google Play Store).
   *
   * If not provided, the system's default handler for `market://` links will be used.
   *
   * Only available on Android.
   *
   * @since 8.1.0
   * @example "com.android.vending"
   */
  androidStorePackageName?: string;
  /**
   * The app ID of the app to open in the App Store.
   *
   * On **iOS**, this is the Apple ID of your app (e.g. `123456789`).
   * You can find the ID in the URL of your app store entry
   * (e.g. `https://apps.apple.com/app/id123456789`).
   *
   * **Attention**: This option is required on iOS.
   *
   * Only available on iOS.
   *
   * @since 6.0.1
   * @example "123456789"
   */
  appId?: string;
}
