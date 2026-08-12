import type { PermissionState } from '@capacitor/core';

export interface AlarmPlugin {
  /**
   * Cancel an alarm that was created by the app.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  cancelAlarm(options: CancelAlarmOptions): Promise<void>;
  /**
   * Check permission to schedule alarms.
   *
   * On Android, this method always returns `granted` since
   * alarms are created via the system clock app.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  checkPermissions(): Promise<PermissionStatus>;
  /**
   * Create a new alarm.
   *
   * On Android, the alarm is created in the system clock app.
   * The app has no access to the created alarm afterwards.
   *
   * On iOS, the alarm is owned by the app and can be listed
   * and canceled using the `getAlarms(...)` and `cancelAlarm(...)`
   * methods.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  createAlarm(options: CreateAlarmOptions): Promise<CreateAlarmResult>;
  /**
   * Create a new countdown timer in the system clock app.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  createTimer(options: CreateTimerOptions): Promise<void>;
  /**
   * Get all alarms that were created by the app.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   */
  getAlarms(): Promise<GetAlarmsResult>;
  /**
   * Check if alarms are available on this device.
   *
   * On Android, this checks whether an app that can handle
   * alarms is installed. On iOS, this returns `true` if the
   * device runs iOS 26 or later.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Open the list of alarms in the system clock app.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  openAlarms(): Promise<void>;
  /**
   * Request permission to schedule alarms.
   *
   * On Android, this method always returns `granted` since
   * alarms are created via the system clock app.
   *
   * Only available on Android and iOS.
   *
   * @since 0.1.0
   */
  requestPermissions(): Promise<PermissionStatus>;
}

/**
 * @since 0.1.0
 */
export interface AlarmInfo {
  /**
   * Whether or not the alarm is active.
   *
   * @since 0.1.0
   * @example true
   */
  enabled: boolean | null;
  /**
   * The hour of the alarm (0-23).
   *
   * @since 0.1.0
   * @example 6
   */
  hour: number | null;
  /**
   * The unique identifier of the alarm.
   *
   * @since 0.1.0
   * @example '2B1A3C4D-5E6F-7A8B-9C0D-1E2F3A4B5C6D'
   */
  id: string;
  /**
   * The label of the alarm.
   *
   * @since 0.1.0
   * @example 'Wake up'
   */
  label: string | null;
  /**
   * The minute of the alarm (0-59).
   *
   * @since 0.1.0
   * @example 30
   */
  minute: number | null;
}

/**
 * @since 0.1.0
 */
export interface CancelAlarmOptions {
  /**
   * The unique identifier of the alarm to cancel.
   *
   * @since 0.1.0
   * @example '2B1A3C4D-5E6F-7A8B-9C0D-1E2F3A4B5C6D'
   */
  id: string;
}

/**
 * @since 0.1.0
 */
export interface CreateAlarmAndroidOptions {
  /**
   * Whether or not to create the alarm without showing
   * the user interface of the system clock app.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   * @default false
   */
  skipUi?: boolean;
  /**
   * Whether or not the alarm should vibrate.
   *
   * If not provided, the default behavior of the system
   * clock app is used.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  vibrate?: boolean;
}

/**
 * @since 0.1.0
 */
export interface CreateAlarmOptions {
  /**
   * Android-specific options.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  android?: CreateAlarmAndroidOptions;
  /**
   * The days of the week on which the alarm repeats.
   *
   * If not provided, the alarm fires once at the next
   * occurrence of the given time.
   *
   * @since 0.1.0
   * @example [Weekday.Monday, Weekday.Friday]
   */
  days?: Weekday[];
  /**
   * The hour of the alarm (0-23).
   *
   * @since 0.1.0
   * @example 6
   */
  hour: number;
  /**
   * The label of the alarm.
   *
   * @since 0.1.0
   * @example 'Wake up'
   */
  label?: string;
  /**
   * The minute of the alarm (0-59).
   *
   * @since 0.1.0
   * @example 30
   */
  minute: number;
}

/**
 * @since 0.1.0
 */
export interface CreateAlarmResult {
  /**
   * The unique identifier of the created alarm.
   *
   * On Android, this is always `null` since the alarm is
   * created in the system clock app.
   *
   * @since 0.1.0
   * @example '2B1A3C4D-5E6F-7A8B-9C0D-1E2F3A4B5C6D'
   */
  id: string | null;
}

/**
 * @since 0.1.0
 */
export interface CreateTimerAndroidOptions {
  /**
   * Whether or not to create the timer without showing
   * the user interface of the system clock app.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   * @default false
   */
  skipUi?: boolean;
}

/**
 * @since 0.1.0
 */
export interface CreateTimerOptions {
  /**
   * Android-specific options.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  android?: CreateTimerAndroidOptions;
  /**
   * The length of the timer in seconds (1-86400).
   *
   * @since 0.1.0
   * @example 300
   */
  duration: number;
  /**
   * The label of the timer.
   *
   * @since 0.1.0
   * @example 'Tea'
   */
  label?: string;
}

/**
 * @since 0.1.0
 */
export interface GetAlarmsResult {
  /**
   * The alarms that were created by the app.
   *
   * @since 0.1.0
   */
  alarms: AlarmInfo[];
}

/**
 * @since 0.1.0
 */
export interface IsAvailableResult {
  /**
   * Whether or not alarms are available on this device.
   *
   * @since 0.1.0
   * @example true
   */
  available: boolean;
}

/**
 * @since 0.1.0
 */
export interface PermissionStatus {
  /**
   * Permission state of scheduling alarms.
   *
   * @since 0.1.0
   */
  alarms: PermissionState;
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * No app that can handle alarms was found on the device.
   *
   * @since 0.1.0
   */
  NoClockApp = 'NO_CLOCK_APP',
  /**
   * The permission to schedule alarms was denied.
   *
   * @since 0.1.0
   */
  PermissionDenied = 'PERMISSION_DENIED',
}

/**
 * The days of the week.
 *
 * @since 0.1.0
 */
export enum Weekday {
  /**
   * @since 0.1.0
   */
  Friday = 'FRIDAY',
  /**
   * @since 0.1.0
   */
  Monday = 'MONDAY',
  /**
   * @since 0.1.0
   */
  Saturday = 'SATURDAY',
  /**
   * @since 0.1.0
   */
  Sunday = 'SUNDAY',
  /**
   * @since 0.1.0
   */
  Thursday = 'THURSDAY',
  /**
   * @since 0.1.0
   */
  Tuesday = 'TUESDAY',
  /**
   * @since 0.1.0
   */
  Wednesday = 'WEDNESDAY',
}
