# Capacitor Proximity Sensor Plugin

Capacitor plugin to read the device's proximity sensor.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Proximity Sensor plugin is one of the most complete proximity sensing solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 📏 **Proximity detection**: Detect whether an object is close to the screen.
- ⚡ **Real-time updates**: Continuous proximity data with event listeners.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [Accelerometer](https://capawesome.io/docs/sdks/capacitor/accelerometer/), [Barometer](https://capawesome.io/docs/sdks/capacitor/barometer/) and [Gyroscope](https://capawesome.io/docs/sdks/capacitor/gyroscope/) plugins.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Proximity Sensor plugin is typically used to react when an object is close to the device's screen, for example:

- **Calling and VoIP apps**: Detect when the user holds the device to their ear during a call.
- **Pocket detection**: Detect when the device is in a pocket or bag to ignore accidental input.
- **Proximity-triggered actions**: Start or stop app behavior based on real-time `measurement` events.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-proximity-sensor` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-proximity-sensor
npx cap sync
```

### Android

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check whether the proximity sensor is available, get a single measurement, and start and stop continuous measurement updates.

### Check whether the sensor is available

Not every device has a proximity sensor, so check its availability first. Only available on Android and iOS:

```typescript
import { ProximitySensor } from '@capawesome/capacitor-proximity-sensor';

const isAvailable = async () => {
  const result = await ProximitySensor.isAvailable();
  return result.available;
};
```

### Get a single measurement

Read the most recent measurement from the proximity sensor. Note that on iOS, reading the measurement enables proximity monitoring, which turns off the screen while an object is close to the sensor (see [Screen Dimming (iOS)](#screen-dimming-ios)):

```typescript
import { ProximitySensor } from '@capawesome/capacitor-proximity-sensor';

const getMeasurement = async () => {
  const measurement = await ProximitySensor.getMeasurement();
  console.log('Near: ', measurement.near);
  console.log('Distance: ', measurement.distance);
};
```

### Start and stop measurement updates

Listen for continuous `measurement` events. On iOS, proximity monitoring stays enabled until `stopMeasurementUpdates()` (or `removeAllListeners()`) is called:

```typescript
import { ProximitySensor } from '@capawesome/capacitor-proximity-sensor';

const startMeasurementUpdates = async () => {
  await ProximitySensor.addListener('measurement', measurement => {
    console.log('Near: ', measurement.near);
    console.log('Distance: ', measurement.distance);
  });
  await ProximitySensor.startMeasurementUpdates();
};

const stopMeasurementUpdates = async () => {
  await ProximitySensor.stopMeasurementUpdates();
};

const removeAllListeners = async () => {
  await ProximitySensor.removeAllListeners();
};
```

## API

<docgen-index>

* [`getMeasurement()`](#getmeasurement)
* [`isAvailable()`](#isavailable)
* [`startMeasurementUpdates()`](#startmeasurementupdates)
* [`stopMeasurementUpdates()`](#stopmeasurementupdates)
* [`addListener('measurement', ...)`](#addlistenermeasurement-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### getMeasurement()

```typescript
getMeasurement() => Promise<GetMeasurementResult>
```

Get the latest measurement.

This method returns the most recent measurement from the proximity sensor.

On iOS, reading the measurement enables proximity monitoring, which turns
off the screen while an object is close to the sensor.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#measurement">Measurement</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if the proximity sensor is available on the device.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### startMeasurementUpdates()

```typescript
startMeasurementUpdates() => Promise<void>
```

Start emitting `measurement` events.

On iOS, this enables proximity monitoring, which turns off the screen
while an object is close to the sensor.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### stopMeasurementUpdates()

```typescript
stopMeasurementUpdates() => Promise<void>
```

Stop emitting `measurement` events.

On iOS, this disables proximity monitoring.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### addListener('measurement', ...)

```typescript
addListener(eventName: 'measurement', listenerFunc: (event: MeasurementEvent) => void) => Promise<PluginListenerHandle>
```

Called when a new measurement is available.

Only available on Android and iOS.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'measurement'</code>                                              |
| **`listenerFunc`** | <code>(event: <a href="#measurement">Measurement</a>) =&gt; void</code> |

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


#### Measurement

| Prop           | Type                        | Description                                                                                                                                                                                                                                                              | Since |
| -------------- | --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`distance`** | <code>number \| null</code> | The distance between the sensor and the nearby object in centimeters (cm). Most devices only report whether an object is near or far, so this value is typically either `0` (near) or the sensor's maximum range (far). Only available on Android. Always `null` on iOS. | 0.1.0 |
| **`near`**     | <code>boolean</code>        | Whether an object is close to the sensor.                                                                                                                                                                                                                                | 0.1.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                              | Since |
| --------------- | -------------------- | -------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether the proximity sensor is available on the device. | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### GetMeasurementResult

<code><a href="#measurement">Measurement</a></code>


#### MeasurementEvent

<code><a href="#measurement">Measurement</a></code>

</docgen-api>

## Screen Dimming (iOS)

On iOS, reading the proximity sensor requires enabling proximity monitoring via `UIDevice`. While monitoring is enabled, iOS automatically turns off the screen whenever an object (such as the user's ear or a hand) is close to the sensor, exactly like during a phone call. This is a system-level behavior that cannot be disabled.

For this reason, monitoring is only enabled while it is needed:

- `getMeasurement()` enables monitoring, reads the current state and disables it again.
- `startMeasurementUpdates()` keeps monitoring enabled until `stopMeasurementUpdates()` (or `removeAllListeners()`) is called.

Only call `startMeasurementUpdates()` when you actually want this behavior.

On Android, reading the proximity sensor has no effect on the screen.

## FAQ

### How is this plugin different from other similar plugins?

It covers the whole proximity story through one fully typed API: a one-time reading, continuous real-time `measurement` events, and an availability check, working consistently across Android and iOS. It surfaces platform specifics honestly — a distance in centimeters on Android and the near/far state on iOS — and documents the iOS screen-dimming behavior so there are no surprises. The package is kept current with the latest Capacitor version and ships for both CocoaPods and Swift Package Manager.

### Why does the screen turn off on iOS when reading the sensor?

On iOS, reading the proximity sensor requires enabling proximity monitoring via `UIDevice`. While monitoring is enabled, iOS automatically turns off the screen whenever an object is close to the sensor, exactly like during a phone call. This is a system-level behavior that cannot be disabled. See the [Screen Dimming (iOS)](#screen-dimming-ios) section for details.

### Why is the `distance` value always `null` on iOS?

iOS only reports whether an object is near the sensor via the `near` property. The `distance` value in centimeters is only available on Android and is always `null` on iOS.

### Why is the `distance` value always either `0` or the maximum range on Android?

Most devices only report whether an object is near or far instead of an exact distance. The `distance` value is therefore typically either `0` (near) or the sensor's maximum range (far).

### How can I check whether a device has a proximity sensor?

Call the `isAvailable()` method before using the sensor. It returns whether the proximity sensor is available on the device.

### Does this plugin work on the Web?

No, this plugin is only available on Android and iOS, since browsers do not expose the device's proximity sensor.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Accelerometer](https://capawesome.io/docs/sdks/capacitor/accelerometer/): Capture the acceleration force along the x, y, and z axes.
- [Barometer](https://capawesome.io/docs/sdks/capacitor/barometer/): Obtain the static air pressure in hectopascals (hPa).
- [Gyroscope](https://capawesome.io/docs/sdks/capacitor/gyroscope/): Read the device's gyroscope sensor.
- [Light Sensor](https://capawesome.io/docs/sdks/capacitor/light-sensor/): Read the device's ambient light sensor.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/proximity-sensor/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/proximity-sensor/LICENSE).
