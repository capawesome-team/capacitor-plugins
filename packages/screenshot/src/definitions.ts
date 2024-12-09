export interface ScreenshotPlugin {
  /**
   * Take a screenshot.
   * 
   * @since 6.0.0
   */
  take(): Promise<TakeResult>;
}

/**
 * @since 6.0.0
 */
export interface TakeResult {
  /**
   * The file path (Android and iOS) or data URI (Web) of the screenshot.
   * 
   * Only available on Android, iOS and Web.
   * 
   * @since 6.0.0
   * @example 'content://com.android.providers.downloads.documents/document/msf%3A1000000073'
   * @example 'data:image/jpeg;base64,SGVsbG8sIFdvc...mxkIQ=='
   */
  uri: string;
}
