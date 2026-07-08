# Capacitor Haptics Plugin

Capacitor plugin to provide haptic feedback such as impacts, notifications, vibrations and custom haptic patterns.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 💥 **Impact**: Trigger impact haptic feedback with five different styles, including the modern `Rigid` and `Soft` styles.
- 🔔 **Notification**: Trigger notification haptic feedback for success, warning and error events.
- 🎯 **Selection**: Trigger selection haptic feedback for picker-style interactions.
- 🎼 **Custom patterns**: Play custom haptic patterns with per-event intensity and sharpness (Core Haptics on iOS, vibration effects on Android).
- 🤖 **Android haptics**: Perform semantic Android haptic feedback effects that respect the user's haptic feedback settings.
- 📳 **Vibrate**: Vibrate the device with a custom duration.
- 🔎 **Availability check**: Check if haptic feedback is available on the device.
- 🌐 **Web support**: Best-effort support for the Web using the Vibration API.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Haptics plugin is typically used to make an app feel more responsive and tactile, for example:

- **Button and gesture feedback**: Trigger impact feedback when user interface elements collide or a drag operation snaps into place.
- **Success and error feedback**: Use notification haptics to communicate that a task has succeeded, failed, or produced a warning.
- **Pickers and selection controls**: Give subtle selection feedback while the user scrolls through a picker.
- **Games and rich interactions**: Play custom haptic patterns with per-event intensity and sharpness for immersive experiences.
- **Alerts**: Vibrate the device with a custom duration to get the user's attention.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-haptics` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-haptics
npx cap sync
```

### Android

This plugin already declares the `android.permission.VIBRATE` permission in its manifest, so no additional configuration is required.

### iOS

No additional configuration is required for this plugin.

## Configuration

No configuration required for this plugin.

## Usage

Import the plugin and call its methods:

```typescript
import {
  AndroidHapticType,
  Haptics,
  ImpactStyle,
  NotificationType,
} from '@capawesome/capacitor-haptics';
```

### Check if haptic feedback is available

Check whether the device supports haptic feedback before using the other methods:

```typescript
const isAvailable = async () => {
  const { available } = await Haptics.isAvailable();
  return available;
};
```

### Trigger impact feedback

Simulate a physical impact, for example when user interface elements collide or a drag operation snaps into place. Five different styles are available, including the modern `Rigid` and `Soft` styles:

```typescript
const impact = async () => {
  await Haptics.impact({ style: ImpactStyle.Medium });
};
```

### Trigger notification feedback

Communicate that a task or action has succeeded, failed, or produced a warning:

```typescript
const notification = async () => {
  await Haptics.notification({ type: NotificationType.Success });
};
```

### Give selection feedback

Give subtle feedback during picker-style interactions. Call `selectionStart()` when the interaction begins, `selectionChanged()` whenever the selection changes, and `selectionEnd()` when the interaction finishes:

```typescript
const selection = async () => {
  await Haptics.selectionStart();
  await Haptics.selectionChanged();
  await Haptics.selectionEnd();
};
```

### Play a custom haptic pattern

Play a custom pattern composed of individual haptic events with per-event intensity and sharpness. On iOS, the pattern is played using Core Haptics; on Android, it is approximated using vibration effects:

```typescript
const playPattern = async () => {
  await Haptics.playPattern({
    events: [
      { time: 0, intensity: 1, sharpness: 0.8 },
      { time: 0.2, intensity: 0.6, sharpness: 0.4 },
      { time: 0.4, intensity: 0.8, duration: 0.5 },
    ],
  });
};
```

### Perform an Android haptic effect

Perform a semantic Android haptic feedback effect that respects the user's haptic feedback settings. Only available on Android:

```typescript
const performAndroidHaptic = async () => {
  await Haptics.performAndroidHaptic({ type: AndroidHapticType.Confirm });
};
```

### Vibrate the device

Vibrate the device. The `duration` option is only available on Android and Web:

```typescript
const vibrate = async () => {
  await Haptics.vibrate({ duration: 500 });
};
```

## API

<docgen-index>

* [`impact(...)`](#impact)
* [`isAvailable()`](#isavailable)
* [`notification(...)`](#notification)
* [`performAndroidHaptic(...)`](#performandroidhaptic)
* [`playPattern(...)`](#playpattern)
* [`selectionChanged()`](#selectionchanged)
* [`selectionEnd()`](#selectionend)
* [`selectionStart()`](#selectionstart)
* [`vibrate(...)`](#vibrate)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### impact(...)

```typescript
impact(options?: ImpactOptions | undefined) => Promise<void>
```

Trigger an impact haptic feedback.

Use this to simulate a physical impact, for example when user
interface elements collide or a drag operation snaps into place.

On Android, the impact style is mapped to a best-effort vibration effect.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#impactoptions">ImpactOptions</a></code> |

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if haptic feedback is available on the device.

On Android, this checks whether the device has a vibrator.
On iOS, this checks whether the device supports haptic event playback.
On the web, this checks whether the Vibration API is supported.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### notification(...)

```typescript
notification(options?: NotificationOptions | undefined) => Promise<void>
```

Trigger a notification haptic feedback.

Use this to communicate that a task or action has succeeded, failed,
or produced a warning.

On Android, the notification type is mapped to a best-effort vibration
effect.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#notificationoptions">NotificationOptions</a></code> |

**Since:** 0.1.0

--------------------


### performAndroidHaptic(...)

```typescript
performAndroidHaptic(options: PerformAndroidHapticOptions) => Promise<void>
```

Perform a predefined Android haptic feedback effect.

In contrast to the other methods, this method does not use the vibrator
but the semantic haptic feedback constants of the Android view system.
This respects the user's haptic feedback settings.

Only available on Android.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#performandroidhapticoptions">PerformAndroidHapticOptions</a></code> |

**Since:** 0.1.0

--------------------


### playPattern(...)

```typescript
playPattern(options: PlayPatternOptions) => Promise<void>
```

Play a custom haptic pattern composed of individual haptic events.

On iOS, the pattern is played using Core Haptics with full support for
intensity and sharpness. This requires a device with haptic event
playback support (for example an iPhone with a Taptic Engine).
Otherwise, the call rejects as unavailable.

On Android, the pattern is played using vibration effects. Intensity is
only respected on devices with amplitude control.

On the web, the pattern is approximated using the Vibration API.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#playpatternoptions">PlayPatternOptions</a></code> |

**Since:** 0.1.0

--------------------


### selectionChanged()

```typescript
selectionChanged() => Promise<void>
```

Trigger a selection changed haptic feedback.

Call this method after `selectionStart()` whenever the selection changes.

On the web, this method does nothing.

**Since:** 0.1.0

--------------------


### selectionEnd()

```typescript
selectionEnd() => Promise<void>
```

End a selection session.

Call this method when the user finishes a selection interaction.

On the web, this method does nothing.

**Since:** 0.1.0

--------------------


### selectionStart()

```typescript
selectionStart() => Promise<void>
```

Start a selection session.

Call this method when the user starts a selection interaction, for
example when a picker starts scrolling. It prepares the haptic hardware
to reduce the latency of subsequent `selectionChanged()` calls.

On the web, this method does nothing.

**Since:** 0.1.0

--------------------


### vibrate(...)

```typescript
vibrate(options?: VibrateOptions | undefined) => Promise<void>
```

Vibrate the device.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#vibrateoptions">VibrateOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### ImpactOptions

| Prop        | Type                                                | Description              | Default                         | Since |
| ----------- | --------------------------------------------------- | ------------------------ | ------------------------------- | ----- |
| **`style`** | <code><a href="#impactstyle">ImpactStyle</a></code> | The style of the impact. | <code>ImpactStyle.Medium</code> | 0.1.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                                | Since |
| --------------- | -------------------- | ---------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not haptic feedback is available on the device. | 0.1.0 |


#### NotificationOptions

| Prop       | Type                                                          | Description                   | Default                               | Since |
| ---------- | ------------------------------------------------------------- | ----------------------------- | ------------------------------------- | ----- |
| **`type`** | <code><a href="#notificationtype">NotificationType</a></code> | The type of the notification. | <code>NotificationType.Success</code> | 0.1.0 |


#### PerformAndroidHapticOptions

| Prop       | Type                                                            | Description                                    | Since |
| ---------- | --------------------------------------------------------------- | ---------------------------------------------- | ----- |
| **`type`** | <code><a href="#androidhaptictype">AndroidHapticType</a></code> | The Android haptic feedback effect to perform. | 0.1.0 |


#### PlayPatternOptions

| Prop         | Type                       | Description                                 | Since |
| ------------ | -------------------------- | ------------------------------------------- | ----- |
| **`events`** | <code>HapticEvent[]</code> | The haptic events that make up the pattern. | 0.1.0 |


#### HapticEvent

| Prop            | Type                | Description                                                                                                                                                                                             | Default          | Since |
| --------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- | ----- |
| **`duration`**  | <code>number</code> | The duration of the event in seconds. If omitted, the event is a transient tap.                                                                                                                         |                  | 0.1.0 |
| **`intensity`** | <code>number</code> | The intensity of the event as a value between `0` and `1`. On Android, the intensity is only respected on devices with amplitude control.                                                               |                  | 0.1.0 |
| **`sharpness`** | <code>number</code> | The sharpness of the event as a value between `0` and `1`. A lower value results in a rounder, softer feedback while a higher value results in a crisper, more precise feedback. Only available on iOS. | <code>0.5</code> | 0.1.0 |
| **`time`**      | <code>number</code> | The relative time at which the event occurs, in seconds.                                                                                                                                                |                  | 0.1.0 |


#### VibrateOptions

| Prop           | Type                | Description                                                                       | Default          | Since |
| -------------- | ------------------- | --------------------------------------------------------------------------------- | ---------------- | ----- |
| **`duration`** | <code>number</code> | The duration of the vibration in milliseconds. Only available on Android and Web. | <code>300</code> | 0.1.0 |


### Enums


#### ImpactStyle

| Members      | Value                 | Description                                                     | Since |
| ------------ | --------------------- | --------------------------------------------------------------- | ----- |
| **`Heavy`**  | <code>'HEAVY'</code>  | A collision between large, heavy user interface elements.       | 0.1.0 |
| **`Light`**  | <code>'LIGHT'</code>  | A collision between small, light user interface elements.       | 0.1.0 |
| **`Medium`** | <code>'MEDIUM'</code> | A collision between moderately sized user interface elements.   | 0.1.0 |
| **`Rigid`**  | <code>'RIGID'</code>  | A collision between hard or inflexible user interface elements. | 0.1.0 |
| **`Soft`**   | <code>'SOFT'</code>   | A collision between soft or flexible user interface elements.   | 0.1.0 |


#### NotificationType

| Members       | Value                  | Description                                  | Since |
| ------------- | ---------------------- | -------------------------------------------- | ----- |
| **`Error`**   | <code>'ERROR'</code>   | A task or action has failed.                 | 0.1.0 |
| **`Success`** | <code>'SUCCESS'</code> | A task or action has completed successfully. | 0.1.0 |
| **`Warning`** | <code>'WARNING'</code> | A task or action has produced a warning.     | 0.1.0 |


#### AndroidHapticType

| Members            | Value                        | Description                                                                                                               | Since |
| ------------------ | ---------------------------- | ------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`ClockTick`**    | <code>'CLOCK_TICK'</code>    | The user has pressed either an hour or minute tick of a clock.                                                            | 0.1.0 |
| **`Confirm`**      | <code>'CONFIRM'</code>       | The confirmation of a user's action. On Android 10 and older, `ContextClick` is performed instead.                        | 0.1.0 |
| **`ContextClick`** | <code>'CONTEXT_CLICK'</code> | The user has performed a context click on an object.                                                                      | 0.1.0 |
| **`KeyboardTap`**  | <code>'KEYBOARD_TAP'</code>  | The user has pressed a virtual or software keyboard key.                                                                  | 0.1.0 |
| **`LongPress`**    | <code>'LONG_PRESS'</code>    | The user has performed a long press on an object.                                                                         | 0.1.0 |
| **`Reject`**       | <code>'REJECT'</code>        | The rejection or failure of a user's action. On Android 10 and older, `LongPress` is performed instead.                   | 0.1.0 |
| **`ToggleOff`**    | <code>'TOGGLE_OFF'</code>    | The user has toggled a switch or button into the off position. On Android 13 and older, `ClockTick` is performed instead. | 0.1.0 |
| **`ToggleOn`**     | <code>'TOGGLE_ON'</code>     | The user has toggled a switch or button into the on position. On Android 13 and older, `ClockTick` is performed instead.  | 0.1.0 |
| **`VirtualKey`**   | <code>'VIRTUAL_KEY'</code>   | The user has pressed on a virtual on-screen key.                                                                          | 0.1.0 |

</docgen-api>

## Migrating from `@capacitor/haptics`

This plugin is a drop-in replacement for the official `@capacitor/haptics` plugin with a few differences:

| `@capacitor/haptics`                             | Haptics                                                       |
| ------------------------------------------------ | ------------------------------------------------------------- |
| `impact()` (defaults to `ImpactStyle.Heavy`)     | `impact()` (defaults to `ImpactStyle.Medium`)                 |
| `impact({ style })` (`Heavy`, `Medium`, `Light`) | `impact({ style })` (plus the new `Rigid` and `Soft` styles)  |
| `notification({ type })`                         | `notification({ type })`                                      |
| `vibrate({ duration })`                          | `vibrate({ duration })`                                       |
| `selectionStart()`                               | `selectionStart()`                                            |
| `selectionChanged()`                             | `selectionChanged()`                                          |
| `selectionEnd()`                                 | `selectionEnd()`                                              |
| —                                                | `isAvailable()`                                               |
| —                                                | `performAndroidHaptic({ type })`                              |
| —                                                | `playPattern({ events })`                                     |

**Attention**: The default impact style changed from `ImpactStyle.Heavy` to `ImpactStyle.Medium`. If you rely on the old default, pass `ImpactStyle.Heavy` explicitly.

## Platform behavior

The haptic capabilities of the platforms differ. This plugin maps every method to the best available platform API:

| Method                   | Android                                     | iOS                                        | Web                       |
| ------------------------ | ------------------------------------------- | ------------------------------------------ | ------------------------- |
| `impact(...)`            | Predefined or tuned vibration effects       | `UIImpactFeedbackGenerator` (all 5 styles) | Vibration API             |
| `notification(...)`      | Tuned vibration waveforms                   | `UINotificationFeedbackGenerator`          | Vibration API             |
| `selection*()`           | Tick vibration effect                       | `UISelectionFeedbackGenerator`             | No-op                     |
| `vibrate(...)`           | Vibrator with custom duration               | System vibration (fixed duration)          | Vibration API             |
| `performAndroidHaptic(...)` | Haptic feedback constants (no vibrator)  | Not available                              | Not available             |
| `playPattern(...)`       | Vibration effects (composition or waveform) | Core Haptics (intensity and sharpness)     | Vibration API             |

Custom haptic patterns are strongest on iOS, where Core Haptics supports per-event intensity and sharpness. On Android, patterns are approximated using vibration effect primitives (Android 11+ with supported hardware) or amplitude-controlled waveforms. On the web, patterns are approximated using the Vibration API, which only supports on/off timings.

## FAQ

### How is this plugin different from the official Capacitor Haptics plugin?

This plugin is a drop-in replacement for the official `@capacitor/haptics` plugin with additional features: an `isAvailable()` method, semantic Android haptic effects via `performAndroidHaptic(...)`, custom haptic patterns via `playPattern(...)`, and the modern `Rigid` and `Soft` impact styles. Note that the default impact style is `ImpactStyle.Medium` instead of `ImpactStyle.Heavy`, see [Migrating from `@capacitor/haptics`](#migrating-from-capacitorhaptics).

### Do I need to configure any permissions?

No, the plugin already declares the `android.permission.VIBRATE` permission in its own manifest, so no additional configuration is required on Android. On iOS and Web, no configuration is required either.

### Why don't I feel any haptic feedback on my device?

First, use the `isAvailable()` method to check whether haptic feedback is available: on Android it checks whether the device has a vibrator, on iOS whether the device supports haptic event playback, and on the web whether the Vibration API is supported. Also keep in mind that on Android, the intensity of haptic events is only respected on devices with amplitude control, and that `performAndroidHaptic(...)` respects the user's haptic feedback settings.

### Are custom haptic patterns supported on all platforms?

Custom patterns are strongest on iOS, where Core Haptics supports per-event intensity and sharpness; this requires a device with haptic event playback support (for example an iPhone with a Taptic Engine), otherwise the call rejects as unavailable. On Android, patterns are approximated using vibration effect primitives or amplitude-controlled waveforms. On the web, patterns are approximated using the Vibration API, which only supports on/off timings.

### What is the difference between `vibrate` and `playPattern`?

The `vibrate(...)` method triggers a single vibration with a custom duration, whereby the duration is only respected on Android and Web (on iOS, a system vibration with a fixed duration is played). The `playPattern(...)` method plays a sequence of individual haptic events, each with its own time, intensity, sharpness, and duration.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Shake](https://capawesome.io/docs/sdks/capacitor/shake/): Detect shake gestures, for example to respond with haptic feedback.
- [Silent Mode](https://capawesome.io/docs/sdks/capacitor/silent-mode/): Detect whether the device is in silent mode.
- [Volume](https://capawesome.io/docs/sdks/capacitor/volume/): Control the volume and observe hardware volume button presses.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/haptics/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/haptics/LICENSE).
