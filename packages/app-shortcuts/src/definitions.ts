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
    eventName: 'onAppShortcut',
    listenerFunc: (event: OnAppShortcutEvent) => void,
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
   * Th list of app shortcuts.
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
   * Only available on Android.
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
}

/**
 * @since 6.0.0
 */
export interface OnAppShortcutEvent {
  /**
   * The unique identifier of the app shortcut that was clicked.
   *
   * @since 6.0.0
   */
  id: string;
}
