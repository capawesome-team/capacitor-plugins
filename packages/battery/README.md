# Capacitor Battery Plugin

Capacitor plugin to access battery information.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for battery information. Here are some of the key features:

- 🔋 **Battery level**: Read the current battery level of the device.
- ⚡ **Battery state**: Read whether the device is charging, full or unplugged.
- 🪫 **Low power mode**: Read whether the low power mode is enabled.
- 👂 **Change events**: Listen for changes to the battery level, state and low power mode.
- 🌐 **Web support**: Read the battery level and state on supported browsers.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🤝 **Compatibility**: Works alongside the [Android Battery Optimization](https://capawesome.io/docs/sdks/capacitor/android-battery-optimization/) plugin.
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

```typescript
import { Battery } from '@capawesome/capacitor-battery';

const getBatteryLevel = async () => {
  const { level } = await Battery.getBatteryLevel();
  return level;
};

const getBatteryState = async () => {
  const { state } = await Battery.getBatteryState();
  return state;
};

const isLowPowerModeEnabled = async () => {
  const { enabled } = await Battery.isLowPowerModeEnabled();
  return enabled;
};

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/battery/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/battery/LICENSE).
