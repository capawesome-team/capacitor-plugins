# Capacitor Barometer Plugin

Capacitor plugin to obtain the static air pressure, which is measured in hectopascals (hPa).

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Barometer plugin is one of the most complete air pressure sensing solutions for Capacitor apps. Here are some of the key features:

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

## Use Cases

The Barometer plugin is typically used whenever an app needs to react to changes in air pressure, for example:

- **Weather apps**: Show the current air pressure and track pressure trends to indicate weather changes.
- **Altitude tracking**: Use the relative altitude on iOS to detect elevation changes, for example while hiking or climbing stairs.
- **Outdoor and fitness apps**: Combine the pressure readings with their timestamps to analyze elevation profiles during activities.
- **Sensor dashboards**: Display real-time barometer readings using continuous measurement updates.

📖 **Guide**: For outdoor and hiking apps that need faster-than-GPS elevation change detection, see [The Complete Guide to Capacitor Device Sensors](https://capawesome.io/blog/capacitor-device-sensors-guide/).

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 8.x.x          | >=8.x.x           | Active support |
| 7.x.x          | 7.x.x             | Deprecated     |

## Guides

- [Announcing the Capacitor Barometer Plugin](https://capawesome.io/blog/announcing-the-capacitor-barometer-plugin/)
- [Exploring the Capacitor Barometer API](https://capawesome.io/blog/exploring-the-capacitor-barometer-api/)

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-barometer` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
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

The following examples show how to check whether the barometer is available, request permissions, read the latest measurement, and listen for continuous measurement updates.

### Check if the barometer is available

Check whether the device has a barometer sensor before using the other methods:

```typescript
import { Barometer } from '@capawesome-team/capacitor-barometer';

const isAvailable = async () => {
  const result = await Barometer.isAvailable();
  console.log('Barometer is available:', result.isAvailable);
};
```

### Check and request permissions

Check and request permission to access the barometer sensor. On iOS, make sure you have added the `NSMotionUsageDescription` key to your `Info.plist` file (see [Installation](#installation)):

```typescript
import { Barometer } from '@capawesome-team/capacitor-barometer';

const checkPermissions = async () => {
  const status = await Barometer.checkPermissions();
  console.log('Barometer permission status:', status.barometer);
};

const requestPermissions = async () => {
  const status = await Barometer.requestPermissions();
  console.log('Barometer permission status after request:', status.barometer);
};
```

### Get the latest measurement

Get the most recent measurement from the barometer sensor. It contains the air pressure in hectopascals (hPa), the timestamp of the measurement and, on iOS, the relative altitude in meters:

```typescript
import { Barometer } from '@capawesome-team/capacitor-barometer';

const getMeasurement = async () => {
  const { measurement } = await Barometer.getMeasurement();
  console.log('Pressure:', measurement.pressure, 'hPa');
  console.log('Relative Altitude:', measurement.relativeAltitude, 'm');
  console.log('Timestamp:', new Date(measurement.timestamp));
};
```

### Listen for measurement updates

Add a listener for the `measurement` event and start emitting measurements to monitor air pressure changes continuously. When you no longer need updates, stop them and remove your listeners:

```typescript
import { Barometer } from '@capawesome-team/capacitor-barometer';

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

## FAQ

### What unit is the air pressure measured in?

The plugin returns the static air pressure in hectopascals (hPa). Each measurement also includes a timestamp in milliseconds since the epoch.

### How can I measure altitude with this plugin?

On iOS, each measurement includes the relative altitude in meters in addition to the air pressure. On Android, only the air pressure is available. See the [usage example](#get-the-latest-measurement) above.

### What happens if the device does not have a barometer sensor?

You can check whether the barometer sensor is available on the device using the `isAvailable()` method. It is recommended to call this method before using the other methods of the plugin.

### Do I need any permissions to use the barometer?

The plugin provides the `checkPermissions()` and `requestPermissions()` methods to manage access to the barometer sensor. On iOS, you also need to add the `NSMotionUsageDescription` key to your `Info.plist` file as described in the [Installation](#installation) section.

### How do I monitor air pressure changes continuously?

Add a listener for the `measurement` event and call `startMeasurementUpdates()` to start receiving measurements. When you no longer need updates, call `stopMeasurementUpdates()` and remove your listeners with `removeAllListeners()`. See the [usage example](#listen-for-measurement-updates) above.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Accelerometer](https://capawesome.io/docs/sdks/capacitor/accelerometer/): Capture the acceleration force along the x, y, and z axes.
- [Gyroscope](https://capawesome.io/docs/sdks/capacitor/gyroscope/): Read the device's gyroscope sensor.
- [Compass](https://capawesome.io/docs/sdks/capacitor/compass/): Read the device's compass heading.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/barometer/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/barometer/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/barometer/LICENSE).
