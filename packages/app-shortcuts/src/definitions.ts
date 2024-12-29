import type { PluginListenerHandle } from '@capacitor/core';

export interface AppShortcutsPlugin {
  /**
   * Remove all app shortcuts.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  clear(): Promise<void>;
  /**
   * Get all app shortcuts.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  get(): Promise<GetResult>;
  /**
   * Create or update app shortcuts.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  set(options: SetOptions): Promise<void>;
  /**
   * Called when an app shortcut is clicked.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  addListener(
    eventName: 'click',
    listenerFunc: (event: ClickEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 6.0.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 6.0.0
 */
export interface GetResult {
  /**
   * The list of app shortcuts.
   *
   * @since 6.0.0
   */
  shortcuts: Shortcut[];
}

/**
 * @since 6.0.0
 */
export interface SetOptions {
  /**
   * The list of app shortcuts.
   *
   * @since 6.0.0
   */
  shortcuts: Shortcut[];
}

/**
 * @since 6.0.0
 */
export interface Shortcut {
  /**
   * The description.
   *
   * @since 6.0.0
   */
  description?: string;
  /**
   * The unique identifier.
   *
   * @since 6.0.0
   */
  id: string;
  /**
   * The display name.
   *
   * @since 6.0.0
   */
  title: string;
  /**
   * The icon to display.
   * On Android use the constant value of the icons.
   * Available Android icons: https://developer.android.com/reference/android/R.drawable
   *
   * On iOS, the icon and description can only be used together and the icon is the raw representation of the
   * UIApplicationShortcutIcon.IconType enum.
   *
   *
   * @since 6.1.0
   * @example
   * Android: 17301547 (R.drawable.ic_input_add)
   * iOS: 6 (.share)
   */
  icon?: number;
}

/**
 * @since 6.0.0
 */
export interface ClickEvent {
  /**
   * The unique identifier of the app shortcut that was clicked.
   *
   * @since 6.0.0
   */
  shortcutId: string;
}
