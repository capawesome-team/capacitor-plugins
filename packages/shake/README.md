# Capacitor Shake Plugin

Capacitor plugin to detect shake gestures.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 📳 **Shake detection**: Detect physical shake gestures on the device.
- 🎚️ **Sensitivity**: Configure how strong a shake must be to trigger an event.
- 🔋 **Battery-friendly**: The sensor is only active while you are watching for shakes.
- 📱 **Cross-platform**: One consistent API for Android and iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Shake plugin is typically used to trigger an action when the user shakes the device, for example:

- **Feedback and bug reporting**: Let users shake the device to open a feedback or bug report dialog.
- **Debug menus**: Open a hidden developer or debug menu in internal builds when the device is shaken.
- **Undo actions**: Offer a shake-to-undo interaction, a gesture many users already know from iOS.
- **Refresh content**: Reload data or shuffle content when a shake gesture is detected.

📖 **Guide**: For shake-to-undo and shake-to-reroll gestures alongside the Accelerometer and Gyroscope, see [The Complete Guide to Capacitor Device Sensors](https://capawesome.io/docs/blog/capacitor-device-sensors-guide/).

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-shake` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following command:

```bash
npm install @capawesome/capacitor-shake
npx cap sync
```

This plugin is available on **Android** and **iOS**. On Web, all methods reject as unimplemented.

No additional permissions or privacy descriptions are required.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to detect shake gestures and stop detecting them.

### Detect shake gestures

Attach a `shake` listener and start watching for shake gestures. Use the `sensitivity` option to control how strong a shake must be to trigger an event (see [Sensitivity Levels](#sensitivity-levels)). Only available on Android and iOS:

```typescript
import { Shake } from '@capawesome/capacitor-shake';

const startWatching = async () => {
  await Shake.addListener('shake', () => {
    console.log('Shake detected!');
  });
  await Shake.startWatching({ sensitivity: 'medium' });
};
```

### Stop detecting shake gestures

Stop watching and remove all listeners when you no longer need shake detection, for example when the user leaves the corresponding screen. The sensor is only active while you are watching, so this keeps the plugin battery-friendly:

```typescript
import { Shake } from '@capawesome/capacitor-shake';

const stopWatching = async () => {
  await Shake.stopWatching();
  await Shake.removeAllListeners();
};
```

## API

<docgen-index>

* [`startWatching(...)`](#startwatching)
* [`stopWatching()`](#stopwatching)
* [`addListener('shake', ...)`](#addlistenershake-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startWatching(...)

```typescript
startWatching(options?: StartWatchingOptions | undefined) => Promise<void>
```

Start watching for shake gestures.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#startwatchingoptions">StartWatchingOptions</a></code> |

**Since:** 0.1.0

--------------------


### stopWatching()

```typescript
stopWatching() => Promise<void>
```

Stop watching for shake gestures.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### addListener('shake', ...)

```typescript
addListener(eventName: 'shake', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when a shake gesture is detected.

Only available on Android and iOS.

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>'shake'</code>       |
| **`listenerFunc`** | <code>() =&gt; void</code> |

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


#### StartWatchingOptions

| Prop              | Type                                                | Description                                                                                                                                                 | Default               | Since |
| ----------------- | --------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------- | ----- |
| **`sensitivity`** | <code><a href="#sensitivity">Sensitivity</a></code> | The sensitivity of the shake detection. Use `'light'` to detect gentle shakes and `'hard'` to only detect strong shakes. Only available on Android and iOS. | <code>'medium'</code> | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### Sensitivity

<code>'hard' | 'light' | 'medium'</code>

</docgen-api>

## Sensitivity Levels

The `sensitivity` option controls how strong a shake must be to emit a `shake` event:

- `light`: A gentle shake is enough to trigger an event.
- `medium`: A moderate shake is required to trigger an event (default).
- `hard`: Only a strong shake triggers an event.

## FAQ

### How is this plugin different from other similar plugins?

It focuses on doing one thing well: detecting physical shake gestures on Android and iOS through a single, fully typed API. You can tune how strong a shake must be with three sensitivity levels, and the motion sensor only runs between `startWatching(...)` and `stopWatching()`, so it stays battery-friendly. It is actively maintained against the latest Capacitor version, giving you consistent shake behavior on both platforms from one dependency.

### Which platforms are supported by the Shake plugin?

The plugin is available on Android and iOS. On the Web, all methods reject as unimplemented, since browsers do not provide a comparable shake detection API.

### Does shake detection drain the battery?

No, the plugin is designed to be battery-friendly. The motion sensor is only active between `startWatching(...)` and `stopWatching()`, so no sensor data is processed while you are not watching for shakes. Remember to call `stopWatching()` and `removeAllListeners()` when you no longer need shake detection.

### How can I control how strong a shake must be?

Pass the `sensitivity` option to `startWatching(...)`. Use `light` to detect gentle shakes, `medium` (the default) for moderate shakes, or `hard` to only detect strong shakes. See [Sensitivity Levels](#sensitivity-levels) for details.

### Do I need any permissions to detect shake gestures?

No, the plugin does not require any additional permissions or privacy descriptions on Android or iOS. You can install it and start watching for shake gestures right away.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Accelerometer](https://capawesome.io/docs/sdks/capacitor/accelerometer/): Capture the acceleration force along the x, y, and z axes.
- [Gyroscope](https://capawesome.io/docs/sdks/capacitor/gyroscope/): Read the device's gyroscope sensor.
- [Haptics](https://capawesome.io/docs/sdks/capacitor/haptics/): Provide haptic feedback such as impacts, notifications, and vibrations.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/shake/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/shake/LICENSE).
