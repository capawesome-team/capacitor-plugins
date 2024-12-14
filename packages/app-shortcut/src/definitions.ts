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

export interface GetResult {
  shortcuts: Shortcut[];
}

export interface SetOptions {
  shortcuts: Shortcut[];
}

export interface Shortcut {
  description?: string;
  id: string;
  title: string;
  iosIcon?: number;
}

export interface OnAppShortcutEvent {
  id: string;
}
