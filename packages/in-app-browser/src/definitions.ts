import type { PluginListenerHandle } from '@capacitor/core';

export interface InAppBrowserPlugin {
  /**
   * Clear the cache of the web view.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  clearCache(): Promise<void>;
  /**
   * Clear the session data (cookies and web storage) of the web view.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  clearSessionData(): Promise<void>;
  /**
   * Close the currently open browser.
   *
   * This closes browsers opened with `openInWebView(...)` or
   * `openInSystemBrowser(...)`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  close(): Promise<void>;
  /**
   * Execute a JavaScript script in the currently open web view.
   *
   * This method is only available for browsers opened with
   * `openInWebView(...)`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  executeScript(options: ExecuteScriptOptions): Promise<ExecuteScriptResult>;
  /**
   * Get the cookies for a specific URL.
   *
   * On iOS, only cookies from the shared data store are returned. Cookies from
   * a web view opened with `dataStore: 'isolated'` are not included.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  getCookies(options: GetCookiesOptions): Promise<GetCookiesResult>;
  /**
   * Open a URL in the default browser app of the device.
   *
   * @since 0.1.0
   */
  openInExternalBrowser(options: OpenInExternalBrowserOptions): Promise<void>;
  /**
   * Open a URL in the system browser (Custom Tabs on Android,
   * `SFSafariViewController` on iOS).
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  openInSystemBrowser(options: OpenInSystemBrowserOptions): Promise<void>;
  /**
   * Open a URL in an embedded web view with a native toolbar.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  openInWebView(options: OpenInWebViewOptions): Promise<void>;
  /**
   * Post a message to the currently open web view.
   *
   * The web page receives the message by listening for the
   * `capacitorInAppBrowserMessage` window event. The message data is
   * available in the `detail` property of the event.
   *
   * This method is only available for browsers opened with
   * `openInWebView(...)`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  postMessage(options: PostMessageOptions): Promise<void>;
  /**
   * Called when the browser is closed.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'browserClosed',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the initial page of the browser has finished loading.
   *
   * On Android, this event is only emitted for browsers opened with
   * `openInWebView(...)`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'browserPageLoaded',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when a page navigation has been completed in the web view.
   *
   * This event is only emitted for browsers opened with `openInWebView(...)`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'browserPageNavigationCompleted',
    listenerFunc: (event: BrowserPageNavigationCompletedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Called when the web page posts a message to the app using the injected
   * `window.CapacitorInAppBrowser.postMessage(...)` function.
   *
   * This event is only emitted for browsers opened with `openInWebView(...)`.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'messageReceived',
    listenerFunc: (event: MessageReceivedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.1.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface OpenInExternalBrowserOptions {
  /**
   * The URL to open in the external browser.
   *
   * @example 'https://capawesome.io'
   * @since 0.1.0
   */
  url: string;
}

/**
 * @since 0.1.0
 */
export interface OpenInSystemBrowserOptions {
  /**
   * Options that are only applied on Android.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  android?: OpenInSystemBrowserAndroidOptions;
  /**
   * Options that are only applied on iOS.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  ios?: OpenInSystemBrowserIosOptions;
  /**
   * The URL to open in the system browser.
   *
   * @example 'https://capawesome.io'
   * @since 0.1.0
   */
  url: string;
}

/**
 * @since 0.1.0
 */
export interface OpenInSystemBrowserAndroidOptions {
  /**
   * Whether or not the toolbar should be hidden when the user scrolls down
   * and shown when the user scrolls up.
   *
   * @default false
   * @since 0.1.0
   */
  hideToolbarOnScroll?: boolean;
  /**
   * Whether or not the title of the web page should be shown in the toolbar.
   *
   * @default false
   * @since 0.1.0
   */
  showTitle?: boolean;
  /**
   * The background color of the toolbar as a hex color code.
   *
   * @example '#008080'
   * @since 0.1.0
   */
  toolbarColor?: string;
}

/**
 * @since 0.1.0
 */
export interface OpenInSystemBrowserIosOptions {
  /**
   * Whether or not the toolbar should collapse when the user scrolls down.
   *
   * @default true
   * @since 0.1.0
   */
  barCollapsing?: boolean;
  /**
   * The style of the dismiss button in the toolbar.
   *
   * @default 'done'
   * @since 0.1.0
   */
  dismissButtonStyle?: DismissButtonStyle;
  /**
   * Whether or not the reader mode should be entered if it is available for
   * the web page.
   *
   * @default false
   * @since 0.1.0
   */
  readerMode?: boolean;
  /**
   * The background color of the toolbar as a hex color code.
   *
   * @example '#008080'
   * @since 0.1.0
   */
  toolbarColor?: string;
}

/**
 * @since 0.1.0
 */
export interface OpenInWebViewOptions {
  /**
   * Options that are only applied on Android.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  android?: OpenInWebViewAndroidOptions;
  /**
   * The data store to use for the web view.
   *
   * On Android, this option is ignored. The web view always uses the
   * app-global (`shared`) data store.
   *
   * Only available on iOS.
   *
   * @default 'shared'
   * @since 0.1.0
   */
  dataStore?: WebViewDataStore;
  /**
   * Additional HTTP headers to send with the initial request.
   *
   * @since 0.1.0
   */
  headers?: { [key: string]: string };
  /**
   * Options that are only applied on iOS.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  ios?: OpenInWebViewIosOptions;
  /**
   * Whether or not media playback requires user action.
   *
   * @default false
   * @since 0.1.0
   */
  mediaPlaybackRequiresUserAction?: boolean;
  /**
   * Options for the toolbar of the web view.
   *
   * @since 0.1.0
   */
  toolbar?: WebViewToolbarOptions;
  /**
   * The URL to open in the web view.
   *
   * @example 'https://capawesome.io'
   * @since 0.1.0
   */
  url: string;
  /**
   * The custom user agent to use for the web view.
   *
   * @since 0.1.0
   */
  userAgent?: string;
}

