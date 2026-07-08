# Capacitor Battery Plugin

Capacitor plugin to access battery information.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Battery plugin is one of the most complete battery monitoring solutions for Capacitor apps. Here are some of the key features:

- 🔋 **Battery level**: Read the current battery level of the device.
- ⚡ **Battery state**: Read whether the device is charging, full or unplugged.
- 🪫 **Low power mode**: Read whether the low power mode is enabled.
- 👂 **Change events**: Listen for changes to the battery level, state and low power mode.
- 🌐 **Web support**: Read the battery level and state on supported browsers.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🤝 **Compatibility**: Works alongside the [Android Battery Optimization](https://capawesome.io/docs/sdks/capacitor/android-battery-optimization/) plugin.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Battery plugin is typically used whenever an app should adapt its behavior to the device's power situation, for example:

- **Energy-aware features**: Reduce background work, animations, or sync frequency when the battery level is low.
- **Low power mode handling**: Disable power-hungry features when the user has enabled low power mode.
- **Charging-dependent tasks**: Only start heavy tasks such as large downloads while the device is charging.
- **Status display**: Show the current battery level and charging state inside your app, for example in a kiosk or fleet app.
- **Reacting to changes**: Warn the user when the battery level drops by listening for change events.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-battery` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-battery
npx cap sync
```

This plugin does not require any additional configuration or permissions on Android or iOS.

On the **Web**, the battery level and state are only available in browsers that implement the [Battery Status API](https://developer.mozilla.org/en-US/docs/Web/API/Battery_Status_API) (Chromium-based browsers). The low power mode is not available on the Web.

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to get the current battery level and state, check if low power mode is enabled, listen for battery changes, and remove all listeners.

### Get the current battery level

Read the current battery level of the device as a value between `0.0` and `1.0`:

```typescript
import { Battery } from '@capawesome/capacitor-battery';

const getBatteryLevel = async () => {
  const { level } = await Battery.getBatteryLevel();
  return level;
};
```

### Get the current battery state

Check whether the device is charging, full or unplugged:

```typescript
import { Battery } from '@capawesome/capacitor-battery';

const getBatteryState = async () => {
  const { state } = await Battery.getBatteryState();
  return state;
};
```

### Check if low power mode is enabled

Read whether the low power mode (power saver mode on Android, Low Power Mode on iOS) is currently enabled. Only available on Android and iOS:

```typescript
import { Battery } from '@capawesome/capacitor-battery';

const isLowPowerModeEnabled = async () => {
  const { enabled } = await Battery.isLowPowerModeEnabled();
  return enabled;
};
```

### Listen for battery changes

Get notified when the battery level, battery state or low power mode changes. The device is only observed while at least one listener is attached. The `lowPowerModeChange` event is only available on Android and iOS:

```typescript
import { Battery } from '@capawesome/capacitor-battery';

const addBatteryLevelChangeListener = async () => {
  await Battery.addListener('batteryLevelChange', event => {
    console.log('Battery level changed:', event.level);
  });
};

const addBatteryStateChangeListener = async () => {
  await Battery.addListener('batteryStateChange', event => {
    console.log('Battery state changed:', event.state);
  });
};

const addLowPowerModeChangeListener = async () => {
  await Battery.addListener('lowPowerModeChange', event => {
    console.log('Low power mode changed:', event.enabled);
  });
};
```

### Remove all listeners

Remove all listeners that were registered for this plugin:

```typescript
import { Battery } from '@capawesome/capacitor-battery';

const removeAllListeners = async () => {
  await Battery.removeAllListeners();
};
```

## API

<docgen-index>

* [`getBatteryLevel()`](#getbatterylevel)
* [`getBatteryState()`](#getbatterystate)
* [`isLowPowerModeEnabled()`](#islowpowermodeenabled)
* [`addListener('batteryLevelChange', ...)`](#addlistenerbatterylevelchange-)
* [`addListener('batteryStateChange', ...)`](#addlistenerbatterystatechange-)
* [`addListener('lowPowerModeChange', ...)`](#addlistenerlowpowermodechange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getBatteryLevel()

```typescript
getBatteryLevel() => Promise<GetBatteryLevelResult>
```

Get the current battery level of the device.

On the web, this is only supported in browsers that implement the
[Battery Status API](https://developer.mozilla.org/en-US/docs/Web/API/Battery_Status_API)
(Chromium-based browsers).

Only available on Android, iOS and Web.

**Returns:** <code>Promise&lt;<a href="#getbatterylevelresult">GetBatteryLevelResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getBatteryState()

```typescript
getBatteryState() => Promise<GetBatteryStateResult>
```

Get the current battery state of the device.

On the web, this is only supported in browsers that implement the
[Battery Status API](https://developer.mozilla.org/en-US/docs/Web/API/Battery_Status_API)
(Chromium-based browsers).

Only available on Android, iOS and Web.

**Returns:** <code>Promise&lt;<a href="#getbatterystateresult">GetBatteryStateResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isLowPowerModeEnabled()

```typescript
isLowPowerModeEnabled() => Promise<IsLowPowerModeEnabledResult>
```

Get whether the low power mode is currently enabled.

On Android, this refers to the power saver mode. On iOS, this refers to
the Low Power Mode.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#islowpowermodeenabledresult">IsLowPowerModeEnabledResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('batteryLevelChange', ...)

```typescript
addListener(eventName: 'batteryLevelChange', listenerFunc: (event: BatteryLevelChangeEvent) => void) => Promise<PluginListenerHandle>
```

Listen for changes to the battery level of the device.

The device is only observed while at least one listener is attached.

On the web, this is only supported in browsers that implement the
[Battery Status API](https://developer.mozilla.org/en-US/docs/Web/API/Battery_Status_API)
(Chromium-based browsers).

Only available on Android, iOS and Web.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'batteryLevelChange'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#batterylevelchangeevent">BatteryLevelChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('batteryStateChange', ...)

```typescript
addListener(eventName: 'batteryStateChange', listenerFunc: (event: BatteryStateChangeEvent) => void) => Promise<PluginListenerHandle>
```

Listen for changes to the battery state of the device.

The device is only observed while at least one listener is attached.

On the web, this is only supported in browsers that implement the
[Battery Status API](https://developer.mozilla.org/en-US/docs/Web/API/Battery_Status_API)
(Chromium-based browsers).

Only available on Android, iOS and Web.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'batteryStateChange'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#batterystatechangeevent">BatteryStateChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('lowPowerModeChange', ...)

```typescript
addListener(eventName: 'lowPowerModeChange', listenerFunc: (event: LowPowerModeChangeEvent) => void) => Promise<PluginListenerHandle>
```

Listen for changes to the low power mode of the device.

The device is only observed while at least one listener is attached.

Only available on Android and iOS.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'lowPowerModeChange'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#lowpowermodechangeevent">LowPowerModeChangeEvent</a>) =&gt; void</code> |

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


#### GetBatteryLevelResult

| Prop        | Type                | Description                                                                 | Since |
| ----------- | ------------------- | --------------------------------------------------------------------------- | ----- |
| **`level`** | <code>number</code> | The current battery level of the device as a value between `0.0` and `1.0`. | 0.1.0 |


#### GetBatteryStateResult

| Prop        | Type                                                  | Description                              | Since |
| ----------- | ----------------------------------------------------- | ---------------------------------------- | ----- |
| **`state`** | <code><a href="#batterystate">BatteryState</a></code> | The current battery state of the device. | 0.1.0 |


#### IsLowPowerModeEnabledResult

| Prop          | Type                 | Description                                      | Since |
| ------------- | -------------------- | ------------------------------------------------ | ----- |
| **`enabled`** | <code>boolean</code> | Whether the low power mode is currently enabled. | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### BatteryLevelChangeEvent

| Prop        | Type                | Description                                                                 | Since |
| ----------- | ------------------- | --------------------------------------------------------------------------- | ----- |
| **`level`** | <code>number</code> | The current battery level of the device as a value between `0.0` and `1.0`. | 0.1.0 |


#### BatteryStateChangeEvent

| Prop        | Type                                                  | Description                              | Since |
| ----------- | ----------------------------------------------------- | ---------------------------------------- | ----- |
| **`state`** | <code><a href="#batterystate">BatteryState</a></code> | The current battery state of the device. | 0.1.0 |


#### LowPowerModeChangeEvent

| Prop          | Type                 | Description                                      | Since |
| ------------- | -------------------- | ------------------------------------------------ | ----- |
| **`enabled`** | <code>boolean</code> | Whether the low power mode is currently enabled. | 0.1.0 |


### Type Aliases


#### BatteryState

The battery state of the device.

- `charging`: The device is plugged into power and the battery is charging.
- `full`: The device is plugged into power and the battery is fully charged.
- `unplugged`: The device is not plugged into power and the battery is
  discharging.
- `unknown`: The battery state could not be determined.

<code>'charging' | 'full' | 'unplugged' | 'unknown'</code>

</docgen-api>

## Battery Information

Keep the following platform differences in mind when accessing battery information:

- **Android**: The battery level and state are read from the sticky [`ACTION_BATTERY_CHANGED`](https://developer.android.com/reference/android/content/Intent#ACTION_BATTERY_CHANGED) broadcast. The low power mode reflects the [power saver mode](https://developer.android.com/reference/android/os/PowerManager#isPowerSaveMode()) of the device.
- **iOS**: The battery level is not available on the iOS Simulator, so `getBatteryLevel()` rejects with an error there. Use a real device to test this method. The low power mode reflects the [Low Power Mode](https://support.apple.com/en-us/101604) of the device.
- **Web**: The battery level and state are only available in browsers that implement the [Battery Status API](https://developer.mozilla.org/en-US/docs/Web/API/Battery_Status_API) (Chromium-based browsers). The low power mode is not available on the Web.

## FAQ

### Why does `getBatteryLevel` fail on the iOS Simulator?

The battery level is not available on the iOS Simulator, so `getBatteryLevel()` rejects with an error there. Use a real device to test this method.

### Does this plugin work in all browsers?

No, on the Web the battery level and state are only available in browsers that implement the [Battery Status API](https://developer.mozilla.org/en-US/docs/Web/API/Battery_Status_API), which are Chromium-based browsers. The low power mode is not available on the Web at all.

### What is the range of the battery level value?

The battery level is returned as a value between `0.0` and `1.0`, where `0.0` means the battery is empty and `1.0` means it is fully charged. Multiply the value by 100 if you want to display it as a percentage.

### What does low power mode mean on Android and iOS?

On Android, it refers to the [power saver mode](https://developer.android.com/reference/android/os/PowerManager#isPowerSaveMode()) of the device. On iOS, it refers to the [Low Power Mode](https://support.apple.com/en-us/101604). The `isLowPowerModeEnabled()` method and the `lowPowerModeChange` event are only available on Android and iOS.

### Does listening for battery changes drain the battery?

The device is only observed while at least one listener is attached. As soon as you remove all listeners, for example with `removeAllListeners()`, the plugin stops observing the device.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Android Battery Optimization](https://capawesome.io/docs/sdks/capacitor/android-battery-optimization/): Manage battery optimization settings and request exemptions on Android.
- [Device Info](https://capawesome.io/docs/sdks/capacitor/device-info/): Read device information, such as the model, manufacturer, operating system, and memory.
- [Thermal State](https://capawesome.io/docs/sdks/capacitor/thermal-state/): Read the device thermal state and react before the operating system throttles your app.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/battery/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/battery/LICENSE).
