# Capacitor Thermal State Plugin

Capacitor plugin to read the device thermal state and react before the operating system throttles your app.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🌡️ **Thermal state**: Read the current thermal state of the device.
- 🔔 **Change events**: Get notified whenever the thermal state changes.
- 🤝 **Compatibility**: Works alongside the [Battery](https://capawesome.io/docs/sdks/capacitor/battery/) and [Device Info](https://capawesome.io/docs/sdks/capacitor/device-info/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Thermal State plugin is typically used to adapt an app's workload to the device's thermal condition, for example:

- **Video and streaming apps**: Lower the video quality or frame rate when the thermal state becomes serious.
- **Games and 3D rendering**: Reduce graphical effects to help the device cool down before the operating system throttles the app.
- **Machine learning**: Defer on-device ML inference or other background work while the thermal state is elevated.
- **Data prefetching**: Reduce the prefetching rate or pause background sync when the device gets warm.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Guides

- [The Complete Guide to Capacitor Device Sensors](https://capawesome.io/blog/capacitor-device-sensors-guide/): How to gate heavy work like video processing or ML inference before the OS throttles your app.

## Installation

You can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-thermal-state` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-thermal-state
npx cap sync
```

This plugin is available on **Android** and **iOS**. On the web, all methods reject as unimplemented.

### Android

Reading the thermal state requires **Android 10** (API level 29) or newer. On older versions, `getThermalState(...)` rejects as unavailable and the `thermalStateChange` event is never emitted.

The Android thermal status values are mapped to the four common thermal states as follows:

| Android Thermal Status                                                                  | Thermal State |
| --------------------------------------------------------------------------------------- | ------------- |
| `THERMAL_STATUS_NONE`                                                                    | `nominal`     |
| `THERMAL_STATUS_LIGHT`                                                                   | `fair`        |
| `THERMAL_STATUS_MODERATE`                                                                | `serious`     |
| `THERMAL_STATUS_SEVERE` / `THERMAL_STATUS_CRITICAL` / `THERMAL_STATUS_EMERGENCY` / `THERMAL_STATUS_SHUTDOWN` | `critical`    |

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to get the current thermal state, listen for thermal state changes, and remove all listeners.

### Get the current thermal state

Read the current thermal state of the device, for example to decide how much work your app should perform. Only available on Android (API level 29+) and iOS:

```typescript
import { ThermalState } from '@capawesome/capacitor-thermal-state';

const getThermalState = async () => {
  const { state } = await ThermalState.getThermalState();
  return state;
};
```

### Listen for thermal state changes

Get notified whenever the thermal state of the device changes. The device is only observed while at least one listener is attached. Only available on Android (API level 29+) and iOS:

```typescript
import { ThermalState } from '@capawesome/capacitor-thermal-state';

const addThermalStateChangeListener = async () => {
  await ThermalState.addListener('thermalStateChange', event => {
    console.log(event.state);
  });
};
```

### Remove all listeners

Remove all listeners for this plugin when you no longer need to observe the thermal state:

```typescript
import { ThermalState } from '@capawesome/capacitor-thermal-state';

const removeAllListeners = async () => {
  await ThermalState.removeAllListeners();
};
```

## API

<docgen-index>

* [`getThermalState()`](#getthermalstate)
* [`addListener('thermalStateChange', ...)`](#addlistenerthermalstatechange-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getThermalState()

```typescript
getThermalState() => Promise<GetThermalStateResult>
```

Get the current thermal state of the device.

Only available on Android (API level 29+) and iOS.

**Returns:** <code>Promise&lt;<a href="#getthermalstateresult">GetThermalStateResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### addListener('thermalStateChange', ...)

```typescript
addListener(eventName: 'thermalStateChange', listenerFunc: (event: ThermalStateChangeEvent) => void) => Promise<PluginListenerHandle>
```

Listen for changes to the thermal state of the device.

The device is only observed while at least one listener is attached.

Only available on Android (API level 29+) and iOS.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'thermalStateChange'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#thermalstatechangeevent">ThermalStateChangeEvent</a>) =&gt; void</code> |

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


#### GetThermalStateResult

| Prop        | Type                                                            | Description                              | Since |
| ----------- | --------------------------------------------------------------- | ---------------------------------------- | ----- |
| **`state`** | <code><a href="#thermalstatevalue">ThermalStateValue</a></code> | The current thermal state of the device. | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### ThermalStateChangeEvent

| Prop        | Type                                                            | Description                              | Since |
| ----------- | --------------------------------------------------------------- | ---------------------------------------- | ----- |
| **`state`** | <code><a href="#thermalstatevalue">ThermalStateValue</a></code> | The current thermal state of the device. | 0.1.0 |


### Type Aliases


#### ThermalStateValue

The thermal state of the device.

- `critical`: The thermal state is significantly impacting performance.
  Reduce the workload as much as possible.
- `fair`: The thermal state is slightly elevated. Consider reducing
  non-essential work.
- `nominal`: The thermal state is within normal limits. No action is needed.
- `serious`: The thermal state is high. Reduce the workload to help the
  device cool down.

<code>'critical' | 'fair' | 'nominal' | 'serious'</code>

</docgen-api>

## Handling the Thermal State

Use the thermal state to progressively reduce your app's workload before the operating system throttles it. The following table lists suggested reactions for each state:

| State      | Suggested App Reaction                                                                 |
| ---------- | -------------------------------------------------------------------------------------- |
| `nominal`  | No action needed. Run at full quality.                                                  |
| `fair`     | Reduce non-essential work, e.g. lower the prefetching rate.                             |
| `serious`  | Reduce the frame rate, pause prefetching, and defer background or ML work.              |
| `critical` | Reduce the workload as much as possible to help the device cool down.                   |

## FAQ

### Which platforms are supported by this plugin?

The plugin is available on Android and iOS. On Android, reading the thermal state requires Android 10 (API level 29) or newer. On the web, all methods reject as unimplemented.

### Why does `getThermalState` reject on my Android device?

Reading the thermal state requires Android 10 (API level 29) or newer. On older Android versions, `getThermalState(...)` rejects as unavailable and the `thermalStateChange` event is never emitted.

### What do the different thermal states mean?

The plugin reports one of four states: `nominal` means the thermal state is within normal limits, `fair` means it is slightly elevated, `serious` means it is high and the workload should be reduced, and `critical` means performance is significantly impacted and the workload should be reduced as much as possible. See [Handling the Thermal State](#handling-the-thermal-state) for suggested reactions to each state.

### Why is the `thermalStateChange` event not emitted?

The device is only observed while at least one listener is attached, so make sure you have added a listener for the `thermalStateChange` event. Also note that the event is never emitted on Android versions older than Android 10 (API level 29).

### Do I need any permissions or configuration to use this plugin?

No, the plugin does not require any permissions or configuration. Simply [install](#installation) it and call its methods.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Battery](https://capawesome.io/docs/sdks/capacitor/battery/): Access battery information of the device.
- [Device Info](https://capawesome.io/docs/sdks/capacitor/device-info/): Read device information such as the model, manufacturer, operating system, and memory.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/thermal-state/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/thermal-state/LICENSE).
