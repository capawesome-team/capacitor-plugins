# @capawesome/capacitor-compass

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

```typescript
import { Compass } from '@capawesome/capacitor-compass';

const getHeading = async () => {
  const heading = await Compass.getHeading();
  return heading;
};

const isAvailable = async () => {
  const { available } = await Compass.isAvailable();
  return available;
};

const startHeadingUpdates = async () => {
  await Compass.startHeadingUpdates();
};

const stopHeadingUpdates = async () => {
  await Compass.stopHeadingUpdates();
};

const addHeadingChangeListener = async () => {
  await Compass.addListener('headingChange', heading => {
    console.log(heading);
  });
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

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/compass/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/compass/LICENSE).
