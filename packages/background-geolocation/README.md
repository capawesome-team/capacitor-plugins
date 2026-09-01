# Capacitor Background Geolocation Plugin

Capacitor plugin for reliable background geolocation tracking on Android and iOS. Provides a first-class permissions API, a correctly plumbed Android foreground service and fine-grained tuning options.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Background Geolocation plugin provides everything you need for reliable location tracking, even while the app is in the background. Here are some of the key features:

- 🛰️ **Background Tracking**: Keeps receiving position updates while the app is in the background.
- 🔐 **Granular Permissions**: First-class permissions API including the two-step background location upgrade flow.
- 📍 **One-Shot Position**: Get the current position with configurable accuracy, timeout and maximum age.
- 🗄️ **Local Queue**: Persist every position in an on-device SQLite queue and read it out later, so nothing is lost while the web view is suspended.
- ☁️ **HTTP Upload**: Upload queued positions to your own server in batches with automatic retries and at-least-once delivery.
- 🧪 **Upload Testing**: Test the upload pipeline without your own server using the free [Background Geolocation Playground](https://background-geolocation-playground.capawesome.io) webpage.
- 🛠️ **Real Tuning Knobs**: Configure accuracy, distance filter and update interval.
- 🤖 **Foreground Service**: Correct Android 14+ foreground service with a fully configurable notification.
- 📡 **Provider Choice**: Fused location provider by default with an escape hatch to the platform location manager for devices without Google Play services.
- 🎯 **Full Accuracy Requests**: Request temporary full accuracy from users with reduced location accuracy on iOS.
- 🕵️ **Mock Location Detection**: Every position reports whether it was delivered by a mock location provider on Android.
- 🔋 **Battery Friendly**: Optional automatic pausing of position updates on iOS when the device is stationary.
- 🔒 **Public APIs Only**: Built exclusively on public platform APIs, so it is safe for App Review and resilient to OS updates.
- 🤝 **Compatibility**: Works hand in hand with the [Geofences](https://capawesome.io/docs/sdks/capacitor/geofences/) and [Settings Launcher](https://capawesome.io/docs/sdks/capacitor/settings-launcher/) plugins.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Background Geolocation plugin is typically used whenever an app needs to know where the device is over a period of time, for example:

- **Fitness and activity tracking**: Record runs, rides and hikes even when the screen is off.
- **Fleet and workforce management**: Track delivery drivers or field workers during their shift.
- **Navigation**: Keep the position up to date while the user switches to another app.
- **Safety**: Share the live location with family members or emergency contacts.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Guides

- [Announcing the Capacitor Background Geolocation Plugin](https://capawesome.io/blog/announcing-the-capacitor-background-geolocation-plugin/): A tour of watch sessions, the native SQLite queue, and the HTTP upload pipeline.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-background-geolocation` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-background-geolocation
npx cap sync
```

### Android

#### Permissions

The plugin already declares the location and foreground service permissions in its own manifest.
If you want to receive position updates while the app is in the **background**, you must additionally declare the `ACCESS_BACKGROUND_LOCATION` permission in the `AndroidManifest.xml` file of your app before or after the `application` tag:

```xml
<!-- Required if you want to receive position updates while the app is in the background. -->
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
```

**Attention**: This permission is deliberately **not** declared by the plugin because Google Play requires a policy declaration and review for every app that requests background location access. Only declare it if your app really needs background tracking and make sure to complete the [location permissions declaration](https://support.google.com/googleplay/android-developer/answer/9799150) in the Google Play Console.

The `INTERNET` permission that the [HTTP Upload](#http-upload) feature requires is already declared by every Capacitor app template, so no additional configuration is needed for it.

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

#### Variables

If needed, you can define the following project variable in your app’s `variables.gradle` file to change the default version of the dependency:

- `$playServicesLocationVersion` version of `com.google.android.gms:play-services-location` (default: `21.4.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

Add the `NSLocationWhenInUseUsageDescription` and `NSLocationAlwaysAndWhenInUseUsageDescription` keys to the `Info.plist` file of your app to explain why your app needs access to the location:

```xml
<key>NSLocationWhenInUseUsageDescription</key>
<string>The app needs access to your location to track your position.</string>
<key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
<string>The app needs access to your location to track your position, even while the app is in the background.</string>
```

If you want to receive position updates while the app is in the **background**, you must also enable the `location` background mode in the `Info.plist` file of your app:

```xml
<key>UIBackgroundModes</key>
<array>
  <string>location</string>
</array>
```

If you want to use the `requestTemporaryFullAccuracy(...)` method, you must also add the `NSLocationTemporaryUsageDescriptionDictionary` key to the `Info.plist` file of your app with one entry per purpose key:

```xml
<key>NSLocationTemporaryUsageDescriptionDictionary</key>
<dict>
  <key>navigation</key>
  <string>The app needs your precise location to provide turn-by-turn navigation.</string>
</dict>
```

## Configuration

No configuration required for this plugin.

## Usage

The following examples show how to get the current position, watch the position of the device, read queued positions, upload positions to a server, request temporary full accuracy, and check and request permissions.

### Get the current position

Get a one-shot position of the device with a configurable accuracy, timeout and maximum age. Only available on Android and iOS:

```typescript
import { Accuracy, BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const getCurrentPosition = async () => {
  const { position } = await BackgroundGeolocation.getCurrentPosition({
    accuracy: Accuracy.High,
    timeout: 10000,
  });
  return position;
};
```

### Watch the position

Start a watch session to receive position updates via the `positionChange` event, even while the app is in the background. Errors that occur during an active watch session are delivered via the `positionError` event. Only one watch session can be active at a time. Only available on Android and iOS:

```typescript
import { Accuracy, ActivityType, BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const startWatching = async () => {
  // Enable the queue so that no position is lost while the web view is suspended.
  await BackgroundGeolocation.setConfig({ maxSize: 50000 });
  await BackgroundGeolocation.addListener('positionChange', event => {
    console.log('New position: ', event.position);
  });
  await BackgroundGeolocation.addListener('positionError', event => {
    console.error('Position error: ', event.code, event.message);
  });
  await BackgroundGeolocation.startWatching({
    accuracy: Accuracy.High,
    distanceFilter: 10,
    androidInterval: 5000,
    androidNotification: {
      title: 'Location Tracking',
      text: 'Your location is being tracked.',
    },
    iosActivityType: ActivityType.Fitness,
  });
};

const stopWatching = async () => {
  await BackgroundGeolocation.stopWatching();
};

const isWatching = async () => {
  const { watching } = await BackgroundGeolocation.isWatching();
  return watching;
};
```

### Read queued positions

The plugin can store every position of a watch session in a local queue that survives app restarts, so your app can read the positions it missed while the web view was suspended. Enable the queue with `setConfig(...)` and drain it whenever your app is in the foreground again. Only available on Android and iOS:

```typescript
import { BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const enableQueue = async () => {
  await BackgroundGeolocation.setConfig({ maxSize: 50000 });
};

const drainQueue = async () => {
  let hasMore = true;
  while (hasMore) {
    const result = await BackgroundGeolocation.getQueuedPositions({
      limit: 1000,
    });
    if (!result.positions.length) {
      break;
    }
    await persist(result.positions);
    await BackgroundGeolocation.deleteQueuedPositions({
      upToId: result.positions[result.positions.length - 1].id,
    });
    hasMore = result.hasMore;
  }
};
```

See [Queue](#queue) for all options, the drain loop and the queue behavior.

### Upload positions to a server

The plugin can upload every queued position to your own server without involving JavaScript, so that no position is lost while the device is offline. Provide the `url` option to enable it. Only available on Android and iOS:

```typescript
import { BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const enableUpload = async () => {
  await BackgroundGeolocation.setConfig({
    url: 'https://api.example.com/positions',
    headers: {
      Authorization: 'Bearer eyJhbGciOi...',
    },
  });
};
```

See [HTTP Upload](#http-upload) for all options, the server contract and the response handling.

### Request temporary full accuracy

Ask users who have granted reduced location accuracy for full accuracy for the duration of the app session. The `NSLocationTemporaryUsageDescriptionDictionary` key must contain an entry for the given purpose key. Only available on iOS:

```typescript
import { BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const requestTemporaryFullAccuracy = async () => {
  await BackgroundGeolocation.requestTemporaryFullAccuracy({
    purposeKey: 'navigation',
  });
};
```

### Check and request permissions

Check and request the permissions to access the location services. The background location permission must be requested in **two steps** on both platforms. Only available on Android and iOS:

1. Request the `location` (and optionally `notifications`) permission first. This displays the default system dialog.
2. Request the `backgroundLocation` permission in a **separate** call, ideally after explaining to the user why the app needs it:
   - On **Android 11+**, the user is taken to the location settings of the app where the `Allow all the time` option must be selected. Google recommends showing an in-app explanation before triggering this step.
   - On **iOS**, the operating system presents the upgrade prompt that asks the user to change the permission from `While Using the App` to `Always`.

```typescript
import { BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const checkPermissions = async () => {
  return BackgroundGeolocation.checkPermissions();
};

const requestPermissions = async () => {
  return BackgroundGeolocation.requestPermissions({
    permissions: ['location', 'notifications'],
  });
};

const requestBackgroundLocationPermission = async () => {
  return BackgroundGeolocation.requestPermissions({
    permissions: ['backgroundLocation'],
  });
};
```

If a permission was permanently denied, you can take the user to the app settings to change it:

```typescript
import { BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const openSettings = async () => {
  await BackgroundGeolocation.openSettings();
};
```

## API

<docgen-index>

* [`checkPermissions()`](#checkpermissions)
* [`clearQueue()`](#clearqueue)
* [`deleteQueuedPositions(...)`](#deletequeuedpositions)
* [`getConfig()`](#getconfig)
* [`getCurrentPosition(...)`](#getcurrentposition)
* [`getQueuedPositions(...)`](#getqueuedpositions)
* [`getQueueStatus()`](#getqueuestatus)
* [`isWatching()`](#iswatching)
* [`openSettings()`](#opensettings)
* [`requestPermissions(...)`](#requestpermissions)
* [`requestTemporaryFullAccuracy(...)`](#requesttemporaryfullaccuracy)
* [`resetConfig()`](#resetconfig)
* [`setConfig(...)`](#setconfig)
* [`startWatching(...)`](#startwatching)
* [`stopWatching()`](#stopwatching)
* [`triggerUpload()`](#triggerupload)
* [`addListener('positionChange', ...)`](#addlistenerpositionchange-)
* [`addListener('positionError', ...)`](#addlistenerpositionerror-)
* [`addListener('uploadFailed', ...)`](#addlisteneruploadfailed-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check the current permission status.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### clearQueue()

```typescript
clearQueue() => Promise<void>
```

Delete all positions from the queue and reset the dropped counter.

This method can be called with or without an active watch session, for
example to discard pending positions when the user signs out.

Only available on Android and iOS.

**Since:** 0.2.0

--------------------


### deleteQueuedPositions(...)

```typescript
deleteQueuedPositions(options: DeleteQueuedPositionsOptions) => Promise<void>
```

Delete all queued positions up to and including the given id.

Call this method after the positions returned by `getQueuedPositions()`
have been persisted by your app.

Only available on Android and iOS.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletequeuedpositionsoptions">DeleteQueuedPositionsOptions</a></code> |

**Since:** 0.2.0

--------------------


### getConfig()

```typescript
getConfig() => Promise<GetConfigResult>
```

Get the configuration that was last set via `setConfig(...)`.

Only the properties that were provided are returned, so the result can be
spread into `setConfig(...)` to change a single property:

```typescript
const config = await BackgroundGeolocation.getConfig();
await BackgroundGeolocation.setConfig({ ...config, maxSize: 5000 });
```

An empty object is returned if `setConfig(...)` has not been called yet.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#setconfigoptions">SetConfigOptions</a>&gt;</code>

**Since:** 0.2.0

--------------------


### getCurrentPosition(...)

```typescript
getCurrentPosition(options?: GetCurrentPositionOptions | undefined) => Promise<GetCurrentPositionResult>
```

Get the current position of the device.

On **Android**, the location permission is requested automatically if it
has not been granted yet.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getcurrentpositionoptions">GetCurrentPositionOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getcurrentpositionresult">GetCurrentPositionResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getQueuedPositions(...)

```typescript
getQueuedPositions(options?: GetQueuedPositionsOptions | undefined) => Promise<GetQueuedPositionsResult>
```

Get the positions that are currently stored in the queue, oldest first.

The positions are **not** deleted from the queue. Call
`deleteQueuedPositions(...)` after the positions have been persisted by
your app.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getqueuedpositionsoptions">GetQueuedPositionsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getqueuedpositionsresult">GetQueuedPositionsResult</a>&gt;</code>

**Since:** 0.2.0

--------------------


### getQueueStatus()

```typescript
getQueueStatus() => Promise<GetQueueStatusResult>
```

Get the current status of the queue.

This method can be called with or without an active watch session.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getqueuestatusresult">GetQueueStatusResult</a>&gt;</code>

**Since:** 0.2.0

--------------------


### isWatching()

```typescript
isWatching() => Promise<IsWatchingResult>
```

Check whether or not a watch session is currently active.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#iswatchingresult">IsWatchingResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

Open the native app settings page to allow the user to grant the app the
required permissions.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### requestPermissions(...)

```typescript
requestPermissions(options?: RequestPermissionsOptions | undefined) => Promise<PermissionStatus>
```

Request permissions.

The `backgroundLocation` permission must be requested in a **second**,
separate call after the `location` permission has been granted:

- On **Android 11+**, the user is taken to the location settings of the
  app where the `Allow all the time` option must be selected.
- On **iOS**, the operating system presents the upgrade prompt that asks
  the user to change the permission from `While Using the App` to
  `Always`.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#requestpermissionsoptions">RequestPermissionsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### requestTemporaryFullAccuracy(...)

```typescript
requestTemporaryFullAccuracy(options: RequestTemporaryFullAccuracyOptions) => Promise<void>
```

Request temporary access to the full accuracy location of the device.

Call this method if the user has granted the app reduced location
accuracy to ask for full accuracy for the duration of the app session.

The `NSLocationTemporaryUsageDescriptionDictionary` key must be defined
in the `Info.plist` file of your app with an entry for the given
`purposeKey`.

Only available on iOS.

| Param         | Type                                                                                                |
| ------------- | --------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#requesttemporaryfullaccuracyoptions">RequestTemporaryFullAccuracyOptions</a></code> |

**Since:** 0.0.1

--------------------


### resetConfig()

```typescript
resetConfig() => Promise<void>
```

Discard the configuration so that no positions are stored or uploaded.

Positions that are already queued are kept, use `clearQueue()` to discard them.

Only available on Android and iOS.

**Since:** 0.2.0

--------------------


### setConfig(...)

```typescript
setConfig(options: SetConfigOptions) => Promise<void>
```

Set the configuration of the position queue and of the upload.

The configuration is persisted natively so that it keeps working when the
operating system wakes your app without a web view.

Positions are stored in the queue if `maxSize` or `url` is provided, and
they are uploaded if `url` is provided. Use `resetConfig()` to stop
storing and uploading positions.

**This method replaces the whole configuration**, so every property you
omit falls back to its default. `setConfig({ maxSize: 5000 })` therefore
also stops the upload, because `url` is no longer set. Keep your
configuration in one place and pass it whole:

```typescript
await BackgroundGeolocation.setConfig({ maxSize: 5000, url });
```

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setconfigoptions">SetConfigOptions</a></code> |

**Since:** 0.2.0

--------------------


### startWatching(...)

```typescript
startWatching(options?: StartWatchingOptions | undefined) => Promise<void>
```

Start watching the position of the device.

Position updates are delivered via the `positionChange` event. Errors
that occur after the watch session has been started (e.g. the user
disables the location services) are delivered via the `positionError`
event.

Only one watch session can be active at a time. The promise rejects with
the `ALREADY_WATCHING` error code if a watch session is already active.

On **Android**, a foreground service with a persistent notification is
started so that the app keeps receiving position updates while it is in
the background. For this reason, the `androidNotification` option must
be provided. The location permission is requested automatically if it
has not been granted yet.

On **iOS**, position updates are delivered while the app is in the
background if the `location` background mode is enabled in the app.

If the queue is enabled via `setConfig(...)`, every position of the
watch session is additionally stored in the local queue.

Without the `backgroundLocation` permission, the watch session keeps
working but position updates may be suspended while the app is in the
background.

Only available on Android and iOS.

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#startwatchingoptions">StartWatchingOptions</a></code> |

**Since:** 0.0.1

--------------------


### stopWatching()

```typescript
stopWatching() => Promise<void>
```

Stop the active watch session.

On **Android**, this also stops the foreground service and removes the
associated notification.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### triggerUpload()

```typescript
triggerUpload() => Promise<void>
```

Immediately attempt to upload all queued positions.

Any pending retry backoff is cancelled and a new upload attempt is
started right away. The promise resolves as soon as the attempt has
been scheduled, not when the positions have been delivered.

The promise rejects if no `url` has been set via `setConfig(...)`.

Only available on Android and iOS.

**Since:** 0.2.0

--------------------


### addListener('positionChange', ...)

```typescript
addListener(eventName: 'positionChange', listenerFunc: (event: PositionChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when a new position is available during an active watch session.

This event is only delivered while the web view is alive. If the queue
is enabled, treat the queue as the single source of truth instead.

Only available on Android and iOS.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'positionChange'</code>                                                           |
| **`listenerFunc`** | <code>(event: <a href="#positionchangeevent">PositionChangeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('positionError', ...)

```typescript
addListener(eventName: 'positionError', listenerFunc: (event: PositionErrorEvent) => void) => Promise<PluginListenerHandle>
```

Called when an error occurs during an active watch session, for example
when the user disables the location services or revokes the location
permission.

Only available on Android and iOS.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'positionError'</code>                                                          |
| **`listenerFunc`** | <code>(event: <a href="#positionerrorevent">PositionErrorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('uploadFailed', ...)

```typescript
addListener(eventName: 'uploadFailed', listenerFunc: (event: UploadFailedEvent) => void) => Promise<PluginListenerHandle>
```

Called when an upload attempt of queued positions fails.

The affected positions remain in the queue and are retried
automatically unless the server rejected them permanently.

Only available on Android and iOS.

| Param              | Type                                                                                |
| ------------------ | ----------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'uploadFailed'</code>                                                         |
| **`listenerFunc`** | <code>(event: <a href="#uploadfailedevent">UploadFailedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.2.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.0.1

--------------------


### Interfaces


#### PermissionStatus

| Prop                     | Type                                                        | Description                                                                                                                                                                                                                   | Since |
| ------------------------ | ----------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`backgroundLocation`** | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for using the location services while the app is in the background. On **Android 9 and older**, this always mirrors the `location` permission state since no separate background location permission exists. | 0.0.1 |
| **`location`**           | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for using the location services while the app is in use.                                                                                                                                                     | 0.0.1 |
| **`notifications`**      | <code><a href="#permissionstate">PermissionState</a></code> | Permission state for posting notifications. On **Android 12 and older** and on **iOS**, this is always `granted` since no notification permission is required.                                                                | 0.0.1 |


#### DeleteQueuedPositionsOptions

| Prop         | Type                | Description                                                                                           | Since |
| ------------ | ------------------- | ----------------------------------------------------------------------------------------------------- | ----- |
| **`upToId`** | <code>number</code> | The id of the newest position to delete. All queued positions with this id or a lower id are deleted. | 0.2.0 |


#### SetConfigOptions

| Prop                | Type                                                         | Description                                                                                                                                                                                                                                                                                                                                                                                        | Default            | Since |
| ------------------- | ------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`batchSize`**     | <code>number</code>                                          | The maximum number of positions that are uploaded in a single request. Must be positive and must not be greater than `maxSize`. Set this option to `1` to upload each position immediately.                                                                                                                                                                                                        | <code>100</code>   | 0.2.0 |
| **`extras`**        | <code>{ [key: string]: string \| number \| boolean; }</code> | Static metadata that is attached to every upload request as the `extras` property of the request body.                                                                                                                                                                                                                                                                                             |                    | 0.2.0 |
| **`flushInterval`** | <code>number</code>                                          | The maximum time in milliseconds before queued positions are uploaded, even if the batch size has not been reached yet. Must be positive.                                                                                                                                                                                                                                                          | <code>60000</code> | 0.2.0 |
| **`headers`**       | <code>{ [key: string]: string; }</code>                      | Static HTTP headers that are sent with every upload request, for example for authorization.                                                                                                                                                                                                                                                                                                        |                    | 0.2.0 |
| **`maxSize`**       | <code>number</code>                                          | The maximum number of positions that are stored in the queue. When the queue is full, the oldest positions are dropped first. A stored position occupies about `210` bytes, so the default of `50000` needs about `10` MB. Must be positive. Set this option to `1` to keep only the most recent position, for example when you upload every position immediately and only the latest one matters. | <code>50000</code> | 0.2.0 |
| **`url`**           | <code>string</code>                                          | The URL the positions are uploaded to via HTTP `POST`. Providing this property enables the upload. Omit it to keep the positions on the device only.                                                                                                                                                                                                                                               |                    | 0.2.0 |


#### GetCurrentPositionResult

| Prop           | Type                                          | Description                         | Since |
| -------------- | --------------------------------------------- | ----------------------------------- | ----- |
| **`position`** | <code><a href="#position">Position</a></code> | The current position of the device. | 0.0.1 |


#### Position

| Prop                   | Type                         | Description                                                                                                                                                            | Since |
| ---------------------- | ---------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`accuracy`**         | <code>number</code>          | The estimated horizontal accuracy radius of the position in meters.                                                                                                    | 0.0.1 |
| **`altitude`**         | <code>number \| null</code>  | The altitude of the position in meters or `null` if the altitude is not available.                                                                                     | 0.0.1 |
| **`altitudeAccuracy`** | <code>number \| null</code>  | The estimated accuracy of the altitude in meters or `null` if the altitude accuracy is not available.                                                                  | 0.0.1 |
| **`bearing`**          | <code>number \| null</code>  | The direction in which the device is traveling in degrees relative to north or `null` if the bearing is not available.                                                 | 0.0.1 |
| **`latitude`**         | <code>number</code>          | The latitude of the position in degrees.                                                                                                                               | 0.0.1 |
| **`longitude`**        | <code>number</code>          | The longitude of the position in degrees.                                                                                                                              | 0.0.1 |
| **`simulated`**        | <code>boolean \| null</code> | Whether or not the position was delivered by a mock location provider. On **iOS**, this is always `null` since the operating system does not provide this information. | 0.0.1 |
| **`speed`**            | <code>number \| null</code>  | The speed of the device in meters per second or `null` if the speed is not available.                                                                                  | 0.0.1 |
| **`timestamp`**        | <code>number</code>          | The time at which the position was determined in milliseconds since the Unix epoch.                                                                                    | 0.0.1 |


#### GetCurrentPositionOptions

| Prop                              | Type                                          | Description                                                                                                                                                                                                                                                                   | Default                    | Since |
| --------------------------------- | --------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------- | ----- |
| **`accuracy`**                    | <code><a href="#accuracy">Accuracy</a></code> | The desired accuracy of the position.                                                                                                                                                                                                                                         | <code>Accuracy.High</code> | 0.0.1 |
| **`androidForceLocationManager`** | <code>boolean</code>                          | Whether or not to force the use of the platform location manager instead of the fused location provider, even if Google Play services is available. See the option of the same name of `startWatching(...)` for the cases in which this is useful. Only available on Android. | <code>false</code>         | 0.2.0 |
| **`maximumAge`**                  | <code>number</code>                           | The maximum age in milliseconds of a cached position that is accepted as the current position. If set to `0`, a fresh position is always fetched.                                                                                                                             | <code>0</code>             | 0.0.1 |
| **`timeout`**                     | <code>number</code>                           | The maximum time in milliseconds to wait for a position. The promise rejects with the `TIMEOUT` error code if no position is available within this time.                                                                                                                      | <code>10000</code>         | 0.0.1 |


#### GetQueuedPositionsResult

| Prop            | Type                          | Description                                                     | Since |
| --------------- | ----------------------------- | --------------------------------------------------------------- | ----- |
| **`hasMore`**   | <code>boolean</code>          | Whether or not more positions are available than were returned. | 0.2.0 |
| **`positions`** | <code>QueuedPosition[]</code> | The queued positions, oldest first.                             | 0.2.0 |


#### QueuedPosition

| Prop     | Type                | Description                                                                                         | Since |
| -------- | ------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`id`** | <code>number</code> | The id of the position in the queue. The id is unique per app installation and strictly increasing. | 0.2.0 |


#### GetQueuedPositionsOptions

| Prop          | Type                | Description                                                                                                                                                              | Default          | Since |
| ------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ---------------- | ----- |
| **`afterId`** | <code>number</code> | The id of the newest position that has already been read. Only positions with a higher id are returned. If not provided, the oldest positions in the queue are returned. |                  | 0.2.0 |
| **`limit`**   | <code>number</code> | The maximum number of positions to return.                                                                                                                               | <code>100</code> | 0.2.0 |


#### GetQueueStatusResult

| Prop                 | Type                        | Description                                                                                                                                                            | Since |
| -------------------- | --------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`droppedCount`**   | <code>number</code>         | The number of positions that were dropped without being read since the queue was last cleared, for example because the queue was full or the positions expired.        | 0.2.0 |
| **`lastUploadedAt`** | <code>number \| null</code> | The time at which the last batch of positions was uploaded successfully in milliseconds since the Unix epoch or `null` if no batch has been uploaded successfully yet. | 0.2.0 |
| **`pendingCount`**   | <code>number</code>         | The number of positions that are currently stored in the queue.                                                                                                        | 0.2.0 |


#### IsWatchingResult

| Prop           | Type                 | Description                                         | Since |
| -------------- | -------------------- | --------------------------------------------------- | ----- |
| **`watching`** | <code>boolean</code> | Whether or not a watch session is currently active. | 0.0.1 |


#### RequestPermissionsOptions

| Prop              | Type                          | Description                 | Default                                    | Since |
| ----------------- | ----------------------------- | --------------------------- | ------------------------------------------ | ----- |
| **`permissions`** | <code>PermissionType[]</code> | The permissions to request. | <code>['location', 'notifications']</code> | 0.0.1 |


#### RequestTemporaryFullAccuracyOptions

| Prop             | Type                | Description                                                                                                                                                                              | Since |
| ---------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`purposeKey`** | <code>string</code> | The key of the entry in the `NSLocationTemporaryUsageDescriptionDictionary` dictionary of the `Info.plist` file of your app that describes why the app needs the full accuracy location. | 0.0.1 |


#### StartWatchingOptions

| Prop                              | Type                                                                              | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | Default                         | Since |
| --------------------------------- | --------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------- | ----- |
| **`accuracy`**                    | <code><a href="#accuracy">Accuracy</a></code>                                     | The desired accuracy of the position updates.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | <code>Accuracy.High</code>      | 0.0.1 |
| **`androidForceLocationManager`** | <code>boolean</code>                                                              | Whether or not to force the use of the platform location manager instead of the fused location provider, even if Google Play services is available. Devices without Google Play services use the platform location manager anyway, so this option is not needed for them. Set it to `true` to receive the unmodified positions of a single provider instead of the fused ones, or if the fused location provider is unreliable on a device that reports Google Play services as available. This consumes more battery. Only available on Android. | <code>false</code>              | 0.0.1 |
| **`androidInterval`**             | <code>number</code>                                                               | The interval in milliseconds at which position updates are requested. A position is requested on every interval, whether or not the device has moved, so use `distanceFilter` to skip the positions of a device that stands still. iOS has no equivalent option: the operating system decides how often it reports a position, which is far less often while the device is stationary. Only available on Android.                                                                                                                                 | <code>5000</code>               | 0.0.1 |
| **`androidNotification`**         | <code><a href="#androidnotificationoptions">AndroidNotificationOptions</a></code> | The configuration of the notification that is displayed while the watch session is active. This option must be provided on Android. Only available on Android.                                                                                                                                                                                                                                                                                                                                                                                    |                                 | 0.0.1 |
| **`distanceFilter`**              | <code>number</code>                                                               | The minimum distance in meters that the device must move before a new position update is delivered. Set this option to `0` to receive every position, which also means that a device that stands still keeps reporting the same position.                                                                                                                                                                                                                                                                                                         | <code>10</code>                 | 0.0.1 |
| **`iosActivityType`**             | <code><a href="#activitytype">ActivityType</a></code>                             | The type of activity for which the position updates are used. This helps the operating system to decide when position updates may be paused automatically. Only available on iOS.                                                                                                                                                                                                                                                                                                                                                                 | <code>ActivityType.Other</code> | 0.0.1 |
| **`iosPausesAutomatically`**      | <code>boolean</code>                                                              | Whether or not the operating system is allowed to pause position updates automatically when the device is unlikely to move (e.g. when the user is stationary for a longer period of time). This can significantly improve battery life. Only available on iOS.                                                                                                                                                                                                                                                                                    | <code>false</code>              | 0.0.1 |
| **`iosShowBackgroundIndicator`**  | <code>boolean</code>                                                              | Whether or not the status bar indicator is displayed when the app uses the location services in the background. Only available on iOS.                                                                                                                                                                                                                                                                                                                                                                                                            | <code>true</code>               | 0.0.1 |


#### AndroidNotificationOptions

| Prop              | Type                | Description                                                                                                                                                                 | Default                               | Since |
| ----------------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------- | ----- |
| **`channelName`** | <code>string</code> | The name of the notification channel in which the notification is displayed.                                                                                                | <code>'Background Geolocation'</code> | 0.0.1 |
| **`color`**       | <code>string</code> | The color of the notification as a hex color code (e.g. `#42A5F5`).                                                                                                         |                                       | 0.0.1 |
| **`icon`**        | <code>string</code> | The name of the drawable resource that is displayed as the small icon of the notification (e.g. `ic_stat_location`). If not provided, the launcher icon of the app is used. |                                       | 0.0.1 |
| **`text`**        | <code>string</code> | The body text of the notification.                                                                                                                                          |                                       | 0.0.1 |
| **`title`**       | <code>string</code> | The title of the notification.                                                                                                                                              |                                       | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### PositionChangeEvent

| Prop           | Type                                          | Description                     | Since |
| -------------- | --------------------------------------------- | ------------------------------- | ----- |
| **`position`** | <code><a href="#position">Position</a></code> | The new position of the device. | 0.0.1 |


#### PositionErrorEvent

| Prop          | Type                                            | Description        | Since |
| ------------- | ----------------------------------------------- | ------------------ | ----- |
| **`code`**    | <code><a href="#errorcode">ErrorCode</a></code> | The error code.    | 0.0.1 |
| **`message`** | <code>string</code>                             | The error message. | 0.0.1 |


#### UploadFailedEvent

| Prop             | Type                | Description                                                       | Since |
| ---------------- | ------------------- | ----------------------------------------------------------------- | ----- |
| **`message`**    | <code>string</code> | The error message.                                                | 0.0.1 |
| **`statusCode`** | <code>number</code> | The HTTP status code of the response, if a response was received. | 0.0.1 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### GetConfigResult

<code><a href="#setconfigoptions">SetConfigOptions</a></code>


#### PermissionType

The permissions that can be requested.

<code>'backgroundLocation' | 'location' | 'notifications'</code>


### Enums


#### Accuracy

| Members        | Value                   | Description                                                                                                                         | Since |
| -------------- | ----------------------- | ----------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Balanced`** | <code>'BALANCED'</code> | A balance between accuracy and power consumption. The position is accurate to within about a hundred meters.                        | 0.0.1 |
| **`High`**     | <code>'HIGH'</code>     | The most accurate position that is available. This uses the most power and should only be used when a precise position is required. | 0.0.1 |
| **`Low`**      | <code>'LOW'</code>      | A low accuracy position with minimal power consumption. The position is accurate to within about a kilometer.                       | 0.0.1 |


#### ActivityType

| Members                    | Value                                | Description                                                                                               | Since |
| -------------------------- | ------------------------------------ | --------------------------------------------------------------------------------------------------------- | ----- |
| **`Airborne`**             | <code>'AIRBORNE'</code>              | <a href="#position">Position</a> updates for activities in the air (e.g. flying).                         | 0.0.1 |
| **`AutomotiveNavigation`** | <code>'AUTOMOTIVE_NAVIGATION'</code> | <a href="#position">Position</a> updates for automotive navigation.                                       | 0.0.1 |
| **`Fitness`**              | <code>'FITNESS'</code>               | <a href="#position">Position</a> updates for fitness activities (e.g. walking, running or cycling).       | 0.0.1 |
| **`Other`**                | <code>'OTHER'</code>                 | <a href="#position">Position</a> updates for activities that are not covered by the other activity types. | 0.0.1 |
| **`OtherNavigation`**      | <code>'OTHER_NAVIGATION'</code>      | <a href="#position">Position</a> updates for vehicular navigation that is not automotive (e.g. boating).  | 0.0.1 |


#### ErrorCode

| Members                        | Value                                     | Description                                         | Since |
| ------------------------------ | ----------------------------------------- | --------------------------------------------------- | ----- |
| **`AlreadyWatching`**          | <code>'ALREADY_WATCHING'</code>           | A watch session is already active.                  | 0.0.1 |
| **`LocationServicesDisabled`** | <code>'LOCATION_SERVICES_DISABLED'</code> | The location services are disabled on the device.   | 0.0.1 |
| **`PermissionDenied`**         | <code>'PERMISSION_DENIED'</code>          | The location permission has not been granted.       | 0.0.1 |
| **`PositionUnavailable`**      | <code>'POSITION_UNAVAILABLE'</code>       | The position is currently not available.            | 0.0.1 |
| **`Timeout`**                  | <code>'TIMEOUT'</code>                    | No position was available within the given timeout. | 0.0.1 |

</docgen-api>

## Queue

The plugin can store every position of a watch session in a local SQLite queue. The queue is the durable record of a watch session: it is written natively, survives app restarts and does not depend on a running web view. The [`positionChange`](#addlistenerpositionchange-) event is only an ephemeral live feed on top of it.

### Enabling the queue

The queue is **opt-in** because precise location history at rest is sensitive data. Enable it with `setConfig(...)`:

```typescript
import { BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const enableQueue = async () => {
  await BackgroundGeolocation.setConfig({
    maxSize: 50000,
  });
};
```

The configuration is persisted natively so that the plugin keeps working when the operating system runs your app without a web view. Call `setConfig(...)` on every app start anyway: the stored copy is only a cache of your last call, and it is discarded whenever the plugin's configuration format changes in a future version. Queued positions are never discarded with it.

**Attention**: The configuration you pass replaces the stored one entirely. Every property you omit falls back to its default, so read the stored configuration with `getConfig()` first if you only want to change a single property:

```typescript
const config = await BackgroundGeolocation.getConfig();
await BackgroundGeolocation.setConfig({ ...config, maxSize: 10000 });
```

Call `resetConfig()` to discard the configuration again. Positions that are already queued are kept, use `clearQueue()` to discard them:

```typescript
await BackgroundGeolocation.resetConfig();
```


### Queue Options

| Option    | Default | Description                                                                                                |
| --------- | ------- | ---------------------------------------------------------------------------------------------------------- |
| `maxSize` | `50000` | The maximum number of queued positions. When the queue is full, the **oldest** positions are dropped first. |

The default `maxSize` of `50000` records is about 10 MB on disk and about 100 hours of walking at the default `distanceFilter` of `10` meters, at which a device that stands still records nothing. It is a disaster ceiling, not a working set: an app that drains the queue whenever it is in the foreground sits close to zero.

### Draining the queue

Reading and deleting are two separate operations, because the watch session keeps recording between them. Read a page of positions, persist them in your app, then delete everything up to the id of the last position you persisted:

```typescript
import { BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const drainQueue = async () => {
  let hasMore = true;
  while (hasMore) {
    const result = await BackgroundGeolocation.getQueuedPositions({
      limit: 1000,
    });
    if (!result.positions.length) {
      break;
    }
    await persist(result.positions);
    await BackgroundGeolocation.deleteQueuedPositions({
      upToId: result.positions[result.positions.length - 1].id,
    });
    hasMore = result.hasMore;
  }
};
```

Every queued position is a [`Position`](#position) object with an additional `id` property. Ids are unique per app installation and strictly increasing, so `deleteQueuedPositions(...)` can never delete a position that is newer than the one you read. The `limit` option defaults to `100`, so a completely full queue is about 50 calls at `limit: 1000`.

Use `getQueueStatus()` to inspect the queue and `clearQueue()` to discard everything, for example when the user signs out:

```typescript
import { BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const getQueueStatus = async () => {
  const { pendingCount, droppedCount, lastUploadedAt } = await BackgroundGeolocation.getQueueStatus();
  console.log(`${pendingCount} positions pending, ${droppedCount} dropped, last upload: ${lastUploadedAt}`);
};

const clearQueue = async () => {
  await BackgroundGeolocation.clearQueue();
};
```

### Queue Behavior

- **Persistence**: The queue is stored in a local SQLite database and survives app restarts and force-quits. Positions are only deleted when your app deletes them, the server acknowledged them or they were evicted.
- **Capacity**: The queue holds at most `maxSize` positions. If the queue is full, the **oldest** positions are dropped first.
- **Observability**: The `droppedCount` property of `getQueueStatus()` counts the positions that were dropped since the queue was last cleared. It is persisted and only reset by `clearQueue()`.
- **Ordering**: Positions are returned oldest first and their ids are strictly increasing.
- **Encryption at rest**: The queue database is not encrypted. It is stored in the sandboxed app storage of the operating system.

### Caveats

- **Positions arrive twice**: While the web view is alive, every position is delivered via the `positionChange` event **and** stored in the queue. With the queue enabled, treat the queue as the single source of truth and use the event for live UI only, otherwise you process every position twice.
- **The queue only fills while the watch session is alive**: If the user force-quits the app, the watch session ends and no further positions are recorded until it is started again. The already queued positions are not lost, but the gap in between cannot be filled.

## HTTP Upload

The plugin can upload every queued position to your own server without involving JavaScript. Positions are uploaded in batches and only deleted from the [queue](#queue) after the server has acknowledged them. Since the whole pipeline runs natively, it keeps working while the web view is suspended.

**Attention**: The upload drains the same queue that `getQueuedPositions(...)` reads. Positions are stored once and deleted once, so a position your app deletes with `deleteQueuedPositions(...)` is never uploaded, and a position the server acknowledged is no longer readable. Use either the upload or your own drain loop, not both.

### Options

Provide the `url` option to enable the upload pipeline. It is persisted, so it stays active across app restarts until it is changed or discarded. Failed upload attempts are reported via the `uploadFailed` event:

```typescript
import { BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const enableUpload = async () => {
  await BackgroundGeolocation.addListener('uploadFailed', event => {
    console.error('Upload failed: ', event.statusCode, event.message);
  });
  await BackgroundGeolocation.setConfig({
    url: 'https://api.example.com/positions',
    batchSize: 100,
    flushInterval: 60000,
    headers: {
      Authorization: 'Bearer eyJhbGciOi...',
    },
    extras: {
      userId: 'abc',
    },
  });
};
```

Providing `url` also enables the queue, because the upload drains it. A rotated authorization header can be applied without stopping the watch session by calling `setConfig(...)` again with the updated configuration.

Call `resetConfig()` to discard the configuration again. Queued positions are kept and are uploaded as soon as the upload is enabled again:

```typescript
await BackgroundGeolocation.resetConfig();
```

To stop the upload but keep storing positions, call `setConfig(...)` again without `url`:

```typescript
const { url, ...config } = await BackgroundGeolocation.getConfig();
await BackgroundGeolocation.setConfig(config);
```

An upload attempt can be triggered manually at any time:

```typescript
import { BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const triggerUpload = async () => {
  await BackgroundGeolocation.triggerUpload();
};
```

### Server Contract

Positions are uploaded with an HTTP `POST` request and the `Content-Type: application/json; charset=utf-8` header. Your own `headers` are applied afterwards and may override it. The request body looks as follows:

```json
{
  "positions": [
    {
      "id": 4711,
      "latitude": 52.52,
      "longitude": 13.405,
      "accuracy": 5,
      "altitude": null,
      "altitudeAccuracy": null,
      "bearing": null,
      "speed": null,
      "simulated": false,
      "timestamp": 1723291200000
    }
  ],
  "extras": { "userId": "abc" }
}
```

Every entry of the `positions` array is a [`Position`](#position) object with an additional `id` property. The `extras` property is omitted entirely if the `extras` option was not provided.

**Idempotency**: The `id` is unique per app installation and strictly increasing. Positions are delivered **at least once**, so the same position may be uploaded more than once, for example if the acknowledgment of the server is lost on the way back. Deduplicate the positions on the server by `id` per device to make the upload idempotent.

### Response Handling

The response body is always ignored. Only the status code decides what happens to the uploaded batch:

| Status Code                                 | Behavior                                                                                                                                         |
| ------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------ |
| `2xx`                                       | The batch is acknowledged and deleted from the queue. The next batch is uploaded immediately if more positions are pending.                     |
| `401`, `408`, `429`, `5xx`, network error, timeout | The batch stays at the head of the queue and is retried with an exponential backoff. A `uploadFailed` event is emitted.                    |
| Any other status code                       | The batch is **dropped permanently without being uploaded again** and a `uploadFailed` event is emitted. The upload continues with the next batch. |

**Attention**: A batch that the server rejects with any status code other than `2xx`, `401`, `408`, `429` or `5xx` (for example `400 Bad Request` or `422 Unprocessable Entity`) is deleted from the queue and **lost**. This is intentional: a permanently rejected batch must never block the head of the queue forever. `401` is treated as retryable, because it usually means an expired token rather than an unacceptable batch — refresh the credentials with `setConfig(...)` and the queued positions are uploaded afterwards. Make sure your endpoint answers with a retryable status code (e.g. `503`) if it is temporarily unable to accept positions, and listen to the `uploadFailed` event to detect such drops. Dropped positions are also counted in the `droppedCount` property of `getQueueStatus()`.

The request timeout is 30 seconds. Retries start with a delay of 5 seconds and double after every attempt up to a maximum of 10 minutes. The backoff is reset after every successful upload, whenever `setConfig(...)` is called with a `url` and whenever `triggerUpload()` is called.

### Upload Behavior

- **Delivery window**: Positions are uploaded whenever the app process is alive and a `url` is configured, with or without an active watch session. Enabling the upload first deletes expired positions and then immediately uploads everything that is left over from previous sessions.
- **Acknowledgment**: Positions are only deleted from the queue after the server acknowledged them or rejected them permanently.
- **Shared queue**: The uploader and `getQueuedPositions(...)` drain the same queue. If your app reads and deletes positions itself, the uploader only sees what is left.
- **Without a `url`**: The queue is left untouched. It is neither uploaded nor cleared.
- **Observability**: Permanently rejected batches are counted in the `droppedCount` property of `getQueueStatus()` and `lastUploadedAt` reports the time of the last successful upload. Both values are persisted.

### Instant Upload

There is no dedicated mode for uploading each position on its own. Set the `batchSize` option to `1` instead to upload every position as soon as it arrives:

```typescript
await BackgroundGeolocation.setConfig({
  url: 'https://api.example.com/positions',
  batchSize: 1,
});
```

### Battery Consumption

Every upload wakes up the cellular radio, which is one of the most expensive operations on a mobile device. Uploading one position at a time (see [Instant Upload](#instant-upload)) therefore has a noticeable impact on the battery life. Prefer a larger `batchSize` and a longer `flushInterval` whenever your use case allows it, so that many positions share a single radio wake-up.

### Testing

The free [Background Geolocation Playground](https://background-geolocation-playground.capawesome.io) webpage is a ready-to-use upload target, so you can verify the upload pipeline before your own endpoint exists. Generate a session key on the webpage, use it as bearer token and every uploaded position appears in a table and on a map:

```typescript
await BackgroundGeolocation.setConfig({
  url: 'https://background-geolocation-playground.capawesome.io/v1/positions',
  headers: {
    Authorization: 'Bearer <YOUR_SESSION_KEY>',
  },
});
```

![Background Geolocation Playground webpage showing the uploaded positions of a session in a table and on a map](https://raw.githubusercontent.com/capawesome-team/capacitor-plugins/main/packages/background-geolocation/assets/background-geolocation-playground.png)

**Attention**: Session keys and recorded positions are deleted after 14 days and everyone who knows the session key can view its positions, so use the webpage for debugging and testing only and never in production.

### Deliberately Not Supported

The following features are intentionally not part of the upload pipeline:

- **Network constraints**: Uploads cannot be restricted to Wi-Fi or unmetered networks.
- **Response processing**: The response body of the server is always ignored, so the server cannot send commands back to the device.
- **Upload after a force-quit**: Queued positions are not uploaded while the app is terminated. They are uploaded the next time the app is started.

## FAQ

### How is this plugin different from other similar plugins?

It is built for reliable background location tracking with a correctly plumbed Android 14+ foreground service, a first-class permissions API that handles the two-step background upgrade flow, and real tuning knobs for accuracy, distance filtering and update interval. It also reports mock locations on Android, supports temporary full-accuracy requests on iOS, and falls back to the platform location manager on devices without Google Play services — all through one fully typed, actively maintained API with dedicated support. If you only need a single foreground position, a simpler geolocation setup may suffice; if you need dependable tracking that keeps running in the background, this plugin is made for it.

### Does the tracking continue when the user force-quits the app?

No. On both platforms, the watch session ends when the user force-quits (terminates) the app. This is an operating system restriction. Only operating-system-managed APIs like geofencing can relaunch a terminated app. Take a look at the [Geofences](https://capawesome.io/docs/sdks/capacitor/geofences/) plugin if you need this capability.

### Why is the `androidNotification` option required?

On Android, receiving position updates while the app is in the background requires a foreground service, and every foreground service must display a persistent notification. The plugin builds this notification from the `androidNotification` option so that you have full control over its content.

### Does the plugin work on devices without Google Play services?

Yes. The plugin automatically falls back to the platform location manager if Google Play services is not available on the device (e.g. on certain Huawei devices). You can also force this behavior with the `androidForceLocationManager` option.

### Why does the plugin not declare the `ACCESS_BACKGROUND_LOCATION` permission?

Google Play requires a policy declaration and review for every app that requests background location access. If the plugin declared this permission, every app using the plugin would be subject to this review, even if it only needs foreground location access. Therefore, the permission must be declared at the app level (see [Installation](#installation)).

### Can I start multiple watch sessions at the same time?

No. Only one watch session can be active at a time. The native location engine delivers one stream of position updates, so multiple concurrent watchers would only be an illusion with a shared configuration. Call `stopWatching()` before starting a new watch session with different options.

### How can I reduce the battery consumption?

The plugin deliberately does not include a motion-detection state machine that turns the GPS on and off based on accelerometer data. Instead, it exposes real tuning knobs: use a lower `accuracy` (e.g. `Accuracy.Balanced` instead of `Accuracy.High`), increase the `distanceFilter` to reduce the number of position updates and increase the `androidInterval` option on Android.

On iOS, you can additionally enable the `iosPausesAutomatically` option so that the operating system pauses the position updates when the device is unlikely to move. Be aware of the trade-off: the operating system decides on its own when to pause and only resumes the position updates once the device has moved significantly again, so a watch session can stay silent for a long time. The plugin logs both the pause and the resume, but keeps the watch session active, which means that `isWatching()` still returns `true` while the position updates are paused.

### What happens if the user grants only approximate location?

On Android, the user can grant approximate instead of precise location. This is not reported separately but is reflected in the `accuracy` property of each position. On iOS, the user can grant reduced accuracy, which you can upgrade for the duration of the app session using `requestTemporaryFullAccuracy(...)`.

### Why are queued positions not uploaded after force-quit?

Uploading requires a running app process. When the user force-quits (terminates) the app, no more requests can be sent. The queued positions are **not** lost though: they stay in the local queue and are uploaded as soon as the app is started again. See [Queue Behavior](#queue-behavior) for details.

### How do I secure the upload endpoint?

Use the `headers` option to send a static credential (e.g. `Authorization: Bearer ...`) with every upload request and always use an `https://` URL, since the credential is otherwise sent in plain text. On iOS, App Transport Security blocks plain `http://` requests by default anyway. Call `setConfig(...)` again with the full configuration whenever the token is rotated, which applies the new header without interrupting the watch session.

### How can I test the upload endpoint locally?

Point the `url` option to a local HTTP endpoint that logs the request body. A few lines of Node.js are enough:

```javascript
// server.js
import { createServer } from 'node:http';

createServer((request, response) => {
  let body = '';
  request.on('data', chunk => (body += chunk));
  request.on('end', () => {
    console.log(JSON.parse(body));
    response.writeHead(200).end();
  });
}).listen(3000);
```

Start it with `node server.js` and use the IP address of your development machine in the `url` option (e.g. `http://192.168.1.10:3000`). On the Android emulator, use `http://10.0.2.2:3000` instead. Plain `http://` requires an App Transport Security exception on iOS and a network security configuration that allows cleartext traffic on Android, so it is often easier to expose the local server via an HTTPS tunnel. Answer with a `503` status code to observe the retry behavior and with a `400` status code to observe a permanently dropped batch.

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Geocoder](https://capawesome.io/docs/sdks/capacitor/geocoder/): Convert the tracked positions into human-readable addresses.
- [Geofences](https://capawesome.io/docs/sdks/capacitor/geofences/): Monitor circular regions with operating-system-managed geofencing, including delivery when the app is terminated.
- [Settings Launcher](https://capawesome.io/docs/sdks/capacitor/settings-launcher/): Take the user to the app settings, for example to grant the background location permission.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/background-geolocation/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/background-geolocation/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/background-geolocation/LICENSE).
