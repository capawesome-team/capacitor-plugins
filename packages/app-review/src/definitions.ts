export interface AppReviewPlugin {
  /**
   * Open the App Store page for the current app and, if possible, open the dialog to leave a review.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  openAppStore(): Promise<void>;
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
