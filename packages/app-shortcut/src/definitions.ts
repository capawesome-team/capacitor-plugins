import type { PluginListenerHandle } from '@capacitor/core';

export interface AppShortcutPlugin {
  /**
   * Clear the list of app shortcuts.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  clear(): Promise<void>;
  /**
   * Get the list of app shortcuts.
   *
   * Only available on Android and iOS.
   *
   * @since 6.0.0
   */
  get(): Promise<GetResult>;
  /**
   * Create or update the list of app shortcuts.
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
   * @since 6.0.0
   */
  shortcuts: Shortcut[];
}

/**
 * @since 6.0.0
 */
export interface SetOptions {
  /**
   * @since 6.0.0
   */
  shortcuts: Shortcut[];
}

/**
 * @since 6.0.0
 */
export interface Shortcut {
  /**
   * Only supported on Android
   *
   * @since 6.0.0
   */
  description?: string;
  /**
   * @since 6.0.0
   */
  id: string;
  /**
   * @since 6.0.0
   */
  title: string;
}

/**
 * @since 6.0.0
 */
export interface OnAppShortcutEvent {
  /**
   * @since 6.0.0
   */
  id: string;
}
