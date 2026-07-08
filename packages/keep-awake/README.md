# Capacitor Keep Awake Plugin

Capacitor plugin to keep the screen awake.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🌙 **Keep awake**: Prevent the screen from dimming or turning off.
- 💤 **Allow sleep**: Restore the default screen sleep behavior at any time.
- 🔍 **State**: Read whether the screen is currently kept awake.
- 🌐 **Cross-platform**: Supports Android, iOS and Web (via the Screen Wake Lock API).
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Keep Awake plugin is typically used whenever the screen must stay visible without user interaction, for example:

- **Media playback**: Keep the screen on while users watch videos or follow along with content.
- **Navigation and tracking**: Prevent the display from turning off during turn-by-turn navigation or workout tracking.
- **Hands-free reading**: Keep recipes, sheet music, or step-by-step instructions visible while the user's hands are busy.
- **Dashboards and kiosks**: Display live data or presentations without the screen dimming or sleeping.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-keep-awake` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands:

```bash
npm install @capawesome/capacitor-keep-awake
npx cap sync
```

This plugin is available on **Android**, **iOS** and **Web**. No additional permissions or configuration are required.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to keep the screen awake, allow it to sleep again, check whether it is currently kept awake, and check whether the feature is available.

### Keep the screen awake

Prevent the screen from dimming or turning off. The screen is kept awake until `allowSleep(...)` is called or the app is restarted. This only affects your app and is not a system-wide setting:

```typescript
import { KeepAwake } from '@capawesome/capacitor-keep-awake';

const keepAwake = async () => {
  await KeepAwake.keepAwake();
};
```

### Allow the screen to sleep again

Restore the default screen sleep behavior at any time:

```typescript
import { KeepAwake } from '@capawesome/capacitor-keep-awake';

const allowSleep = async () => {
  await KeepAwake.allowSleep();
};
```

### Check if the screen is kept awake

Read whether the screen is currently kept awake:

```typescript
import { KeepAwake } from '@capawesome/capacitor-keep-awake';

const isKeptAwake = async () => {
  const { keptAwake } = await KeepAwake.isKeptAwake();
  return keptAwake;
};
```

### Check if the feature is available

On the Web, keeping the screen awake depends on whether the browser supports the [Screen Wake Lock API](https://developer.mozilla.org/en-US/docs/Web/API/Screen_Wake_Lock_API). On Android and iOS, this always returns `true`:

```typescript
import { KeepAwake } from '@capawesome/capacitor-keep-awake';

const isAvailable = async () => {
  const { available } = await KeepAwake.isAvailable();
  return available;
};
```

## API

<docgen-index>

* [`allowSleep()`](#allowsleep)
* [`isAvailable()`](#isavailable)
* [`isKeptAwake()`](#iskeptawake)
* [`keepAwake()`](#keepawake)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### allowSleep()

```typescript
allowSleep() => Promise<void>
```

Allow the screen to dim or turn off again.

This reverses the effect of `keepAwake(...)`.

Only available on Android, iOS and Web.

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if keeping the screen awake is available on this device.

On Web, this depends on whether the browser supports the
[Screen Wake Lock API](https://developer.mozilla.org/en-US/docs/Web/API/Screen_Wake_Lock_API).
On Android and iOS, this always returns `true`.

Only available on Android, iOS and Web.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isKeptAwake()

```typescript
isKeptAwake() => Promise<IsKeptAwakeResult>
```

Check if the screen is currently kept awake.

Only available on Android, iOS and Web.

**Returns:** <code>Promise&lt;<a href="#iskeptawakeresult">IsKeptAwakeResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### keepAwake()

```typescript
keepAwake() => Promise<void>
```

Keep the screen awake by preventing it from dimming or turning off.

The screen is kept awake until `allowSleep(...)` is called or the app is
restarted. This setting only affects your app and is not system-wide.

Only available on Android, iOS and Web.

**Since:** 0.1.0

--------------------


### Interfaces


#### IsAvailableResult

| Prop            | Type                 | Description                                                          | Since |
| --------------- | -------------------- | -------------------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether keeping the screen awake is available on this device or not. | 0.1.0 |


#### IsKeptAwakeResult

| Prop            | Type                 | Description                                        | Since |
| --------------- | -------------------- | -------------------------------------------------- | ----- |
| **`keptAwake`** | <code>boolean</code> | Whether the screen is currently kept awake or not. | 0.1.0 |

</docgen-api>

## Behavior

Keep the following in mind when using this plugin:

- **App scope**: Keeping the screen awake only affects your app and is not a system-wide setting.
- **State reset**: The screen is only kept awake until `allowSleep(...)` is called or the app is restarted.
- **Web auto-release**: On the Web, the underlying [Screen Wake Lock](https://developer.mozilla.org/en-US/docs/Web/API/Screen_Wake_Lock_API) is automatically released by the browser whenever the tab becomes hidden (for example, when the user switches tabs or minimizes the window). The plugin automatically re-acquires the wake lock once the tab becomes visible again, as long as `allowSleep(...)` has not been called in the meantime.

## FAQ

### Which platforms does this plugin support?

The plugin supports Android, iOS and Web. On the Web, it relies on the Screen Wake Lock API, so availability depends on the browser. You can check this at runtime with the `isAvailable()` method, which always returns `true` on Android and iOS.

### Does keeping the screen awake affect other apps?

No, keeping the screen awake only affects your app and is not a system-wide setting. As soon as the user leaves your app, the default screen sleep behavior of the device applies again.

### How long is the screen kept awake?

The screen is kept awake until `allowSleep(...)` is called or the app is restarted. The setting is not persisted across app restarts.

### Why does the screen sleep again on the Web after switching tabs?

On the Web, the browser automatically releases the underlying wake lock whenever the tab becomes hidden, for example when the user switches tabs or minimizes the window. The plugin automatically re-acquires the wake lock once the tab becomes visible again, as long as `allowSleep(...)` has not been called in the meantime. See the [Behavior](#behavior) section for details.

### Do I need any permissions or configuration?

No, the plugin does not require any permissions or configuration on any platform. Simply install it and call `keepAwake()`.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Screen Brightness](https://capawesome.io/docs/sdks/capacitor/screen-brightness/): Read and control the screen brightness.
- [Screen Orientation](https://capawesome.io/docs/sdks/capacitor/screen-orientation/): Lock and unlock the screen orientation.
- [Battery](https://capawesome.io/docs/sdks/capacitor/battery/): Access battery information of the device.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/keep-awake/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/keep-awake/LICENSE).
