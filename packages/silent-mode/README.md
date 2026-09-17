# Capacitor Silent Mode Plugin

Capacitor plugin to detect whether the device is in silent mode.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🔇 **Silent detection**: Check whether the device is currently in silent mode.
- 🔔 **Ringer mode**: Read the exact ringer mode (normal, vibrate or silent) on Android.
- 👂 **Change events**: Listen for changes to the silent mode state.
- 🤝 **Compatibility**: Works alongside the [Audio Session](https://capawesome.io/docs/sdks/capacitor/audio-session/), [Haptics](https://capawesome.io/docs/sdks/capacitor/haptics/) and [Volume](https://capawesome.io/docs/sdks/capacitor/volume/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Silent Mode plugin is typically used to adapt an app's audio behavior to the device's ringer state, for example:

- **Respect the mute switch**: Skip sound effects or in-app notification sounds while the device is in silent mode.
- **Muted playback defaults**: Start audio or video content muted when the device is silent.
- **Sound or vibration**: Use the exact ringer mode on Android to decide between playing a sound and vibrating.
- **Live state updates**: React immediately when the user changes the silent mode state using the `silentModeChange` listener.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-silent-mode` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-silent-mode
npx cap sync
```

This plugin is only available on **Android** and **iOS**. On the Web, all methods reject as unimplemented.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check whether the device is in silent mode, read the exact ringer mode, and listen for silent mode changes.

### Check whether the device is in silent mode

Get the current silent mode state of the device. On Android, the device is considered silent if the ringer mode is not set to normal (that is, either vibrate or silent). On iOS, the state is determined using a heuristic (see [Silent Mode Detection](#silent-mode-detection)). Only available on Android and iOS:

```typescript
import { SilentMode } from '@capawesome/capacitor-silent-mode';

const isSilent = async () => {
  const { silent } = await SilentMode.isSilent();
  return silent;
};
```

### Read the exact ringer mode

Read the current ringer mode (`normal`, `vibrate` or `silent`), for example to distinguish between the vibrate and silent modes. Only available on Android:

```typescript
import { SilentMode } from '@capawesome/capacitor-silent-mode';

const getRingerMode = async () => {
  const { mode } = await SilentMode.getRingerMode();
  return mode;
};
```

### Listen for silent mode changes

Attach a listener to be notified when the silent mode state changes. The device is only observed while at least one listener is attached. On iOS, the state is polled on a timer while the app is in the foreground:

```typescript
import { SilentMode } from '@capawesome/capacitor-silent-mode';

const addSilentModeChangeListener = async () => {
  await SilentMode.addListener('silentModeChange', event => {
    console.log('Silent mode changed:', event.silent);
  });
};
```

### Stop listening for changes

Remove all listeners when you no longer need to observe the silent mode state:

```typescript
import { SilentMode } from '@capawesome/capacitor-silent-mode';

const removeAllListeners = async () => {
  await SilentMode.removeAllListeners();
};
```

## API

<docgen-index>

* [`getRingerMode()`](#getringermode)
* [`isSilent()`](#issilent)
* [`addListener('silentModeChange', ...)`](#addlistenersilentmodechange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getRingerMode()

```typescript
getRingerMode() => Promise<GetRingerModeResult>
```

Get the current ringer mode of the device.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#getringermoderesult">GetRingerModeResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isSilent()

```typescript
isSilent() => Promise<IsSilentResult>
```

Get the current silent mode state of the device.

On Android, the device is considered silent if the ringer mode is not set
to normal (that is, either vibrate or silent).

On iOS, there is no public API to read the ring/silent switch. The state is
therefore determined using a heuristic that plays a short muted system sound
and measures how long it takes to complete. The result may be inaccurate
while other audio is playing.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#issilentresult">IsSilentResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('silentModeChange', ...)

```typescript
addListener(eventName: 'silentModeChange', listenerFunc: (event: SilentModeChangeEvent) => void) => Promise<PluginListenerHandle>
```

Listen for changes to the silent mode state of the device.

The device is only observed while at least one listener is attached.

On iOS, the state is polled on a timer while the app is in the foreground
and the listener is not called while the app is in the background.

Only available on Android and iOS.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'silentModeChange'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#silentmodechangeevent">SilentModeChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.1.0

--------------------


### Interfaces


#### GetRingerModeResult

| Prop       | Type                                              | Description                            | Since |
| ---------- | ------------------------------------------------- | -------------------------------------- | ----- |
| **`mode`** | <code><a href="#ringermode">RingerMode</a></code> | The current ringer mode of the device. | 0.1.0 |


#### IsSilentResult

| Prop         | Type                 | Description                                     | Since |
| ------------ | -------------------- | ----------------------------------------------- | ----- |
| **`silent`** | <code>boolean</code> | Whether the device is currently in silent mode. | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### SilentModeChangeEvent

| Prop         | Type                 | Description                                     | Since |
| ------------ | -------------------- | ----------------------------------------------- | ----- |
| **`silent`** | <code>boolean</code> | Whether the device is currently in silent mode. | 0.1.0 |


### Type Aliases


#### RingerMode

The ringer mode of the device.

- `normal`: The device plays sounds and rings for incoming calls.
- `silent`: The device is silent and does not vibrate.
- `vibrate`: The device is silent but vibrates.

<code>'normal' | 'silent' | 'vibrate'</code>

</docgen-api>

## Silent Mode Detection

Keep the following platform differences in mind when detecting silent mode:

- **Android**: The silent mode state is derived from the [ringer mode](https://developer.android.com/reference/android/media/AudioManager#getRingerMode()). A device is considered silent whenever the ringer mode is not `normal`, which means that the vibrate mode also counts as silent. Use `getRingerMode()` if you need to distinguish between the vibrate and silent modes.
- **iOS**: There is no public API to read the state of the ring/silent switch. The plugin therefore relies on a heuristic that plays a short muted system sound and measures how long it takes to complete. As a result, the detection may be inaccurate while other audio is playing or when the audio session category overrides the switch. The `silentModeChange` listener polls this heuristic on a timer while the app is in the foreground and pauses while the app is in the background.

## FAQ

### How is this plugin different from other similar plugins?

It reports whether the device is in silent mode on both Android and iOS, and on Android it also exposes the exact ringer mode so you can distinguish vibrate from fully silent. A `silentModeChange` listener lets you react the moment the state changes, and the device is only observed while a listener is attached. Everything is delivered through a fully typed API that supports CocoaPods and Swift Package Manager on iOS and tracks the latest Capacitor version.

### How does silent mode detection work on iOS?

iOS provides no public API to read the state of the ring/silent switch. The plugin therefore relies on a heuristic that plays a short muted system sound and measures how long it takes to complete. As a result, the detection may be inaccurate while other audio is playing or when the audio session category overrides the switch. See [Silent Mode Detection](#silent-mode-detection) for details.

### Why is vibrate mode reported as silent on Android?

On Android, the device is considered silent whenever the ringer mode is not set to normal, which includes both the vibrate and silent modes. If you need to distinguish between them, use the `getRingerMode` method, which returns the exact ringer mode. This method is only available on Android.

### Can I detect silent mode changes while the app is in the background?

On iOS, no. The `silentModeChange` listener polls the silent mode state on a timer while the app is in the foreground and pauses while the app is in the background. Also note that the device is only observed while at least one listener is attached.

### Is the Silent Mode plugin available on the Web?

No, the plugin is only available on Android and iOS. On the Web, all methods reject as unimplemented, since browsers do not expose the device's ringer state.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Audio Session](https://capawesome.io/docs/sdks/capacitor/audio-session/): Configure and observe the iOS audio session.
- [Haptics](https://capawesome.io/docs/sdks/capacitor/haptics/): Provide haptic feedback such as impacts, notifications, and vibrations.
- [Volume](https://capawesome.io/docs/sdks/capacitor/volume/): Control the volume and observe hardware volume button presses.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/silent-mode/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/silent-mode/LICENSE).
