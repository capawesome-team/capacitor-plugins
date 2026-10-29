export interface HapticsPlugin {
  /**
   * Trigger an impact haptic feedback.
   *
   * Use this to simulate a physical impact, for example when user
   * interface elements collide or a drag operation snaps into place.
   *
   * On Android, the impact style is mapped to a best-effort vibration effect.
   *
   * @since 0.1.0
   */
  impact(options?: ImpactOptions): Promise<void>;
  /**
   * Check if haptic feedback is available on the device.
   *
   * On Android, this checks whether the device has a vibrator.
   * On iOS, this checks whether the device supports haptic event playback.
   * On the web, this checks whether the Vibration API is supported.
   *
   * @since 0.1.0
   */
  isAvailable(): Promise<IsAvailableResult>;
  /**
   * Trigger a notification haptic feedback.
   *
   * Use this to communicate that a task or action has succeeded, failed,
   * or produced a warning.
   *
   * On Android, the notification type is mapped to a best-effort vibration
   * effect.
   *
   * @since 0.1.0
   */
  notification(options?: NotificationOptions): Promise<void>;
  /**
   * Perform a predefined Android haptic feedback effect.
   *
   * In contrast to the other methods, this method does not use the vibrator
   * but the semantic haptic feedback constants of the Android view system.
   * This respects the user's haptic feedback settings.
   *
   * Only available on Android.
   *
   * @since 0.1.0
   */
  performAndroidHaptic(options: PerformAndroidHapticOptions): Promise<void>;
  /**
   * Play a custom haptic pattern composed of individual haptic events.
   *
   * On iOS, the pattern is played using Core Haptics with full support for
   * intensity and sharpness. This requires a device with haptic event
   * playback support (for example an iPhone with a Taptic Engine).
   * Otherwise, the call rejects as unavailable.
   *
   * On Android, the pattern is played using vibration effects. Intensity is
   * only respected on devices with amplitude control. On Android 12+, a
   * pattern whose events are all transient or have a `primitive` is played
   * as a composition of haptic primitives. On Android 13+, the pattern
   * respects the system touch feedback setting.
   *
   * On the web, the pattern is approximated using the Vibration API.
   *
   * @since 0.1.0
   */
  playPattern(options: PlayPatternOptions): Promise<void>;
  /**
   * Trigger a selection changed haptic feedback.
   *
   * Call this method after `selectionStart()` whenever the selection changes.
   *
   * On the web, this method does nothing.
   *
   * @since 0.1.0
   */
  selectionChanged(): Promise<void>;
  /**
   * End a selection session.
   *
   * Call this method when the user finishes a selection interaction.
   *
   * On the web, this method does nothing.
   *
   * @since 0.1.0
   */
  selectionEnd(): Promise<void>;
  /**
   * Start a selection session.
   *
   * Call this method when the user starts a selection interaction, for
   * example when a picker starts scrolling. It prepares the haptic hardware
   * to reduce the latency of subsequent `selectionChanged()` calls.
   *
   * On the web, this method does nothing.
   *
   * @since 0.1.0
   */
  selectionStart(): Promise<void>;
  /**
   * Vibrate the device.
   *
   * @since 0.1.0
   */
  vibrate(options?: VibrateOptions): Promise<void>;
}

/**
 * @since 0.1.0
 */
export interface HapticEvent {
  /**
   * The duration of the event in seconds.
   *
   * If omitted, the event is a transient tap.
   *
   * @since 0.1.0
   * @example 0.5
   */
  duration?: number;
  /**
   * The intensity of the event as a value between `0` and `1`.
   *
   * On Android, the intensity is only respected on devices with amplitude
   * control.
   *
   * @since 0.1.0
   * @example 1.0
   */
  intensity: number;
  /**
   * The haptic primitive used to play the event.
   *
   * When every event of a pattern is either transient or has a primitive,
   * the pattern is played as a composition of primitives. Each primitive has
   * its own duration, so the `duration` of an event with a primitive is
   * ignored. A primitive the device does not support is played as `Click`.
   *
   * Only available on Android 12+. On older versions, the event is played as
   * a transient tap.
   *
   * @since 0.1.3
   * @example AndroidHapticPrimitive.SlowRise
   */
  primitive?: AndroidHapticPrimitive;
  /**
   * The sharpness of the event as a value between `0` and `1`.
   *
   * A lower value results in a rounder, softer feedback while a higher value
   * results in a crisper, more precise feedback.
   *
   * Only available on iOS.
   *
   * @since 0.1.0
   * @default 0.5
   * @example 0.7
   */
  sharpness?: number;
  /**
   * The relative time at which the event occurs, in seconds.
   *
   * @since 0.1.0
   * @example 0.2
   */
  time: number;
}

