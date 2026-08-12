export interface InstallReferrerPlugin {
  /**
   * Get the Apple Ad Services attribution token.
   *
   * The token is opaque and only the client half of the attribution flow. It
   * must be exchanged server-side with Apple's Ad Services attribution API
   * (`https://api-adservices.apple.com/api/v1/`) and expires after 24 hours.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  getAttributionToken(): Promise<GetAttributionTokenResult>;
  /**
   * Get the Play Store install referrer information.
   *
   * The referrer should be fetched once shortly after the first launch and
   * persisted by your app. Google only guarantees availability for a limited
   * window after the install.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  getInstallReferrer(): Promise<GetInstallReferrerResult>;
}

/**
 * @since 0.1.0
 */
export interface GetInstallReferrerResult {
  /**
   * Whether the app was installed via a Google Play Instant experience within
   * the past 7 days.
   *
   * @example false
   * @since 0.1.0
   */
  googlePlayInstantParam: boolean;
  /**
   * The client-side timestamp (in milliseconds) when the app installation
   * began.
   *
   * @example 1623161753000
   * @since 0.1.0
   */
  installBeginTimestampMillis: number;
  /**
   * The client-side timestamp (in milliseconds) when the referrer click
   * happened.
   *
   * @example 1623161752000
   * @since 0.1.0
   */
  referrerClickTimestampMillis: number;
  /**
   * The referrer URL of the installed package.
   *
   * @example 'utm_source=google-play&utm_medium=organic'
   * @since 0.1.0
   */
  referrerUrl: string;
}

/**
 * @since 0.1.0
 */
export interface GetAttributionTokenResult {
  /**
   * The Apple Ad Services attribution token.
   *
   * @since 0.1.0
   */
  token: string;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The request could not be completed because of a developer error.
   *
   * @since 0.1.0
   */
  DeveloperError = 'DEVELOPER_ERROR',
  /**
   * The Play Store install referrer API is not supported by the installed
   * Play Store app.
   *
   * @since 0.1.0
   */
  FeatureNotSupported = 'FEATURE_NOT_SUPPORTED',
  /**
   * The Play Store install referrer service is currently unavailable, for
   * example because the Play Store app is missing or outdated. The request can
   * be retried later.
   *
   * @since 0.1.0
   */
  ServiceUnavailable = 'SERVICE_UNAVAILABLE',
  /**
   * The Apple Ad Services attribution token could not be generated.
   *
   * @since 0.1.0
   */
  TokenGenerationFailed = 'TOKEN_GENERATION_FAILED',
}
