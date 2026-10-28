# Capacitor Ferrostar Navigation Plugin

Unofficial Capacitor plugin for headless turn-by-turn navigation based on the [Ferrostar](https://github.com/stadiamaps/ferrostar) core.[^1]

<div class="capawesome-z29o10a">
  <a href="https://capawesome.io/" target="_blank">
    <img alt="Try Capawesome Cloud free for 14 days — iOS builds without a Mac" src="https://capawesome.io/assets/banners/cloud-free-trial-capacitor-apps.png" />
  </a>
</div>

## Features

The Capacitor Ferrostar Navigation plugin runs the navigation engine of Ferrostar natively on Android and iOS without any UI or map. You bring the route and the map, the plugin tracks the user along the route and tells you what to show and say. Here are some of the key features:

- 🧭 **Turn-by-turn navigation**: Tracks the user along a route and reports the current instruction, the distance to the next maneuver and the remaining distance and time.
- 🗺️ **Bring your own route**: Works with any routing response in the OSRM format, e.g. from OSRM, Valhalla, GraphHopper, Stadia Maps or Mapbox Directions.
- 🎨 **Bring your own UI**: No map and no UI are included, so you can render the navigation in your own web UI or with a map plugin of your choice.
- 📍 **Snapped location**: Reports the user location snapped to the route, including the course along the route.
- 🔀 **Route deviation**: Tells you when the user leaves the route so that your app can fetch a new route and replace it without stopping the session.
- 🔊 **Voice guidance**: Speaks the instructions with the text-to-speech engine of the device, in the language of your choice, and can be muted at any time.
- 🧪 **Location simulation**: Simulates the user location along the route at any speed, so you can develop and test without leaving your desk.
- 🌙 **Background navigation**: Keeps navigating while the app is in the background, with a persistent notification on Android.
- 📦 **SPM**: Supports Swift Package Manager for iOS.
- 🔁 **Up-to-date**: Always supports the latest Capacitor version.
- ⭐️ **Support**: First-class support from the Capawesome Team.

## Use Cases

The Ferrostar Navigation plugin is typically used for apps that need navigation but want full control over the map and the UI, for example:

- **Delivery and field service apps**: Guide drivers to the next stop with your own branded navigation screen.
- **Outdoor and sports apps**: Navigate along hiking, running or cycling routes with voice guidance.
- **Fleet and logistics apps**: Follow routes computed by your own routing server with custom restrictions.
- **Tourism and city guides**: Lead users from sight to sight with a map plugin and your own instruction cards.

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
 Use the `capacitor-plugins` skill from `capawesome-team/skills` to install the `@capawesome/capacitor-ferrostar-navigation` plugin in my project.
```

If you prefer **Manual Setup**, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capawesome/capacitor-ferrostar-navigation
npx cap sync
```

### Android

#### Minimum SDK version

The Ferrostar core requires Android 7.1 (API level 25) or higher. Set the `minSdkVersion` in the `variables.gradle` file of your app to at least `25`:

```groovy
ext {
    minSdkVersion = 25
}
```

#### Desugaring

The Ferrostar core uses the `java.time` API, which is only available on Android 8.0 (API level 26) and higher. If your `minSdkVersion` is lower than `26`, enable [Java 8+ API desugaring](https://developer.android.com/studio/write/java8-support#library-desugaring) in the `android/app/build.gradle` file of your app:

```groovy
android {
    compileOptions {
        coreLibraryDesugaringEnabled true
    }
}

dependencies {
    coreLibraryDesugaring "com.android.tools:desugar_jdk_libs:2.1.5"
}
```

#### Permissions

The plugin already declares the location, foreground service and notification permissions in its own manifest. No additional permissions are needed, not even for navigation in the background, because the foreground service is started while the app is in the foreground.

#### Variables

If needed, you can define the following project variable in your app's `variables.gradle` file to change the default version of the dependency:

- `$ferrostarVersion` version of `com.stadiamaps.ferrostar:core` (default: `0.59.0`)

This can be useful if you encounter dependency conflicts with other plugins in your project.

### iOS

#### Swift Package Manager

The Ferrostar core is only available via Swift Package Manager. Therefore, this plugin only supports Swift Package Manager and your app must use it as well. See the [Capacitor documentation](https://capacitorjs.com/docs/ios/spm) for how to migrate from CocoaPods.

#### Deployment target

The Ferrostar core requires iOS 16 or higher. Set the deployment target of your app to at least `16.0`.

#### Usage description

Add the `NSLocationWhenInUseUsageDescription` key to the `Info.plist` file of your app to explain why your app needs access to the location:

```xml
<key>NSLocationWhenInUseUsageDescription</key>
<string>The app needs your location to provide turn-by-turn navigation.</string>
```

#### Background mode

If you want to continue the navigation while the app is in the background (see `continueInBackground`), you must also enable the `location` background mode in the `Info.plist` file of your app:

```xml
<key>UIBackgroundModes</key>
<array>
  <string>location</string>
</array>
```

## Usage

The following examples show how to start a navigation session with a route from your routing server, how to react to state changes, how to handle route deviations and how to control the voice guidance.

### Start the navigation

Fetch a route from your routing server in the OSRM format and pass the raw response to the plugin. The plugin uses the first route of the response:

```typescript
import { FerrostarNavigation } from '@capawesome/capacitor-ferrostar-navigation';

const startNavigation = async () => {
  const response = await fetch(
    'https://graphhopper.com/api/1/navigate?point=48.1351,11.582&point=48.1374,11.5755&profile=car&key=YOUR_API_KEY',
  );
  await FerrostarNavigation.startNavigation({
    osrmResponseJson: await response.text(),
    voiceLanguage: 'en-US',
  });
};
```

### Listen for state changes

The plugin emits a `navigationStateChanged` event on every location update with everything you need to render your own navigation UI:

```typescript
import { FerrostarNavigation } from '@capawesome/capacitor-ferrostar-navigation';

const addListeners = async () => {
  await FerrostarNavigation.addListener('navigationStateChanged', event => {
    console.log('Status:', event.status);
    console.log('Instruction:', event.instruction?.text);
    console.log('Next maneuver in:', event.distanceToNextManeuverMeters, 'm');
    console.log('Remaining:', event.distanceRemainingMeters, 'm');
    console.log('Location:', event.location);
  });
  await FerrostarNavigation.addListener('navigationCompleted', () => {
    console.log('You have arrived.');
  });
};
```

### Handle route deviations

The plugin does not fetch routes. When the user leaves the route, fetch a new route from the current location and replace the route of the running session:

```typescript
import { FerrostarNavigation } from '@capawesome/capacitor-ferrostar-navigation';

const addListener = async () => {
  await FerrostarNavigation.addListener('routeDeviationDetected', async event => {
    const { latitude, longitude } = event.location;
    const response = await fetch(
      `https://graphhopper.com/api/1/navigate?point=${latitude},${longitude}&point=48.1374,11.5755&profile=car&key=YOUR_API_KEY`,
    );
    await FerrostarNavigation.replaceRoute({
      osrmResponseJson: await response.text(),
    });
  });
};
```

### Simulate the location

Simulate the user location along the route to test your app without moving:

```typescript
import { FerrostarNavigation } from '@capawesome/capacitor-ferrostar-navigation';

