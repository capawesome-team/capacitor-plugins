# Capacitor Device Info Plugin

Capacitor plugin to read device information, such as the model, manufacturer, operating system, memory, and a per-install identifier.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🆔 **Identifier**: Read a unique per-install identifier for the device.
- 📱 **Information**: Read the model, manufacturer, operating system, and more.
- 🧠 **Memory**: Read the total memory of the device and the memory used by the app.
- ⏱️ **Uptime**: Read how long the device has been running since its last boot.
- 🖥️ **Cross-platform**: Support for Android, iOS, and Web.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [Battery](https://capawesome.io/docs/sdks/capacitor/battery/) and [Localization](https://capawesome.io/docs/sdks/capacitor/localization/) plugins.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Device Info plugin is typically used whenever an app needs to know more about the device it is running on, for example:

- **Bug reports and support**: Attach the device model, manufacturer, operating system version, and WebView version to support tickets or crash reports.
- **Device registration**: Use the per-install identifier to register a device on your backend without collecting personal data.
- **Adaptive user interfaces**: Adjust your layout based on the device type, for example phone versus tablet.
- **Feature gating**: Enable or disable features depending on the Android SDK version or the major iOS version.
- **Diagnostics**: Monitor the memory used by your app or detect whether it is running on an emulator or simulator.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-device-info` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-device-info
npx cap sync
```

No additional permissions or configuration are required on any platform.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to read the device identifier, read device information, and read the device uptime.

### Read the device identifier

Get a unique per-install identifier for the device, for example to register the device on your backend. See the [API documentation](#getid) for when the identifier is reset on each platform:

```typescript
import { DeviceInfo } from '@capawesome/capacitor-device-info';

const getId = async () => {
  const { identifier } = await DeviceInfo.getId();
  return identifier;
};
```

### Read device information

Get information about the device, such as the model, manufacturer, operating system, device type, and memory. Fields that a platform cannot determine are `null` (see [Platform Support](#platform-support)):

```typescript
import { DeviceInfo } from '@capawesome/capacitor-device-info';

const getInfo = async () => {
  const info = await DeviceInfo.getInfo();
  return info;
};
```

### Read the device uptime

Get the time the device has been running since its last boot, in milliseconds. Only available on Android and iOS:

```typescript
import { DeviceInfo } from '@capawesome/capacitor-device-info';

const getUptime = async () => {
  const { uptime } = await DeviceInfo.getUptime();
  return uptime;
};
```

## API

<docgen-index>

* [`getId()`](#getid)
* [`getInfo()`](#getinfo)
* [`getUptime()`](#getuptime)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getId()

```typescript
getId() => Promise<GetIdResult>
```

Get a unique identifier for the device.

On **Android**, the identifier is the `ANDROID_ID` value, which is unique
per app-signing key, user, and device. It is reset when the app is
reinstalled after the signing key changes or the device is factory reset.

On **iOS**, the identifier is the `identifierForVendor` value, which is
unique per vendor and device. It is reset when all apps from the vendor are
uninstalled.

On **Web**, the identifier is a random UUID that is persisted in the
browser's `localStorage`. It is reset when the browser storage is cleared.

**Returns:** <code>Promise&lt;<a href="#getidresult">GetIdResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getInfo()

```typescript
getInfo() => Promise<GetInfoResult>
```

Get information about the device.

**Returns:** <code>Promise&lt;<a href="#getinforesult">GetInfoResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getUptime()

```typescript
getUptime() => Promise<GetUptimeResult>
```

Get the time the device has been running since its last boot, in
milliseconds.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getuptimeresult">GetUptimeResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### Interfaces


#### GetIdResult

| Prop             | Type                | Description                          | Since |
| ---------------- | ------------------- | ------------------------------------ | ----- |
| **`identifier`** | <code>string</code> | The unique identifier of the device. | 0.1.0 |


#### GetInfoResult

| Prop                    | Type                                                        | Description                                                                                                                                                                                                         | Since |
| ----------------------- | ----------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`androidSdkVersion`** | <code>number \| null</code>                                 | The Android SDK version number (API level). Returns `null` on platforms other than Android. Only available on Android.                                                                                              | 0.1.0 |
| **`deviceType`**        | <code><a href="#devicetype">DeviceType</a></code>           | The type of the device.                                                                                                                                                                                             | 0.1.0 |
| **`iosVersion`**        | <code>number \| null</code>                                 | The major version number of iOS. Returns `null` on platforms other than iOS. Only available on iOS.                                                                                                                 | 0.1.0 |
| **`isVirtual`**         | <code>boolean</code>                                        | Whether the app is running on a virtual device (simulator or emulator).                                                                                                                                             | 0.1.0 |
| **`manufacturer`**      | <code>string</code>                                         | The manufacturer of the device.                                                                                                                                                                                     | 0.1.0 |
| **`model`**             | <code>string</code>                                         | The model identifier of the device. On **iOS**, this is the internal model identifier (e.g. `iPhone13,4`), not the marketing name.                                                                                  | 0.1.0 |
| **`name`**              | <code>string \| null</code>                                 | The name of the device. On **iOS 16 and newer**, a generic device name (e.g. `iPhone`) is returned unless the app has the required entitlement. Returns `null` if the name cannot be determined.                    | 0.1.0 |
| **`operatingSystem`**   | <code><a href="#operatingsystem">OperatingSystem</a></code> | The operating system of the device.                                                                                                                                                                                 | 0.1.0 |
| **`osVersion`**         | <code>string</code>                                         | The version of the operating system.                                                                                                                                                                                | 0.1.0 |
| **`platform`**          | <code><a href="#platform">Platform</a></code>               | The platform the app is running on.                                                                                                                                                                                 | 0.1.0 |
| **`totalMemory`**       | <code>number \| null</code>                                 | The total amount of memory of the device, in bytes. Returns `null` if the total memory cannot be determined. Only available on Android and iOS.                                                                     | 0.1.0 |
| **`usedMemory`**        | <code>number \| null</code>                                 | The amount of memory used by the app, in bytes. Returns `null` if the used memory cannot be determined. Only available on Android and iOS.                                                                          | 0.1.0 |
| **`webViewVersion`**    | <code>string \| null</code>                                 | The version of the WebView that renders the app. On **iOS**, the version of the operating system is returned, because the WebKit version is tied to it. Returns `null` if the WebView version cannot be determined. | 0.1.0 |


#### GetUptimeResult

| Prop         | Type                | Description                                                                | Since |
| ------------ | ------------------- | -------------------------------------------------------------------------- | ----- |
| **`uptime`** | <code>number</code> | The time the device has been running since its last boot, in milliseconds. | 0.1.0 |


### Type Aliases


#### DeviceType

The type of a device.

- `phone`: A handheld phone-sized device.
- `tablet`: A tablet-sized device.
- `desktop`: A desktop or laptop computer.
- `tv`: A television or set-top box.
- `unknown`: The type could not be determined.

<code>'phone' | 'tablet' | 'desktop' | 'tv' | 'unknown'</code>


#### OperatingSystem

The operating system of a device.

<code>'android' | 'ios' | 'windows' | 'mac' | 'unknown'</code>


#### Platform

The platform an app is running on.

<code>'android' | 'ios' | 'web'</code>

</docgen-api>

## Platform Support

The plugin returns `null` for any field that a platform cannot determine. The following table shows which fields of the `getInfo()` result are available on each platform:

| Property            | Android | iOS | Web              |
| ------------------- | ------- | --- | ---------------- |
| `androidSdkVersion` | ✅      | ❌  | ❌               |
| `deviceType`        | ✅      | ✅  | ✅ (best-effort) |
| `iosVersion`        | ❌      | ✅  | ❌               |
| `isVirtual`         | ✅      | ✅  | ✅               |
| `manufacturer`      | ✅      | ✅  | ❌               |
| `model`             | ✅      | ✅  | ✅ (best-effort) |
| `name`              | ✅      | ✅  | ❌               |
| `operatingSystem`   | ✅      | ✅  | ✅ (best-effort) |
| `osVersion`         | ✅      | ✅  | ✅ (best-effort) |
| `platform`          | ✅      | ✅  | ✅               |
| `totalMemory`       | ✅      | ✅  | ❌               |
| `usedMemory`        | ✅      | ✅  | ❌               |
| `webViewVersion`    | ✅      | ✅  | ✅ (best-effort) |

The `getUptime()` method is only available on Android and iOS.

## Migration from `@capacitor/device`

This plugin can be used as a replacement for the official [`@capacitor/device`](https://capacitorjs.com/docs/apis/device) plugin. The following table shows how the methods map:

| `@capacitor/device` | `@capawesome/capacitor-device-info`                                                    |
| ------------------- | -------------------------------------------------------------------------------------- |
| `getId()`           | `getId()`                                                                              |
| `getInfo()`         | `getInfo()`                                                                            |
| `getBatteryInfo()`  | Use the [Battery](https://capawesome.io/docs/sdks/capacitor/battery/) plugin           |
| `getLanguageCode()` | Use the [Localization](https://capawesome.io/docs/sdks/capacitor/localization/) plugin |
| `getLanguageTag()`  | Use the [Localization](https://capawesome.io/docs/sdks/capacitor/localization/) plugin |

## FAQ

### How stable is the identifier returned by `getId`?

The identifier is unique per install. On Android, it is the `ANDROID_ID` value, which is reset when the app is reinstalled after the signing key changes or the device is factory reset. On iOS, it is the `identifierForVendor` value, which is reset when all apps from the vendor are uninstalled. On the Web, it is a random UUID persisted in the browser's `localStorage`, which is reset when the browser storage is cleared.

### Can this plugin replace the official `@capacitor/device` plugin?

Yes, this plugin can be used as a replacement for the official `@capacitor/device` plugin. The `getId()` and `getInfo()` methods map directly, while battery information and language settings are covered by the [Battery](https://capawesome.io/docs/sdks/capacitor/battery/) and [Localization](https://capawesome.io/docs/sdks/capacitor/localization/) plugins. See the [migration table](#migration-from-capacitordevice) above.

### Why is the device name always a generic value like `iPhone` on iOS?

On iOS 16 and newer, a generic device name is returned unless the app has the required entitlement from Apple. If the name cannot be determined at all, the `name` property is `null`.

### Why are some fields `null` on my platform?

The plugin returns `null` for any field that a platform cannot determine. For example, `totalMemory` and `usedMemory` are only available on Android and iOS, and `androidSdkVersion` is only available on Android. See the [Platform Support](#platform-support) section for a complete overview.

### Can I get the battery level or the device language with this plugin?

No, this plugin focuses on device information such as the model, manufacturer, operating system, and memory. For battery information, use the [Battery](https://capawesome.io/docs/sdks/capacitor/battery/) plugin. For the user's language and locale preferences, use the [Localization](https://capawesome.io/docs/sdks/capacitor/localization/) plugin.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Battery](https://capawesome.io/docs/sdks/capacitor/battery/): Access battery information.
- [Localization](https://capawesome.io/docs/sdks/capacitor/localization/): Read the user's localization preferences, such as preferred locales and time zone.
- [Network](https://capawesome.io/docs/sdks/capacitor/network/): Access network information.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/device-info/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/device-info/LICENSE).
