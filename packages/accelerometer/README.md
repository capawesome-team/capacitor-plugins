# Capacitor Accelerometer Plugin

Capacitor plugin to capture the acceleration force along the x, y, and z axes.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Accelerometer plugin is one of the most complete motion sensing solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- ⚡ **Real-time measurements**: Continuous accelerometer data with event listeners.
- 📊 **High precision**: Accurate x, y, and z-axis acceleration measurements in G's.
- 🔒 **Permission handling**: Built-in permission management for sensor access.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Accelerometer plugin is typically used whenever an app needs to react to device motion, for example:

- **Shake gestures**: React to rapid device movement, for example to trigger a feedback dialog or undo an action.
- **Motion-based games**: Use the tilt of the device as game input by reading the acceleration along the x, y, and z axes.
- **Fitness and activity tracking**: Analyze continuous motion data to detect movement patterns during workouts.
- **Device orientation**: Determine how the device is being held based on the gravitational force along each axis.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 0.1.x          | 7.x.x             | Deprecated     |

## Guides

- [The Complete Guide to Capacitor Device Sensors](https://capawesome.io/blog/capacitor-device-sensors-guide/): How this plugin pairs with the Pedometer for fitness tracking and the Gyroscope for motion-based games.

## Installation

This plugin is only available to [Capawesome Insiders](https://capawesome.io/insiders/). 
First, make sure you have the Capawesome npm registry set up.
You can do this by running the following commands:

```
npm config set @capawesome-team:registry https://npm.registry.capawesome.io
npm config set //npm.registry.capawesome.io/:_authToken <YOUR_LICENSE_KEY>
```

**Attention**: Replace `<YOUR_LICENSE_KEY>` with the license key you received from Polar. If you don't have a license key yet, you can get one by becoming a [Capawesome Insider](https://capawesome.io/insiders/).

Next, you can use our **AI-Assisted Setup** to install the plugin.
Add the [Capawesome Skills](https://github.com/capawesome-team/skills) to your AI tool using the following command:

```bash
npx skills add capawesome-team/skills --skill capacitor-plugins
```

Then use the following prompt:

```
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-accelerometer` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
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

The following examples show how to check whether the accelerometer is available, request permissions, read the latest measurement, and start and stop continuous measurement updates.

### Check if the accelerometer is available

Check whether the device has an accelerometer sensor before using the other methods:

```typescript
import { Accelerometer } from '@capawesome-team/capacitor-accelerometer';

const isAvailable = async () => {
  const result = await Accelerometer.isAvailable();
  return result.isAvailable;
};
```

### Check and request permissions

Check and request permission to access the accelerometer sensor. On iOS, make sure you have added the `NSMotionUsageDescription` key to your `Info.plist` file (see [Installation](#installation)):

```typescript
import { Accelerometer } from '@capawesome-team/capacitor-accelerometer';

const checkPermissions = async () => {
  const result = await Accelerometer.checkPermissions();
  return result;
};

const requestPermissions = async () => {
  const result = await Accelerometer.requestPermissions();
  return result;
};
```

### Get the latest measurement

Get the most recent measurement from the accelerometer sensor. The acceleration along the x, y, and z axes is returned in G's (gravitational force):

```typescript
import { Accelerometer } from '@capawesome-team/capacitor-accelerometer';

const getMeasurement = async () => {
  const measurement = await Accelerometer.getMeasurement();
  console.log("X: ", measurement.x);
  console.log("Y: ", measurement.y);
  console.log("Z: ", measurement.z);
};
```

### Start and stop measurement updates

Start emitting `measurement` events to receive continuous accelerometer data. When you no longer need updates, stop them and remove your listeners:

```typescript
import { Accelerometer } from '@capawesome-team/capacitor-accelerometer';

const startMeasurementUpdates = async () => {
  await Accelerometer.startMeasurementUpdates();
};

const stopMeasurementUpdates = async () => {
  await Accelerometer.stopMeasurementUpdates();
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

## FAQ

### Which platforms are supported by this plugin?

The Accelerometer plugin supports Android and iOS. You can use the `isAvailable()` method to check whether the accelerometer sensor is available on the current device.

### What unit are the measurements returned in?

The `getMeasurement()` method and the `measurement` event return the acceleration along the x, y, and z axes in G's (gravitational force).

### How do I receive continuous accelerometer updates?

Call `startMeasurementUpdates()` to start emitting `measurement` events and add a listener for the `measurement` event to receive them. When you no longer need updates, call `stopMeasurementUpdates()` and remove your listeners with `removeAllListeners()`. See the [usage example](#start-and-stop-measurement-updates) above.

### Do I need any permissions to use the accelerometer?

The plugin provides the `checkPermissions()` and `requestPermissions()` methods to manage access to the accelerometer sensor. On iOS, you also need to add the `NSMotionUsageDescription` key to your `Info.plist` file as described in the [Installation](#installation) section.

### How can I check if a device has an accelerometer sensor?

Call the `isAvailable()` method. It returns whether the accelerometer sensor is available on the device, so you can check this before calling any other methods.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Gyroscope](https://capawesome.io/docs/sdks/capacitor/gyroscope/): Read the device's gyroscope sensor.
- [Compass](https://capawesome.io/docs/sdks/capacitor/compass/): Read the device's compass heading.
- [Barometer](https://capawesome.io/docs/sdks/capacitor/barometer/): Obtain the static air pressure, measured in hectopascals (hPa).
- [Shake](https://capawesome.io/docs/sdks/capacitor/shake/): Detect shake gestures on the device.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/accelerometer/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/accelerometer/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/accelerometer/LICENSE).
