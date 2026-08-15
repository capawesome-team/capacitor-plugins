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
- ☁️ **HTTP Sync**: Upload positions to your own server in batches with an on-device SQLite queue, automatic retries and at-least-once delivery.
- 🧪 **Sync Testing**: Test the sync engine without your own server using the free [Background Geolocation Playground](https://background-geolocation-playground.capawesome.io) webpage.
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

The `INTERNET` permission that the [HTTP Sync](#http-sync) feature requires is already declared by every Capacitor app template, so no additional configuration is needed for it.

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

The following examples show how to get the current position, watch the position of the device, request temporary full accuracy, and check and request permissions.

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

### Sync positions to a server

The plugin can upload every position of a watch session to your own server without involving JavaScript, buffered in a local queue so that no position is lost while the device is offline. Provide the `sync` option when you start the watch session to enable it. Only available on Android and iOS:

```typescript
import { BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const startWatchingWithSync = async () => {
  await BackgroundGeolocation.startWatching({
    androidNotification: {
      title: 'Location Tracking',
      text: 'Your location is being tracked.',
    },
    sync: {
      url: 'https://api.example.com/positions',
      headers: {
        Authorization: 'Bearer eyJhbGciOi...',
      },
    },
  });
};
```

See [HTTP Sync](#http-sync) for all options, the server contract, response handling and queue behavior.

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
* [`clearSyncQueue()`](#clearsyncqueue)
* [`getCurrentPosition(...)`](#getcurrentposition)
* [`getSyncStatus()`](#getsyncstatus)
* [`isWatching()`](#iswatching)
* [`openSettings()`](#opensettings)
* [`requestPermissions(...)`](#requestpermissions)
* [`requestTemporaryFullAccuracy(...)`](#requesttemporaryfullaccuracy)
* [`startWatching(...)`](#startwatching)
* [`stopWatching()`](#stopwatching)
* [`triggerSync()`](#triggersync)
* [`addListener('positionChange', ...)`](#addlistenerpositionchange-)
* [`addListener('positionError', ...)`](#addlistenerpositionerror-)
* [`addListener('syncFailed', ...)`](#addlistenersyncfailed-)
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


### clearSyncQueue()

```typescript
clearSyncQueue() => Promise<void>
```

Delete all buffered positions from the sync queue.

This method can be called with or without an active watch session, for
example to discard pending positions when the user signs out.

Only available on Android and iOS.

**Since:** 0.0.1

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


### getSyncStatus()

```typescript
getSyncStatus() => Promise<GetSyncStatusResult>
```

Get the current status of the sync queue.

This method can be called with or without an active watch session.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getsyncstatusresult">GetSyncStatusResult</a>&gt;</code>

**Since:** 0.0.1

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

If the `sync` option is provided, every position of the watch session is
additionally buffered in a local queue and uploaded in batches to the
configured server while the watch session is active.

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


### triggerSync()

```typescript
triggerSync() => Promise<void>
```

Immediately attempt to upload all buffered positions.

Any pending retry backoff is cancelled and a new upload attempt is
started right away. The promise resolves as soon as the attempt has
been scheduled, not when the positions have been delivered.

The promise rejects if no watch session with a `sync` configuration is
active.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### addListener('positionChange', ...)

```typescript
addListener(eventName: 'positionChange', listenerFunc: (event: PositionChangeEvent) => void) => Promise<PluginListenerHandle>
```

Called when a new position is available during an active watch session.

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


### addListener('syncFailed', ...)

```typescript
addListener(eventName: 'syncFailed', listenerFunc: (event: SyncFailedEvent) => void) => Promise<PluginListenerHandle>
```

Called when an upload attempt of buffered positions fails.

The affected positions remain in the queue and are retried
automatically unless the server rejected them permanently.

Only available on Android and iOS.

| Param              | Type                                                                            |
| ------------------ | ------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'syncFailed'</code>                                                       |
| **`listenerFunc`** | <code>(event: <a href="#syncfailedevent">SyncFailedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

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

| Prop             | Type                                          | Description                                                                                                                                              | Default                    | Since |
| ---------------- | --------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------- | ----- |
| **`accuracy`**   | <code><a href="#accuracy">Accuracy</a></code> | The desired accuracy of the position.                                                                                                                    | <code>Accuracy.High</code> | 0.0.1 |
| **`maximumAge`** | <code>number</code>                           | The maximum age in milliseconds of a cached position that is accepted as the current position. If set to `0`, a fresh position is always fetched.        | <code>0</code>             | 0.0.1 |
| **`timeout`**    | <code>number</code>                           | The maximum time in milliseconds to wait for a position. The promise rejects with the `TIMEOUT` error code if no position is available within this time. | <code>10000</code>         | 0.0.1 |


#### GetSyncStatusResult

| Prop               | Type                        | Description                                                                                                                                                                                                                                                                               | Since |
| ------------------ | --------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`droppedCount`** | <code>number</code>         | The number of positions that were dropped without being uploaded since the queue was last empty or cleared, for example because the queue was full, the positions expired or the server rejected them permanently. This counter is kept in memory and is reset when the app is restarted. | 0.0.1 |
| **`lastSyncedAt`** | <code>number \| null</code> | The time at which the last batch of positions was uploaded successfully in milliseconds since the Unix epoch or `null` if no batch has been uploaded successfully yet. This value is kept in memory and is reset when the app is restarted.                                               | 0.0.1 |
| **`pendingCount`** | <code>number</code>         | The number of positions that are currently buffered in the sync queue.                                                                                                                                                                                                                    | 0.0.1 |


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

| Prop                              | Type                                                                              | Description                                                                                                                                                                                                                                                                     | Default                         | Since |
| --------------------------------- | --------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------- | ----- |
| **`accuracy`**                    | <code><a href="#accuracy">Accuracy</a></code>                                     | The desired accuracy of the position updates.                                                                                                                                                                                                                                   | <code>Accuracy.High</code>      | 0.0.1 |
| **`androidForceLocationManager`** | <code>boolean</code>                                                              | Whether or not to force the use of the platform location manager instead of the fused location provider, even if Google Play services is available. Set this option to `true` on devices without Google Play services (e.g. certain Huawei devices). Only available on Android. | <code>false</code>              | 0.0.1 |
| **`androidInterval`**             | <code>number</code>                                                               | The interval in milliseconds at which position updates are requested. Only available on Android.                                                                                                                                                                                | <code>1000</code>               | 0.0.1 |
| **`androidNotification`**         | <code><a href="#androidnotificationoptions">AndroidNotificationOptions</a></code> | The configuration of the notification that is displayed while the watch session is active. This option must be provided on Android. Only available on Android.                                                                                                                  |                                 | 0.0.1 |
| **`distanceFilter`**              | <code>number</code>                                                               | The minimum distance in meters that the device must move before a new position update is delivered.                                                                                                                                                                             | <code>0</code>                  | 0.0.1 |
| **`iosActivityType`**             | <code><a href="#activitytype">ActivityType</a></code>                             | The type of activity for which the position updates are used. This helps the operating system to decide when position updates may be paused automatically. Only available on iOS.                                                                                               | <code>ActivityType.Other</code> | 0.0.1 |
| **`iosPausesAutomatically`**      | <code>boolean</code>                                                              | Whether or not the operating system is allowed to pause position updates automatically when the device is unlikely to move (e.g. when the user is stationary for a longer period of time). This can significantly improve battery life. Only available on iOS.                  | <code>false</code>              | 0.0.1 |
| **`iosShowBackgroundIndicator`**  | <code>boolean</code>                                                              | Whether or not the status bar indicator is displayed when the app uses the location services in the background. Only available on iOS.                                                                                                                                          | <code>true</code>               | 0.0.1 |
| **`sync`**                        | <code><a href="#syncoptions">SyncOptions</a></code>                               | The configuration for uploading positions to a server. If provided, every position is buffered in a local queue and uploaded in batches to the configured URL while the watch session is active.                                                                                |                                 | 0.0.1 |


#### AndroidNotificationOptions

| Prop              | Type                | Description                                                                                                                                                                 | Default                               | Since |
| ----------------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------- | ----- |
| **`channelName`** | <code>string</code> | The name of the notification channel in which the notification is displayed.                                                                                                | <code>'Background Geolocation'</code> | 0.0.1 |
| **`color`**       | <code>string</code> | The color of the notification as a hex color code (e.g. `#42A5F5`).                                                                                                         |                                       | 0.0.1 |
| **`icon`**        | <code>string</code> | The name of the drawable resource that is displayed as the small icon of the notification (e.g. `ic_stat_location`). If not provided, the launcher icon of the app is used. |                                       | 0.0.1 |
| **`text`**        | <code>string</code> | The body text of the notification.                                                                                                                                          |                                       | 0.0.1 |
| **`title`**       | <code>string</code> | The title of the notification.                                                                                                                                              |                                       | 0.0.1 |


#### SyncOptions

| Prop                | Type                                                         | Description                                                                                                                                                                                                                              | Default            | Since |
| ------------------- | ------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`batchSize`**     | <code>number</code>                                          | The maximum number of positions that are uploaded in a single request. Set this option to `1` to upload each position immediately.                                                                                                       | <code>100</code>   | 0.0.1 |
| **`extras`**        | <code>{ [key: string]: string \| number \| boolean; }</code> | Static metadata that is attached to every upload request as the `extras` property of the request body.                                                                                                                                   |                    | 0.0.1 |
| **`flushInterval`** | <code>number</code>                                          | The maximum time in milliseconds before buffered positions are uploaded, even if the batch size has not been reached yet.                                                                                                                | <code>60000</code> | 0.0.1 |
| **`headers`**       | <code>{ [key: string]: string; }</code>                      | Static HTTP headers that are sent with every upload request, for example for authorization.                                                                                                                                              |                    | 0.0.1 |
| **`maxAge`**        | <code>number</code>                                          | The maximum age in milliseconds of a buffered position. Older positions are deleted from the queue without being uploaded. Must be positive. If not provided, positions are kept until they are uploaded or evicted from the full queue. |                    | 0.0.1 |
| **`maxQueueSize`**  | <code>number</code>                                          | The maximum number of buffered positions. When the queue is full, the oldest positions are dropped first.                                                                                                                                | <code>10000</code> | 0.0.1 |
| **`url`**           | <code>string</code>                                          | The URL the positions are uploaded to via HTTP `POST`.                                                                                                                                                                                   |                    | 0.0.1 |


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


#### SyncFailedEvent

| Prop             | Type                | Description                                                       | Since |
| ---------------- | ------------------- | ----------------------------------------------------------------- | ----- |
| **`message`**    | <code>string</code> | The error message.                                                | 0.0.1 |
| **`statusCode`** | <code>number</code> | The HTTP status code of the response, if a response was received. | 0.0.1 |


### Type Aliases


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


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

## HTTP Sync

The plugin can upload every position of a watch session to your own server without involving JavaScript. Positions are buffered in a local SQLite queue, uploaded in batches and only deleted from the queue after the server has acknowledged them. Since the whole pipeline runs natively, it keeps working while the web view is suspended.

### Options

Provide the `sync` option when you start the watch session to enable the upload pipeline for that session. Failed upload attempts are reported via the `syncFailed` event:

```typescript
import { Accuracy, BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const startWatchingWithSync = async () => {
  await BackgroundGeolocation.addListener('syncFailed', event => {
    console.error('Upload failed: ', event.statusCode, event.message);
  });
  await BackgroundGeolocation.startWatching({
    accuracy: Accuracy.High,
    distanceFilter: 10,
    androidNotification: {
      title: 'Location Tracking',
      text: 'Your location is being tracked.',
    },
    sync: {
      url: 'https://api.example.com/positions',
      batchSize: 100,
      flushInterval: 60000,
      maxAge: 86400000,
      maxQueueSize: 10000,
      headers: {
        Authorization: 'Bearer eyJhbGciOi...',
      },
      extras: {
        userId: 'abc',
      },
    },
  });
};
```

The queue itself can be inspected and controlled with or without an active watch session:

```typescript
import { BackgroundGeolocation } from '@capawesome-team/capacitor-background-geolocation';

const getSyncStatus = async () => {
  const { pendingCount, droppedCount, lastSyncedAt } = await BackgroundGeolocation.getSyncStatus();
  console.log(`${pendingCount} positions pending, ${droppedCount} dropped, last upload: ${lastSyncedAt}`);
};

const triggerSync = async () => {
  await BackgroundGeolocation.triggerSync();
};

const clearSyncQueue = async () => {
  await BackgroundGeolocation.clearSyncQueue();
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
| `2xx`                                       | The batch is acknowledged and deleted from the queue. The next batch is uploaded immediately if more positions are pending.                      |
| `408`, `429`, `5xx`, network error, timeout | The batch stays at the head of the queue and is retried with an exponential backoff. A `syncFailed` event is emitted.                            |
| Any other status code                       | The batch is **dropped permanently without being uploaded again** and a `syncFailed` event is emitted. The upload continues with the next batch. |

**Attention**: A batch that the server rejects with any status code other than `2xx`, `408`, `429` or `5xx` (for example `400 Bad Request` or `422 Unprocessable Entity`) is deleted from the queue and **lost**. This is intentional: a permanently rejected batch must never block the head of the queue forever. Make sure your endpoint answers with a retryable status code (e.g. `503`) if it is temporarily unable to accept positions, and listen to the `syncFailed` event to detect such drops. Dropped positions are also counted in the `droppedCount` property of `getSyncStatus()`.

The request timeout is 30 seconds. Retries start with a delay of 5 seconds and double after every attempt up to a maximum of 10 minutes. The backoff is reset after every successful upload, whenever a new watch session is started and whenever `triggerSync()` is called.

### Queue Behavior

- **Persistence**: The queue is stored in a local SQLite database and survives app restarts and force-quits. Positions are only deleted after the server has acknowledged them, they expired or they were evicted.
- **Capacity**: The queue holds at most `maxQueueSize` positions (default: `10000`). If the queue is full, the **oldest** positions are dropped first.
- **Expiry**: If the `maxAge` option is provided, positions older than this age are deleted from the queue without being uploaded. Without the option, positions are kept until they are uploaded or evicted.
- **Observability**: The `droppedCount` property of `getSyncStatus()` counts the positions that were dropped since the queue was last empty or cleared. The counter is kept in memory and is reset when the queue becomes empty, when `clearSyncQueue()` is called or when the app is restarted.
- **Delivery window**: Positions are only uploaded while a watch session **with** a `sync` configuration is active. Starting such a session first deletes expired positions and then immediately uploads everything that is left over from previous sessions.
- **Without a sync configuration**: If you start a watch session without the `sync` option, the queue is left untouched. It is neither uploaded nor cleared.
- **After `stopWatching()`**: The flush timer and any pending retry are cancelled and no further requests are started. A request that is already in flight is allowed to finish, so its positions may still be acknowledged and deleted. Everything else stays in the queue until the next watch session with a `sync` configuration.

### Instant Upload

There is no dedicated mode for uploading each position on its own. Set the `batchSize` option to `1` instead to upload every position as soon as it arrives:

```typescript
sync: {
  url: 'https://api.example.com/positions',
  batchSize: 1,
},
```

### Battery Consumption

Every upload wakes up the cellular radio, which is one of the most expensive operations on a mobile device. Uploading one position at a time (see [Instant Upload](#instant-upload)) therefore has a noticeable impact on the battery life. Prefer a larger `batchSize` and a longer `flushInterval` whenever your use case allows it, so that many positions share a single radio wake-up.

### Testing

The free [Background Geolocation Playground](https://background-geolocation-playground.capawesome.io) webpage is a ready-to-use sync target, so you can verify the upload pipeline before your own endpoint exists. Generate a session key on the webpage, use it as bearer token and every uploaded position appears in a table and on a map:

```typescript
sync: {
  url: 'https://background-geolocation-playground.capawesome.io/v1/positions',
  headers: {
    Authorization: 'Bearer <YOUR_SESSION_KEY>',
  },
},
```

![Background Geolocation Playground webpage showing the synced positions of a session in a table and on a map](https://raw.githubusercontent.com/capawesome-team/capacitor-plugins/main/packages/background-geolocation/assets/background-geolocation-playground.png)

**Attention**: Session keys and recorded positions are deleted after 14 days and everyone who knows the session key can view its positions, so use the webpage for debugging and testing only and never in production.

### Deliberately Not Supported

The following features are intentionally not part of the sync pipeline:

- **Network constraints**: Uploads cannot be restricted to Wi-Fi or unmetered networks.
- **Response processing**: The response body of the server is always ignored, so the server cannot send commands back to the device.
- **Upload after a force-quit**: Buffered positions are not uploaded while the app is terminated. They are delivered with the next watch session that has a `sync` configuration.
- **Encryption at rest**: The queue database is not encrypted. It is stored in the sandboxed app storage of the operating system.

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

The plugin deliberately does not include a motion-detection state machine that turns the GPS on and off based on accelerometer data. Instead, it exposes real tuning knobs: use a lower `accuracy` (e.g. `Accuracy.Balanced` instead of `Accuracy.High`), use a `distanceFilter` to reduce the number of position updates and increase the `androidInterval` option on Android.

On iOS, you can additionally enable the `iosPausesAutomatically` option so that the operating system pauses the position updates when the device is unlikely to move. Be aware of the trade-off: the operating system decides on its own when to pause and only resumes the position updates once the device has moved significantly again, so a watch session can stay silent for a long time. The plugin logs both the pause and the resume, but keeps the watch session active, which means that `isWatching()` still returns `true` while the position updates are paused.

### What happens if the user grants only approximate location?

On Android, the user can grant approximate instead of precise location. This is not reported separately but is reflected in the `accuracy` property of each position. On iOS, the user can grant reduced accuracy, which you can upgrade for the duration of the app session using `requestTemporaryFullAccuracy(...)`.

### Why are queued positions not uploaded after force-quit?

Uploading requires a running app process. When the user force-quits (terminates) the app, the watch session ends and no more requests can be sent. The buffered positions are **not** lost though: they stay in the local queue and are uploaded as soon as the next watch session with a `sync` configuration is started. See [Queue Behavior](#queue-behavior) for details.

### How do I secure the sync endpoint?

Use the `headers` option of the `sync` configuration to send a static credential (e.g. `Authorization: Bearer ...`) with every upload request and always use an `https://` URL, since the credential is otherwise sent in plain text. On iOS, App Transport Security blocks plain `http://` requests by default anyway. Since the headers are static for the duration of the watch session, use a long-lived token and restart the watch session whenever the token is rotated.

### How can I test the sync endpoint locally?

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
