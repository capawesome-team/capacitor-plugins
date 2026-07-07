# @capawesome/capacitor-shake

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

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

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

```typescript
import { Shake } from '@capawesome/capacitor-shake';

const startWatching = async () => {
  await Shake.addListener('shake', () => {
    console.log('Shake detected!');
  });
  await Shake.startWatching({ sensitivity: 'medium' });
};

const stopWatching = async () => {
  await Shake.stopWatching();
  await Shake.removeAllListeners();
};
```

## API

<docgen-index>

* [`addListener('shake', ...)`](#addlistenershake-)
* [`removeAllListeners()`](#removealllisteners)
* [`startWatching(...)`](#startwatching)
* [`stopWatching()`](#stopwatching)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

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


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### StartWatchingOptions

| Prop              | Type                                                | Description                                                                                                                                                 | Default               | Since |
| ----------------- | --------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------- | ----- |
| **`sensitivity`** | <code><a href="#sensitivity">Sensitivity</a></code> | The sensitivity of the shake detection. Use `'light'` to detect gentle shakes and `'hard'` to only detect strong shakes. Only available on Android and iOS. | <code>'medium'</code> | 0.1.0 |


### Type Aliases


#### Sensitivity

<code>'hard' | 'light' | 'medium'</code>

</docgen-api>

## Sensitivity Levels

The `sensitivity` option controls how strong a shake must be to emit a `shake` event:

- `light`: A gentle shake is enough to trigger an event.
- `medium`: A moderate shake is required to trigger an event (default).
- `hard`: Only a strong shake triggers an event.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/shake/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/shake/LICENSE).
