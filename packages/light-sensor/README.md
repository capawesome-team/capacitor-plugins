# Capacitor Light Sensor Plugin

Capacitor plugin to read the device's ambient light sensor.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for ambient light measurements. Here are some of the key features:

- 🌗 **Illuminance**: Read the ambient light level in lux.
- ⚡ **Real-time measurements**: Continuous light data with event listeners.
- 🖥️ **Native**: Supports Android.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [Screen Brightness](https://capawesome.io/docs/sdks/capacitor/screen-brightness/) plugin.

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

```typescript
import { LightSensor } from '@capawesome/capacitor-light-sensor';

const getMeasurement = async () => {
  const measurement = await LightSensor.getMeasurement();
  console.log('Illuminance: ', measurement.illuminance);
};

const isAvailable = async () => {
  const result = await LightSensor.isAvailable();
  return result.available;
};

const startMeasurementUpdates = async () => {
  await LightSensor.addListener('measurement', measurement => {
    console.log('Illuminance: ', measurement.illuminance);
  });
  await LightSensor.startMeasurementUpdates();
};

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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/light-sensor/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/light-sensor/LICENSE).
