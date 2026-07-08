# Capacitor Android Battery Optimization Plugin

Capacitor plugin for Android to manage battery optimization settings, request exemptions, and enhance app performance under Doze and App Standby modes.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Use Cases

The Android Battery Optimization plugin is typically used by apps whose core function is affected by Doze and App Standby, for example:

- **Background reliability checks**: Detect whether battery optimization is enabled and inform the user that background work may be deferred.
- **Guided settings flow**: Open the battery optimization settings page so the user can manually exclude your app from battery optimization.
- **Direct exemption requests**: Request the battery optimization ignore permission when the core function of your app is adversely affected by Power Management features.
- **Troubleshooting support**: Help users understand why they receive delayed updates from your app by checking the battery optimization status.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-android-battery-optimization` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-android-battery-optimization
npx cap sync
```

### Android

This API requires the following permissions be added to your `AndroidManifest.xml` before the `application` tag if you want to request direct exemption from Power Management features:

```xml
<uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />
```

⚠️ **Attention**: Google Play policies prohibit apps from requesting direct exemption from Power Management features in Android 6.0+ (Doze and App Standby) unless the core function of the app is adversely affected. [Source](https://developer.android.com/training/monitoring-device-state/doze-standby.html#support_for_other_use_cases)

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-plugin-demo](https://github.com/robingenz/capacitor-plugin-demo)

| Android                                                                                                                                            |
| -------------------------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://user-images.githubusercontent.com/13857929/188197064-c042a970-f555-40b8-a19d-d05fc2443b5a.gif" width="324" alt="Android Demo" /> |

## Usage

Import the plugin and call its methods:

```typescript
import { Capacitor } from '@capacitor/core';
import { BatteryOptimization } from '@capawesome-team/capacitor-android-battery-optimization';
```

### Check if battery optimization is enabled

Find out whether battery optimization is enabled for the device, for example to inform the user that background work may be deferred. All methods of this plugin are only available on Android, so guard your calls with a platform check:

```typescript
const isBatteryOptimizationEnabled = async () => {
  if (Capacitor.getPlatform() !== 'android') {
    return false;
  }
  const { enabled } = await BatteryOptimization.isBatteryOptimizationEnabled();
  return enabled;
};
```

### Open the battery optimization settings

Open the battery optimization settings page so the user can manually exclude your app from battery optimization:

```typescript
const openBatteryOptimizationSettings = async () => {
  if (Capacitor.getPlatform() !== 'android') {
    return;
  }
  await BatteryOptimization.openBatteryOptimizationSettings();
};
```

### Request an exemption from battery optimization

Request the battery optimization ignore permission directly. This method requires the `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS` manifest permission (see [Installation](#installation)) and should only be used if your app meets an acceptable use case according to the Google Play policy:

```typescript
const requestIgnoreBatteryOptimization = async () => {
  if (Capacitor.getPlatform() !== 'android') {
    return;
  }
  await BatteryOptimization.requestIgnoreBatteryOptimization();
};
```

## API

<docgen-index>

* [`isBatteryOptimizationEnabled()`](#isbatteryoptimizationenabled)
* [`openBatteryOptimizationSettings()`](#openbatteryoptimizationsettings)
* [`requestIgnoreBatteryOptimization()`](#requestignorebatteryoptimization)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### isBatteryOptimizationEnabled()

```typescript
isBatteryOptimizationEnabled() => Promise<IsBatteryOptimizationEnabledResult>
```

Returns whether or not battery optimization is enabled.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#isbatteryoptimizationenabledresult">IsBatteryOptimizationEnabledResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### openBatteryOptimizationSettings()

```typescript
openBatteryOptimizationSettings() => Promise<void>
```

Opens the battery optimization settings page.

Only available on Android.

**Since:** 0.0.1

--------------------


### requestIgnoreBatteryOptimization()

```typescript
requestIgnoreBatteryOptimization() => Promise<void>
```

Requests the battery optimization ignore permission.
This method needs the `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS` manifest permission.
Use this method only if your app meets an acceptable use case (see Google Play Policy).

Only available on Android.

**Since:** 0.0.1

--------------------


### Interfaces


#### IsBatteryOptimizationEnabledResult

| Prop          | Type                 | Description                                     | Since |
| ------------- | -------------------- | ----------------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Whether or not battery optimization is enabled. | 0.0.1 |

</docgen-api>

## FAQ

### Does this plugin work on iOS or the Web?

No, all methods of this plugin are only available on Android, since battery optimization with Doze and App Standby is an Android concept. On other platforms, guard your calls with a platform check using `Capacitor.getPlatform()` as shown in the [usage examples](#usage).

### What is the difference between opening the settings and requesting an exemption?

The `openBatteryOptimizationSettings()` method opens the battery optimization settings page, where the user has to find and exclude your app manually. The `requestIgnoreBatteryOptimization()` method asks the user directly to exempt your app, but requires the `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS` manifest permission and is subject to Google Play policy restrictions.

### Do I need to add any permissions to my app?

Only if you want to use `requestIgnoreBatteryOptimization()`. In that case, add the `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS` permission to your `AndroidManifest.xml` as described in the [Installation](#installation) section. Checking the battery optimization status and opening the settings page do not require any permissions.

### Can requesting an exemption get my app rejected from Google Play?

Google Play policies prohibit apps from requesting direct exemption from Power Management features in Android 6.0+ (Doze and App Standby) unless the core function of the app is adversely affected. Use `requestIgnoreBatteryOptimization()` only if your app meets an acceptable use case, or open the battery optimization settings with `openBatteryOptimizationSettings()` instead. See the [Android documentation](https://developer.android.com/training/monitoring-device-state/doze-standby.html#support_for_other_use_cases) for more information.

### Why is background work in my app delayed on Android?

When battery optimization is enabled, Android defers background work under Doze and App Standby, which can delay tasks your app performs in the background. You can use `isBatteryOptimizationEnabled()` to detect this and guide the user to exclude your app from battery optimization.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Android Foreground Service](https://capawesome.io/docs/sdks/capacitor/android-foreground-service/): Run a foreground service on Android.
- [Background Task](https://capawesome.io/docs/sdks/capacitor/background-task/): Run background tasks in your app.
- [Battery](https://capawesome.io/docs/sdks/capacitor/battery/): Access battery information.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-battery-optimization/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-battery-optimization/LICENSE).
