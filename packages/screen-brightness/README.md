# Capacitor Screen Brightness Plugin

Capacitor plugin for reading and controlling the screen brightness.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- ☀️ **Read brightness**: Read the current screen brightness.
- 🔆 **Set brightness**: Set the screen brightness, e.g. to boost it for barcode or ticket display.
- 🔄 **Reset brightness**: Hand the brightness control back to the operating system.
- 🤝 **Compatibility**: Works alongside the [Keep Awake](https://capawesome.io/docs/sdks/capacitor/keep-awake/) and [Screen Orientation](https://capawesome.io/docs/sdks/capacitor/screen-orientation/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Screen Brightness plugin is typically used whenever an app needs to take control of the display brightness, for example:

- **Barcode and QR code display**: Boost the brightness to maximum so scanners can reliably read tickets, boarding passes, or loyalty cards.
- **Reading and media apps**: Dim the screen for a more comfortable viewing experience in dark environments.
- **Brightness-aware features**: Read the current brightness to adapt your UI or restore it later.
- **Kiosk and point-of-sale apps**: Keep the display at a defined brightness level while the app is in use and hand control back to the operating system afterwards.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-screen-brightness` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands:

```bash
npm install @capawesome/capacitor-screen-brightness
npx cap sync
```

This plugin is available on **Android** and **iOS**. On Web, all methods reject as unimplemented.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to get, set, and reset the screen brightness.

### Get the current screen brightness

Read the current screen brightness as a value between `0.0` (darkest) and `1.0` (brightest):

```typescript
import { ScreenBrightness } from '@capawesome/capacitor-screen-brightness';

const getBrightness = async () => {
  const { brightness } = await ScreenBrightness.getBrightness();
  return brightness;
};
```

### Set the screen brightness

Set the screen brightness to a value between `0.0` (darkest) and `1.0` (brightest). On Android, only the brightness of the current app window is changed and the change is reverted automatically as soon as the app is closed. On iOS, this changes the system brightness and the change persists after the app is closed:

```typescript
import { ScreenBrightness } from '@capawesome/capacitor-screen-brightness';

const setBrightness = async () => {
  await ScreenBrightness.setBrightness({ brightness: 1 });
};
```

### Reset the screen brightness

Hand the brightness control back to the operating system. Only available on Android:

```typescript
import { ScreenBrightness } from '@capawesome/capacitor-screen-brightness';

const resetBrightness = async () => {
  await ScreenBrightness.resetBrightness();
};
```

## API

<docgen-index>

* [`getBrightness()`](#getbrightness)
* [`resetBrightness()`](#resetbrightness)
* [`setBrightness(...)`](#setbrightness)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getBrightness()

```typescript
getBrightness() => Promise<GetBrightnessResult>
```

Get the current screen brightness.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getbrightnessresult">GetBrightnessResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### resetBrightness()

```typescript
resetBrightness() => Promise<void>
```

Reset the screen brightness to the system default.

This hands the brightness control back to the operating system.

Only available on Android.

**Since:** 0.1.0

--------------------


### setBrightness(...)

```typescript
setBrightness(options: SetBrightnessOptions) => Promise<void>
```

Set the screen brightness.

On Android, only the brightness of the current app window is changed.
The change is reverted automatically as soon as the app is closed.

On iOS, this changes the **system** brightness and the change persists
after the app is closed.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#setbrightnessoptions">SetBrightnessOptions</a></code> |

**Since:** 0.1.0

--------------------


### Interfaces


#### GetBrightnessResult

| Prop             | Type                | Description                                                                             | Since |
| ---------------- | ------------------- | --------------------------------------------------------------------------------------- | ----- |
| **`brightness`** | <code>number</code> | The current screen brightness as a value between `0.0` (darkest) and `1.0` (brightest). | 0.1.0 |


#### SetBrightnessOptions

| Prop             | Type                | Description                                                                            | Since |
| ---------------- | ------------------- | -------------------------------------------------------------------------------------- | ----- |
| **`brightness`** | <code>number</code> | The screen brightness to set as a value between `0.0` (darkest) and `1.0` (brightest). | 0.1.0 |

</docgen-api>

## Platform Notes

### Android

Setting the brightness only affects the **current app window** and does not require the `WRITE_SETTINGS` permission. The change is reverted automatically as soon as the app is closed. Use `resetBrightness()` to hand the brightness control back to the operating system while the app is running.

### iOS

Setting the brightness changes the **system** brightness and the change **persists** after the app is closed. There is no system API to hand the brightness control back to the operating system, so `resetBrightness()` is not available on iOS and rejects as unimplemented.

## FAQ

### How is this plugin different from other similar plugins?

It reads, sets, and (on Android) resets the screen brightness through a small, fully typed API, using a normalized `0.0`–`1.0` range on both platforms. On Android it changes only the current app window, so no `WRITE_SETTINGS` permission is required and the change reverts automatically when the app closes, which makes use cases like boosting brightness to display a barcode or ticket straightforward. It supports CocoaPods and Swift Package Manager on iOS and is actively maintained against the latest Capacitor version.

### Why does the brightness stay changed after closing my app on iOS?

On iOS, setting the brightness changes the system brightness, which persists after the app is closed. There is no system API to hand the brightness control back to the operating system on iOS. If you want to be a good citizen, read the current brightness with `getBrightness()` before changing it and restore that value when your app goes to the background.

### Why is the resetBrightness method not available on iOS?

The `resetBrightness()` method hands the brightness control back to the operating system, which is only possible on Android. On iOS, there is no system API for this, so the method rejects as unimplemented. See the [Platform Notes](#platform-notes) section for details.

### Does setting the brightness require any permissions on Android?

No. On Android, the plugin only changes the brightness of the current app window, which does not require the `WRITE_SETTINGS` permission. The change is reverted automatically as soon as the app is closed.

### What values can I use for the brightness?

The brightness is a number between `0.0` (darkest) and `1.0` (brightest). The same range is used by `getBrightness()` when reading the current brightness.

### Does this plugin work on the Web?

No. The plugin is available on Android and iOS. On the Web, all methods reject as unimplemented, since browsers do not provide an API to control the screen brightness.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Keep Awake](https://capawesome.io/docs/sdks/capacitor/keep-awake/): Keep the screen awake while your app is in use.
- [Screen Orientation](https://capawesome.io/docs/sdks/capacitor/screen-orientation/): Lock and unlock the screen orientation.
- [Torch](https://capawesome.io/docs/sdks/capacitor/torch/): Switch the flashlight on and off.
- [Volume](https://capawesome.io/docs/sdks/capacitor/volume/): Control the volume and observe hardware volume button presses.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screen-brightness/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screen-brightness/LICENSE).
