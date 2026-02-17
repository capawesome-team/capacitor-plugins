# @capawesome/capacitor-pixlive

Unofficial Capacitor plugin for [PixLive SDK](https://www.vidinoti.com/) by Vidinoti.

## Installation

```bash
npm install @capawesome/capacitor-pixlive
npx cap sync
```

### Android

#### SDK Setup

Copy the `vdarsdk-release.aar` file into your app's `android/app/libs/` directory.

### iOS

#### SDK Setup

The PixLive iOS SDK (`VDARSDK.xcframework`) must be manually installed:

1. Copy `VDARSDK.xcframework` into `ios/App/Frameworks/` in your Capacitor project.
2. Open `ios/App/App.xcworkspace` in Xcode.
3. Select the **App** target, go to **General** > **Frameworks, Libraries, and Embedded Content**.
4. Click **+**, then **Add Other...** > **Add Files...** and select `ios/App/Frameworks/VDARSDK.xcframework`.
5. Set the embed option to **Embed & Sign**.

## Configuration

Configure the plugin in your `capacitor.config.ts`:

```typescript
const config: CapacitorConfig = {
  plugins: {
    Pixlive: {
      licenseKey: 'YOUR_LICENSE_KEY',
      apiUrl: 'https://ar.vidinoti.com/api/api.php', // optional
      sdkUrl: 'https://sdk.vidinoti.com', // optional
    },
  },
};
```

| Prop             | Type                | Description                      | Default                                    |
| ---------------- | ------------------- | -------------------------------- | ------------------------------------------ |
| **`licenseKey`** | <code>string</code> | The PixLive Maker license key.   |                                            |
| **`apiUrl`**     | <code>string</code> | The PixLive Maker API endpoint.  | `https://ar.vidinoti.com/api/api.php`      |
| **`sdkUrl`**     | <code>string</code> | The PixLive SDK resource server. | `https://sdk.vidinoti.com`                 |

## Usage

```typescript
import { Pixlive } from '@capawesome/capacitor-pixlive';

const synchronize = async () => {
  await Pixlive.synchronize({ tags: [['my-tag']] });
};

const createARView = async () => {
  await Pixlive.createARView({ x: 0, y: 0, width: 300, height: 400 });
};
```

## API

<docgen-index>

* [`synchronize(...)`](#synchronize)
* [`synchronizeWithToursAndContexts(...)`](#synchronizewithtoursandcontexts)
* [`updateTagMapping(...)`](#updatetagmapping)
* [`enableContextsWithTags(...)`](#enablecontextswithtags)
* [`getContexts()`](#getcontexts)
* [`getContext(...)`](#getcontext)
* [`activateContext(...)`](#activatecontext)
* [`stopContext()`](#stopcontext)
* [`getNearbyGPSPoints(...)`](#getnearbygpspoints)
* [`getGPSPointsInBoundingBox(...)`](#getgpspointsinboundingbox)
* [`getNearbyBeacons()`](#getnearbybeacons)
* [`startNearbyGPSDetection()`](#startnearbygpsdetection)
* [`stopNearbyGPSDetection()`](#stopnearbygpsdetection)
* [`startGPSNotifications()`](#startgpsnotifications)
* [`stopGPSNotifications()`](#stopgpsnotifications)
* [`setInterfaceLanguage(...)`](#setinterfacelanguage)
* [`createARView(...)`](#createarview)
* [`destroyARView()`](#destroyarview)
* [`resizeARView(...)`](#resizearview)
* [`setARViewTouchEnabled(...)`](#setarviewtouchenabled)
* [`setARViewTouchHole(...)`](#setarviewtouchhole)
* [`addListener('codeRecognize', ...)`](#addlistenercoderecognize-)
* [`addListener('enterContext', ...)`](#addlistenerentercontext-)
* [`addListener('exitContext', ...)`](#addlistenerexitcontext-)
* [`addListener('presentAnnotations', ...)`](#addlistenerpresentannotations-)
* [`addListener('hideAnnotations', ...)`](#addlistenerhideannotations-)
* [`addListener('eventFromContent', ...)`](#addlistenereventfromcontent-)
* [`addListener('syncProgress', ...)`](#addlistenersyncprogress-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### synchronize(...)

```typescript
synchronize(options: SynchronizeOptions) => Promise<void>
```

Sync content from PixLive Maker filtered by tags.

Only available on Android and iOS.

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#synchronizeoptions">SynchronizeOptions</a></code> |

**Since:** 8.0.0

--------------------


### synchronizeWithToursAndContexts(...)

```typescript
synchronizeWithToursAndContexts(options: SynchronizeWithToursAndContextsOptions) => Promise<void>
```

Sync content filtered by tags, tour IDs, and context IDs.

Only available on Android and iOS.

| Param         | Type                                                                                                      |
| ------------- | --------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#synchronizewithtoursandcontextsoptions">SynchronizeWithToursAndContextsOptions</a></code> |

**Since:** 8.0.0

--------------------


### updateTagMapping(...)

```typescript
updateTagMapping(options: UpdateTagMappingOptions) => Promise<void>
```

Update tag-to-context mappings for language filtering.

Only available on Android and iOS.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#updatetagmappingoptions">UpdateTagMappingOptions</a></code> |

**Since:** 8.0.0

--------------------


### enableContextsWithTags(...)

```typescript
enableContextsWithTags(options: EnableContextsWithTagsOptions) => Promise<void>
```

Enable only contexts matching specific tags.

Only available on Android and iOS.

| Param         | Type                                                                                    |
| ------------- | --------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#enablecontextswithtagsoptions">EnableContextsWithTagsOptions</a></code> |

**Since:** 8.0.0

--------------------


### getContexts()

```typescript
getContexts() => Promise<GetContextsResult>
```

Get all synchronized contexts.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getcontextsresult">GetContextsResult</a>&gt;</code>

**Since:** 8.0.0

--------------------


### getContext(...)

```typescript
getContext(options: GetContextOptions) => Promise<GetContextResult>
```

Get a single context by ID.

Only available on Android and iOS.

| Param         | Type                                                            |
| ------------- | --------------------------------------------------------------- |
| **`options`** | <code><a href="#getcontextoptions">GetContextOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getcontextresult">GetContextResult</a>&gt;</code>

**Since:** 8.0.0

--------------------


### activateContext(...)

```typescript
activateContext(options: ActivateContextOptions) => Promise<void>
```

Programmatically trigger/activate a context.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#activatecontextoptions">ActivateContextOptions</a></code> |

**Since:** 8.0.0

--------------------


### stopContext()

```typescript
stopContext() => Promise<void>
```

Stop the currently playing/active context.

Only available on Android and iOS.

**Since:** 8.0.0

--------------------


### getNearbyGPSPoints(...)

```typescript
getNearbyGPSPoints(options: GetNearbyGPSPointsOptions) => Promise<GetNearbyGPSPointsResult>
```

Get GPS points near a given location, sorted by distance.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getnearbygpspointsoptions">GetNearbyGPSPointsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getnearbygpspointsresult">GetNearbyGPSPointsResult</a>&gt;</code>

**Since:** 8.0.0

--------------------


### getGPSPointsInBoundingBox(...)

```typescript
getGPSPointsInBoundingBox(options: GetGPSPointsInBoundingBoxOptions) => Promise<GetGPSPointsInBoundingBoxResult>
```

Get all GPS points within a geographic bounding box.

Only available on Android and iOS.

| Param         | Type                                                                                          |
| ------------- | --------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#getgpspointsinboundingboxoptions">GetGPSPointsInBoundingBoxOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getgpspointsinboundingboxresult">GetGPSPointsInBoundingBoxResult</a>&gt;</code>

**Since:** 8.0.0

--------------------


### getNearbyBeacons()

```typescript
getNearbyBeacons() => Promise<GetNearbyBeaconsResult>
```

Get contexts associated with nearby detected beacons.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getnearbybeaconsresult">GetNearbyBeaconsResult</a>&gt;</code>

**Since:** 8.0.0

--------------------


### startNearbyGPSDetection()

```typescript
startNearbyGPSDetection() => Promise<void>
```

Start background GPS proximity detection.

Only available on Android and iOS.

**Since:** 8.0.0

--------------------


### stopNearbyGPSDetection()

```typescript
stopNearbyGPSDetection() => Promise<void>
```

Stop background GPS proximity detection.

Only available on Android and iOS.

**Since:** 8.0.0

--------------------


### startGPSNotifications()

```typescript
startGPSNotifications() => Promise<void>
```

Enable GPS-triggered local notifications.

Only available on Android and iOS.

**Since:** 8.0.0

--------------------


### stopGPSNotifications()

```typescript
stopGPSNotifications() => Promise<void>
```

Disable GPS-triggered local notifications.

Only available on Android and iOS.

**Since:** 8.0.0

--------------------


### setInterfaceLanguage(...)

```typescript
setInterfaceLanguage(options: SetInterfaceLanguageOptions) => Promise<void>
```

Set the language for SDK UI elements.

Only available on Android and iOS.

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setinterfacelanguageoptions">SetInterfaceLanguageOptions</a></code> |

**Since:** 8.0.0

--------------------


### createARView(...)

```typescript
createARView(options: CreateARViewOptions) => Promise<void>
```

Create the native AR camera view at specified screen coordinates.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#createarviewoptions">CreateARViewOptions</a></code> |

**Since:** 8.0.0

--------------------


### destroyARView()

```typescript
destroyARView() => Promise<void>
```

Destroy the AR camera view.

Only available on Android and iOS.

**Since:** 8.0.0

--------------------


### resizeARView(...)

```typescript
resizeARView(options: ResizeARViewOptions) => Promise<void>
```

Resize the AR view.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#resizearviewoptions">ResizeARViewOptions</a></code> |

**Since:** 8.0.0

--------------------


### setARViewTouchEnabled(...)

```typescript
setARViewTouchEnabled(options: SetARViewTouchEnabledOptions) => Promise<void>
```

Enable or disable touch event interception on the AR view.

Only available on Android and iOS.

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setarviewtouchenabledoptions">SetARViewTouchEnabledOptions</a></code> |

**Since:** 8.0.0

--------------------


### setARViewTouchHole(...)

```typescript
setARViewTouchHole(options: SetARViewTouchHoleOptions) => Promise<void>
```

Define a rectangular region where touches pass through the AR view to the web layer.

Only available on Android and iOS.

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#setarviewtouchholeoptions">SetARViewTouchHoleOptions</a></code> |

**Since:** 8.0.0

--------------------


### addListener('codeRecognize', ...)

```typescript
addListener(eventName: 'codeRecognize', listenerFunc: (event: CodeRecognizeEvent) => void) => Promise<PluginListenerHandle>
```

Called when a QR code or barcode is scanned by the AR camera.

| Param              | Type                                                                                  |
| ------------------ | ------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'codeRecognize'</code>                                                          |
| **`listenerFunc`** | <code>(event: <a href="#coderecognizeevent">CodeRecognizeEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.0.0

--------------------


### addListener('enterContext', ...)

```typescript
addListener(eventName: 'enterContext', listenerFunc: (event: EnterContextEvent) => void) => Promise<PluginListenerHandle>
```

Called when an AR context is detected/entered.

| Param              | Type                                                                                |
| ------------------ | ----------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'enterContext'</code>                                                         |
| **`listenerFunc`** | <code>(event: <a href="#entercontextevent">EnterContextEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.0.0

--------------------


### addListener('exitContext', ...)

```typescript
addListener(eventName: 'exitContext', listenerFunc: (event: ExitContextEvent) => void) => Promise<PluginListenerHandle>
```

Called when an AR context is lost/exited.

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'exitContext'</code>                                                        |
| **`listenerFunc`** | <code>(event: <a href="#exitcontextevent">ExitContextEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.0.0

--------------------


### addListener('presentAnnotations', ...)

```typescript
addListener(eventName: 'presentAnnotations', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when AR content/annotations become visible on screen.

| Param              | Type                              |
| ------------------ | --------------------------------- |
| **`eventName`**    | <code>'presentAnnotations'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>        |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.0.0

--------------------


### addListener('hideAnnotations', ...)

```typescript
addListener(eventName: 'hideAnnotations', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when AR content/annotations are hidden.

| Param              | Type                           |
| ------------------ | ------------------------------ |
| **`eventName`**    | <code>'hideAnnotations'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>     |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.0.0

--------------------


### addListener('eventFromContent', ...)

```typescript
addListener(eventName: 'eventFromContent', listenerFunc: (event: EventFromContentEvent) => void) => Promise<PluginListenerHandle>
```

Called when AR content dispatches a custom event.

| Param              | Type                                                                                        |
| ------------------ | ------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'eventFromContent'</code>                                                             |
| **`listenerFunc`** | <code>(event: <a href="#eventfromcontentevent">EventFromContentEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.0.0

--------------------


### addListener('syncProgress', ...)

```typescript
addListener(eventName: 'syncProgress', listenerFunc: (event: SyncProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called during synchronization with progress updates.

| Param              | Type                                                                                |
| ------------------ | ----------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'syncProgress'</code>                                                         |
| **`listenerFunc`** | <code>(event: <a href="#syncprogressevent">SyncProgressEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 8.0.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 8.0.0

--------------------


### Interfaces


#### SynchronizeOptions

| Prop       | Type                    | Description                    | Since |
| ---------- | ----------------------- | ------------------------------ | ----- |
| **`tags`** | <code>string[][]</code> | The tags to filter content by. | 8.0.0 |


#### SynchronizeWithToursAndContextsOptions

| Prop             | Type                    | Description                    | Since |
| ---------------- | ----------------------- | ------------------------------ | ----- |
| **`tags`**       | <code>string[][]</code> | The tags to filter content by. | 8.0.0 |
| **`tourIds`**    | <code>number[]</code>   | The tour IDs to sync.          | 8.0.0 |
| **`contextIds`** | <code>string[]</code>   | The context IDs to sync.       | 8.0.0 |


#### UpdateTagMappingOptions

| Prop       | Type                  | Description      | Since |
| ---------- | --------------------- | ---------------- | ----- |
| **`tags`** | <code>string[]</code> | The tags to map. | 8.0.0 |


#### EnableContextsWithTagsOptions

| Prop       | Type                  | Description                      | Since |
| ---------- | --------------------- | -------------------------------- | ----- |
| **`tags`** | <code>string[]</code> | The tags to enable contexts for. | 8.0.0 |


#### GetContextsResult

| Prop           | Type                   | Description                        | Since |
| -------------- | ---------------------- | ---------------------------------- | ----- |
| **`contexts`** | <code>Context[]</code> | The list of synchronized contexts. | 8.0.0 |


#### Context

| Prop                      | Type                        | Description                           | Since |
| ------------------------- | --------------------------- | ------------------------------------- | ----- |
| **`contextId`**           | <code>string</code>         | The unique identifier of the context. | 8.0.0 |
| **`name`**                | <code>string</code>         | The name of the context.              | 8.0.0 |
| **`description`**         | <code>string \| null</code> | The description of the context.       | 8.0.0 |
| **`lastUpdate`**          | <code>string</code>         | The last update timestamp.            | 8.0.0 |
| **`imageThumbnailURL`**   | <code>string \| null</code> | The URL of the thumbnail image.       | 8.0.0 |
| **`imageHiResURL`**       | <code>string \| null</code> | The URL of the high-resolution image. | 8.0.0 |
| **`notificationTitle`**   | <code>string \| null</code> | The notification title.               | 8.0.0 |
| **`notificationMessage`** | <code>string \| null</code> | The notification message.             | 8.0.0 |
| **`tags`**                | <code>string[]</code>       | The tags associated with the context. | 8.0.0 |


#### GetContextResult

| Prop          | Type                                        | Description  | Since |
| ------------- | ------------------------------------------- | ------------ | ----- |
| **`context`** | <code><a href="#context">Context</a></code> | The context. | 8.0.0 |


#### GetContextOptions

| Prop            | Type                | Description                        | Since |
| --------------- | ------------------- | ---------------------------------- | ----- |
| **`contextId`** | <code>string</code> | The ID of the context to retrieve. | 8.0.0 |


#### ActivateContextOptions

| Prop            | Type                | Description                        | Since |
| --------------- | ------------------- | ---------------------------------- | ----- |
| **`contextId`** | <code>string</code> | The ID of the context to activate. | 8.0.0 |


#### GetNearbyGPSPointsResult

| Prop         | Type                    | Description                               | Since |
| ------------ | ----------------------- | ----------------------------------------- | ----- |
| **`points`** | <code>GPSPoint[]</code> | The nearby GPS points sorted by distance. | 8.0.0 |


#### GPSPoint

| Prop                              | Type                        | Description                                       | Since |
| --------------------------------- | --------------------------- | ------------------------------------------------- | ----- |
| **`contextId`**                   | <code>string</code>         | The ID of the associated context.                 | 8.0.0 |
| **`latitude`**                    | <code>number</code>         | The latitude of the GPS point.                    | 8.0.0 |
| **`longitude`**                   | <code>number</code>         | The longitude of the GPS point.                   | 8.0.0 |
| **`detectionRadius`**             | <code>number \| null</code> | The detection radius in meters.                   | 8.0.0 |
| **`distanceFromCurrentPosition`** | <code>number</code>         | The distance from the current position in meters. | 8.0.0 |


#### GetNearbyGPSPointsOptions

| Prop            | Type                | Description                              | Since |
| --------------- | ------------------- | ---------------------------------------- | ----- |
| **`latitude`**  | <code>number</code> | The latitude of the reference location.  | 8.0.0 |
| **`longitude`** | <code>number</code> | The longitude of the reference location. | 8.0.0 |


#### GetGPSPointsInBoundingBoxResult

| Prop         | Type                    | Description                             | Since |
| ------------ | ----------------------- | --------------------------------------- | ----- |
| **`points`** | <code>GPSPoint[]</code> | The GPS points within the bounding box. | 8.0.0 |


#### GetGPSPointsInBoundingBoxOptions

| Prop               | Type                | Description                                | Since |
| ------------------ | ------------------- | ------------------------------------------ | ----- |
| **`minLatitude`**  | <code>number</code> | The minimum latitude of the bounding box.  | 8.0.0 |
| **`minLongitude`** | <code>number</code> | The minimum longitude of the bounding box. | 8.0.0 |
| **`maxLatitude`**  | <code>number</code> | The maximum latitude of the bounding box.  | 8.0.0 |
| **`maxLongitude`** | <code>number</code> | The maximum longitude of the bounding box. | 8.0.0 |


#### GetNearbyBeaconsResult

| Prop           | Type                   | Description                                  | Since |
| -------------- | ---------------------- | -------------------------------------------- | ----- |
| **`contexts`** | <code>Context[]</code> | The contexts associated with nearby beacons. | 8.0.0 |


#### SetInterfaceLanguageOptions

| Prop           | Type                | Description               | Since |
| -------------- | ------------------- | ------------------------- | ----- |
| **`language`** | <code>string</code> | The language code to set. | 8.0.0 |


#### CreateARViewOptions

| Prop         | Type                | Description                    | Since |
| ------------ | ------------------- | ------------------------------ | ----- |
| **`x`**      | <code>number</code> | The x position of the AR view. | 8.0.0 |
| **`y`**      | <code>number</code> | The y position of the AR view. | 8.0.0 |
| **`width`**  | <code>number</code> | The width of the AR view.      | 8.0.0 |
| **`height`** | <code>number</code> | The height of the AR view.     | 8.0.0 |


#### ResizeARViewOptions

| Prop         | Type                | Description                        | Since |
| ------------ | ------------------- | ---------------------------------- | ----- |
| **`x`**      | <code>number</code> | The new x position of the AR view. | 8.0.0 |
| **`y`**      | <code>number</code> | The new y position of the AR view. | 8.0.0 |
| **`width`**  | <code>number</code> | The new width of the AR view.      | 8.0.0 |
| **`height`** | <code>number</code> | The new height of the AR view.     | 8.0.0 |


#### SetARViewTouchEnabledOptions

| Prop          | Type                 | Description                                                | Since |
| ------------- | -------------------- | ---------------------------------------------------------- | ----- |
| **`enabled`** | <code>boolean</code> | Whether touch events should be intercepted by the AR view. | 8.0.0 |


#### SetARViewTouchHoleOptions

| Prop         | Type                | Description                                     | Since |
| ------------ | ------------------- | ----------------------------------------------- | ----- |
| **`top`**    | <code>number</code> | The top coordinate of the touch hole region.    | 8.0.0 |
| **`bottom`** | <code>number</code> | The bottom coordinate of the touch hole region. | 8.0.0 |
| **`left`**   | <code>number</code> | The left coordinate of the touch hole region.   | 8.0.0 |
| **`right`**  | <code>number</code> | The right coordinate of the touch hole region.  | 8.0.0 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### CodeRecognizeEvent

| Prop       | Type                | Description                   | Since |
| ---------- | ------------------- | ----------------------------- | ----- |
| **`code`** | <code>string</code> | The scanned code value.       | 8.0.0 |
| **`type`** | <code>string</code> | The type of the scanned code. | 8.0.0 |


#### EnterContextEvent

| Prop            | Type                | Description                    | Since |
| --------------- | ------------------- | ------------------------------ | ----- |
| **`contextId`** | <code>string</code> | The ID of the entered context. | 8.0.0 |


#### ExitContextEvent

| Prop            | Type                | Description                   | Since |
| --------------- | ------------------- | ----------------------------- | ----- |
| **`contextId`** | <code>string</code> | The ID of the exited context. | 8.0.0 |


#### EventFromContentEvent

| Prop         | Type                | Description                         | Since |
| ------------ | ------------------- | ----------------------------------- | ----- |
| **`name`**   | <code>string</code> | The name of the custom event.       | 8.0.0 |
| **`params`** | <code>string</code> | The parameters of the custom event. | 8.0.0 |


#### SyncProgressEvent

| Prop           | Type                | Description                                  | Since |
| -------------- | ------------------- | -------------------------------------------- | ----- |
| **`progress`** | <code>number</code> | The sync progress value between 0.0 and 1.0. | 8.0.0 |

</docgen-api>
