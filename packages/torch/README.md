# Capacitor Torch Plugin

Capacitor plugin for switching the flashlight on and off.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Torch plugin is one of the most complete flashlight control solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android, iOS, and Web.
- 🔦 **Torch control**: Enable, disable, and toggle torch/flashlight.
- ✅ **Availability check**: Check if torch is available on the device.
- 🌐 **Web support**: Uses MediaStream API for web torch control.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Torch plugin is typically used whenever an app needs to control the device's flashlight, for example:

- **Flashlight features**: Turn the flashlight on and off with a single tap in your app.
- **Barcode and QR code scanning**: Let users enable the torch to scan codes in low-light environments.
- **Document capture**: Improve capture quality in dark surroundings by enabling the torch.
- **Signaling**: Toggle the torch to draw attention, for example as an emergency signal light.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-torch` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-torch
npx cap sync
```

### Android

#### Permissions

This API requires the following permissions be added to your `AndroidManifest.xml` before the `application` tag:

```xml
<!-- To get access to the flashlight. -->
<uses-permission android:name="android.permission.FLASHLIGHT"/>
```

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$androidxCameraCoreVersion` version of `androidx.camera:camera-core` (default: `1.5.2`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

## Usage

Import the plugin and call its methods:

```typescript
import { Torch } from '@capawesome/capacitor-torch';
```

### Check if the torch is available

Not every device has a torch, so check the availability before showing a flashlight control in your UI:

```typescript
const isAvailable = async () => {
    const result = await Torch.isAvailable();
    return result.available;
};
```

### Turn the torch on and off

Enable or disable the torch. On Android, these methods require SDK 23 or newer. On the Web, you can optionally pass your own `MediaStream` with a video track via the `stream` option:

```typescript
const enable = async () => {
  await Torch.enable();
};

const disable = async () => {
  await Torch.disable();
};
```

### Toggle the torch

Switch the torch to the opposite state with a single call:

```typescript
const toggle = async () => {
  await Torch.toggle();
};
```

### Check if the torch is enabled

Read the current state of the torch, for example to display the state in your UI:

```typescript
const isEnabled = async () => {
    const result = await Torch.isEnabled();
    return result.enabled;
};
```

## API

<docgen-index>

* [`enable(...)`](#enable)
* [`disable(...)`](#disable)
* [`isAvailable()`](#isavailable)
* [`isEnabled(...)`](#isenabled)
* [`toggle(...)`](#toggle)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### enable(...)

```typescript
enable(options?: EnableOptions | undefined) => Promise<void>
```

Enable the torch.

Only available on Android (SDK 23+), iOS and Web.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#enableoptions">EnableOptions</a></code> |

**Since:** 6.0.0

--------------------


### disable(...)

```typescript
disable(options?: DisableOptions | undefined) => Promise<void>
```

Disable the torch.

Only available on Android (SDK 23+), iOS and Web.

| Param         | Type                                                      |
| ------------- | --------------------------------------------------------- |
| **`options`** | <code><a href="#disableoptions">DisableOptions</a></code> |

**Since:** 6.0.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if the torch is available.

Only available on Android, iOS and Web.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### isEnabled(...)

```typescript
isEnabled(options?: IsEnabledOptions | undefined) => Promise<IsEnabledResult>
```

Check if the torch is enabled.

Only available on Android, iOS and Web.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#isenabledoptions">IsEnabledOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#isenabledresult">IsEnabledResult</a>&gt;</code>

**Since:** 6.0.0

--------------------


### toggle(...)

```typescript
toggle(options?: ToggleOptions | undefined) => Promise<void>
```

Toggle the torch.

Only available on Android (SDK 23+), iOS and Web.

| Param         | Type                                                    |
| ------------- | ------------------------------------------------------- |
| **`options`** | <code><a href="#toggleoptions">ToggleOptions</a></code> |

**Since:** 6.0.0

--------------------


### Interfaces


#### EnableOptions

| Prop         | Type                     | Description                                                                                                                                                                                             | Since |
| ------------ | ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`stream`** | <code>MediaStream</code> | The stream of media to enable the torch on. **Attention**: The stream must have a video track. The facing mode of the video track must be the one that corresponds to the torch. Only available on Web. | 6.2.0 |


#### DisableOptions

| Prop         | Type                     | Description                                                                                                                                                                                              | Since |
| ------------ | ------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`stream`** | <code>MediaStream</code> | The stream of media to disable the torch on. **Attention**: The stream must have a video track. The facing mode of the video track must be the one that corresponds to the torch. Only available on Web. | 6.2.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                            | Since |
| --------------- | -------------------- | -------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether the torch is available or not. | 6.0.0 |


#### IsEnabledResult

| Prop          | Type                 | Description                          | Since |
| ------------- | -------------------- | ------------------------------------ | ----- |
| **`enabled`** | <code>boolean</code> | Whether the torch is enabled or not. | 6.0.0 |


#### IsEnabledOptions

| Prop         | Type                     | Description                                                                                                                                                                                                          | Since |
| ------------ | ------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`stream`** | <code>MediaStream</code> | The stream of media to check if the torch is enabled on. **Attention**: The stream must have a video track. The facing mode of the video track must be the one that corresponds to the torch. Only available on Web. | 6.2.0 |


#### ToggleOptions

| Prop         | Type                     | Description                                                                                                                                                                                             | Since |
| ------------ | ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`stream`** | <code>MediaStream</code> | The stream of media to toggle the torch on. **Attention**: The stream must have a video track. The facing mode of the video track must be the one that corresponds to the torch. Only available on Web. | 6.2.0 |

</docgen-api>

## FAQ

### Which platforms are supported by this plugin?

The plugin is available on Android, iOS and Web. On Android, the `enable(...)`, `disable(...)` and `toggle(...)` methods require SDK 23 or newer. On the Web, the plugin uses the MediaStream API to control the torch.

### Why does `isAvailable` return false on my device?

The `isAvailable()` method reports whether the current device provides a torch. It returns `false` on devices without a torch, for example many tablets or desktop browsers. Use this method to hide or disable your flashlight UI on such devices.

### Do I need any permissions to use this plugin?

On Android, you need to add the `FLASHLIGHT` permission to your `AndroidManifest.xml` file, see the [Installation](#installation) section. No permissions are required on iOS.

### How does the torch control work on the web?

The web implementation uses the MediaStream API. You can optionally pass your own `MediaStream` via the `stream` option of `enable(...)`, `disable(...)`, `isEnabled(...)` and `toggle(...)`. The stream must have a video track, and the facing mode of the video track must be the one that corresponds to the torch.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Barcode Scanning](https://capawesome.io/docs/sdks/capacitor/mlkit/barcode-scanning/): Scan barcodes and QR codes with ML Kit Barcode Scanning.
- [Screen Brightness](https://capawesome.io/docs/sdks/capacitor/screen-brightness/): Read and control the screen brightness.
- [Light Sensor](https://capawesome.io/docs/sdks/capacitor/light-sensor/): Read the device's ambient light sensor.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/torch/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/torch/LICENSE).
