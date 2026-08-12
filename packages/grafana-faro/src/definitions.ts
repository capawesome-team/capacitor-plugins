/// <reference types="@capacitor/cli" />

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    /**
     * Build-time configuration for the Grafana Faro plugin.
     *
     * When `url` and `appName` are both provided, the native plugin
     * auto-initializes during app startup. This allows native crash and ANR
     * handlers to be installed before any JavaScript code runs. Calling
     * `GrafanaFaro.initialize(...)` from JavaScript afterwards is not required
     * and will fail with an `already initialized` error.
     *
     * Only applies to Android and iOS.
     *
     * @since 0.1.0
     */
    GrafanaFaro?: {
      /**
       * The API key sent as the `x-api-key` header.
       *
       * @since 0.1.0
       */
      apiKey?: string;
      /**
       * The environment of the application (e.g. `production`, `staging`).
       *
       * @since 0.1.0
       */
      appEnvironment?: string;
      /**
       * The name of the application.
       *
       * @since 0.1.0
       */
      appName?: string;
      /**
       * The namespace of the application.
       *
       * @since 0.1.0
       */
      appNamespace?: string;
      /**
       * The version of the application.
       *
       * @since 0.1.0
       */
      appVersion?: string;
      /**
       * Toggles for built-in automatic instrumentations.
       *
       * Only the toggles that apply at build time on Android and iOS are honored.
       *
       * @since 0.1.0
       */
      instrumentations?: {
        /**
         * Whether to detect Application Not Responding events on the main thread.
         *
         * Only available for Android.
         *
         * @default false
         * @since 0.1.0
         */
        anrTracking?: boolean;
        /**
         * Whether to capture native crashes (reported on the next session).
         *
         * Only available for Android and iOS.
         *
         * @default false
         * @since 0.1.0
         */
        nativeCrashReporting?: boolean;
      };
      /**
       * The Faro collector endpoint URL.
       *
       * @since 0.1.0
       * @example 'https://faro-collector-prod.grafana.net/collect/<token>'
       */
      url?: string;
    };
  }
}

