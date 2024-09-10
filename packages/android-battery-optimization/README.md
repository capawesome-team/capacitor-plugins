# @capawesome-team/capacitor-android-battery-optimization

Capacitor plugin to access battery optimization settings on Android. 

## Installation

See [Getting started with Insiders](https://capawesome.io/insiders/getting-started/?plugin=capacitor-android-battery-optimization) and follow the instructions to install the plugin.

After that, follow the platform-specific instructions in the section [Android](#android).

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

| Android                                                                                                                         |
| ------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://user-images.githubusercontent.com/13857929/188197064-c042a970-f555-40b8-a19d-d05fc2443b5a.gif" width="324" /> |

## Usage

```typescript
import { Capacitor } from '@capacitor/core';
import { BatteryOptimization } from '@capawesome-team/capacitor-android-battery-optimization';

const isBatteryOptimizationEnabled = async () => {
  if (Capacitor.getPlatform() !== 'android') {
    return false;
  }
  const { enabled } = await BatteryOptimization.isBatteryOptimizationEnabled();
  return enabled;
};

const openBatteryOptimizationSettings = async () => {
  if (Capacitor.getPlatform() !== 'android') {
    return;
  }
  await BatteryOptimization.openBatteryOptimizationSettings();
};

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-battery-optimization/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/android-battery-optimization/LICENSE).
