# Capacitor Pedometer Plugin

Capacitor plugin to retrieve motion data, such as the number of steps and other information about the distance traveled.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Pedometer plugin is one of the most complete motion tracking solutions for Capacitor apps. Here are some of the key features:

- 🖥️ **Cross-platform**: Supports Android and iOS.
- 🚶 **Motion Tracking**: Retrieve step count, distance, pace, cadence, and floor counting data.
- 📊 **Real-time Updates**: Stream live pedometer data with event listeners for continuous monitoring.
- 📅 **Historical Data**: Query pedometer measurements for specific time ranges and periods.
- 🔍 **Feature Detection**: Check device capability for different pedometer features (steps, distance, floors, etc.).
- 🤝 **Compatibility**: Works alongside the [Accelerometer](https://capawesome.io/docs/sdks/capacitor/accelerometer/), [Barometer](https://capawesome.io/docs/sdks/capacitor/barometer/) and [Gyroscope](https://capawesome.io/docs/sdks/capacitor/gyroscope/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Pedometer plugin is typically used whenever an app needs to track the user's physical activity, for example:

- **Fitness and step tracking**: Show the user's step count in a health or fitness app.
- **Workout sessions**: Stream live pedometer data with the `measurement` event to display real-time progress during a walk or run.
- **Distance and pace insights**: Display the distance traveled, current pace, and cadence of the user on iOS.
- **Floor climbing challenges**: Count the floors ascended and descended by the user on iOS.
- **Activity history**: Query pedometer measurements for a specific time range to build daily or weekly summaries.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |

## Guides

- [The Complete Guide to Capacitor Device Sensors](https://capawesome.io/blog/capacitor-device-sensors-guide/): Fitness-tracking use cases and how this plugin compares to the raw Accelerometer.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-pedometer` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-pedometer
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

The following examples show how to check which pedometer features are available, retrieve a measurement, receive live measurement updates, and check and request permissions.

### Check which features are available

Not every device supports every pedometer feature. Use `isAvailable()` to check whether step counting, distance tracking, pace, cadence, and floor counting are supported before relying on them:

```typescript
import { Pedometer } from '@capawesome-team/capacitor-pedometer';

const isAvailable = async () => {
  const { cadence, distance, floorCounting, pace, stepCounting } = await Pedometer.isAvailable();
  console.log('Cadence available:', cadence);
  console.log('Distance available:', distance);
  console.log('Floor counting available:', floorCounting);
  console.log('Pace available:', pace);
  console.log('Step counting available:', stepCounting);
};
```

### Retrieve a pedometer measurement

Retrieve the pedometer data, such as the number of steps taken by the user. On iOS, you must provide the `start` and `end` options to query a specific time range. Only available on Android and iOS:

```typescript
import { Pedometer } from '@capawesome-team/capacitor-pedometer';

const getMeasurement = async () => {
  const { measurement } = await Pedometer.getMeasurement();
  console.log(measurement);
};
```

### Receive live measurement updates

Add a listener for the `measurement` event and call `startMeasurementUpdates()` to continuously receive pedometer data, for example during a workout session. Call `stopMeasurementUpdates()` when you no longer need updates. Only available on Android and iOS:

```typescript
import { Pedometer } from '@capawesome-team/capacitor-pedometer';

const startMeasurementUpdates = async () => {
  Pedometer.addListener('measurement', (event) => {
    console.log('New measurement:', event);
  });
  await Pedometer.startMeasurementUpdates();
};

const stopMeasurementUpdates = async () => {
  await Pedometer.stopMeasurementUpdates();
};
```

### Check and request permissions

Check and request the permission for the activity recognition feature before accessing pedometer data:

```typescript
import { Pedometer } from '@capawesome-team/capacitor-pedometer';

const checkPermissions = async () => {
  const status = await Pedometer.checkPermissions();
  console.log('Permission status:', status);
};

const requestPermissions = async () => {
  const status = await Pedometer.requestPermissions();
  console.log('Permission status after request:', status);
};
```

## API

<docgen-index>

* [`getMeasurement(...)`](#getmeasurement)
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

### getMeasurement(...)

```typescript
getMeasurement(options?: GetMeasurementOptions | undefined) => Promise<GetMeasurementResult>
```

Retrieve the pedometer data (for a specific time range).

Only available on Android and iOS.

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#getmeasurementoptions">GetMeasurementOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#measurement">Measurement</a>&gt;</code>

**Since:** 7.0.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check which features are available on the device.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 7.0.0

--------------------


### startMeasurementUpdates()

```typescript
startMeasurementUpdates() => Promise<void>
```

Start receiving updates for the pedometer data.

Only available on Android and iOS.

**Since:** 7.0.0

--------------------


### stopMeasurementUpdates()

```typescript
stopMeasurementUpdates() => Promise<void>
```

Stop receiving updates for the pedometer data.

Only available on Android and iOS.

**Since:** 7.0.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permissions for the plugin.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 7.0.0

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request permissions for the plugin.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 7.0.0

--------------------


### addListener('measurement', ...)

```typescript
addListener(eventName: 'measurement', listenerFunc: (event: MeasurementEvent) => void) => Promise<PluginListenerHandle>
```

Called when the pedometer data is updated.

When the app is suspended, the delivery of updates stops temporarily.
When the app is resumed, the updates will continue from where they left off.

**Note:** The `startMeasurementUpdates` method must be called
before this event can be received.

Only available on Android and iOS.

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'measurement'</code>                                              |
| **`listenerFunc`** | <code>(event: <a href="#measurement">Measurement</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.0.0

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

| Prop                    | Type                | Description                                                                             | Since |
| ----------------------- | ------------------- | --------------------------------------------------------------------------------------- | ----- |
| **`averageActivePace`** | <code>number</code> | The average pace of the user, measured in seconds per meter. Only available on iOS.     | 7.0.0 |
| **`currentCadence`**    | <code>number</code> | The rate at which steps are taken, measured in steps per second. Only available on iOS. | 7.0.0 |
| **`currentPace`**       | <code>number</code> | The current pace of the user, measured in seconds per meter. Only available on iOS.     | 7.0.0 |
| **`distance`**          | <code>number</code> | The estimated distance traveled by the user, measured in meters. Only available on iOS. | 7.0.0 |
| **`end`**               | <code>number</code> | The end date of the data in milliseconds since epoch.                                   | 7.0.0 |
| **`floorsAscended`**    | <code>number</code> | The number of floors ascended by the user. Only available on iOS.                       | 7.0.0 |
| **`floorsDescended`**   | <code>number</code> | The number of floors descended by the user. Only available on iOS.                      | 7.0.0 |
| **`numberOfSteps`**     | <code>number</code> | The number of steps taken by the user.                                                  | 7.0.0 |
| **`start`**             | <code>number</code> | The start date of the data in milliseconds since epoch.                                 | 7.0.0 |


#### GetMeasurementOptions

| Prop        | Type                | Description                                                                                                                                                                                   | Since |
| ----------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`end`**   | <code>number</code> | The end date for the query in milliseconds since epoch. On **iOS**, this option must be provided. Only available on iOS.                                                                      | 7.0.0 |
| **`start`** | <code>number</code> | The start date for the query in milliseconds since epoch. On **Android**, this is always set to the boot time of the device. On **iOS**, this option must be provided. Only available on iOS. | 7.0.0 |


#### IsAvailableResult

| Prop                | Type                 | Description                                                                                                                                        | Since |
| ------------------- | -------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`cadence`**       | <code>boolean</code> | Indicates whether cadence tracking is available on the device. On **Android**, this is always `false` because cadence tracking is not supported.   | 7.0.0 |
| **`distance`**      | <code>boolean</code> | Indicates whether distance tracking is available on the device. On **Android**, this is always `false` because distance tracking is not supported. | 7.0.0 |
| **`floorCounting`** | <code>boolean</code> | Indicates whether floor counting is available on the device. On **Android**, this is always `false` because floor counting is not supported.       | 7.0.0 |
| **`pace`**          | <code>boolean</code> | Indicates whether pace tracking is available on the device. On **Android**, this is always `false` because pace tracking is not supported.         | 7.0.0 |
| **`stepCounting`**  | <code>boolean</code> | Indicates whether step counting is available on the device.                                                                                        | 7.0.0 |


#### PermissionStatus

| Prop                      | Type                                                        | Description                                                | Since |
| ------------------------- | ----------------------------------------------------------- | ---------------------------------------------------------- | ----- |
| **`activityRecognition`** | <code><a href="#permissionstate">PermissionState</a></code> | The permission state for the activity recognition feature. | 7.0.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### GetMeasurementResult

<code><a href="#measurement">Measurement</a></code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### MeasurementEvent

<code><a href="#measurement">Measurement</a></code>

</docgen-api>

## FAQ

### Does the plugin work on the web?

No, the plugin only supports Android and iOS. The pedometer methods such as `getMeasurement(...)` and `startMeasurementUpdates()` rely on the native motion APIs of the device and are therefore not available on the web.

### Why are distance, pace, cadence, and floor counts missing on Android?

On Android, only step counting is supported. Distance tracking, pace, cadence, and floor counting are only available on iOS. You can use the `isAvailable()` method to check at runtime which features are supported on the current device, as shown in the [usage example](#check-which-features-are-available) above.

### Do I need any permissions to use the plugin?

Yes. The plugin provides the `checkPermissions()` and `requestPermissions()` methods to check and request the permission for the activity recognition feature. On iOS, you also need to add the `NSMotionUsageDescription` key to your `Info.plist` file, as described in the [Installation](#installation) section.

### Can I query pedometer data for a specific time range?

Yes, the `getMeasurement(...)` method accepts `start` and `end` options in milliseconds since epoch. On iOS, both options must be provided. On Android, the start date is always set to the boot time of the device.

### Why do I stop receiving measurement events while the app is suspended?

When the app is suspended, the delivery of updates stops temporarily. When the app is resumed, the updates will continue from where they left off. Also note that the `startMeasurementUpdates()` method must be called before the `measurement` event can be received.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Accelerometer](https://capawesome.io/docs/sdks/capacitor/accelerometer/): Capture the acceleration force along the x, y, and z axes.
- [Barometer](https://capawesome.io/docs/sdks/capacitor/barometer/): Obtain the static air pressure measured in hectopascals.
- [Gyroscope](https://capawesome.io/docs/sdks/capacitor/gyroscope/): Read the device's gyroscope sensor.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/pedometer/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/pedometer/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/pedometer/LICENSE).
