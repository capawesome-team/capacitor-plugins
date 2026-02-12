# @capawesome-team/capacitor-accelerometer

Capacitor plugin to capture the acceleration force along the x, y, and z axes.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

We are proud to offer one of the most complete and feature-rich Capacitor plugins for accelerometer measurements. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- ⚡ **Real-time measurements**: Continuous accelerometer data with event listeners.
- 📊 **High precision**: Accurate x, y, and z-axis acceleration measurements in G's.
- 🔒 **Permission handling**: Built-in permission management for sensor access.
📦  **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 0.1.x          | 7.x.x             | Deprecated     |

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
npm install @capawesome-team/capacitor-accelerometer
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
import { Accelerometer } from '@capawesome-team/capacitor-accelerometer';

const getMeasurement = async () => {
  const measurement = await Accelerometer.getMeasurement();
  console.log("X: ", measurement.x);
  console.log("Y: ", measurement.y);
  console.log("Z: ", measurement.z);
};

const isAvailable = async () => {
  const result = await Accelerometer.isAvailable();
  return result.isAvailable;
};

const startMeasurementUpdates = async () => {
  await Accelerometer.startMeasurementUpdates();
};

const stopMeasurementUpdates = async () => {
  await Accelerometer.stopMeasurementUpdates();
};

const checkPermissions = async () => {
  const result = await Accelerometer.checkPermissions();
  return result;
};

const requestPermissions = async () => {
  const result = await Accelerometer.requestPermissions();
  return result;
};

const removeAllListeners = async () => {
  await Accelerometer.removeAllListeners();
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

This method returns the most recent measurement from the accelerometer sensor.

**Returns:** <code>Promise&lt;<a href="#measurement">Measurement</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if the accelerometer sensor is available on the device.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### startMeasurementUpdates()

```typescript
startMeasurementUpdates() => Promise<void>
```

Starts emitting `measurement` events.

**Since:** 0.1.0

--------------------


### stopMeasurementUpdates()

```typescript
stopMeasurementUpdates() => Promise<void>
```

Stops emitting `measurement` events.

**Since:** 0.1.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check if the app has permission to access the accelerometer sensor.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.1.0

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request permission to access the accelerometer sensor.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.1.0

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

| Prop    | Type                | Description                                           | Since |
| ------- | ------------------- | ----------------------------------------------------- | ----- |
| **`x`** | <code>number</code> | The x-axis acceleration in G's (gravitational force). | 0.1.0 |
| **`y`** | <code>number</code> | The y-axis acceleration in G's (gravitational force). | 0.1.0 |
| **`z`** | <code>number</code> | The z-axis acceleration in G's (gravitational force). | 0.1.0 |


#### IsAvailableResult

| Prop              | Type                 | Description                                                  | Since |
| ----------------- | -------------------- | ------------------------------------------------------------ | ----- |
| **`isAvailable`** | <code>boolean</code> | Whether the accelerometer sensor is available on the device. | 0.1.0 |


#### PermissionStatus

| Prop                | Type                                                                                  | Description                             | Since |
| ------------------- | ------------------------------------------------------------------------------------- | --------------------------------------- | ----- |
| **`accelerometer`** | <code><a href="#accelerometerpermissionstate">AccelerometerPermissionState</a></code> | The permission status of accelerometer. | 0.1.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### GetMeasurementResult

<code><a href="#measurement">Measurement</a></code>


#### AccelerometerPermissionState

<code><a href="#permissionstate">PermissionState</a> | 'limited'</code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### MeasurementEvent

<code><a href="#measurement">Measurement</a></code>

</docgen-api>

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/accelerometer/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/accelerometer/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/accelerometer/LICENSE).
