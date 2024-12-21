import type { PluginListenerHandle } from '@capacitor/core';

export interface ForegroundServicePlugin {
  /**
   * Moves the app to the foreground.
   *
   * On Android SDK 23+, the user must grant the manage overlay permission.
   * You can use the `requestManageOverlayPermission()` method to request the
   * permission and the `checkManageOverlayPermission()` method to check if the
   * permission is granted.
   *
   * Only available on Android.
   *
   * @since 0.3.0
   * @experimental This method is experimental and may not work as expected.
   */
  moveToForeground(): Promise<void>;
  /**
   * Starts the foreground service.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  startForegroundService(options: StartForegroundServiceOptions): Promise<void>;
  /**
   * Updates the notification details of the running foreground service.
   *
   * Only available on Android.
   *
   * @since 6.1.0
   */
  updateForegroundService(
    options: UpdateForegroundServiceOptions,
  ): Promise<void>;
  /**
   * Stops the foreground service.
   *
   * Only available on Android.
   *
   * @since 0.0.1
   */
  stopForegroundService(): Promise<void>;
  /**
   * Check permission to display notifications.
   *
   * On **Android**, this method only needs to be called on Android 13+.
   *
   * Only available on Android.
   *
   * @since 5.0.0
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Request permission to display notifications.
   *
   * On **Android**, this method only needs to be called on Android 13+.
   *
   * Only available on Android.
   *
   * @since 5.0.0
   */
  requestPermissions(): Promise<PermissionStatus>;
  /**
   * Check if the overlay permission is granted.
   *
   * Only available on Android.
   *
   * @since 0.3.0
   */
  checkManageOverlayPermission(): Promise<ManageOverlayPermissionResult>;
  /**
   * Request the manage overlay permission.
   *
   * Only available on Android.
   *
   * @since 0.3.0
   */
  requestManageOverlayPermission(): Promise<ManageOverlayPermissionResult>;
  /**
   * Create a notification channel.
   * If not invoked, the plugin creates a channel with name and description set to "Default".
   *
   * Only available for Android (SDK 26+).
   *
   * @since 6.1.0
   */
  createNotificationChannel(
    options: CreateNotificationChannelOptions,
  ): Promise<void>;
  /**
   * Delete a notification channel.
   *
   * Only available for Android (SDK 26+).
   *
   * @since 6.1.0
   */
  deleteNotificationChannel(
    options: DeleteNotificationChannelOptions,
  ): Promise<void>;
  /**
   * Called when a notification button is clicked.
   *
   * Only available on iOS.
   *
   * @since 0.2.0
   */
  addListener(
    eventName: 'buttonClicked',
    listenerFunc: ButtonClickedEventListener,
  ): Promise<PluginListenerHandle>;
  /**
   * Remove all listeners for this plugin.
   *
   * @since 0.2.0
   */
  removeAllListeners(): Promise<void>;
}

export interface StartForegroundServiceOptions {
  /**
   * The body of the notification, shown below the title.
   *
   * @since 0.0.1
   * @example "This is the body of the notification"
   */
  body: string;
  /**
   * The buttons to show on the notification.
   *
   * Only available on Android (SDK 24+).
   *
   * @since 0.2.0
   */
  buttons?: NotificationButton[];
  /**
   * The notification identifier.
   *
   * @since 0.0.1
   * @example 123
   */
  id: number;
  /**
   * The foreground service type.
   *
   * Only available on Android (SDK 29+).
   *
   * @since 6.2.0
   * @see https://developer.android.com/develop/background-work/services/fgs/service-types
   */
  serviceType?: ServiceType;
  /**
   * The status bar icon for the notification.
   *
   * Icons should be placed in your app's `res/drawable` folder. The value for
   * this option should be the drawable resource ID, which is the filename
   * without an extension.
   *
   * @since 0.0.1
   * @example "ic_stat_icon_config_sample"
   */
  smallIcon: string;
  /**
   * The title of the notification.
   *
   * @since 0.0.1
   * @example "This is the title of the notification"
   */
  title: string;
  /**
   * If true, will only alert (sound/vibration) on the first notification.
   * Subsequent updates will be silent.
   *
   * @since 6.1.0
   * @default false
   */
  silent?: boolean;
  /**
   * The notification channel identifier.
   *
   * @since 6.1.0
   */
  notificationChannelId?: string;
}

/**
 * @since 0.2.0
 */
export interface NotificationButton {
  /**
   * The button title.
   *
   * @since 0.2.0
   * @example "Stop"
   */
  title: string;
  /**
   * The button identifier.
   *
   * This is used to identify the button when
   * the `buttonClicked` event is emitted.
   *
   * @since 0.2.0
   * @example 123
   */
  id: number;
}

/**
 * @since 0.3.0
 */
export interface ManageOverlayPermissionResult {
  /**
   * Whether the permission is granted.
   *
   * This is always `true` on Android SDK < 23.
   *
   * @since 0.3.0
   * @example true
   */
  granted: boolean;
}

/**
 * @since 0.2.0
 */
export type ButtonClickedEventListener = (event: ButtonClickedEvent) => void;

/**
 * @since 0.2.0
 */
export interface ButtonClickedEvent {
  /**
   * The button identifier.
   *
   * @since 0.2.0
   */
  buttonId: number;
}

/**
 * @since 5.0.0
 */
export interface PermissionStatus {
  /**
   * Permission state of displaying notifications.
   *
   * @since 5.0.0
   */
  display: PermissionState;
}

/**
 * @since 6.1.0
 */
export type UpdateForegroundServiceOptions = StartForegroundServiceOptions;

/**
 * @since 6.1.0
 */
export interface CreateNotificationChannelOptions {
  /**
   * The description of this channel (presented to the user).
   *
   * @since 6.1.0
   */
  description?: string;
  /**
   * The channel identifier.
   *
   * @since 6.1.0
   */
  id: string;
  /**
   * The level of interruption for notifications posted to this channel.
   *
   * @since 6.1.0
   */
  importance?: Importance;
  /**
   * The name of this channel (presented to the user).
   *
   * @since 6.1.0
   */
  name: string;
}

/**
 * The importance level.
 *
 * For more details, see the [Android Developer Docs](https://developer.android.com/reference/android/app/NotificationManager#IMPORTANCE_DEFAULT)
 *
 * @since 6.1.0
 */
export enum Importance {
  /**
   * @since 6.1.0
   */
  Min = 1,
  /**
   * @since 6.1.0
   */
  Low = 2,
  /**
   * @since 6.1.0
   */
  Default = 3,
  /**
   * @since 6.1.0
   */
  High = 4,
  /**
   * @since 6.1.0
   */
  Max = 5,
}

/**
 * @since 6.1.0
 */
export interface DeleteNotificationChannelOptions {
  /**
   * The channel identifier.
   *
   * @since 6.1.0
   */
  id: string;
}

/**
 * @since 6.2.0
 */
export enum ServiceType {
  Location = 8,
  Microphone = 128,
}