const startSimulatedNavigation = async (osrmResponseJson: string) => {
  await FerrostarNavigation.startNavigation({
    osrmResponseJson,
    simulateLocation: true,
    simulationSpeedMultiplier: 4,
  });
};
```

### Control the voice guidance

Mute and unmute the voice guidance of the running session:

```typescript
import { FerrostarNavigation } from '@capawesome/capacitor-ferrostar-navigation';

const muteVoice = async () => {
  await FerrostarNavigation.muteVoice();
};

const unmuteVoice = async () => {
  await FerrostarNavigation.unmuteVoice();
};
```

### Continue in the background

Keep navigating while the app is in the background. On Android, a persistent notification is shown:

```typescript
import { FerrostarNavigation } from '@capawesome/capacitor-ferrostar-navigation';

const startBackgroundNavigation = async (osrmResponseJson: string) => {
  await FerrostarNavigation.startNavigation({
    osrmResponseJson,
    continueInBackground: true,
    androidNotification: {
      title: 'Navigation',
    },
  });
};
```

### Stop the navigation

```typescript
import { FerrostarNavigation } from '@capawesome/capacitor-ferrostar-navigation';

const stopNavigation = async () => {
  await FerrostarNavigation.stopNavigation();
};
```

## API

<docgen-index>

* [`startNavigation(...)`](#startnavigation)
* [`replaceRoute(...)`](#replaceroute)
* [`stopNavigation()`](#stopnavigation)
* [`muteVoice()`](#mutevoice)
* [`unmuteVoice()`](#unmutevoice)
* [`addListener('navigationStateChanged', ...)`](#addlistenernavigationstatechanged-)
* [`addListener('routeDeviationDetected', ...)`](#addlistenerroutedeviationdetected-)
* [`addListener('navigationCompleted', ...)`](#addlistenernavigationcompleted-)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startNavigation(...)

```typescript
startNavigation(options: StartNavigationOptions) => Promise<void>
```

Start a navigation session for the first route of the given routing response.

A running session is stopped first.
The plugin requests the location permission automatically if the location is not simulated.
On **Android**, it also requests it if `continueInBackground` is `true`, because the foreground service needs it.

Only available on Android and iOS.

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#startnavigationoptions">StartNavigationOptions</a></code> |

**Since:** 0.0.1

--------------------


### replaceRoute(...)

```typescript
replaceRoute(options: ReplaceRouteOptions) => Promise<void>
```

Replace the route of the running navigation session, e.g. after the app fetched a new route
because the user left the route.

All other options of the session stay as they are.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#replacerouteoptions">ReplaceRouteOptions</a></code> |

**Since:** 0.0.1

--------------------


### stopNavigation()

```typescript
stopNavigation() => Promise<void>
```

Stop the running navigation session.

Does nothing if no session is running.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### muteVoice()

```typescript
muteVoice() => Promise<void>
```

Mute the voice guidance of the running navigation session.

The mute state lasts until the session is stopped.
Does nothing if no session is running.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### unmuteVoice()

```typescript
unmuteVoice() => Promise<void>
```

Unmute the voice guidance of the running navigation session.

The mute state lasts until the session is stopped.
Does nothing if no session is running.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### addListener('navigationStateChanged', ...)

```typescript
addListener(eventName: 'navigationStateChanged', listenerFunc: (event: NavigationStateChangedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the navigation state changes, e.g. on every location update.

Only available on Android and iOS.

| Param              | Type                                                                                                    |
| ------------------ | ------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'navigationStateChanged'</code>                                                                   |
| **`listenerFunc`** | <code>(event: <a href="#navigationstatechangedevent">NavigationStateChangedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('routeDeviationDetected', ...)

```typescript
addListener(eventName: 'routeDeviationDetected', listenerFunc: (event: RouteDeviationDetectedEvent) => void) => Promise<PluginListenerHandle>
```

Called when the user leaves the route.

The plugin does not fetch a new route. Fetch one and call `replaceRoute(...)`.
The event is emitted once per deviation and again after the user returned to the route and left it again.

Only available on Android and iOS.

| Param              | Type                                                                                                    |
| ------------------ | ------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'routeDeviationDetected'</code>                                                                   |
| **`listenerFunc`** | <code>(event: <a href="#routedeviationdetectedevent">RouteDeviationDetectedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('navigationCompleted', ...)

```typescript
addListener(eventName: 'navigationCompleted', listenerFunc: () => void) => Promise<PluginListenerHandle>
```

Called when the user arrived at the destination.

Only available on Android and iOS.

| Param              | Type                               |
| ------------------ | ---------------------------------- |
| **`eventName`**    | <code>'navigationCompleted'</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>         |

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


#### StartNavigationOptions

| Prop                                   | Type                                                                | Description                                                                                                                                                                                                                                                                                                                                                                       | Default                                  | Since |
| -------------------------------------- | ------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------- | ----- |
| **`osrmResponseJson`**                 | <code>string</code>                                                 | The routing response as JSON string in the OSRM format, which is also used by Mapbox Directions, the GraphHopper `/navigate` endpoint and Valhalla with `format=osrm`. The first route of the response is used.                                                                                                                                                                   |                                          | 0.0.1 |
| **`polylinePrecision`**                | <code>number</code>                                                 | The precision of the encoded polyline in the `geometry` of the route. Use `6` for Valhalla, Stadia Maps, GraphHopper and Mapbox (`polyline6`) and `5` for the classic OSRM `polyline` format.                                                                                                                                                                                     | <code>6</code>                           | 0.0.1 |
| **`simulateLocation`**                 | <code>boolean</code>                                                | Whether to simulate the location along the route instead of using the device location. Useful for development and testing.                                                                                                                                                                                                                                                        | <code>false</code>                       | 0.0.1 |
| **`simulationSpeedMultiplier`**        | <code>number</code>                                                 | The integer factor by which the simulated location moves faster than in real time. Only used if `simulateLocation` is `true`.                                                                                                                                                                                                                                                     | <code>1</code>                           | 0.0.1 |
| **`waypointAdvanceDistanceMeters`**    | <code>number</code>                                                 | The distance to a waypoint in meters at which the waypoint counts as reached.                                                                                                                                                                                                                                                                                                     | <code>100</code>                         | 0.0.1 |
| **`stepAdvanceEntryDistanceMeters`**   | <code>number</code>                                                 | The distance to the end of the current step in meters that the user must reach before the next step can become the current step.                                                                                                                                                                                                                                                  | <code>30</code>                          | 0.0.1 |
| **`stepAdvanceExitDistanceMeters`**    | <code>number</code>                                                 | The distance in meters that the user must move away from the current step after reaching its end before the next step becomes the current step. This makes sure the user completes the maneuver before the next instruction is shown.                                                                                                                                             | <code>5</code>                           | 0.0.1 |
| **`arrivalStepAdvanceDistanceMeters`** | <code>number</code>                                                 | The distance to the destination in meters at which the user counts as arrived.                                                                                                                                                                                                                                                                                                    | <code>10</code>                          | 0.0.1 |
| **`routeDeviationThresholdMeters`**    | <code>number</code>                                                 | The distance from the route line in meters above which the user counts as off route. Set to `0` to disable route deviation tracking.                                                                                                                                                                                                                                              | <code>50</code>                          | 0.0.1 |
| **`courseFiltering`**                  | <code><a href="#coursefiltering">CourseFiltering</a></code>         | How the course of the snapped location is determined.                                                                                                                                                                                                                                                                                                                             | <code>CourseFiltering.SnapToRoute</code> | 0.0.1 |
| **`speakInstructions`**                | <code>boolean</code>                                                | Whether to speak the instructions with the text-to-speech engine of the device.                                                                                                                                                                                                                                                                                                   | <code>true</code>                        | 0.0.1 |
| **`muteVoice`**                        | <code>boolean</code>                                                | Whether to start the session with muted voice guidance. Only used if `speakInstructions` is `true`.                                                                                                                                                                                                                                                                               | <code>false</code>                       | 0.0.1 |
| **`voiceLanguage`**                    | <code>string</code>                                                 | The BCP 47 language tag of the voice. If not set, the default voice of the device is used. Only used if `speakInstructions` is `true`.                                                                                                                                                                                                                                            |                                          | 0.0.1 |
| **`continueInBackground`**             | <code>boolean</code>                                                | Whether to continue the navigation while the app is in the background. On **Android**, this starts a foreground service with a persistent notification (see `androidNotification`). The session must be started while the app is in the foreground, otherwise the call is rejected. On **iOS**, this requires the `location` background mode (see the installation instructions). | <code>false</code>                       | 0.0.1 |
| **`androidNotification`**              | <code><a href="#androidnotification">AndroidNotification</a></code> | The notification that is shown while the navigation continues in the background. Only used if `continueInBackground` is `true`. Only available on Android.                                                                                                                                                                                                                        |                                          | 0.0.1 |


#### AndroidNotification

| Prop              | Type                | Description                                                                                                                       | Default                   | Since |
| ----------------- | ------------------- | --------------------------------------------------------------------------------------------------------------------------------- | ------------------------- | ----- |
| **`channelName`** | <code>string</code> | The name of the notification channel.                                                                                             | <code>'Navigation'</code> | 0.0.1 |
| **`color`**       | <code>string</code> | The accent color of the notification as hex string.                                                                               |                           | 0.0.1 |
| **`icon`**        | <code>string</code> | The name of the drawable resource to use as the small icon of the notification. If not set, the launcher icon of the app is used. |                           | 0.0.1 |
| **`text`**        | <code>string</code> | The text of the notification. If not set, the text of the current instruction is shown.                                           |                           | 0.0.1 |
| **`title`**       | <code>string</code> | The title of the notification. If not set, the name of the app is shown.                                                          |                           | 0.0.1 |


#### ReplaceRouteOptions

| Prop                    | Type                | Description                                                                                      | Default        | Since |
| ----------------------- | ------------------- | ------------------------------------------------------------------------------------------------ | -------------- | ----- |
| **`osrmResponseJson`**  | <code>string</code> | The routing response as JSON string in the OSRM format. The first route of the response is used. |                | 0.0.1 |
| **`polylinePrecision`** | <code>number</code> | The precision of the encoded polyline in the `geometry` of the route.                            | <code>6</code> | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### NavigationStateChangedEvent

| Prop                               | Type                                                          | Description                                                                                                              | Since |
| ---------------------------------- | ------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------ | ----- |
| **`status`**                       | <code><a href="#navigationstatus">NavigationStatus</a></code> | The status of the navigation session.                                                                                    | 0.0.1 |
| **`instruction`**                  | <code><a href="#instruction">Instruction</a> \| null</code>   | The current instruction. `null` if no session is running or the current step has no instruction.                         | 0.0.1 |
| **`distanceToNextManeuverMeters`** | <code>number \| null</code>                                   | The distance to the next maneuver in meters. `null` if no session is running.                                            | 0.0.1 |
| **`distanceRemainingMeters`**      | <code>number \| null</code>                                   | The remaining distance to the destination in meters. `null` if no session is running.                                    | 0.0.1 |
| **`durationRemainingSeconds`**     | <code>number \| null</code>                                   | The remaining travel time to the destination in seconds. `null` if no session is running.                                | 0.0.1 |
| **`distanceTraveledMeters`**       | <code>number \| null</code>                                   | The distance traveled along the route in meters. `null` if no session is running.                                        | 0.0.1 |
| **`location`**                     | <code><a href="#location">Location</a> \| null</code>         | The location of the user snapped to the route. `null` if no location is known yet.                                       | 0.0.1 |
| **`isOffRoute`**                   | <code>boolean</code>                                          | Whether the user is off route.                                                                                           | 0.0.1 |
| **`spokenInstructionText`**        | <code>string \| null</code>                                   | The text of the instruction that is spoken at the current location. `null` if nothing is spoken at the current location. | 0.0.1 |


#### Instruction

| Prop                   | Type                                                                  | Description                                                    | Since |
| ---------------------- | --------------------------------------------------------------------- | -------------------------------------------------------------- | ----- |
| **`text`**             | <code>string</code>                                                   | The main text of the instruction.                              | 0.0.1 |
| **`secondaryText`**    | <code>string \| null</code>                                           | The secondary text of the instruction, e.g. the next maneuver. | 0.0.1 |
| **`maneuverType`**     | <code><a href="#maneuvertype">ManeuverType</a> \| null</code>         | The type of the maneuver.                                      | 0.0.1 |
| **`maneuverModifier`** | <code><a href="#maneuvermodifier">ManeuverModifier</a> \| null</code> | The direction of the maneuver.                                 | 0.0.1 |


#### Location

| Prop                           | Type                        | Description                                                                                   | Since |
| ------------------------------ | --------------------------- | --------------------------------------------------------------------------------------------- | ----- |
| **`latitude`**                 | <code>number</code>         | The latitude in degrees.                                                                      | 0.0.1 |
| **`longitude`**                | <code>number</code>         | The longitude in degrees.                                                                     | 0.0.1 |
| **`courseDegrees`**            | <code>number \| null</code> | The course over ground in degrees clockwise from true north. `null` if the course is unknown. | 0.0.1 |
| **`horizontalAccuracyMeters`** | <code>number</code>         | The horizontal accuracy in meters.                                                            | 0.0.1 |


#### RouteDeviationDetectedEvent

| Prop                  | Type                                          | Description                                 | Since |
| --------------------- | --------------------------------------------- | ------------------------------------------- | ----- |
| **`deviationMeters`** | <code>number</code>                           | The distance from the route line in meters. | 0.0.1 |
| **`location`**        | <code><a href="#location">Location</a></code> | The raw location of the user.               | 0.0.1 |


### Enums


#### CourseFiltering

| Members           | Value                        | Description                                              | Since |
| ----------------- | ---------------------------- | -------------------------------------------------------- | ----- |
| **`Raw`**         | <code>'RAW'</code>           | Use the raw course as reported by the location provider. | 0.0.1 |
| **`SnapToRoute`** | <code>'SNAP_TO_ROUTE'</code> | Snap the course to the direction of the current step.    | 0.0.1 |


#### NavigationStatus

| Members          | Value                     | Description                          | Since |
| ---------------- | ------------------------- | ------------------------------------ | ----- |
| **`Idle`**       | <code>'IDLE'</code>       | No session is running.               | 0.0.1 |
| **`Navigating`** | <code>'NAVIGATING'</code> | A session is running.                | 0.0.1 |
| **`Complete`**   | <code>'COMPLETE'</code>   | The user arrived at the destination. | 0.0.1 |


#### ManeuverType

| Members              | Value                          | Since |
| -------------------- | ------------------------------ | ----- |
| **`Turn`**           | <code>'TURN'</code>            | 0.0.1 |
| **`NewName`**        | <code>'NEW_NAME'</code>        | 0.0.1 |
| **`Depart`**         | <code>'DEPART'</code>          | 0.0.1 |
| **`Arrive`**         | <code>'ARRIVE'</code>          | 0.0.1 |
| **`Merge`**          | <code>'MERGE'</code>           | 0.0.1 |
| **`OnRamp`**         | <code>'ON_RAMP'</code>         | 0.0.1 |
| **`OffRamp`**        | <code>'OFF_RAMP'</code>        | 0.0.1 |
| **`Fork`**           | <code>'FORK'</code>            | 0.0.1 |
| **`EndOfRoad`**      | <code>'END_OF_ROAD'</code>     | 0.0.1 |
| **`Continue`**       | <code>'CONTINUE'</code>        | 0.0.1 |
| **`Roundabout`**     | <code>'ROUNDABOUT'</code>      | 0.0.1 |
| **`Rotary`**         | <code>'ROTARY'</code>          | 0.0.1 |
| **`RoundaboutTurn`** | <code>'ROUNDABOUT_TURN'</code> | 0.0.1 |
| **`Notification`**   | <code>'NOTIFICATION'</code>    | 0.0.1 |
| **`ExitRoundabout`** | <code>'EXIT_ROUNDABOUT'</code> | 0.0.1 |
| **`ExitRotary`**     | <code>'EXIT_ROTARY'</code>     | 0.0.1 |


#### ManeuverModifier

| Members           | Value                       | Since |
| ----------------- | --------------------------- | ----- |
| **`UTurn`**       | <code>'U_TURN'</code>       | 0.0.1 |
| **`SharpRight`**  | <code>'SHARP_RIGHT'</code>  | 0.0.1 |
| **`Right`**       | <code>'RIGHT'</code>        | 0.0.1 |
| **`SlightRight`** | <code>'SLIGHT_RIGHT'</code> | 0.0.1 |
| **`Straight`**    | <code>'STRAIGHT'</code>     | 0.0.1 |
| **`SlightLeft`**  | <code>'SLIGHT_LEFT'</code>  | 0.0.1 |
| **`Left`**        | <code>'LEFT'</code>         | 0.0.1 |
| **`SharpLeft`**   | <code>'SHARP_LEFT'</code>   | 0.0.1 |

</docgen-api>

## FAQ

### Which routing services can I use?

Any service that returns routes in the OSRM format, for example [OSRM](https://project-osrm.org/), [Valhalla](https://valhalla.github.io/valhalla/) with `format=osrm`, [Stadia Maps](https://stadiamaps.com/), the [GraphHopper Navigation API](https://docs.graphhopper.com/openapi/navigation) and [Mapbox Directions](https://docs.mapbox.com/api/navigation/directions/). Make sure to request the response with `steps` and banner and voice instructions, and set `polylinePrecision` to the precision of the route geometry (`6` for most services, `5` for classic OSRM).

### Does the plugin fetch routes or reroute automatically?

No. The plugin never calls a routing server. Your app fetches the initial route and, after a `routeDeviationDetected` event, a new route from the current location, and passes it to `replaceRoute(...)`. This keeps your API keys in your hands and lets you decide when to reroute.

### Does the plugin render a map?

No. The plugin is headless and does not include any UI. Combine it with a map of your choice, for example the [MapLibre](https://capawesome.io/docs/sdks/capacitor/maplibre/) plugin, and use the `location` from the `navigationStateChanged` event to move the map.

### Why does the plugin not support CocoaPods?

The Ferrostar core is only distributed via Swift Package Manager. Capacitor supports Swift Package Manager since version 6, see the [Capacitor documentation](https://capacitorjs.com/docs/ios/spm).

### Can I use this plugin with Ionic, React, Vue or Angular?

Yes, the plugin is framework-agnostic. It works in any Capacitor app regardless of the web framework, including Ionic with Angular, React, or Vue, as well as plain JavaScript projects.

## Related Plugins

- [Background Geolocation](https://capawesome.io/docs/sdks/capacitor/background-geolocation/): Track the location of the user in the background.
- [MapLibre](https://capawesome.io/docs/sdks/capacitor/maplibre/): Render native MapLibre maps.
- [Geocoder](https://capawesome.io/docs/sdks/capacitor/geocoder/): Convert addresses to coordinates and back.

## Newsletter

Stay up to date with the latest news and updates about the Capawesome, Capacitor, and Ionic ecosystem by subscribing to our [Capawesome Newsletter](https://cloud.capawesome.io/newsletter/).

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/ferrostar-navigation/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-plugins/blob/main/packages/ferrostar-navigation/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Stadia Maps, Inc. or any of their affiliates or subsidiaries.
