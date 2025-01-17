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
   * The app ID of the app to open in the App Store.
   *
   * On **iOS**, this is the Apple ID of your app (e.g. `123456789`).
   *
   * Only available on iOS.
   *
   * @since 6.0.1
   * @example 123456789
   */
  appId: string;
}