export interface GrafanaFaroPlugin {
  /**
   * Get the current session.
   *
   * @since 0.1.0
   */
  getSession(): Promise<GetSessionResult>;
  /**
   * Get the current view.
   *
   * @since 0.1.0
   */
  getView(): Promise<GetViewResult>;
  /**
   * Initialize the Faro SDK.
   *
   * **Attention**: This method must be called before any other method.
   *
   * @since 0.1.0
   */
  initialize(options: InitializeOptions): Promise<void>;
  /**
   * Pause sending telemetry. New signals are still buffered locally.
   *
   * @since 0.1.0
   */
  pause(): Promise<void>;
  /**
   * Push an error/exception.
   *
   * @since 0.1.0
   */
  pushError(options: PushErrorOptions): Promise<void>;
  /**
   * Push a custom event.
   *
   * @since 0.1.0
   */
  pushEvent(options: PushEventOptions): Promise<void>;
  /**
   * Push a log message.
   *
   * @since 0.1.0
   */
  pushLog(options: PushLogOptions): Promise<void>;
  /**
   * Push a measurement (numeric metric).
   *
   * @since 0.1.0
   */
  pushMeasurement(options: PushMeasurementOptions): Promise<void>;
  /**
   * Clear the current session and start a new one.
   *
   * @since 0.1.0
   */
  resetSession(): Promise<void>;
  /**
   * Clear the current user.
   *
   * @since 0.1.0
   */
  resetUser(): Promise<void>;
  /**
   * Set the current session.
   *
   * @since 0.1.0
   */
  setSession(options: SetSessionOptions): Promise<void>;
  /**
   * Set the current user.
   *
   * @since 0.1.0
   */
  setUser(options: SetUserOptions): Promise<void>;
  /**
   * Set the current view (e.g. screen / route name).
   *
   * @since 0.1.0
   */
  setView(options: SetViewOptions): Promise<void>;
  /**
   * Resume sending telemetry.
   *
   * @since 0.1.0
   */
  unpause(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface AppMetadata {
  /**
   * The environment of the application (e.g. `production`, `staging`).
   *
   * @since 0.1.0
   */
  environment?: string;
  /**
   * The name of the application.
   *
   * @since 0.1.0
   */
  name: string;
  /**
   * The namespace of the application.
   *
   * @since 0.1.0
   */
  namespace?: string;
  /**
   * The version of the application.
   *
   * @since 0.1.0
   */
  version?: string;
}

/**
 * @since 0.1.0
 */
export interface GetSessionResult {
  /**
   * The current session attributes.
   *
   * @since 0.1.0
   */
  attributes?: { [key: string]: string };
  /**
   * The current session ID.
   *
   * @since 0.1.0
   */
  id?: string;
}

/**
 * @since 0.1.0
 */
export interface GetViewResult {
  /**
   * The current view name.
   *
   * @since 0.1.0
   */
  name?: string;
}

/**
 * @since 0.1.0
 */
export interface InitializeOptions {
  /**
   * API key sent as the `x-api-key` header to the collector.
   *
   * @since 0.1.0
   */
  apiKey?: string;
  /**
   * Application metadata attached to every signal.
   *
   * @since 0.1.0
   */
  app: AppMetadata;
  /**
   * Error message patterns to ignore.
   *
   * @since 0.1.0
   */
  ignoreErrors?: string[];
  /**
   * URL patterns to ignore when instrumenting network calls.
   *
   * @since 0.1.0
   */
  ignoreUrls?: string[];
  /**
   * Toggles for built-in automatic instrumentations.
   *
   * @since 0.1.0
   */
  instrumentations?: InstrumentationsOptions;
  /**
   * Start in a paused state. Telemetry must be resumed via `unpause()`.
   *
   * @default false
   * @since 0.1.0
   */
  paused?: boolean;
  /**
   * Initial session attributes.
   *
   * @since 0.1.0
   */
  sessionAttributes?: { [key: string]: string };
  /**
   * Session sampling rate in `[0, 1]`. `1` = all sessions, `0` = none.
   *
   * @default 1
   * @since 0.1.0
   */
  sessionSamplingRate?: number;
  /**
   * The Faro collector endpoint URL.
   *
   * @since 0.1.0
   * @example 'https://faro-collector-prod.grafana.net/collect/<token>'
   */
  url: string;
  /**
   * Initial user metadata.
   *
   * @since 0.1.0
   */
  user?: UserMetadata;
  /**
   * Initial view metadata.
   *
   * @since 0.1.0
   */
  view?: ViewMetadata;
}

/**
 * Toggles for built-in automatic instrumentations.
 *
 * Note: some toggles only apply on specific platforms.
 * The plugin silently ignores toggles that do not apply on the current platform.
 *
 * @since 0.1.0
 */
export interface InstrumentationsOptions {
  /**
   * Detect Application Not Responding events on the main thread.
   *
   * Only available for Android.
   *
   * @default false
   * @since 0.1.0
   */
  anrTracking?: boolean;
  /**
   * Capture `console.warn` and `console.error` calls.
   *
   * Only available for Web.
   *
   * @default true
   * @since 0.1.0
   */
  console?: boolean;
  /**
   * Capture uncaught errors and unhandled promise rejections.
   *
   * Only available for Web.
   *
   * @default true
   * @since 0.1.0
   */
  errors?: boolean;
  /**
   * Capture native crashes (reported on the next session).
   *
   * Only available for Android and iOS.
   *
   * @default false
   * @since 0.1.0
   */
  nativeCrashReporting?: boolean;
  /**
   * Capture `fetch` and `XHR` performance entries.
   *
   * Only available for Web.
   *
   * @default true
   * @since 0.1.0
   */
  performance?: boolean;
  /**
   * Capture History API navigation as view changes.
   *
   * Only available for Web.
   *
   * @default true
   * @since 0.1.0
   */
  view?: boolean;
  /**
   * Capture Core Web Vitals (LCP, FID, CLS, INP, TTFB).
   *
   * Only available for Web.
   *
   * @default true
   * @since 0.1.0
   */
  webVitals?: boolean;
}

/**
 * @since 0.1.0
 */
export type LogLevel = 'debug' | 'error' | 'info' | 'log' | 'trace' | 'warn';

/**
 * @since 0.1.0
 */
export interface PushErrorOptions {
  /**
   * Additional context attached to the error.
   *
   * @since 0.1.0
   */
  context?: { [key: string]: string };
  /**
   * Mark the error as fatal (e.g. caused a crash).
   *
   * @default false
   * @since 0.1.0
   */
  fatal?: boolean;
  /**
   * Pre-parsed stack frames generated by `stacktrace.js`.
   *
   * @since 0.1.0
   */
  stackFrames?: StackFrame[];
  /**
   * The error type (e.g. `'TypeError'`).
   *
   * @since 0.1.0
   */
  type: string;
  /**
   * The error message.
   *
   * @since 0.1.0
   */
  value: string;
}

/**
 * @since 0.1.0
 */
export interface PushEventOptions {
  /**
   * Key-value attributes attached to the event.
   *
   * @since 0.1.0
   */
  attributes?: { [key: string]: string };
  /**
   * Logical grouping for the event.
   * Defaults to the platform domain (`'web'`, `'android'`, `'ios'`).
   *
   * @since 0.1.0
   */
  domain?: string;
  /**
   * The name of the event.
   *
   * @since 0.1.0
   */
  name: string;
}

/**
 * @since 0.1.0
 */
export interface PushLogOptions {
  /**
   * Additional context attached to the log.
   *
   * @since 0.1.0
   */
  context?: { [key: string]: string };
  /**
   * The log level.
   *
   * @default 'info'
   * @since 0.1.0
   */
  level?: LogLevel;
  /**
   * The log message.
   *
   * @since 0.1.0
   */
  message: string;
}

/**
 * @since 0.1.0
 */
export interface PushMeasurementOptions {
  /**
   * Additional context attached to the measurement.
   *
   * @since 0.1.0
   */
  context?: { [key: string]: string };
  /**
   * The measurement type (e.g. `'custom_latency'`).
   *
   * @since 0.1.0
   */
  type: string;
  /**
   * Key-value numeric measurements.
   *
   * @since 0.1.0
   */
  values: { [key: string]: number };
}

/**
 * @since 0.1.0
 */
export interface SetSessionOptions {
  /**
   * The session attributes.
   *
   * @since 0.1.0
   */
  attributes?: { [key: string]: string };
  /**
   * Optional session ID. A UUID v4 is generated when omitted.
   *
   * @since 0.1.0
   */
  id?: string;
}

/**
 * @since 0.1.0
 */
export type SetUserOptions = UserMetadata;

/**
 * @since 0.1.0
 */
export type SetViewOptions = ViewMetadata;

/**
 * Subset of the stacktrace generated by `stacktrace.js`.
 *
 * @since 0.1.0
 */
export interface StackFrame {
  /**
   * @since 0.1.0
   */
  columnNumber?: number;
  /**
   * @since 0.1.0
   */
  fileName?: string;
  /**
   * @since 0.1.0
   */
  functionName?: string;
  /**
   * @since 0.1.0
   */
  lineNumber?: number;
}

/**
 * @since 0.1.0
 */
export interface UserMetadata {
  /**
   * Additional key-value attributes for the user.
   *
   * @since 0.1.0
   */
  attributes?: { [key: string]: string };
  /**
   * The user's email address.
   *
   * @since 0.1.0
   */
  email?: string;
  /**
   * The user's full name.
   *
   * @since 0.1.0
   */
  fullName?: string;
  /**
   * The user's ID.
   *
   * @since 0.1.0
   */
  id?: string;
  /**
   * The user's username.
   *
   * @since 0.1.0
   */
  username?: string;
}

/**
 * @since 0.1.0
 */
export interface ViewMetadata {
  /**
   * The name of the view.
   *
   * @since 0.1.0
   */
  name: string;
}
