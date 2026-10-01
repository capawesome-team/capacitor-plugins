/// <reference types="@capacitor/cli" />

import type { PluginListenerHandle } from '@capacitor/core';

declare module '@capacitor/cli' {
  export interface PluginsConfig {
    /**
     * These configuration values are available:
     */
    TabBar?: {
      /**
       * The tabs to display at launch.
       *
       * If set, the tab bar is created and visible from launch.
       * Do not set this option if you use `@capacitor/splash-screen`,
       * as the tab bar would be displayed on top of the splash screen.
       *
       * Only available on iOS.
       *
       * @since 0.1.0
       * @example [{ "id": "home", "title": "Home", "systemImage": "house.fill" }]
       */
      tabs?: Tab[];
      /**
       * The ID of the tab to select at launch.
       *
       * If not set, the first tab is selected.
       *
       * Only available on iOS.
       *
       * @since 0.1.0
       * @example "home"
       */
      selectedTabId?: string;
      /**
       * The color of the selected tab as a hex color code.
       *
       * If not set, the system default is used.
       *
       * Only available on iOS.
       *
       * @since 0.1.0
       * @example "#007AFF"
       */
      selectedColor?: string;
      /**
       * The color of the unselected tabs as a hex color code.
       *
       * If not set, the system default is used.
       * Has no effect on iOS 26 and later, as the system determines the color of the unselected tabs on Liquid Glass.
       *
       * Only available on iOS.
       *
       * @since 0.1.0
       * @example "#8E8E93"
       */
      unselectedColor?: string;
    };
  }
}

/**
 * @since 0.1.0
 */
export interface TabBarPlugin {
  /**
   * Hide the tab bar.
   *
   * The tabs are kept and the bottom safe area inset is reset.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  hide(): Promise<void>;
  /**
   * Select the tab with the given ID.
   *
   * This method does not emit the `tabSelected` event.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  selectTabById(options: SelectTabByIdOptions): Promise<void>;
  /**
   * Set the colors of the tabs.
   *
   * Colors that are not provided are reset to the system default.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  setColors(options: SetColorsOptions): Promise<void>;
  /**
   * Set the tabs of the tab bar.
   *
   * This replaces all existing tabs.
   * The tab with the ID `selectedTabId` is selected if provided.
   * Otherwise, the currently selected tab stays selected if its ID still exists, or the first tab is selected.
   *
   * This method does not change the visibility of the tab bar.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  setTabs(options: SetTabsOptions): Promise<void>;
  /**
   * Show the tab bar.
   *
   * The tab bar is placed at the bottom of the screen and the bottom
   * safe area inset of the web view is increased by the height of the tab bar.
   *
   * The tabs must be set first using `setTabs(...)` or the `tabs` configuration.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  show(): Promise<void>;
  /**
   * Called when the user taps a tab.
   *
   * This event is also emitted if the user taps the tab that is already selected.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  addListener(
    eventName: 'tabSelected',
    listenerFunc: (event: TabSelectedEvent) => void,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface SelectTabByIdOptions {
  /**
   * The ID of the tab to select.
   *
   * @since 0.1.0
   * @example 'home'
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface SetColorsOptions {
  /**
   * The color of the selected tab as a hex color code.
   *
   * If not provided, the system default is used.
   *
   * @since 0.1.0
   * @example '#007AFF'
   */
  selectedColor?: string;
  /**
   * The color of the unselected tabs as a hex color code.
   *
   * If not provided, the system default is used.
   * Has no effect on iOS 26 and later, as the system determines the color of the unselected tabs on Liquid Glass.
   *
   * @since 0.1.0
   * @example '#8E8E93'
   */
  unselectedColor?: string;
}

/**
 * @since 0.1.0
 */
export interface SetTabsOptions {
  /**
   * The tabs to display.
   *
   * Each tab must have a unique ID.
   *
   * @since 0.1.0
   */
  tabs: Tab[];
  /**
   * The ID of the tab to select.
   *
   * If not provided, the currently selected tab stays selected if its ID still exists.
   * Otherwise, the first tab is selected.
   *
   * @since 0.1.0
   * @example 'home'
   */
  selectedTabId?: string;
}

/**
 * @since 0.1.0
 */
export interface Tab {
  /**
   * The unique ID of the tab.
   *
   * @since 0.1.0
   * @example 'home'
   */
  id: string;
  /**
   * The title of the tab.
   *
   * @since 0.1.0
   * @example 'Home'
   */
  title: string;
  /**
   * The name of an SF Symbol to use as the icon of the tab.
   *
   * Exactly one of `systemImage` or `image` must be provided.
   *
   * @since 0.1.0
   * @example 'house.fill'
   */
  systemImage?: string;
  /**
   * The name of an image in the asset catalog of the app to use as the icon of the tab.
   *
   * The image is rendered as a template image.
   * Exactly one of `systemImage` or `image` must be provided.
   *
   * @since 0.1.0
   * @example 'custom-icon'
   */
  image?: string;
  /**
   * The badge value of the tab.
   *
   * If not provided, no badge is shown.
   *
   * @since 0.1.0
   * @example '3'
   */
  badge?: string;
}

/**
 * @since 0.1.0
 */
export interface TabSelectedEvent {
  /**
   * The ID of the tab that was tapped.
   *
   * @since 0.1.0
   * @example 'home'
   */
  id: string;
}
