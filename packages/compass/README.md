# Capacitor Compass Plugin

Capacitor plugin for reading the device compass heading.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

- 🧭 **Heading**: Read the current compass heading on demand.
- 🔄 **Live updates**: Listen for continuous heading changes.
- 🌍 **True north**: Read the true (geographic) north heading on iOS.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Compass plugin is typically used whenever an app needs to know which direction the device is pointing, for example:

- **Compass apps**: Build a classic compass UI that rotates with live heading updates.
- **Navigation**: Rotate a map or show the direction the user is currently facing.
- **Outdoor activities**: Guide hikers or geocachers towards a target bearing.
- **Points of interest**: Point the user towards a fixed geographic direction, such as a landmark or a prayer direction.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-compass` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-compass
npx cap sync
```

This plugin is only available on **Android** and **iOS**. On the Web, all methods reject as unimplemented.

### iOS

#### Privacy Descriptions

The magnetic heading is available without any permission. To also read the true (geographic) north heading, location services must be enabled and the `NSLocationWhenInUseUsageDescription` key must be added to the `Info.plist` file of your app.

```xml
<key>NSLocationWhenInUseUsageDescription</key>
<string>Your location is used to determine the true (geographic) north heading.</string>
```

If the key is missing or location permission is not granted, the `trueHeading` value is `null`.

## Configuration

No configuration required for this plugin.

## Usage

Import the plugin and call its methods:

```typescript
import { Compass } from '@capawesome/capacitor-compass';
```

### Check if the compass sensor is available

Before reading the heading, you can check whether the device has a compass sensor. Only available on Android and iOS:

```typescript
const isAvailable = async () => {
  const { available } = await Compass.isAvailable();
  return available;
};
```

### Read the current heading

Get the most recent heading reading from the device's compass sensor. The result contains the magnetic heading, the true (geographic) heading if available, and the accuracy. Only available on Android and iOS:

```typescript
const getHeading = async () => {
  const heading = await Compass.getHeading();
  return heading;
};
```

### Listen for heading changes

Call `startHeadingUpdates()` to start emitting `headingChange` events and add a listener to receive continuous heading updates, for example to rotate a compass UI. Stop the updates when you no longer need them. Only available on Android and iOS:

```typescript
const startHeadingUpdates = async () => {
  await Compass.startHeadingUpdates();
};

const addHeadingChangeListener = async () => {
  await Compass.addListener('headingChange', heading => {
    console.log(heading);
  });
};

const stopHeadingUpdates = async () => {
  await Compass.stopHeadingUpdates();
};

const removeAllListeners = async () => {
  await Compass.removeAllListeners();
};
```

## API

<docgen-index>

* [`addListener('headingChange', ...)`](#addlistenerheadingchange-)
* [`getHeading()`](#getheading)
* [`isAvailable()`](#isavailable)
* [`removeAllListeners()`](#removealllisteners)
* [`startHeadingUpdates()`](#startheadingupdates)
* [`stopHeadingUpdates()`](#stopheadingupdates)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### addListener('headingChange', ...)

```typescript
addListener(eventName: 'headingChange', listenerFunc: (event: HeadingChangeEvent) => void) => Promise<PluginListenerHandle>
```

Add a listener for heading changes.

Only available on Android and iOS.

| Param              | Type                                                            |
| ------------------ | --------------------------------------------------------------- |
| **`eventName`**    | <code>'headingChange'</code>                                    |
| **`listenerFunc`** | <code>(event: <a href="#heading">Heading</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.1.0

--------------------


### getHeading()

```typescript
getHeading() => Promise<GetHeadingResult>
```

Get the current device heading.

This method returns the most recent heading reading from the device's
compass sensor.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#heading">Heading</a>&gt;</code>

**Since:** 0.1.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if the compass sensor is available on the device.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 0.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### startHeadingUpdates()

```typescript
startHeadingUpdates() => Promise<void>
```

Start emitting `headingChange` events.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### stopHeadingUpdates()

```typescript
stopHeadingUpdates() => Promise<void>
```

Stop emitting `headingChange` events.

Only available on Android and iOS.

**Since:** 0.1.0

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### Heading

| Prop                  | Type                        | Description                                                                                                                                                                                                                                                                                                                                                                                                    | Since |
| --------------------- | --------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`accuracy`**        | <code>number \| null</code> | The maximum deviation between the reported heading and the true heading in degrees. A negative value or `null` indicates that the accuracy is invalid or unknown.                                                                                                                                                                                                                                              | 0.1.0 |
| **`magneticHeading`** | <code>number</code>         | The heading relative to magnetic north in degrees. The value ranges from `0` to `360`, where `0` means the device is pointing towards magnetic north.                                                                                                                                                                                                                                                          | 0.1.0 |
| **`trueHeading`**     | <code>number \| null</code> | The heading relative to true (geographic) north in degrees. The value ranges from `0` to `360`, where `0` means the device is pointing towards true north. Returns `null` if the true heading cannot be determined. On Android, this value is always `null`. On iOS, this value requires location services to be enabled and the `NSLocationWhenInUseUsageDescription` key to be set. Otherwise, it is `null`. | 0.1.0 |


#### IsAvailableResult

| Prop            | Type                 | Description                                            | Since |
| --------------- | -------------------- | ------------------------------------------------------ | ----- |
| **`available`** | <code>boolean</code> | Whether the compass sensor is available on the device. | 0.1.0 |


### Type Aliases


#### HeadingChangeEvent

<code><a href="#heading">Heading</a></code>


#### GetHeadingResult

<code><a href="#heading">Heading</a></code>

</docgen-api>

## FAQ

### What is the difference between the magnetic heading and the true heading?

The `magneticHeading` value is the heading relative to magnetic north, while the `trueHeading` value is the heading relative to true (geographic) north. Both range from `0` to `360` degrees. The magnetic heading is always available, whereas the true heading can only be determined on iOS and may be `null`.

### Why is the `trueHeading` value always `null`?

On Android, the true heading is not supported and the value is always `null`. On iOS, reading the true heading requires location services to be enabled and the `NSLocationWhenInUseUsageDescription` key to be added to your app's `Info.plist` file, as described in the [Installation](#installation) section. If the key is missing or location permission is not granted, the value is `null`.

### Does this plugin work on the Web?

No, this plugin is only available on Android and iOS. On the Web, all methods reject as unimplemented. You can use the `isAvailable()` method to check whether the compass sensor is available on the current device.

### Do I need any permissions to read the compass heading?

The magnetic heading is available without any permission on both Android and iOS. Only if you also want to read the true (geographic) north heading on iOS, location services must be enabled and the `NSLocationWhenInUseUsageDescription` key must be present in your `Info.plist` file.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Accelerometer](https://capawesome.io/docs/sdks/capacitor/accelerometer/): Capture the acceleration force along the x, y, and z axes.
- [Gyroscope](https://capawesome.io/docs/sdks/capacitor/gyroscope/): Read the device's gyroscope sensor.
- [Barometer](https://capawesome.io/docs/sdks/capacitor/barometer/): Obtain the static air pressure measured in hectopascals.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/compass/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/compass/LICENSE).