/**
 * @since 0.1.0
 */
export interface WebViewToolbarOptions {
  /**
   * The background color of the toolbar as a hex color code.
   *
   * @example '#008080'
   * @since 0.1.0
   */
  backgroundColor?: string;
  /**
   * The text of the close button in the toolbar.
   *
   * @default 'Close'
   * @since 0.1.0
   */
  closeButtonText?: string;
  /**
   * The text color of the toolbar as a hex color code.
   *
   * @example '#FFFFFF'
   * @since 0.1.0
   */
  color?: string;
  /**
   * Whether or not the back and forward navigation buttons should be shown
   * in the toolbar.
   *
   * @default false
   * @since 0.1.0
   */
  showNavigationButtons?: boolean;
  /**
   * Whether or not the current URL should be displayed in the toolbar
   * instead of the title.
   *
   * @default false
   * @since 0.1.0
   */
  showUrl?: boolean;
  /**
   * The fixed title to display in the toolbar.
   *
   * If not set, the title of the current web page is displayed.
   *
   * @since 0.1.0
   */
  title?: string;
  /**
   * Whether or not the toolbar should be visible.
   *
   * @default true
   * @since 0.1.0
   */
  visible?: boolean;
}

/**
 * @since 0.1.0
 */
export interface OpenInWebViewAndroidOptions {
  /**
   * Whether or not the user can zoom the web page.
   *
   * @default false
   * @since 0.1.0
   */
  allowZoom?: boolean;
  /**
   * Whether or not the hardware back button navigates back in the web view's
   * history before closing the web view.
   *
   * If `false`, the hardware back button closes the web view immediately.
   *
   * @default true
   * @since 0.1.0
   */
  hardwareBackButton?: boolean;
  /**
   * Whether or not media playback is paused when the app is hidden.
   *
   * @default true
   * @since 0.1.0
   */
  pauseMediaWhenHidden?: boolean;
}

/**
 * @since 0.1.0
 */
export interface OpenInWebViewIosOptions {
  /**
   * Whether or not horizontal swipe gestures navigate back and forward in
   * the web view's history.
   *
   * @default false
   * @since 0.1.0
   */
  allowsBackForwardNavigationGestures?: boolean;
  /**
   * Whether or not the web view bounces when scrolled past the edge of the
   * content.
   *
   * @default true
   * @since 0.1.0
   */
  overscroll?: boolean;
}

/**
 * @since 0.1.0
 */
export interface ExecuteScriptOptions {
  /**
   * The JavaScript code to execute in the web view.
   *
   * @example 'document.title'
   * @since 0.1.0
   */
  script: string;
}

/**
 * @since 0.1.0
 */
export interface ExecuteScriptResult {
  /**
   * The result of the script execution serialized as a JSON string.
   *
   * If the script does not return a JSON-serializable value, `null` is
   * returned.
   *
   * @example '"Capawesome"'
   * @since 0.1.0
   */
  result: string | null;
}

/**
 * @since 0.1.0
 */
export interface PostMessageOptions {
  /**
   * The message data to post to the web view.
   *
   * Must be a JSON-serializable object.
   *
   * @example { name: 'Capawesome' }
   * @since 0.1.0
   */
  data: { [key: string]: unknown };
}

/**
 * @since 0.1.0
 */
export interface GetCookiesOptions {
  /**
   * The URL to get the cookies for.
   *
   * @example 'https://capawesome.io'
   * @since 0.1.0
   */
  url: string;
}

/**
 * @since 0.1.0
 */
export interface GetCookiesResult {
  /**
   * The cookies for the URL as a map of cookie names to values.
   *
   * @since 0.1.0
   */
  cookies: { [key: string]: string };
}

/**
 * @since 0.1.0
 */
export interface BrowserPageNavigationCompletedEvent {
  /**
   * The URL of the page that was navigated to.
   *
   * @example 'https://capawesome.io'
   * @since 0.1.0
   */
  url: string;
}

/**
 * @since 0.1.0
 */
export interface MessageReceivedEvent {
  /**
   * The message data posted by the web page.
   *
   * @since 0.1.0
   */
  data: unknown;
}

/**
 * The style of the dismiss button in the toolbar of the system browser.
 *
 * - `cancel`: A button with the text "Cancel".
 * - `close`: A button with the text "Close".
 * - `done`: A button with the text "Done".
 *
 * @since 0.1.0
 */
export type DismissButtonStyle = 'cancel' | 'close' | 'done';

/**
 * The data store to use for the web view.
 *
 * - `isolated`: The web view uses a non-persistent data store. Cookies and
 *   web storage are discarded when the web view is closed.
 * - `shared`: The web view uses the app-global data store which is shared
 *   with other web views.
 *
 * @since 0.1.0
 */
export type WebViewDataStore = 'isolated' | 'shared';

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * No app was found on the device that can open the URL.
   *
   * @since 0.1.0
   */
  BrowserNotFound = 'BROWSER_NOT_FOUND',
  /**
   * No browser is currently open.
   *
   * @since 0.1.0
   */
  NoBrowserOpen = 'NO_BROWSER_OPEN',
}
