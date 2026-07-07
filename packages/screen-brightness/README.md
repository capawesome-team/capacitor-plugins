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
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
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

```typescript
import { ScreenBrightness } from '@capawesome/capacitor-screen-brightness';

const getBrightness = async () => {
  const { brightness } = await ScreenBrightness.getBrightness();
  return brightness;
};

const setBrightness = async () => {
  await ScreenBrightness.setBrightness({ brightness: 1 });
};

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screen-brightness/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/screen-brightness/LICENSE).