/**
 * @since 0.1.3
 */
export interface HapticParameterCurve {
  /**
   * The control points of the curve, at most 16.
   *
   * The value is interpolated linearly between consecutive control points.
   *
   * @since 0.1.3
   */
  controlPoints: HapticParameterCurveControlPoint[];
  /**
   * The parameter the curve controls.
   *
   * @since 0.1.3
   */
  parameter: HapticParameter;
}

/**
 * @since 0.1.3
 */
export interface HapticParameterCurveControlPoint {
  /**
   * The time of the control point relative to the start of the pattern,
   * in seconds.
   *
   * @since 0.1.3
   * @example 0.3
   */
  time: number;
  /**
   * The value of the parameter at this control point.
   *
   * @since 0.1.3
   * @example 1.0
   */
  value: number;
}

/**
 * @since 0.1.0
 */
export interface ImpactOptions {
  /**
   * The style of the impact.
   *
   * @since 0.1.0
   * @default ImpactStyle.Medium
   */
  style?: ImpactStyle;
}

/**
 * @since 0.1.0
 */
export interface IsAvailableResult {
  /**
   * Whether or not haptic feedback is available on the device.
   *
   * @since 0.1.0
   * @example true
   */
  available: boolean;
}

/**
 * @since 0.1.0
 */
export interface NotificationOptions {
  /**
   * The type of the notification.
   *
   * @since 0.1.0
   * @default NotificationType.Success
   */
  type?: NotificationType;
}

/**
 * @since 0.1.0
 */
export interface PerformAndroidHapticOptions {
  /**
   * The Android haptic feedback effect to perform.
   *
   * @since 0.1.0
   */
  type: AndroidHapticType;
}

/**
 * @since 0.1.0
 */
export interface PlayPatternOptions {
  /**
   * The haptic events that make up the pattern.
   *
   * @since 0.1.0
   */
  events: HapticEvent[];
  /**
   * The curves that change a parameter of the whole pattern over time, for
   * example an intensity that ramps up.
   *
   * Only available on iOS.
   *
   * @since 0.1.3
   */
  parameterCurves?: HapticParameterCurve[];
}

/**
 * @since 0.1.0
 */
export interface VibrateOptions {
  /**
   * The duration of the vibration in milliseconds.
   *
   * Only available on Android and Web.
   *
   * @since 0.1.0
   * @default 300
   * @example 500
   */
  duration?: number;
}

/**
 * The Android haptic feedback effect to perform.
 *
 * The effects correspond to the haptic feedback constants of the Android
 * view system.
 *
 * @since 0.1.0
 */
export enum AndroidHapticType {
  /**
   * The user has pressed either an hour or minute tick of a clock.
   *
   * @since 0.1.0
   */
  ClockTick = 'CLOCK_TICK',
  /**
   * The confirmation of a user's action.
   *
   * On Android 10 and older, `ContextClick` is performed instead.
   *
   * @since 0.1.0
   */
  Confirm = 'CONFIRM',
  /**
   * The user has performed a context click on an object.
   *
   * @since 0.1.0
   */
  ContextClick = 'CONTEXT_CLICK',
  /**
   * The user has pressed a virtual or software keyboard key.
   *
   * @since 0.1.0
   */
  KeyboardTap = 'KEYBOARD_TAP',
  /**
   * The user has performed a long press on an object.
   *
   * @since 0.1.0
   */
  LongPress = 'LONG_PRESS',
  /**
   * The rejection or failure of a user's action.
   *
   * On Android 10 and older, `LongPress` is performed instead.
   *
   * @since 0.1.0
   */
  Reject = 'REJECT',
  /**
   * The user has toggled a switch or button into the off position.
   *
   * On Android 13 and older, `ClockTick` is performed instead.
   *
   * @since 0.1.0
   */
  ToggleOff = 'TOGGLE_OFF',
  /**
   * The user has toggled a switch or button into the on position.
   *
   * On Android 13 and older, `ClockTick` is performed instead.
   *
   * @since 0.1.0
   */
  ToggleOn = 'TOGGLE_ON',
  /**
   * The user has pressed on a virtual on-screen key.
   *
   * @since 0.1.0
   */
  VirtualKey = 'VIRTUAL_KEY',
}

