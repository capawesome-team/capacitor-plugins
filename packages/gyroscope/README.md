# Capacitor Gyroscope Plugin

Capacitor plugin to read the device's gyroscope sensor.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Gyroscope plugin is one of the most complete motion sensing solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- ⚡ **Real-time measurements**: Continuous gyroscope data with event listeners.
- 📊 **Rotation rate**: Accurate x, y, and z-axis rotation rate in rad/s.
- 🔒 **Permission handling**: Built-in permission management for sensor access.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- 🤝 **Compatibility**: Works alongside the [Accelerometer](https://capawesome.io/docs/sdks/capacitor/accelerometer/), [Barometer](https://capawesome.io/docs/sdks/capacitor/barometer/) and [Pedometer](https://capawesome.io/docs/sdks/capacitor/pedometer/) plugins.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Gyroscope plugin is typically used whenever an app needs to react to the rotation of the device, for example:

- **Motion-controlled games**: Use the real-time rotation rate to steer a character or vehicle by tilting the device.
- **Immersive experiences**: Rotate 360° views or augmented reality scenes based on the device's rotation.
- **Gesture detection**: Detect rotation gestures such as twisting the device to trigger actions in your app.
- **Motion analysis**: Record the rotation rate around the x, y, and z axes to analyze movement patterns.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-gyroscope` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-gyroscope
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

Add the `NSMotionUsageDescription` key to the `ios/App/App/Info.plist` file, which tells the user why your app needs access to the device's motion data:

```xml
<key>NSMotionUsageDescription</key>
<string>The app needs to access the motion activity.</string>
```

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to check if the gyroscope is available, get a single measurement, receive continuous measurement updates, check and request permissions, and remove all listeners.

### Check if the gyroscope is available

Check whether the device has a gyroscope sensor before using the other methods:

```typescript
import { Gyroscope } from '@capawesome/capacitor-gyroscope';

const isAvailable = async () => {
  const result = await Gyroscope.isAvailable();
  return result.available;
};
```

### Get a single measurement

Get the most recent measurement from the gyroscope sensor. The rotation rate around the x, y, and z axes is reported in radians per second (rad/s):

```typescript
import { Gyroscope } from '@capawesome/capacitor-gyroscope';

const getMeasurement = async () => {
  const measurement = await Gyroscope.getMeasurement();
  console.log('X: ', measurement.x);
  console.log('Y: ', measurement.y);
  console.log('Z: ', measurement.z);
};
```

### Receive continuous measurement updates

Add a listener for the `measurement` event and start the measurement updates to receive the rotation rate in real time. The `measurement` event is only available on Android and iOS:

```typescript
import { Gyroscope } from '@capawesome/capacitor-gyroscope';

const startMeasurementUpdates = async () => {
  await Gyroscope.addListener('measurement', measurement => {
    console.log('X: ', measurement.x);
    console.log('Y: ', measurement.y);
    console.log('Z: ', measurement.z);
  });
  await Gyroscope.startMeasurementUpdates();
};

const stopMeasurementUpdates = async () => {
  await Gyroscope.stopMeasurementUpdates();
};
```

### Check and request permissions

Check and request the permission to access the gyroscope sensor:

```typescript
import { Gyroscope } from '@capawesome/capacitor-gyroscope';

const checkPermissions = async () => {
  const result = await Gyroscope.checkPermissions();
  return result;
};

const requestPermissions = async () => {
  const result = await Gyroscope.requestPermissions();
  return result;
};
```

### Remove all listeners

Remove all listeners when you no longer need them:

```typescript
import { Gyroscope } from '@capawesome/capacitor-gyroscope';

const removeAllListeners = async () => {
  await Gyroscope.removeAllListeners();
};
```

## API

<docgen-index>

* [`addListener('measurement', ...)`](#addlistenermeasurement-)
* [`checkPermissions()`](#checkpermissions)
* [`getMeasurement()`](#getmeasurement)
* [`isAvailable()`](#isavailable)
* [`removeAllListeners()`](#removealllisteners)
* [`requestPermissions()`](#requestpermissions)
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


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check if the app has permission to access the gyroscope sensor.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getMeasurement()

```typescript
getMeasurement() => Promise<GetMeasurementResult>
```

Get the latest measurement.

This method returns the most recent measurement from the gyroscope sensor.

**Returns:** <code>Promise&lt;<a href="#measurement">Measurement</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if the gyroscope sensor is available on the device.

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


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request permission to access the gyroscope sensor.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.1.0

--------------------


### startMeasurementUpdates()

```typescript
startMeasurementUpdates() => Promise<void>
```

Start emitting `measurement` events.

**Since:** 0.1.0

--------------------


### stopMeasurementUpdates()

```typescript
stopMeasurementUpdates() => Promise<void>
```

Stop emitting `measurement` events.

**Since:** 0.1.0

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### Measurement

| Prop    | Type                | Description                                                        | Since |
| ------- | ------------------- | ------------------------------------------------------------------ | ----- |
| **`x`** | <code>number</code> | The rotation rate around the x-axis in radians per second (rad/s). | 0.1.0 |
| **`y`** | <code>number</code> | The rotation rate around the y-axis in radians per second (rad/s). | 0.1.0 |
| **`z`** | <code>number</code> | The rotation rate around the z-axis in radians per second (rad/s). | 0.1.0 |


#### PermissionStatus

| Prop            | Type                                                                          | Description                                    | Since |
| --------------- | ----------------------------------------------------------------------------- | ---------------------------------------------- | ----- |
| **`gyroscope`** | <code><a href="#gyroscopepermissionstate">GyroscopePermissionState</a></code> | The permission status of the gyroscope sensor. | 0.1.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                              | Since |
| --------------- | -------------------- | -------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether the gyroscope sensor is available on the device. | 0.1.0 |


### Type Aliases


#### MeasurementEvent

<code><a href="#measurement">Measurement</a></code>


#### GyroscopePermissionState

<code><a href="#permissionstate">PermissionState</a> | 'limited'</code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### GetMeasurementResult

<code><a href="#measurement">Measurement</a></code>

</docgen-api>

## FAQ

### Which platforms does the plugin support?

The plugin supports Android and iOS. The continuous `measurement` event is only emitted on Android and iOS, so there is no gyroscope support in the browser.

### What units are the measurements reported in?

Each measurement contains the rotation rate around the x, y, and z axes in radians per second (rad/s).

### What is the difference between `getMeasurement` and the `measurement` event?

The `getMeasurement()` method returns the most recent measurement from the gyroscope sensor once. If you need continuous real-time updates instead, add a listener for the `measurement` event and call `startMeasurementUpdates()`. Call `stopMeasurementUpdates()` when you no longer need updates.

### Do I need any permissions to read the gyroscope?

The plugin provides the `checkPermissions()` and `requestPermissions()` methods to manage access to the gyroscope sensor. On iOS, you also have to add the `NSMotionUsageDescription` key to your `Info.plist` file, which tells the user why your app needs access to the device's motion data (see [Installation](#installation)).

### How do I know if the device has a gyroscope sensor?

Call the `isAvailable()` method. It returns whether the gyroscope sensor is available on the device, so you can hide or disable motion features on devices without a gyroscope.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Accelerometer](https://capawesome.io/docs/sdks/capacitor/accelerometer/): Capture the acceleration force along the x, y, and z axes.
- [Barometer](https://capawesome.io/docs/sdks/capacitor/barometer/): Obtain the static air pressure measured in hectopascals (hPa).
- [Compass](https://capawesome.io/docs/sdks/capacitor/compass/): Read the device compass heading.
- [Pedometer](https://capawesome.io/docs/sdks/capacitor/pedometer/): Retrieve motion data such as the number of steps and the distance traveled.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/gyroscope/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/gyroscope/LICENSE).
