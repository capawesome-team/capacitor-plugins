# Capacitor Light Sensor Plugin

Capacitor plugin to read the device's ambient light sensor.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Light Sensor plugin provides a complete solution for ambient light measurements in Capacitor apps. Here are some of the key features:

- 🌗 **Illuminance**: Read the ambient light level in lux.
- ⚡ **Real-time measurements**: Continuous light data with event listeners.
- 🖥️ **Native**: Supports Android.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [Screen Brightness](https://capawesome.io/docs/sdks/capacitor/screen-brightness/) plugin.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Light Sensor plugin is typically used to react to the lighting conditions around the device, for example:

- **Adaptive theming**: Switch between a light and dark theme based on the ambient light level.
- **Brightness control**: Combine the illuminance readings with the [Screen Brightness](https://capawesome.io/docs/sdks/capacitor/screen-brightness/) plugin to adjust the screen brightness.
- **Environment monitoring**: Continuously measure the light level in a room using real-time measurement events.
- **Reading comfort**: Detect dark environments and suggest a more comfortable display mode to the user.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-light-sensor` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-light-sensor
npx cap sync
```

### Android

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### iOS

iOS does not provide a public API to read the ambient light sensor, so the plugin is not implemented on this platform. The closest available signal is the screen brightness, which you can read using the [Screen Brightness](https://capawesome.io/docs/sdks/capacitor/screen-brightness/) plugin.

## Configuration

No configuration required for this plugin.

## Usage

Import the plugin and call its methods:

```typescript
import { LightSensor } from '@capawesome/capacitor-light-sensor';
```

### Check if the light sensor is available

Check whether the device has an ambient light sensor before using the other methods. Only available on Android:

```typescript
const isAvailable = async () => {
  const result = await LightSensor.isAvailable();
  return result.available;
};
```

### Read the current light level

Get the most recent measurement from the ambient light sensor in lux. Only available on Android:

```typescript
const getMeasurement = async () => {
  const measurement = await LightSensor.getMeasurement();
  console.log('Illuminance: ', measurement.illuminance);
};
```

### Receive continuous measurements

Add a listener for the `measurement` event and start the measurement updates to receive real-time light data. Only available on Android:

```typescript
const startMeasurementUpdates = async () => {
  await LightSensor.addListener('measurement', measurement => {
    console.log('Illuminance: ', measurement.illuminance);
  });
  await LightSensor.startMeasurementUpdates();
};
```

### Stop receiving measurements

Stop the measurement updates and remove the listeners when you no longer need them:

```typescript
const stopMeasurementUpdates = async () => {
  await LightSensor.stopMeasurementUpdates();
};

const removeAllListeners = async () => {
  await LightSensor.removeAllListeners();
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

Only available on Android.

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

This method returns the most recent measurement from the ambient light sensor.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#measurement">Measurement</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if the ambient light sensor is available on the device.

Only available on Android.

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

Only available on Android.

**Since:** 0.1.0

--------------------


### stopMeasurementUpdates()

```typescript
stopMeasurementUpdates() => Promise<void>
```

Stop emitting `measurement` events.

Only available on Android.

**Since:** 0.1.0

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### Measurement

| Prop              | Type                | Description                          | Since |
| ----------------- | ------------------- | ------------------------------------ | ----- |
| **`illuminance`** | <code>number</code> | The ambient light level in lux (lx). | 0.1.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                                  | Since |
| --------------- | -------------------- | ------------------------------------------------------------ | ----- |
| **`available`** | <code>boolean</code> | Whether the ambient light sensor is available on the device. | 0.1.0 |


### Type Aliases


#### MeasurementEvent

<code><a href="#measurement">Measurement</a></code>


#### GetMeasurementResult

<code><a href="#measurement">Measurement</a></code>

</docgen-api>

## FAQ

### Why is this plugin not available on iOS?

iOS does not provide a public API to read the ambient light sensor, so the plugin is not implemented on this platform. The closest available signal is the screen brightness, which you can read using the [Screen Brightness](https://capawesome.io/docs/sdks/capacitor/screen-brightness/) plugin.

### What unit are the measurements in?

The `illuminance` property of a measurement contains the ambient light level in lux (lx).

### How do I know if the device has a light sensor?

Call the `isAvailable()` method, which returns whether the ambient light sensor is available on the device. It is recommended to check this before calling the other methods.

### What is the difference between `getMeasurement` and the `measurement` event?

The `getMeasurement()` method returns the most recent measurement from the ambient light sensor as a one-time value. The `measurement` event, in combination with `startMeasurementUpdates()`, delivers continuous real-time measurements until you call `stopMeasurementUpdates()`. See the [usage examples](#usage) above.

### Do I need any permissions or configuration on Android?

No permissions are required. However, if you are using Proguard, you need to add a keep rule for the plugin classes to your `proguard-rules.pro` file, as described in the [Installation](#installation) section.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Screen Brightness](https://capawesome.io/docs/sdks/capacitor/screen-brightness/): Read and control the screen brightness.
- [Proximity Sensor](https://capawesome.io/docs/sdks/capacitor/proximity-sensor/): Read the device's proximity sensor.
- [Barometer](https://capawesome.io/docs/sdks/capacitor/barometer/): Obtain the static air pressure in hectopascals (hPa).
- [Accelerometer](https://capawesome.io/docs/sdks/capacitor/accelerometer/): Capture the acceleration force along the x, y, and z axes.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/light-sensor/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/light-sensor/LICENSE).
