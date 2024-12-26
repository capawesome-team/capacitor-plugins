import type { PluginListenerHandle } from '@capacitor/core';

export interface AppShortcutsPlugin {
  /**
   * Removes all existing app shortcuts from the device.
   *
   * This method ensures no app shortcuts remain on the user's home screen.
   *
   * Available only on Android and iOS platforms.
   *
   * @since 6.0.0
   */
  clear(): Promise<void>;
  
  /**
   * Retrieves the list of all app shortcuts currently set on the device.
   *
   * This method returns a structured result containing the shortcuts information.
   *
   * Available only on Android and iOS platforms.
   *
   * @since 6.0.0
   */
  get(): Promise<GetResult>;
  
  /**
   * Creates new app shortcuts or updates existing ones based on the provided options.
   *
   * Use this method to define or modify the app shortcuts displayed on the user's device.
   *
   * Available only on Android and iOS platforms.
   *
   * @since 6.0.0
   */
  set(options: SetOptions): Promise<void>;
  
  /**
   * Registers a listener that triggers when an app shortcut is clicked by the user.
   *
   * The listener receives an event object containing information about the clicked shortcut.
   *
   * Available only on Android and iOS platforms.
   *
   * @since 6.0.0
   */
  addListener(
    eventName: 'click',
    listenerFunc: (event: ClickEvent) => void,
  ): Promise<PluginListenerHandle>;
  
  /**
   * Removes all event listeners associated with this plugin.
   *
   * Use this to clean up resources or stop listening for app shortcut events.
   *
   * @since 6.0.0
   */
  removeAllListeners(): Promise<void>;
}

/**
 * Represents the result returned by the `get` method, containing the current app shortcuts.
 *
 * @since 6.0.0
 */
export interface GetResult {
  /**
   * An array of app shortcuts currently configured for the application.
   *
   * Each shortcut provides metadata such as title, ID, and description (if available).
   *
   * @since 6.0.0
   */
  shortcuts: Shortcut[];
}

/**
 * Represents the options required to create or update app shortcuts using the `set` method.
 *
 * @since 6.0.0
 */
export interface SetOptions {
  /**
   * An array of shortcuts to be created or updated.
   *
   * Each shortcut must include a unique identifier (`id`) and a title (`title`).
   *
   * @since 6.0.0
   */
  shortcuts: Shortcut[];
}

/**
 * Represents the metadata for an individual app shortcut.
 *
 * App shortcuts are quick actions that can be accessed directly from the app's icon.
 *
 * @since 6.0.0
 */
export interface Shortcut {
  /**
   * A brief description of the shortcut's functionality.
   *
   * Available only on Android platforms.
   *
   * @since 6.0.0
   */
  description?: string;
  
  /**
   * A unique identifier for the shortcut.
   *
   * This `id` is used to differentiate between shortcuts and is required for actions like updates or event handling.
   *
   * @since 6.0.0
   */
  id: string;
  
  /**
   * The display name of the shortcut.
   *
   * This `title` is shown to the user as the shortcut's label on the home screen or app menu.
   *
   * @since 6.0.0
   */
  title: string;
}

/**
 * Represents the event triggered when an app shortcut is clicked by the user.
 *
 * The event provides the `shortcutId` of the clicked shortcut.
 *
 * @since 6.0.0
 */
export interface ClickEvent {
  /**
   * The unique identifier (`id`) of the app shortcut that was clicked.
   *
   * This `shortcutId` can be used to determine which action to perform in response to the user's interaction.
   *
   * @since 6.0.0
   */
  shortcutId: string;
}
