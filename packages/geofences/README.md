# Capacitor Geofences Plugin

Capacitor plugin for monitoring OS-managed geofences (region monitoring) on Android and iOS. Detects enter, exit and dwell transitions even while the app is in the background or terminated.

<div class="capawesome-z29o10a">
  <a href="https://cloud.capawesome.io/" target="_blank">
    <img alt="Deliver Live Updates to your Capacitor app with Capawesome Cloud" src="https://cloud.capawesome.io/assets/banners/cloud-build-and-deploy-capacitor-apps.png?t=1" />
  </a>
</div>

## Features

The Capacitor Geofences plugin lets your app react when a device enters or leaves a geographic region, using the battery-efficient region monitoring built into the operating system. Here are some of the key features:

- 🌍 **OS-Managed Regions**: Uses `GeofencingClient` on Android and `CLLocationManager` region monitoring on iOS, so transitions are detected by the system with minimal battery impact.
- 🔔 **Transition Events**: Get notified about enter, exit and (on Android) dwell transitions.
- 💀 **Killed-App Delivery**: Transitions that occur while the app is terminated are stored in a durable on-device queue and can trigger a native local notification.
- 📥 **Durable Queue**: Read the queue from JavaScript with keyset pagination and acknowledge what you processed, so nothing is lost between two app starts.
- ☁️ **HTTP Upload**: Upload transitions to your own server with automatic retries and at-least-once delivery — even while the app is in the background or terminated.
- 🔁 **Auto Re-Registration**: On Android, geofences are automatically re-registered after a device reboot or an app update.
- 🔒 **Public APIs Only**: Built exclusively on public platform APIs, so it is safe for App Review and resilient to OS updates.
- 🤝 **Compatibility**: Works hand in hand with the [Background Geolocation](https://capawesome.io/docs/sdks/capacitor/background-geolocation/) plugin for continuous location tracking.
- 📦 **CocoaPods & SPM**: Supports CocoaPods and Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: Priority support from the Capawesome Team.
- ✨ **Handcrafted**: Built from the ground up with care and expertise, not forked or AI-generated.

Missing a feature? Just [open an issue](https://github.com/capawesome-team/capacitor-plugins/issues) and we'll take a look!

## Use Cases

The Geofences plugin is typically used whenever an app needs to react when a device enters or leaves a specific area, for example:

- **Location-based reminders**: Notify users when they arrive at or leave a place, for example a store, an office, or their home.
- **Attendance and check-ins**: Check users in or out automatically when they enter or leave a site, using dwell transitions on Android to confirm that they actually stayed.
- **Proximity marketing**: Display a native local notification with an offer when a customer walks near one of your branches, even while the app is terminated.
- **Field service and logistics**: Record arrivals at and departures from customer sites or depots without draining the battery through continuous tracking.
- **Safety zones**: Alert caregivers or fleet managers when a person or vehicle leaves a defined safe area.
- **Context-aware apps**: Adapt the app to the user's surroundings, for example by switching to a venue-specific view once the user is on site.

## Compatibility

| Plugin Version | Capacitor Version | Status         |
| -------------- | ----------------- | -------------- |
| 0.x.x          | >=8.x.x           | Active support |

## Guides

- [Announcing the Capacitor Geofences Plugin](https://capawesome.io/blog/announcing-the-capacitor-geofences-plugin/): Monitor OS-managed regions on Android and iOS and get enter, exit, and dwell events, even after the app is killed.

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
Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome-team/capacitor-geofences` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome-team/capacitor-geofences
npx cap sync
```

### Android

#### Permissions

The plugin already declares the `ACCESS_FINE_LOCATION`, `POST_NOTIFICATIONS` and `RECEIVE_BOOT_COMPLETED` permissions in its manifest.

Geofencing additionally requires the **background location** permission. For [Google Play policy](https://support.google.com/googleplay/android-developer/answer/9799150) reasons, this permission is **not** declared by the plugin and must be added to your app's `AndroidManifest.xml` before or after the `application` tag:

```xml
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
```

Starting with Android 10 (API level 29), the background location permission cannot be requested together with the foreground location permission. You must first request the foreground location permission and only afterwards request the background location permission (see [Check and request permissions](#check-and-request-permissions)).

#### Proguard

If you are using Proguard, you need to add the following rules to your `proguard-rules.pro` file:

```
-keep class io.capawesome.capacitorjs.plugins.** { *; }
```

#### Variables

If needed, you can define the following project variables in your app's `variables.gradle` file to change the default versions of the dependencies:

- `$androidxWorkVersion` version of `androidx.work:work-runtime` (default: `2.11.2`)
- `$playServicesLocationVersion` version of `com.google.android.gms:play-services-location` (default: `21.4.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

#### Privacy Descriptions

Add the `NSLocationWhenInUseUsageDescription` and `NSLocationAlwaysAndWhenInUseUsageDescription` keys to the `ios/App/App/Info.plist` file, which tells the user why your app needs access to the location:

```xml
<key>NSLocationWhenInUseUsageDescription</key>
<string>The app needs access to your location to monitor geofences.</string>
<key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
<string>The app needs access to your location to monitor geofences while it is in the background.</string>
```

If the keys are missing, `addGeofences(...)` and `requestPermissions(...)` reject with a clear error message.

## Configuration

No configuration required for this plugin.

## Usage

The following examples are grouped into permissions, geofences, transitions and upload.

### Getting started

Request the required permissions, register a geofence and drain the transition queue whenever your app becomes active. The `geofenceTransition` event is a live feed that only fires while your app is running — the **queue is the durable record** of every transition, including the ones that were detected while the app was terminated. Only available on Android and iOS:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const start = async () => {
  // 1. Request the foreground and afterwards the background location permission.
  let status = await Geofences.requestPermissions({ permissions: ['location'] });
  if (status.location !== 'granted') {
    return;
  }
  status = await Geofences.requestPermissions({
    permissions: ['backgroundLocation'],
  });
  if (status.backgroundLocation !== 'granted') {
    return;
  }
  // 2. Add the geofence to monitor.
  await Geofences.addGeofences({
    geofences: [{ latitude: 37.33182, longitude: -122.03118, radius: 200 }],
  });
  // 3. Drain the queue now and whenever the app becomes active again.
  await drainQueue();
  document.addEventListener('visibilitychange', () => {
    if (document.visibilityState === 'visible') {
      drainQueue();
    }
  });
};

const drainQueue = async () => {
  let hasMore = true;
  while (hasMore) {
    const result = await Geofences.getQueuedTransitions({ limit: 1000 });
    if (!result.transitions.length) {
      break;
    }
    await persist(result.transitions);
    await Geofences.deleteQueuedTransitions({
      upToId: result.transitions[result.transitions.length - 1].id,
    });
    hasMore = result.hasMore;
  }
};
```

### Permissions

#### Check and request permissions

Geofencing requires the **Always** location authorization on iOS and the **background location** permission on Android. Because of the platform restrictions described in the [Installation](#installation) section, the permissions must be requested in two steps:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const checkPermissions = async () => {
  return Geofences.checkPermissions();
};

const requestPermissions = async () => {
  // Step 1: Request the foreground location permission.
  let status = await Geofences.requestPermissions({
    permissions: ['location'],
  });
  // Step 2: Request the background location permission.
  if (status.location === 'granted') {
    status = await Geofences.requestPermissions({
      permissions: ['backgroundLocation'],
    });
  }
  // Optionally: Request the notifications permission.
  await Geofences.requestPermissions({ permissions: ['notifications'] });
  return status;
};
```

#### Open the app settings

If a permission was permanently denied, send the user to the native app settings:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const openSettings = async () => {
  await Geofences.openSettings();
};
```

### Geofences

#### Add geofences

Add one or more circular regions to be monitored by the operating system. Optionally, provide a `notification` that is displayed natively when a transition is detected, which is especially useful while the app is terminated. On Android, an enter transition is triggered immediately if the device is already inside a geofence that was just added, while iOS only reports a transition once the device crosses the boundary. Only available on Android and iOS:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const addGeofences = async () => {
  const { ids } = await Geofences.addGeofences({
    geofences: [
      {
        latitude: 37.33182,
        longitude: -122.03118,
        radius: 200,
        notification: {
          title: 'Welcome',
          text: 'You have entered the area.',
        },
      },
    ],
  });
  return ids;
};
```

#### Retrieve geofences

Retrieve all geofences that are currently being monitored. Only available on Android and iOS:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const getGeofences = async () => {
  const { geofences } = await Geofences.getGeofences();
  return geofences;
};
```

#### Remove geofences

Remove specific geofences by their identifier or remove all of them at once. Only available on Android and iOS:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const removeGeofences = async (ids: string[]) => {
  await Geofences.removeGeofences({ ids });
};

const removeAllGeofences = async () => {
  await Geofences.removeAllGeofences();
};
```

### Transitions

#### Listen for geofence transitions

Get notified when the device enters, exits, or (on Android) dwells inside a geofence. The event is a **live feed**: it is only delivered while your app is running and a listener is registered. Transitions that were detected while the app was in the background or terminated are **not** replayed — read them from the [queue](#read-the-queue) instead. Only available on Android and iOS:

```typescript
import { Geofences, TransitionType } from '@capawesome-team/capacitor-geofences';

const addListener = async () => {
  await Geofences.addListener('geofenceTransition', (event) => {
    if (event.transition.type === TransitionType.Enter) {
      console.log(`Entered the geofence ${event.transition.geofenceId}.`);
    }
  });
};
```

#### Read the queue

Every transition is appended to a durable on-device queue, whether or not your app is running. Read the queue in pages, process the transitions and delete the ones you processed. Only available on Android and iOS:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const drainQueue = async () => {
  let hasMore = true;
  while (hasMore) {
    const result = await Geofences.getQueuedTransitions({ limit: 1000 });
    if (!result.transitions.length) {
      break;
    }
    await persist(result.transitions);
    await Geofences.deleteQueuedTransitions({
      upToId: result.transitions[result.transitions.length - 1].id,
    });
    hasMore = result.hasMore;
  }
};
```

See [Queue](#queue) for the capacity, the identifiers and the delivery guarantees.

#### Configure the queue

The queue is disabled until you configure it and then holds `1000` transitions by default. Call `resetConfig()` to disable it again. Only available on Android and iOS:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const configureQueue = async () => {
  await Geofences.setConfig({ maxSize: 5000 });
};
```

#### Inspect and clear the queue

Only available on Android and iOS:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const getQueueStatus = async () => {
  const { pendingCount, droppedCount, lastUploadedAt } =
    await Geofences.getQueueStatus();
  console.log(
    `${pendingCount} transitions pending, ${droppedCount} dropped, last upload: ${lastUploadedAt}`,
  );
};

const clearQueue = async () => {
  await Geofences.clearQueue();
};
```

#### Remove all listeners

Remove all listeners for this plugin when they are no longer needed:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const removeAllListeners = async () => {
  await Geofences.removeAllListeners();
};
```

### Upload

#### Configure the upload

Configure the plugin to upload every queued transition to your own server, even while the app is in the background or terminated. The configuration is persisted natively, so it only needs to be set once (e.g. after sign-in). Failed upload attempts are reported via the `uploadFailed` event. Only available on Android and iOS:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const configureUpload = async () => {
  await Geofences.addListener('uploadFailed', (event) => {
    console.error('Upload failed: ', event.statusCode, event.message);
  });
  await Geofences.setConfig({
    url: 'https://api.example.com/transitions',
    headers: {
      Authorization: 'Bearer eyJhbGciOi...',
    },
    extras: {
      userId: 'abc',
    },
  });
};

const disableUpload = async () => {
  await Geofences.resetConfig();
};
```

**Attention**: `setConfig(...)` replaces the stored configuration entirely, so always pass every property you want to keep.

See [HTTP Upload](#http-upload) for the server contract and the response handling.

#### Trigger the upload

Upload the queued transitions right away instead of waiting for the next retry. Only available on Android and iOS:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const triggerUpload = async () => {
  await Geofences.triggerUpload();
};
```

## API

<docgen-index>

* [`addGeofences(...)`](#addgeofences)
* [`checkPermissions()`](#checkpermissions)
* [`clearQueue()`](#clearqueue)
* [`deleteQueuedTransitions(...)`](#deletequeuedtransitions)
* [`getConfig()`](#getconfig)
* [`getGeofences()`](#getgeofences)
* [`getQueuedTransitions(...)`](#getqueuedtransitions)
* [`getQueueStatus()`](#getqueuestatus)
* [`openSettings()`](#opensettings)
* [`removeAllGeofences()`](#removeallgeofences)
* [`removeGeofences(...)`](#removegeofences)
* [`requestPermissions(...)`](#requestpermissions)
* [`resetConfig()`](#resetconfig)
* [`setConfig(...)`](#setconfig)
* [`triggerUpload()`](#triggerupload)
* [`addListener('geofenceTransition', ...)`](#addlistenergeofencetransition-)
* [`addListener('uploadFailed', ...)`](#addlisteneruploadfailed-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### addGeofences(...)

```typescript
addGeofences(options: AddGeofencesOptions) => Promise<AddGeofencesResult>
```

Add one or more geofences to be monitored.

On **Android**, an enter transition is triggered immediately if the device
is already inside a geofence that was just added. On **iOS**, no transition
is triggered until the device crosses the boundary of the geofence.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#addgeofencesoptions">AddGeofencesOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#addgeofencesresult">AddGeofencesResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check permissions for the plugin.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### clearQueue()

```typescript
clearQueue() => Promise<void>
```

Delete all transitions from the queue.

Use `deleteQueuedTransitions(...)` to only delete the transitions that
have already been read.

Only available on Android and iOS.

**Since:** 0.2.0

--------------------


### deleteQueuedTransitions(...)

```typescript
deleteQueuedTransitions(options: DeleteQueuedTransitionsOptions) => Promise<void>
```

Delete all transitions from the queue up to and including the given
identifier.

Call this method after the transitions returned by
`getQueuedTransitions(...)` have been processed successfully.

Only available on Android and iOS.

| Param         | Type                                                                                      |
| ------------- | ----------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletequeuedtransitionsoptions">DeleteQueuedTransitionsOptions</a></code> |

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
const config = await Geofences.getConfig();
await Geofences.setConfig({ ...config, maxSize: 5000 });
```

An empty object is returned if `setConfig(...)` has not been called yet.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#setconfigoptions">SetConfigOptions</a>&gt;</code>

**Since:** 0.2.0

--------------------


### getGeofences()

```typescript
getGeofences() => Promise<GetGeofencesResult>
```

Get all geofences that are currently being monitored.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getgeofencesresult">GetGeofencesResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### getQueuedTransitions(...)

```typescript
getQueuedTransitions(options?: GetQueuedTransitionsOptions | undefined) => Promise<GetQueuedTransitionsResult>
```

Get the transitions that are currently stored in the queue, oldest first.

The transitions remain in the queue until they are deleted via
`deleteQueuedTransitions(...)` or `clearQueue()`.

Only available on Android and iOS.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getqueuedtransitionsoptions">GetQueuedTransitionsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getqueuedtransitionsresult">GetQueuedTransitionsResult</a>&gt;</code>

**Since:** 0.2.0

--------------------


### getQueueStatus()

```typescript
getQueueStatus() => Promise<GetQueueStatusResult>
```

Get the current status of the queue.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getqueuestatusresult">GetQueueStatusResult</a>&gt;</code>

**Since:** 0.2.0

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

Opens the native app settings page to allow the user to grant the app the required permissions.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### removeAllGeofences()

```typescript
removeAllGeofences() => Promise<void>
```

Remove all geofences that are currently being monitored.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### removeGeofences(...)

```typescript
removeGeofences(options: RemoveGeofencesOptions) => Promise<void>
```

Remove one or more geofences by their identifier.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#removegeofencesoptions">RemoveGeofencesOptions</a></code> |

**Since:** 0.0.1

--------------------


### requestPermissions(...)

```typescript
requestPermissions(options?: RequestPermissionsOptions | undefined) => Promise<PermissionStatus>
```

Request permissions for the plugin.

The `backgroundLocation` permission must be requested in a **second**,
separate call after the `location` permission has been granted:

- On **Android 11+**, the user is taken to the location settings of the
  app where the `Allow all the time` option must be selected.
- On **iOS**, the operating system presents the upgrade prompt that asks
  the user to change the permission from `While Using the App` to
  `Always`.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#requestpermissionsoptions">RequestPermissionsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### resetConfig()

```typescript
resetConfig() => Promise<void>
```

Discard the configuration so that no transitions are stored or uploaded.

Transitions that are already queued are kept, use `clearQueue()` to discard them.

Only available on Android and iOS.

**Since:** 0.2.0

--------------------


### setConfig(...)

```typescript
setConfig(options: SetConfigOptions) => Promise<void>
```

Set the configuration of the transition queue and of the upload.

The configuration is persisted natively so that it keeps working when the
operating system wakes your app without a web view.

Transitions are stored in the queue if `maxSize` or `url` is provided, and
they are uploaded if `url` is provided. Use `resetConfig()` to stop
storing and uploading transitions.

**This method replaces the whole configuration**, so every property you
omit falls back to its default. `setConfig({ maxSize: 5000 })` therefore
also stops the upload, because `url` is no longer set. Keep your
configuration in one place and pass it whole:

```typescript
await Geofences.setConfig({ maxSize: 5000, url });
```

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#setconfigoptions">SetConfigOptions</a></code> |

**Since:** 0.2.0

--------------------


### triggerUpload()

```typescript
triggerUpload() => Promise<void>
```

Immediately attempt to upload all queued transitions.

Any pending retry backoff is cancelled and a new upload attempt is
started right away. The promise resolves as soon as the attempt has
been scheduled, not when the transitions have been delivered.

The promise rejects if no `url` has been set via `setConfig(...)`.

Only available on Android and iOS.

**Since:** 0.2.0

--------------------


### addListener('geofenceTransition', ...)

```typescript
addListener(eventName: 'geofenceTransition', listenerFunc: (event: GeofenceTransitionEvent) => void) => Promise<PluginListenerHandle>
```

Called when a geofence transition (enter, exit or dwell) is detected.

This event is a live feed that is only delivered while a listener is
registered. Transitions that occurred while the app was in the background
or terminated are **not** replayed. Read them from the queue via
`getQueuedTransitions(...)` instead.

Only available on Android and iOS.

| Param              | Type                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'geofenceTransition'</code>                                                               |
| **`listenerFunc`** | <code>(event: <a href="#geofencetransitionevent">GeofenceTransitionEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('uploadFailed', ...)

```typescript
addListener(eventName: 'uploadFailed', listenerFunc: (event: UploadFailedEvent) => void) => Promise<PluginListenerHandle>
```

Called when an upload attempt of queued transitions fails.

The affected transitions remain in the queue and are retried
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


#### AddGeofencesResult

| Prop      | Type                  | Description                                                                                          | Since |
| --------- | --------------------- | ---------------------------------------------------------------------------------------------------- | ----- |
| **`ids`** | <code>string[]</code> | The identifiers of the added geofences. The order matches the order of the geofences in the request. | 0.0.1 |


#### AddGeofencesOptions

| Prop            | Type                    | Description           | Since |
| --------------- | ----------------------- | --------------------- | ----- |
| **`geofences`** | <code>Geofence[]</code> | The geofences to add. | 0.0.1 |


#### Geofence

| Prop                            | Type                                                                  | Description                                                                                                                                                         | Default            | Since |
| ------------------------------- | --------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | ----- |
| **`androidExpirationDuration`** | <code>number</code>                                                   | The time in milliseconds after which the geofence is automatically removed. Only available on Android.                                                              |                    | 0.0.1 |
| **`androidLoiteringDelay`**     | <code>number</code>                                                   | The time in milliseconds the device must dwell inside the geofence before a dwell transition event is triggered. Only available on Android.                         |                    | 0.0.1 |
| **`androidNotifyOnDwell`**      | <code>boolean</code>                                                  | Whether a transition event should be triggered when the device dwells inside the geofence. Only available on Android.                                               | <code>false</code> | 0.0.1 |
| **`id`**                        | <code>string</code>                                                   | A unique identifier for the geofence. If not provided, a random identifier (UUID) is generated and returned in the result of the `addGeofences(...)` method.        |                    | 0.0.1 |
| **`latitude`**                  | <code>number</code>                                                   | The latitude of the center of the geofence in degrees.                                                                                                              |                    | 0.0.1 |
| **`longitude`**                 | <code>number</code>                                                   | The longitude of the center of the geofence in degrees.                                                                                                             |                    | 0.0.1 |
| **`radius`**                    | <code>number</code>                                                   | The radius of the geofence in meters. Apple recommends a radius of at least 200 meters, as smaller radii may not trigger transitions reliably.                      |                    | 0.0.1 |
| **`notifyOnEnter`**             | <code>boolean</code>                                                  | Whether a transition event should be triggered when the device enters the geofence.                                                                                 | <code>true</code>  | 0.0.1 |
| **`notifyOnExit`**              | <code>boolean</code>                                                  | Whether a transition event should be triggered when the device exits the geofence.                                                                                  | <code>true</code>  | 0.0.1 |
| **`notification`**              | <code><a href="#geofencenotification">GeofenceNotification</a></code> | A local notification to display natively when a transition for this geofence is detected. This is especially useful to notify the user while the app is terminated. |                    | 0.0.1 |


#### GeofenceNotification

| Prop        | Type                | Description                        | Since |
| ----------- | ------------------- | ---------------------------------- | ----- |
| **`title`** | <code>string</code> | The title of the notification.     | 0.0.1 |
| **`text`**  | <code>string</code> | The body text of the notification. | 0.0.1 |


#### PermissionStatus

| Prop                     | Type                                                        | Description                                                                                                                                                                        | Since |
| ------------------------ | ----------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`location`**           | <code><a href="#permissionstate">PermissionState</a></code> | The permission state for using the location while the app is in use.                                                                                                               | 0.0.1 |
| **`backgroundLocation`** | <code><a href="#permissionstate">PermissionState</a></code> | The permission state for using the location while the app is in the background. This permission is required to monitor geofences while the app is in the background or terminated. | 0.0.1 |
| **`notifications`**      | <code><a href="#permissionstate">PermissionState</a></code> | The permission state for displaying local notifications on a transition.                                                                                                           | 0.0.1 |


#### DeleteQueuedTransitionsOptions

| Prop         | Type                | Description                                                                                                         | Since |
| ------------ | ------------------- | ------------------------------------------------------------------------------------------------------------------- | ----- |
| **`upToId`** | <code>number</code> | The identifier of the newest transition to delete. All transitions with this identifier or a lower one are deleted. | 0.2.0 |


#### SetConfigOptions

| Prop          | Type                                                         | Description                                                                                                                                                                                                                                        | Default           | Since |
| ------------- | ------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------- | ----- |
| **`extras`**  | <code>{ [key: string]: string \| number \| boolean; }</code> | Static metadata that is attached to every upload request as the `extras` property of the request body.                                                                                                                                             |                   | 0.2.0 |
| **`headers`** | <code>{ [key: string]: string; }</code>                      | Static HTTP headers that are sent with every upload request, for example for authorization.                                                                                                                                                        |                   | 0.2.0 |
| **`maxSize`** | <code>number</code>                                          | The maximum number of transitions that are stored in the queue. When the queue is full, the oldest transitions are dropped first. A stored transition occupies about `140` bytes, so the default of `1000` needs about `140` KB. Must be positive. | <code>1000</code> | 0.2.0 |
| **`url`**     | <code>string</code>                                          | The URL the transitions are uploaded to via HTTP `POST`. Providing this property enables the upload. Omit it to keep the transitions on the device only.                                                                                           |                   | 0.2.0 |


#### GetGeofencesResult

| Prop            | Type                    | Description                                       | Since |
| --------------- | ----------------------- | ------------------------------------------------- | ----- |
| **`geofences`** | <code>Geofence[]</code> | The geofences that are currently being monitored. | 0.0.1 |


#### GetQueuedTransitionsResult

| Prop              | Type                            | Description                                                          | Since |
| ----------------- | ------------------------------- | -------------------------------------------------------------------- | ----- |
| **`hasMore`**     | <code>boolean</code>            | Whether more transitions are stored in the queue than were returned. | 0.2.0 |
| **`transitions`** | <code>QueuedTransition[]</code> | The queued transitions, oldest first.                                | 0.2.0 |


#### QueuedTransition

| Prop     | Type                | Description                                                                                                | Since |
| -------- | ------------------- | ---------------------------------------------------------------------------------------------------------- | ----- |
| **`id`** | <code>number</code> | The identifier of the transition in the queue. The identifier increases monotonically and is never reused. | 0.2.0 |


#### GetQueuedTransitionsOptions

| Prop          | Type                | Description                                                                                                                                    | Default          | Since |
| ------------- | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- | ----- |
| **`afterId`** | <code>number</code> | Only return transitions with an identifier greater than this value. If not provided, the transitions are returned from the start of the queue. |                  | 0.2.0 |
| **`limit`**   | <code>number</code> | The maximum number of transitions to return. Must be greater than `0`.                                                                         | <code>100</code> | 0.2.0 |


#### GetQueueStatusResult

| Prop                 | Type                        | Description                                                                                                                                                                                                           | Since |
| -------------------- | --------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`droppedCount`**   | <code>number</code>         | The number of transitions that were dropped without being read since the queue was last cleared, for example because the queue was full. This counter is persisted across app restarts.                               | 0.2.0 |
| **`lastUploadedAt`** | <code>number \| null</code> | The time at which the last batch of transitions was uploaded successfully in milliseconds since the Unix epoch or `null` if no batch has been uploaded successfully yet. This value is persisted across app restarts. | 0.2.0 |
| **`pendingCount`**   | <code>number</code>         | The number of transitions that are currently stored in the queue.                                                                                                                                                     | 0.2.0 |


#### RemoveGeofencesOptions

| Prop      | Type                  | Description                                 | Since |
| --------- | --------------------- | ------------------------------------------- | ----- |
| **`ids`** | <code>string[]</code> | The identifiers of the geofences to remove. | 0.0.1 |


#### RequestPermissionsOptions

| Prop              | Type                          | Description                 | Default                                    | Since |
| ----------------- | ----------------------------- | --------------------------- | ------------------------------------------ | ----- |
| **`permissions`** | <code>PermissionType[]</code> | The permissions to request. | <code>['location', 'notifications']</code> | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### GeofenceTransitionEvent

| Prop             | Type                                                              | Description              | Since |
| ---------------- | ----------------------------------------------------------------- | ------------------------ | ----- |
| **`transition`** | <code><a href="#geofencetransition">GeofenceTransition</a></code> | The detected transition. | 0.2.0 |


#### GeofenceTransition

| Prop             | Type                                                      | Description                                                                                                                                                | Since |
| ---------------- | --------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`geofenceId`** | <code>string</code>                                       | The identifier of the geofence that triggered the transition.                                                                                              | 0.2.0 |
| **`latitude`**   | <code>number \| null</code>                               | The latitude of the location that triggered the transition in degrees. On **iOS**, this is always `null` because the triggering location is not provided.  | 0.2.0 |
| **`longitude`**  | <code>number \| null</code>                               | The longitude of the location that triggered the transition in degrees. On **iOS**, this is always `null` because the triggering location is not provided. | 0.2.0 |
| **`timestamp`**  | <code>number</code>                                       | The time the transition was detected in milliseconds since epoch.                                                                                          | 0.2.0 |
| **`type`**       | <code><a href="#transitiontype">TransitionType</a></code> | The type of the transition.                                                                                                                                | 0.2.0 |


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

<code>'location' | 'backgroundLocation' | 'notifications'</code>


### Enums


#### TransitionType

| Members     | Value                | Description                                                            | Since |
| ----------- | -------------------- | ---------------------------------------------------------------------- | ----- |
| **`Dwell`** | <code>'DWELL'</code> | The device has dwelled inside the geofence. Only available on Android. | 0.0.1 |
| **`Enter`** | <code>'ENTER'</code> | The device has entered the geofence.                                   | 0.0.1 |
| **`Exit`**  | <code>'EXIT'</code>  | The device has exited the geofence.                                    | 0.0.1 |

</docgen-api>

## Queue

Once the queue is enabled, every geofence transition is appended to it, whether or not your app is running. The queue is then the **single source of truth**: the `geofenceTransition` event is an ephemeral live feed that only fires while your app is running, so an app that only listens to the event misses every transition that was detected while it was terminated.

Read the queue with `getQueuedTransitions(...)` and acknowledge the transitions you processed with `deleteQueuedTransitions(...)`. Reading and deleting are two separate steps on purpose: a crash between reading and persisting must not lose a transition.

### Identifiers

Every queued transition carries a numeric `id` that increases monotonically and is never reused, even after the queue was cleared or the app was restarted. This makes it usable both for keyset pagination via `afterId` and for the inclusive prefix delete via `upToId`.

### Behavior

- **Persistence**: The queue is stored in the sandboxed app storage and survives app restarts, force-quits and device reboots.
- **Opt-in**: Nothing is stored until you call `setConfig(...)` with `maxSize` or `url`, and transitions detected before that are not stored. Call `resetConfig()` to disable it again; transitions that are already queued are kept until `clearQueue()`.
- **Capacity**: The queue holds at most `1000` transitions by default, which can be changed via `setConfig({ maxSize })`. If the queue is full, the **oldest** transitions are dropped first. 
- **Observability**: The `droppedCount` property of `getQueueStatus()` counts the transitions that were dropped since the queue was last cleared. The counter is persisted across app restarts.
- **Duplicates**: A transition that is delivered via the `geofenceTransition` event while your app is running is also stored in the queue. Treat the queue as the source of truth to avoid processing it twice.

## HTTP Upload

The plugin can upload every queued transition to your own server without involving JavaScript. Transitions are uploaded to the configured URL and only deleted from the queue after the server has acknowledged them. Since the whole pipeline runs natively, it keeps working while the web view is suspended — and, unlike a watch session of the [Background Geolocation](https://capawesome.io/docs/sdks/capacitor/background-geolocation/) plugin, even while the app is terminated.

**Attention**: The upload drains the same queue that `getQueuedTransitions(...)` reads. Transitions are stored once and deleted once, so a transition your app deletes with `deleteQueuedTransitions(...)` is never uploaded, and a transition the server acknowledged is no longer readable. Use either the upload or your own drain loop, not both.

### Options

Call `setConfig({ url })` to enable the upload pipeline. The configuration is persisted natively so that transitions can be uploaded when the operating system wakes your app without a web view, and it applies to every future transition until `resetConfig()` is called. Call it on every app start anyway: the stored copy is only a cache of your last call, and it is discarded whenever the plugin's configuration format changes in a future version. Queued transitions are never discarded with it:

```typescript
import { Geofences } from '@capawesome-team/capacitor-geofences';

const configureUpload = async () => {
  await Geofences.setConfig({
    url: 'https://api.example.com/transitions',
    headers: {
      Authorization: 'Bearer eyJhbGciOi...',
    },
    extras: {
      userId: 'abc',
    },
  });
};
```

**Attention**: The configuration you pass replaces the stored one entirely. Every property you omit falls back to its default, so read the stored configuration with `getConfig()` first if you only want to change a single property.

To stop the upload but keep storing transitions, call `setConfig(...)` again without `url`:

```typescript
const { url, ...config } = await Geofences.getConfig();
await Geofences.setConfig(config);
```

### Server Contract

Transitions are uploaded with an HTTP `POST` request and the `Content-Type: application/json; charset=utf-8` header. Your own `headers` are applied afterwards and may override it. The request body looks as follows:

```json
{
  "transitions": [
    {
      "id": 42,
      "geofenceId": "2ca23ff9-b95d-4962-b64f-3e1efe6f2e7d",
      "type": "ENTER",
      "timestamp": 1723291200000,
      "latitude": 52.52,
      "longitude": 13.405
    }
  ],
  "extras": { "userId": "abc" }
}
```

Every entry of the `transitions` array is a [`QueuedTransition`](#queuedtransition) object whose `id` property is the identifier of the transition in the queue and whose `geofenceId` property is the identifier of the geofence. On iOS, `latitude` and `longitude` are always `null`. The `extras` property is omitted entirely if the `extras` option was not provided.

**Idempotency**: The `id` is unique per app installation, not globally. Transitions are delivered **at least once**, so the same transition may be uploaded more than once, for example if the acknowledgment of the server is lost on the way back. Deduplicate the transitions on the server by the `id` together with a device or user identifier from `extras` to make the upload idempotent.

### Response Handling

The response body is always ignored. Only the status code decides what happens to the uploaded transitions:

| Status Code                                 | Behavior                                                                                                                              |
| ------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| `2xx`                                       | The transitions are acknowledged and deleted from the queue.                                                                         |
| `401`, `408`, `429`, `5xx`, network error, timeout | The transitions stay in the queue and are retried with an exponential backoff. A `uploadFailed` event is emitted.               |
| Any other status code                       | The transitions are **dropped permanently without being uploaded again** and a `uploadFailed` event is emitted.                         |

**Attention**: Transitions that the server rejects with any status code other than `2xx`, `401`, `408`, `429` or `5xx` (for example `400 Bad Request` or `422 Unprocessable Entity`) are deleted from the queue and **lost**. This is intentional: a permanently rejected upload must never block the queue forever. `401` is treated as retryable, because it usually means an expired token rather than an unacceptable upload — refresh the credentials with `setConfig(...)` and the queued transitions are uploaded afterwards. Make sure your endpoint answers with a retryable status code (e.g. `503`) if it is temporarily unable to accept transitions, and listen to the `uploadFailed` event to detect such drops. Dropped transitions are also counted in the `droppedCount` property of `getQueueStatus()`.

The request timeout is 30 seconds. On Android, retries are scheduled via WorkManager with an exponential backoff starting at 10 seconds, so they survive a process death and even a device reboot. On iOS, retries start with a delay of 5 seconds and double after every attempt up to a maximum of 10 minutes while the app is alive; pending transitions are also uploaded on the next app launch and whenever a new transition is detected. The backoff is reset whenever `setConfig({ url })` or `triggerUpload()` is called.

### Deliberately Not Supported

The following features are intentionally not part of the upload pipeline:

- **Network constraints**: Uploads cannot be restricted to Wi-Fi or unmetered networks.
- **Response processing**: The response body of the server is always ignored, so the server cannot send commands back to the device.
- **Encryption at rest**: The queue is not encrypted. It is stored in the sandboxed app storage of the operating system.

## FAQ

### When should I use this plugin instead of the Background Geolocation plugin?

Both plugins solve different problems and complement each other. The [Background Geolocation](https://capawesome.io/docs/sdks/capacitor/background-geolocation/) plugin continuously tracks the device's location and streams position updates to your app, which is what you need when you want the actual location trail. This plugin monitors circular regions and only notifies your app when the device crosses a region boundary, which is much cheaper on battery and can even deliver transitions while your app is terminated. Use Background Geolocation when you need the location trail, and Geofences when you only care about entering or leaving specific areas.

### How is this plugin different from other similar plugins?

It uses the operating system's own region monitoring — `GeofencingClient` on Android and Core Location on iOS — so enter, exit and dwell transitions are detected with minimal battery impact, even while your app is in the background or terminated. Transitions that occur while the app is killed are stored in a durable queue your app drains on the next launch and can trigger a native local notification, and on Android geofences are automatically re-registered after a reboot or app update. It's all exposed through one fully typed, actively maintained API with dedicated support; if you need the full location trail rather than boundary crossings, continuous tracking fits better, but for reacting to specific areas efficiently, this plugin is purpose-built.

### Is geofencing available on the web?

No. All methods are only available on Android and iOS. On the web, they reject with an unimplemented error.

### How many geofences can I register?

Android allows up to 100 geofences per app, iOS up to 20 regions (a hard limit of the operating system). If you exceed the limit, `addGeofences(...)` rejects with the `GEOFENCE_LIMIT_EXCEEDED` error code. If your app needs more regions, register only the geofences closest to the user and update them as the user moves.

### What radius should I use for a geofence?

Apple recommends a radius of at least 200 meters, as smaller radii may not trigger transitions reliably. See the [Apple documentation](https://developer.apple.com/documentation/corelocation/monitoring-the-user-s-proximity-to-geographic-regions) for details. On Android, a radius of at least 100 meters is recommended. There is no fixed maximum radius on Android, while iOS clamps the radius to `maximumRegionMonitoringDistance`.

### Why does `addGeofences(...)` reject with a permission error?

Geofencing requires the **Always** location authorization on iOS and the **background location** permission on Android. If only the "while in use" (foreground) permission is granted, `addGeofences(...)` rejects with the `PERMISSION_DENIED` error code. See [Check and request permissions](#check-and-request-permissions) for the required two-step request flow.

### What happens to transitions that occur while my app is in the background or terminated?

If the queue is enabled, they are appended to it. It holds `1000` transitions by default and drops the **oldest** ones when it is full. Read them with `getQueuedTransitions(...)` when your app becomes active again — the `geofenceTransition` event only fires while your app is running and does not replay them. If a geofence defines a `notification`, it is displayed natively regardless of the app state. If your server needs to know about transitions right away instead of on the next launch, configure [HTTP Upload](#http-upload), which uploads them natively at the moment they are detected. On Android, geofences are automatically re-registered after a device reboot or an app update, while on iOS the monitored regions are persisted by the operating system.

### Why don't I receive dwell transitions on iOS?

Dwell transitions are only supported on Android. On iOS, the `androidNotifyOnDwell` and `androidLoiteringDelay` options are ignored, and only enter and exit transitions are reported.

### Why are `latitude` and `longitude` `null` on iOS?

Core Location does not provide the triggering location for a region transition. If you need the geofence's coordinates, look them up by its `geofenceId` (you already know them because you added the geofence).

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Background Geolocation](https://capawesome.io/docs/sdks/capacitor/background-geolocation/): Continuously track the device's location in the background.
- [Geocoder](https://capawesome.io/docs/sdks/capacitor/geocoder/): Convert between coordinates and human-readable addresses.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/geofences/CHANGELOG.md).

## Breaking Changes

See [BREAKING.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/geofences/BREAKING.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/geofences/LICENSE).