/**
 * The haptic primitive of a haptic event.
 *
 * The primitives correspond to the composition primitives of the Android
 * vibrator system.
 *
 * @since 0.1.3
 */
export enum AndroidHapticPrimitive {
  /**
   * A sharp, crisp click.
   *
   * @since 0.1.3
   */
  Click = 'CLICK',
  /**
   * A very short, low frequency tick.
   *
   * @since 0.1.3
   */
  LowTick = 'LOW_TICK',
  /**
   * A quick downwards movement with gravity.
   *
   * @since 0.1.3
   */
  QuickFall = 'QUICK_FALL',
  /**
   * A quick upwards movement against gravity.
   *
   * @since 0.1.3
   */
  QuickRise = 'QUICK_RISE',
  /**
   * A slow upwards movement against gravity.
   *
   * @since 0.1.3
   */
  SlowRise = 'SLOW_RISE',
  /**
   * A spinning momentum.
   *
   * @since 0.1.3
   */
  Spin = 'SPIN',
  /**
   * A downwards movement with gravity, ending in a collision.
   *
   * @since 0.1.3
   */
  Thud = 'THUD',
  /**
   * A very short, light and crisp tick.
   *
   * @since 0.1.3
   */
  Tick = 'TICK',
}

/**
 * @since 0.1.0
 */
export enum ErrorCode {
  /**
   * The haptic pattern could not be played.
   *
   * @since 0.1.0
   */
  PatternPlaybackFailed = 'PATTERN_PLAYBACK_FAILED',
}

/**
 * The parameter of a haptic pattern that a parameter curve controls.
 *
 * @since 0.1.3
 */
export enum HapticParameter {
  /**
   * Multiplies the intensity of every event.
   *
   * Control point values range from `0` to `1`.
   *
   * @since 0.1.3
   */
  Intensity = 'INTENSITY',
  /**
   * Offsets the sharpness of every event.
   *
   * Control point values range from `-1` to `1`.
   *
   * @since 0.1.3
   */
  Sharpness = 'SHARPNESS',
}

/**
 * The style of the impact.
 *
 * @since 0.1.0
 */
export enum ImpactStyle {
  /**
   * A collision between large, heavy user interface elements.
   *
   * @since 0.1.0
   */
  Heavy = 'HEAVY',
  /**
   * A collision between small, light user interface elements.
   *
   * @since 0.1.0
   */
  Light = 'LIGHT',
  /**
   * A collision between moderately sized user interface elements.
   *
   * @since 0.1.0
   */
  Medium = 'MEDIUM',
  /**
   * A collision between hard or inflexible user interface elements.
   *
   * @since 0.1.0
   */
  Rigid = 'RIGID',
  /**
   * A collision between soft or flexible user interface elements.
   *
   * @since 0.1.0
   */
  Soft = 'SOFT',
}

/**
 * The type of the notification.
 *
 * @since 0.1.0
 */
export enum NotificationType {
  /**
   * A task or action has failed.
   *
   * @since 0.1.0
   */
  Error = 'ERROR',
  /**
   * A task or action has completed successfully.
   *
   * @since 0.1.0
   */
  Success = 'SUCCESS',
  /**
   * A task or action has produced a warning.
   *
   * @since 0.1.0
   */
  Warning = 'WARNING',
}
