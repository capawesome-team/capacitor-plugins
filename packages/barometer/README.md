# @capawesome-team/capacitor-barometer

Capacitor plugin to obtain the static air pressure, which is measured in hectopascals (hPa).

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for barometer measurements. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 📏 **Precise Measurements**: Get accurate air pressure readings in hectopascals (hPa).
- 🔄 **Real-time Updates**: Continuously monitor barometer changes with event listeners.
- 🔍 **Device Detection**: Check if barometer sensor is available on the device.
- 🔑 **Permissions**: Check and request barometer sensor permissions.
- 📊 **Additional Data**: Get relative altitude measurements.
- ⏱️ **Timestamps**: Each measurement includes precise timing information.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |

## Guides

- [Announcing the Capacitor Barometer Plugin](https://capawesome.io/blog/announcing-the-capacitor-barometer-plugin/)
- [Exploring the Capacitor Barometer API](https://capawesome.io/blog/exploring-the-capacitor-barometer-api/)

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/sponsors/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/sponsors/insiders/).

Next, install the package:

```
npm install @capawesome-team/capacitor-barometer
npx cap sync
```

### Android

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

### iOS

#### Privacy Descriptions

Add the `NSMotionUsageDescription` key to the `ios/App/App/Info.plist` file, which tells the user why your app needs access to the user's contacts:

```xml
<key>NSMotionUsageDescription</key>
<string>The app needs to access the motion activity.</string>
```

## Usage

```typescript
import { Barometer } from '@capawesome-team/capacitor-barometer';

const getMeasurement = async () => {
  const { measurement } = await Barometer.getMeasurement();
  console.log('Pressure:', measurement.pressure, 'hPa');
  console.log('Relative Altitude:', measurement.relativeAltitude, 'm');
  console.log('Timestamp:', new Date(measurement.timestamp));
};

const isAvailable = async () => {
  const result = await Barometer.isAvailable();
  console.log('Barometer is available:', result.isAvailable);
};

const startMeasurementUpdates = async () => {
  Barometer.addListener('measurement', (event) => {
    console.log('New measurement:', event);
  });
  await Barometer.startMeasurementUpdates();
};

const stopMeasurementUpdates = async () => {
  await Barometer.stopMeasurementUpdates();
  Barometer.removeAllListeners();
};

const checkPermissions = async () => {
  const status = await Barometer.checkPermissions();
  console.log('Barometer permission status:', status.barometer);
};

const requestPermissions = async () => {
  const status = await Barometer.requestPermissions();
  console.log('Barometer permission status after request:', status.barometer);
};
```

## API

<docgen-index>

* [`getMeasurement()`](#getmeasurement)
* [`isAvailable()`](#isavailable)
* [`startMeasurementUpdates()`](#startmeasurementupdates)
* [`stopMeasurementUpdates()`](#stopmeasurementupdates)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
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

This method returns the most recent measurement from the barometer sensor.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#measurement">Measurement</a>&gt;</code>

**Since:** 7.0.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if the barometer sensor is available on the device.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### startMeasurementUpdates()

```typescript
startMeasurementUpdates() => Promise<void>
```

Starts emitting `measurement` events.

Only available on Android and iOS.

**Since:** 7.0.0

--------------------


### stopMeasurementUpdates()

```typescript
stopMeasurementUpdates() => Promise<void>
```

Stops emitting `measurement` events.

Only available on Android and iOS.

**Since:** 7.0.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check if the app has permission to access the barometer sensor.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 7.0.0

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request permission to access the barometer sensor.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 7.0.0

--------------------


### addListener('measurement', ...)

```typescript
addListener(eventName: 'measurement', listenerFunc: (event: MeasurementEvent) => void) => Promise<PluginListenerHandle>
```

Only available on Android and iOS.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'measurement'</code>                                              |
| **`listenerFunc`** | <code>(event: <a href="#measurement">Measurement</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 7.0.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 7.0.0

--------------------


### Interfaces


#### Measurement

| Prop                   | Type                | Description                                                       | Since |
| ---------------------- | ------------------- | ----------------------------------------------------------------- | ----- |
| **`pressure`**         | <code>number</code> | The pressure in hPa (hectopascal).                                | 7.0.0 |
| **`relativeAltitude`** | <code>number</code> | The relative altitude in meters. Only available on iOS.           | 7.0.0 |
| **`timestamp`**        | <code>number</code> | The timestamp of the measurement in milliseconds since the epoch. | 7.0.0 |


#### IsAvailableResult

| Prop              | Type                 | Description                                                        | Since |
| ----------------- | -------------------- | ------------------------------------------------------------------ | ----- |
| **`isAvailable`** | <code>boolean</code> | Indicates whether the barometer sensor is available on the device. | 7.0.0 |


#### PermissionStatus

| Prop            | Type                                                                          | Description                         | Since |
| --------------- | ----------------------------------------------------------------------------- | ----------------------------------- | ----- |
| **`barometer`** | <code><a href="#barometerpermissionstate">BarometerPermissionState</a></code> | The permission status of barometer. | 7.0.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### GetMeasurementResult

<code><a href="#measurement">Measurement</a></code>


#### BarometerPermissionState

<code><a href="#permissionstate">PermissionState</a> | 'limited'</code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### MeasurementEvent

<code><a href="#measurement">Measurement</a></code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/barometer/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/barometer/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/barometer/LICENSE).
