/// <reference types="@capacitor/cli" />

import type { PluginListenerHandle } from '@capacitor/core';

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    AppShortcuts?: {
      /**
       * The list of app shortcuts that should be set by default.
       *
       * Only available on Android and iOS.
       *
       * @since 7.2.0
       * @example [{ id: 'feedback', title: 'Feedback' }]
       */
      shortcuts?: Shortcut[];
    };
  }
}

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
   * On **Android**, the launcher shows this instead of the short title when it has enough space.
   *
   * **Attention**: On **iOS**, the icon and the description must be used together.
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
   *
   * On **Android**, the icon is the constant integer value of the [R.drawable](https://developer.android.com/reference/android/R.drawable) enum (e.g. `17301547`).
   *
   * On **iOS**, the icon can be one of the following:
   * - The constant integer value of the [UIApplicationShortcutIcon.IconType](https://developer.apple.com/documentation/uikit/uiapplicationshortcuticon/icontype) enum (e.g. `6`).
   * - A system symbol name (e.g. `star.fill`).
   * - Name of the image asset from the asset catalogue.
   *
   * @since 6.1.0
   * @deprecated If provided, the icon will fall back to this property if `androidIcon` or `iosIcon` are not set.
   * @example 17301547
   * @example 6
   * @example "star.fill"
   */
  icon?: number | string;
  /**
   * The icon to display on Android.
   *
   * The icon is the constant integer value of the [R.drawable](https://developer.android.com/reference/android/R.drawable) enum (e.g. `17301547`).
   *
   * @since 7.3.0
   * @example 17301547
   */
  androidIcon?: number;
  /**
   * The icon to display on iOS.
   *
   * The icon can be one of the following:
   * - The constant integer value of the [UIApplicationShortcutIcon.IconType](https://developer.apple.com/documentation/uikit/uiapplicationshortcuticon/icontype) enum (e.g. `6`).
   * - A system symbol name (e.g. `star.fill`).
   * - Name of the image asset from the asset catalogue.
   *
   * @since 7.3.0
   * @example 6
   * @example "star.fill"
   */
  iosIcon?: number | string;
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
