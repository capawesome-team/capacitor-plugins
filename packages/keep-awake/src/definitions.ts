export interface KeepAwakePlugin {
  /**
   * Allow the screen to dim or turn off again.
   *
   * This reverses the effect of `keepAwake(...)`.
   *
   * Only available on Android, iOS and Web.
   *
   * @since 0.1.0
   */
  allowSleep(): Promise<void>;
  /**
   * Check if keeping the screen awake is available on this device.
   *
   * On Web, this depends on whether the browser supports the
   * [Screen Wake Lock API](https://developer.mozilla.org/en-US/docs/Web/API/Screen_Wake_Lock_API).
   * On Android and iOS, this always returns `true`.
   *
   * Only available on Android, iOS and Web.
   *
   * @since 0.1.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Check if the screen is currently kept awake.
   *
   * Only available on Android, iOS and Web.
   *
   * @since 0.1.0
   */
  isKeptAwake(): Promise<IsKeptAwakeResult>;
  /**
   * Keep the screen awake by preventing it from dimming or turning off.
   *
   * The screen is kept awake until `allowSleep(...)` is called or the app is
   * restarted. This setting only affects your app and is not system-wide.
   *
   * Only available on Android, iOS and Web.
   *
   * @since 0.1.0
   */
  keepAwake(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface IsKeptAwakeResult {
  /**
   * Whether the screen is currently kept awake or not.
   *
   * @since 0.1.0
   */
  keptAwake: boolean;
}

/**
 * @since 0.1.0
 */
export interface IsAvailableResult {
  /**
   * Whether keeping the screen awake is available on this device or not.
   *
   * @since 0.1.0
   */
  available: boolean;
}
