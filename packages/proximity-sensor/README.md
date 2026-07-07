# Capacitor Proximity Sensor Plugin

Capacitor plugin to read the device's proximity sensor.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for proximity measurements. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 📏 **Proximity detection**: Detect whether an object is close to the screen.
- ⚡ **Real-time updates**: Continuous proximity data with event listeners.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [Accelerometer](https://capawesome.io/docs/sdks/capacitor/accelerometer/), [Barometer](https://capawesome.io/docs/sdks/capacitor/barometer/) and [Gyroscope](https://capawesome.io/docs/sdks/capacitor/gyroscope/) plugins.

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

```typescript
import { ProximitySensor } from '@capawesome/capacitor-proximity-sensor';

const getMeasurement = async () => {
  const measurement = await ProximitySensor.getMeasurement();
  console.log('Near: ', measurement.near);
  console.log('Distance: ', measurement.distance);
};

const isAvailable = async () => {
  const result = await ProximitySensor.isAvailable();
  return result.available;
};

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

* [`addListener('measurement', ...)`](#addlistenermeasurement-)
* [`getMeasurement()`](#getmeasurement)
* [`isAvailable()`](#isavailable)
* [`removeAllListeners()`](#removealllisteners)
* [`startMeasurementUpdates()`](#startmeasurementupdates)
* [`stopMeasurementUpdates()`](#stopmeasurementupdates)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

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


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

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


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### Measurement

| Prop           | Type                        | Description                                                                                                                                                                                                                                                              | Since |
| -------------- | --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`distance`** | <code>number \| null</code> | The distance between the sensor and the nearby object in centimeters (cm). Most devices only report whether an object is near or far, so this value is typically either `0` (near) or the sensor's maximum range (far). Only available on Android. Always `null` on iOS. | 0.1.0 |
| **`near`**     | <code>boolean</code>        | Whether an object is close to the sensor.                                                                                                                                                                                                                                | 0.1.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                              | Since |
| --------------- | -------------------- | -------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether the proximity sensor is available on the device. | 0.1.0 |


### Type Aliases


#### MeasurementEvent

<code><a href="#measurement">Measurement</a></code>


#### GetMeasurementResult

<code><a href="#measurement">Measurement</a></code>

</docgen-api>

## Screen Dimming (iOS)

On iOS, reading the proximity sensor requires enabling proximity monitoring via `UIDevice`. While monitoring is enabled, iOS automatically turns off the screen whenever an object (such as the user's ear or a hand) is close to the sensor, exactly like during a phone call. This is a system-level behavior that cannot be disabled.

For this reason, monitoring is only enabled while it is needed:

- `getMeasurement()` enables monitoring, reads the current state and disables it again.
- `startMeasurementUpdates()` keeps monitoring enabled until `stopMeasurementUpdates()` (or `removeAllListeners()`) is called.

Only call `startMeasurementUpdates()` when you actually want this behavior.

On Android, reading the proximity sensor has no effect on the screen.

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/proximity-sensor/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/proximity-sensor/LICENSE).
